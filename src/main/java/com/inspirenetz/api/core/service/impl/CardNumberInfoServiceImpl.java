package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CardNumberInfoRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ameen on 20/10/15.
 */
@Service
public class CardNumberInfoServiceImpl extends BaseServiceImpl<CardNumberInfo> implements CardNumberInfoService {

    private static Logger log = LoggerFactory.getLogger(CardNumberInfoServiceImpl.class);

    @Autowired
    private CardNumberInfoRepository cardNumberInfoRepository;

    @Autowired
    private CardNumberBatchInfoService cardNumberBatchInfoService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    // Queue holding the available card numbers
    private ConcurrentHashMap<String,LinkedList<CardNumberInfo>> cardNumberList = new ConcurrentHashMap<>(0);

    @Value("${cardnumber.prepopulate.queue.size}")
    private int prePopulateSize;

    public CardNumberInfoServiceImpl() {

        super(CardNumberInfo.class);

    }

    @Override
    protected BaseRepository<CardNumberInfo,Long> getDao() {
        return cardNumberInfoRepository;
    }

    @Override
    public Page<CardNumberInfo> findByCniMerchantNo(Long cniMerchantNo, Pageable pageable) {
        return cardNumberInfoRepository.findByCniMerchantNo(cniMerchantNo,pageable);
    }

    @Override
    public CardNumberInfo findByCniId(Long cniId) {
        return cardNumberInfoRepository.findByCniId(cniId);
    }

    @Override
    public CardNumberInfo findByCniMerchantNoAndCniCardNumber(Long cniMerchantNo, String cniCardNumber) {
        return cardNumberInfoRepository.findByCniMerchantNoAndCniCardNumber(cniMerchantNo,cniCardNumber);
    }

    @Override
    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(Long cniMerchantNo, Long cniBatchId, String cniCardNumber, Pageable pageable) {
        return cardNumberInfoRepository.findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(cniMerchantNo, cniBatchId, cniCardNumber, pageable);
    }



    @Override
    @Async
    public void processBatchFile(CardNumberInfoRequest cardNumberInfoRequest) throws InspireNetzException {

        //when processing information first update the status batch info table
        CardNumberBatchInfo cardNumberBatchInfo = getCardNumberBatchInfo(cardNumberInfoRequest.getCniBatchName() ==null?"":cardNumberInfoRequest.getCniBatchName(),cardNumberInfoRequest.getCniMerchantNo());

        //set the status
        cardNumberBatchInfo.setCnbProcessStatus(BulkUploadBatchInfoStatus.PROCESSING);

        //set card the card type
        cardNumberBatchInfo.setCnbCardType(cardNumberInfoRequest.getCniCardType());

        //update status
        cardNumberBatchInfo =cardNumberBatchInfoService.validateAnSaveBatchInfoInformation(cardNumberBatchInfo);

        try{


            //get the file upload path
            String uploadRoot = fileUploadService.getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

            //get the complete file path
            String filePath = uploadRoot + cardNumberInfoRequest.getFilePath();

            //parse the file and process  card number info request
            processFile(filePath,cardNumberInfoRequest.getCniCardType()==null?0L:cardNumberInfoRequest.getCniCardType(),cardNumberInfoRequest.getCniMerchantNo(),cardNumberBatchInfo);


        }finally {

            cardNumberBatchInfo.setCnbProcessStatus(BulkUploadBatchInfoStatus.COMPLETED);

            cardNumberBatchInfoService.saveBatchInfoInformation(cardNumberBatchInfo);
        }


    }

    @Override
    public CardNumberInfo saveCardNumberInfo(CardNumberInfo cardNumberInfo) {
        return cardNumberInfoRepository.save(cardNumberInfo);
    }

    @Override
    public void processFile(String fileName, Long cniCardType, Long cniMerchantNo, CardNumberBatchInfo cardNumberBatchInfo) {

        //read file from specified file path
        try{

            //check file is exist in directory
            Path path = Paths.get(fileName);

            if(!Files.exists(path)){

                throw new InspireNetzException(APIErrorCode.ERR_NOT_FOUND);
            }


            //create buffer reader with buffer size is 102400 default buffer only 8kb
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            //declare string
            String cardNumberFile;



            //read the file and process informations
            while ((cardNumberFile = br.readLine()) != null) {

                //String card number
                String cardNumber ="";

                String cardPin ="0";

                try{

                    //skip reading if the data is empty
                    if(cardNumberFile.equals("")){

                        continue;
                    }

                    //split the string
                    String [] content =cardNumberFile.split(",");

                    //check the content size
                    if(content.length <1){

                        continue;
                    }


                    try {


                        if(content[0] ==null || content[0].equals("")){

                            log.info("CardNumber is null possible pls check:"+cardNumberFile);

                            continue;
                        }

                        //set carnumber
                        cardNumber =content[0];

                        if(content.length>1){

                            cardPin = content[1];

                        }


                    }catch (Exception e){

                        log.info("parsing exception");
                    }

                    //check the card number duplicate or already issue then skip that number
                    boolean isDuplicateCardNumber = isDuplicateCardNumber(cardNumber==null?"":content[0]);

                    if(isDuplicateCardNumber){

                        log.info("Duplicate Card Number Exist pls check card Number:"+cardNumberFile);

                        continue;
                    }

                    //check the card number is 16 0r not
                    if(cardNumber.length() !=16){

                        log.info("Card Number is not 16 digit  pls check data:"+cardNumberFile);

                        continue;
                    }

                    //check card number pin is zero or not
                    if(cardPin !=null &&!cardPin.equals("0")){

                        //check the length of pin
                        if(cardPin.length() !=4){

                            log.info("Pin Number  is not 4 digit pls check data:"+cardNumberFile);

                            continue;
                        }

                    }

                    //create new object
                    CardNumberInfo cardNumberInfo =new CardNumberInfo();
                    cardNumberInfo.setCniBatchId(cardNumberBatchInfo.getCnbId());
                    cardNumberInfo.setCniCardType(cniCardType);
                    cardNumberInfo.setCniCardNumber(cardNumber);
                    cardNumberInfo.setCniMerchantNo(cniMerchantNo);
                    cardNumberInfo.setCniPin(cardPin);
                    //status one is processed information
                    cardNumberInfo.setCniCardStatus(IndicatorStatus.NO);

                    //save the card master information
                    saveCardNumberInfo(cardNumberInfo);

                }catch (Exception e){

                    log.info("Exception for Reading individual row"+e.toString());
                }



            }

            //close buffer
            br.close();

        }catch (IOException e){

            log.info("Exception in file reading");

            e.printStackTrace();
        }catch (Exception e){

            log.info("Exception in file reading");

            e.printStackTrace();
        }



    }

    @Override
    public Page<CardNumberInfo> searchCardNumberInfo(Long cniMerchantNo,Long cniBatchId,String filter, String query, Pageable pageable) {


        Page<CardNumberInfo> cardNumberInfoPage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            cardNumberInfoPage =findByCniMerchantNoAndCniBatchId(cniMerchantNo,cniBatchId,pageable);


        } else if ( filter.equalsIgnoreCase("cardNumber") ) {

            cardNumberInfoPage = findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(cniMerchantNo, cniBatchId, "%" + query + "%", pageable);

        }

        return cardNumberInfoPage;
    }

    @Override
    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchId(Long cniMerchantNo, Long cniBatchId, Pageable pageable) {
        return cardNumberInfoRepository.findByCniMerchantNoAndCniBatchId(cniMerchantNo,cniBatchId,pageable);
    }

    @Override
    public void delete(CardNumberInfo cardNumberInfo) {
        cardNumberInfoRepository.delete(cardNumberInfo);
    }

    @Override
    public CardNumberInfo findByCniCardNumber(String cniCardNumber) {
        return cardNumberInfoRepository.findByCniCardNumber(cniCardNumber);
    }


    private boolean isDuplicateCardNumber(String cardNumber) {
        
        //check the duplicate card number for card number info table 
        boolean isDuplicateCardNo = isDuplicateNumber(cardNumber);

        if(isDuplicateCardNo){

            return true;
        }

        //check the card number exist in  card master table
        boolean isExistMaster = isExistCardMaster(cardNumber);

        if(isExistMaster){

            return true;
        }

        return false;

    }

    private boolean isExistCardMaster(String cardNumber) {

        //find out card master
        CardMaster cardMaster =cardMasterService.findByCrmCardNo(cardNumber);

        //check the card number exist card number
        if(cardMaster !=null ){

            return true;
        }

        return false;
    }

    private boolean isDuplicateNumber(String cardNumber) {

        // Get the cardNumberInfo information
        CardNumberInfo exCardNumberInfo = cardNumberInfoRepository.findByCniCardNumber(cardNumber);

        // If the cniId is 0L, then its a new cardNumberInfo so we just need to check if there is ano
        // then cardNumberInfo code
        if(exCardNumberInfo !=null && exCardNumberInfo.getCniId().longValue() !=0L){

            return true;
        }

        // Return false;
        return false;
    }

    @Override
    public CardNumberInfo isValidCardNumber(CardNumberInfo cardNumberInfo,boolean isActivationPinValidate){

        // Get the cardNumberInfo information
        CardNumberInfo exCardNumberInfo =null;

        if(cardNumberInfo==null ||(cardNumberInfo.getCniCardNumber()==null||cardNumberInfo.getCniCardNumber().equals(""))){

            return null;
        }

        if(cardNumberInfo.getCniMerchantNo()==null ||cardNumberInfo.getCniMerchantNo().longValue()==0){

            exCardNumberInfo=cardNumberInfoRepository.findByCniCardNumberAndCniCardStatus(cardNumberInfo.getCniCardNumber(), IndicatorStatus.NO);

        }else{

            exCardNumberInfo=cardNumberInfoRepository.findByCniMerchantNoAndCniCardNumberAndCniCardStatus(cardNumberInfo.getCniMerchantNo(),cardNumberInfo.getCniCardNumber(),IndicatorStatus.NO);
        }


        // If the cniId is 0L, then its a new cardNumberInfo so we just need to check if there is ano
        // then cardNumberInfo code
        if(exCardNumberInfo ==null || exCardNumberInfo.getCniId().longValue() ==0L){

            return null;
        }

        if(isActivationPinValidate){

            if(exCardNumberInfo.getCniPin()!=null&& !exCardNumberInfo.getCniPin().equals("0")){

                if(!exCardNumberInfo.getCniPin().equals(cardNumberInfo.getCniPin())){

                    return null;
                }
            }
        }

        CardMaster cardMaster=createCardMasterObject(exCardNumberInfo.getCniCardNumber(),exCardNumberInfo.getCniMerchantNo(),exCardNumberInfo.getCniCardType());

        boolean isDuplicateCardExist=cardMasterService.isDuplicateCardExisting(cardMaster);

        if(isDuplicateCardExist){

            return null;
        }

        // Return false;
        return exCardNumberInfo;
    }

    @Override
    public CardNumberInfo isCardNumberValid(CardNumberInfo cardNumberInfo){

        // Get the cardNumberInfo information
        CardNumberInfo exCardNumberInfo =null;

        if(cardNumberInfo==null ||(cardNumberInfo.getCniCardNumber()==null||cardNumberInfo.getCniCardNumber().equals(""))){

            return null;
        }

        if(cardNumberInfo.getCniMerchantNo()==null ||cardNumberInfo.getCniMerchantNo().longValue()==0){

            exCardNumberInfo=cardNumberInfoRepository.findByCniCardNumberAndCniCardStatus(cardNumberInfo.getCniCardNumber(), IndicatorStatus.NO);

        }else{

            exCardNumberInfo=cardNumberInfoRepository.findByCniMerchantNoAndCniCardNumberAndCniCardStatus(cardNumberInfo.getCniMerchantNo(),cardNumberInfo.getCniCardNumber(),IndicatorStatus.NO);
        }



        // If the cniId is 0L, then its a new cardNumberInfo so we just need to check if there is ano
        // then cardNumberInfo code
        if(exCardNumberInfo ==null || exCardNumberInfo.getCniId().longValue() ==0L){

            return null;
        }

        if(exCardNumberInfo.getCniPin()!=null&& !exCardNumberInfo.getCniPin().equals("0")){

            if(!exCardNumberInfo.getCniPin().equals(cardNumberInfo.getCniPin())){

                return null;
            }
        }


        // Return false;
        return exCardNumberInfo;
    }

    @Override
    public CardNumberInfo createCardNumberInfoObject(String cardNumber,Long merchantNo,Long cardType,String pin){
        CardNumberInfo cardNumberInfo =new CardNumberInfo();
        cardNumberInfo.setCniCardNumber(cardNumber);
        cardNumberInfo.setCniMerchantNo(merchantNo);
        cardNumberInfo.setCniCardType(cardType);
        cardNumberInfo.setCniPin(pin);

        return cardNumberInfo;
    }

    private CardMaster createCardMasterObject(String cardNumber,Long merchantNo,Long cardType){
        CardMaster cardMaster =new CardMaster();
        cardMaster.setCrmCardNo(cardNumber);
        cardMaster.setCrmMerchantNo(merchantNo);
        cardMaster.setCrmType(cardType);

        return cardMaster;
    }

    private CardNumberBatchInfo getCardNumberBatchInfo(String cniBatchName, Long cniMerchantNo) {

        //create method for batch information
        CardNumberBatchInfo cardNumberBatchInfo =new CardNumberBatchInfo();
        cardNumberBatchInfo.setCnbMerchantNo(cniMerchantNo);
        cardNumberBatchInfo.setCnbName(cniBatchName);
        cardNumberBatchInfo.setCnbDate(new java.sql.Date(new Date().getTime()));
        cardNumberBatchInfo.setCnbTime(new Time(new java.util.Date().getTime()));

        return cardNumberBatchInfo;
    }

    @Override
    public CardNumberInfo getValidatedCardDetails(String cniCardNumber,String pin)throws InspireNetzException{

        //get merchant number from session
        Long merchantNo=authSessionUtils.getMerchantNo();

        //create cardNumberInfo object to check is valid carNumber or not
        CardNumberInfo cardNumberInfo=createCardNumberInfoObject(cniCardNumber,merchantNo,0L,pin);

        CardNumberInfo validatedCardNumberInfo=isValidCardNumber(cardNumberInfo,false);

        if(validatedCardNumberInfo!=null && validatedCardNumberInfo.getCniId()!=null && validatedCardNumberInfo.getCniId().longValue()!=0){

            return validatedCardNumberInfo;

        }

        return null;
    }

    @Override
    public CardNumberInfo getValidatedCardDetailsForPublic(String cniCardNumber,String mobile,String pin,Long merchantNo)throws InspireNetzException{


        //create cardNumberInfo object to check is valid carNumber or not
        CardNumberInfo cardNumberInfo=createCardNumberInfoObject(cniCardNumber,merchantNo,0L,pin);

        CardNumberInfo validatedCardNumberInfo=isValidCardNumber(cardNumberInfo,true);

        if(validatedCardNumberInfo==null){

            log.info("getValidatedCardDetailsForPublic : Card Number validation failed");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        if(merchantNo==0){

            merchantNo=generalUtils.getDefaultMerchantNo();
        }

        boolean isOTPGenerated=oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.PUBLIC,mobile,OTPType.PUBLIC_CARD_REGISTER);

        if(isOTPGenerated){

            return validatedCardNumberInfo;
        }

        return null;
    }


    /**
     * Method to get the next available card number for the given merchant no and the card type
     * This method is tagged as synchronized so that only one thread can execute the method any given time to
     * avoid multiple threads taking the same  number at the same time.
     *
     * @param cniMerchantNo : The merchant number of the merchant requestiing the card
     * @param cniCardType : The card type id of the card being requested
     *
     * @return            : null if a card number is not available
     *                      CardNumberInfo object if a valid card is availble
     */
    @Override
    public synchronized CardNumberInfo getAvailableCardNumber(Long cniMerchantNo,Long cniCardType){

        // Get the key
        String key = getCardNumberMapKey(cniMerchantNo,cniCardType);

        // Check if the entry is existing
        LinkedList<CardNumberInfo> cardNumberInfoList = cardNumberList.get(key);

        // Check if the value is available
        if ( cardNumberInfoList == null || cardNumberInfoList.size() == 0 ) {

            // Get the list from the db
            List<CardNumberInfo> cardNumberInfoDbList = getNextAvailableCardNumbers(cniMerchantNo,cniCardType,prePopulateSize);

            // If the list from db is null, return null
            if ( cardNumberInfoDbList == null ) {

                return  null;

            }

            // Create the linked list from the dblist
            cardNumberInfoList = Lists.newLinkedList(cardNumberInfoDbList);

            // Set the data for the key
            cardNumberList.put(key,cardNumberInfoList);

        }

        // Check if the list is empty
        if ( cardNumberInfoList == null || cardNumberInfoList.size() == 0) {

            return null;

        }

        // Get the next item
        // We are calling the remove so that the item is removed from the list and returned
        CardNumberInfo cardNumberInfo = cardNumberInfoList.remove(0);

        // Set the list back
        // Set the data for the key
        cardNumberList.put(key,cardNumberInfoList);

        // Return the cardNumber
        return cardNumberInfo;

    }


    /**
     * Method to create the card numberm map key from the fields passed
     *
     * @param cniMerchantNo : The merchant number of the merchant
     * @param cniCardType   : The card type of the card
     *
     * @return      : The key build from the fields;
     */
    public String getCardNumberMapKey(Long cniMerchantNo,Long cniCardType) {

        // Return key as concatenated merchantno and card type value
        return cniMerchantNo + "#"+ cniCardType;

    }


    /**
     * Method to get the next available card numbers from the database
     * This method will return @param limit number of items from the database in the form of list
     *
     * @param cniMerchantNo     : The merchant number of the merchant
     * @param cniCardType       : The cniCardType
     * @param limit             : The limit
     * @return
     */
    public List<CardNumberInfo> getNextAvailableCardNumbers(Long cniMerchantNo,Long cniCardType, int limit) {

        // Get the next limit card numbers
        Page<CardNumberInfo> cardNumberInfos=cardNumberInfoRepository.findByCniMerchantNoAndCniCardTypeAndCniCardStatus(cniMerchantNo,cniCardType,IndicatorStatus.NO,new PageRequest(0,limit));

        // Return the list
        return cardNumberInfos.getContent();

    }

}
