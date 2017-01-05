package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CardTypeRepository;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.sawano.java.text.AlphanumericComparator;

import javax.smartcardio.Card;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CardTypeServiceImpl extends BaseServiceImpl<CardType> implements CardTypeService {


    private static Logger log = LoggerFactory.getLogger(CardTypeServiceImpl.class);


    @Autowired
    CardTypeRepository cardTypeRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public CardTypeServiceImpl() {

        super(CardType.class);

    }


    @Override
    protected BaseRepository<CardType,Long> getDao() {
        return cardTypeRepository;
    }

    @Override
    public Page<CardType> findByCrtMerchantNo(Long crtMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<CardType> cardTypeList = cardTypeRepository.findByCrtMerchantNo(crtMerchantNo,pageable);

        // Return the list
        return cardTypeList;

    }

    @Override
    public CardType findByCrtId(Long crtId) {


        // Get the cardType for the given cardType id from the repository
        CardType cardType = cardTypeRepository.findByCrtId(crtId);

        // Return the cardType
        return cardType;


    }

    @Override
    public Page<CardType> findByCrtMerchantNoAndCrtNameLike(Long crtMerchantNo, String crtName,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<CardType> cardTypeList = cardTypeRepository.findByCrtMerchantNoAndCrtNameLike(crtMerchantNo, crtName,pageable);

        // Return the list
        return cardTypeList;

    }

    @Override
    public boolean isDuplicateCardTypeExisting(CardType cardType) {

        // Get the cardType information
        CardType exCardType = cardTypeRepository.findByCrtMerchantNoAndCrtName(cardType.getCrtMerchantNo(), cardType.getCrtName());

        // If the crtId is 0L, then its a new cardType so we just need to check if there is ano
        // ther cardType code
        if ( cardType.getCrtId() == null || cardType.getCrtId() == 0L ) {

            // If the cardType is not null, then return true
            if ( exCardType != null ) {

                return true;

            }

        } else {

            // Check if the cardType is null
            if ( exCardType != null && cardType.getCrtId().longValue() != exCardType.getCrtId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public CardType findByCrtMerchantNoAndCrtName(Long crtMerchantNo, String crtName) {

        // Get the CardType
        CardType cardType = cardTypeRepository.findByCrtMerchantNoAndCrtName(crtMerchantNo,crtName);

        // return the cartType
        return cardType;

    }

    @Override
    public boolean isCardNumberValid(String cardNumber, CardType cardType) {


        //we need to compare alpha numeric values in string compareto operation is based on lexical parser
        //some time there not be return valid result for alpha numeric
        if(cardNumber.equals(cardType.getCrtCardNoRangeTo()) || cardNumber.equals(cardType.getCrtCardNoRangeFrom() )){

            //return true
            return true;
        }

        //range checking alpha numeric values
        List <String> sortingForStartRange =getListItem(cardNumber,cardType.getCrtCardNoRangeFrom());
        sortingForStartRange.sort(new AlphanumericComparator(Locale.ENGLISH));

        //check end range sorting
        List <String> sortingForEndRange =getListItem(cardNumber,cardType.getCrtCardNoRangeTo());
        sortingForEndRange.sort(new AlphanumericComparator(Locale.ENGLISH));

        //card number is in rage then return true otherwise return false
        if(cardNumber.equals(sortingForStartRange.get(1)) && cardNumber.equals(sortingForEndRange.get(0))){

            return true;

        }else {

            return  false;
        }


    }

    @Override
    public Date getExpiryDateForCardType(CardType cardType) {

        // The date of expiry
        java.util.Date expiryDate = new java.util.Date();

        // Check the type of expiry and set the date
        if ( cardType.getCrtExpiryOption() == CardTypeExpiryOption.EXPIRY_DATE ) {

            expiryDate = cardType.getCrtExpiryDate();

        } else if ( cardType.getCrtExpiryOption() == CardTypeExpiryOption.DAYS_AFTER_ISSUANCE || cardType.getCrtExpiryOption() == CardTypeExpiryOption.DAYS_AFTER_TOPUP ) {

            // Get the Calendar instance
            Calendar calendar = Calendar.getInstance();

            // Add the expiry days
            calendar.add(Calendar.DATE,cardType.getCrtExpiryDays());

            // Format and store the date in expiryDate
            expiryDate = calendar.getTime();

        } else {

            // Set the expiry date to the the lagest available date
            expiryDate = DBUtils.covertToSqlDate("9999-12-31");

        }


        // Return the expiry date as java.sql.Date
        return new Date(expiryDate.getTime());

    }

    @Override
    public boolean isCardValueValid(CardType cardType,Double amount,Double currBalance,Integer transactionType) {

        // Check the cardType
        if ( cardType.getCrtType() == CardTypeType.FIXED_VALUE ) {

            // Check if the amount passed is equal to the amount of the fixed value
            // for the card type
            if ( cardType.getCrtFixedValue() == amount ||(transactionType== CardTransactionType.TRANSFER_TO && (amount+currBalance)<= cardType.getCrtFixedValue())) {

                return true;

            }

        } else if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE ) {

            // Check if the amount is greater than topup value and that
            // the amount will not make the card value greater than the
            // allowed maximum value
            if ( amount >= cardType.getCrtMinTopupValue() && (amount + currBalance)  <= ( cardType.getCrtMaxValue() ) ) {

                return true;
            }

        }


        // If nothing matches, return false
        return false;

    }

    @Override
    public CardType validateAndSaveCardType(CardType cardType) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_ADD_CARD_TYPES);

        //check the card type is range is valid or not -Because now no need of this card range because we removed option to specify cardRange
        //checkCardNumberRangeValid(cardType);


        return saveCardType(cardType);

    }

    public void checkCardNumberRangeValid(CardType cardType) throws InspireNetzException {

        //check card number range
        if(cardType.getCrtCardNoRangeFrom().length() !=cardType.getCrtCardNoRangeTo().length()){

            //throw inspirenetz exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD_NUMBER_RANGE);
        }

        //check card number range is used any other card
        List<CardType> cardTypes=findByCrtMerchantNo(cardType.getCrtMerchantNo());

        for (CardType cardType1:cardTypes){

            if( cardType.getCrtId() ==null || (cardType1.getCrtId() !=null && cardType.getCrtId().longValue() !=cardType1.getCrtId().longValue())){

                boolean duplicateRangeFrom =checkDuplicateRange(cardType1.getCrtCardNoRangeFrom(),cardType1.getCrtCardNoRangeTo(),cardType.getCrtCardNoRangeFrom(),cardType.getCrtCardNoRangeTo());

                if(duplicateRangeFrom){

                    throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_CARD_RANGE);
                }

            }

        }
    }

    public boolean checkDuplicateRange(String crtCardNoRangeFrom, String crtCardNoRangeTo, String startRange,String endRange) {


        //compareTo  string range if its equal then return false
        if(startRange.equals(crtCardNoRangeFrom) || startRange.equals(crtCardNoRangeTo)){

            return true;

        }else if (endRange.equals(crtCardNoRangeTo)){

            return  true;
        }else {

            if(crtCardNoRangeFrom==null|| crtCardNoRangeTo==null){

                return false;
            }
            //sort the content why because the alpha numeric string comparisson is lexical parser
            //we are not get exact information
            List <String> sortingForStartRange =getListItem(startRange,crtCardNoRangeTo);
            sortingForStartRange.sort(new AlphanumericComparator(Locale.ENGLISH));

            //check end range sorting
            List <String> sortingForEndRange =getListItem(endRange,crtCardNoRangeFrom);
            sortingForEndRange.sort(new AlphanumericComparator(Locale.ENGLISH));

            //alpha numeric comparisson
            String startRangeLessEndRange =sortingForStartRange.get(0);
            String endRangeLess =sortingForEndRange.get(0);
            String sortingRange2=sortingForStartRange.get(1);
            //range is below the existing range
            if(startRangeLessEndRange.equals(startRange) && endRangeLess.equals(endRange)){

                //return false
                return false;

            }else if(sortingRange2.equals(startRange)){

                //return false
                return false;
            }

        }

        return true;
    }

    private List getListItem(String startRange, String crtCardNoRangeTo) {

        List<String> sortList =new ArrayList();

        sortList.add(startRange);

        sortList.add(crtCardNoRangeTo);

        return sortList;
    }


    @Override
    public CardType saveCardType(CardType cardType ) throws InspireNetzException {

        // Check if the cardType is existing
        boolean isExist = isDuplicateCardTypeExisting(cardType);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCardType - Response : CardType name is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // If the cardType.getCrtId is  null, then set the created_by, else set the updated_by
        if ( cardType.getCrtId() == null ) {

            cardType.setCreatedBy(auditDetails);

        } else {

            cardType.setUpdatedBy(auditDetails);

        }

        // Save the cardType
        return cardTypeRepository.save(cardType);

    }

    @Override
    public boolean validateAndDeleteCardType(Long crtId , Long merchantNo) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_CARD_TYPE);

        return deleteCardType(crtId, merchantNo);

    }

    @Override
    public boolean deleteCardType(Long crtId,Long merchantNo) throws InspireNetzException {

        // Get the cardType information
        CardType cardType = findByCrtId(crtId);

        // Check if the cardType is found
        if ( cardType == null || cardType.getCrtId() == null) {

            // Log the response
            log.info("deleteCardType - Response : No cardType information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( cardType.getCrtMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCardType - Response : You are not authorized to delete the cardType");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        // Delete the cardType
        cardTypeRepository.delete(crtId);

        // return true
        return true;

    }

    @Override
    public Page<CardType> searchCardTypes(String filter, String query, Long merchantNo,Pageable pageable) {

        Page<CardType> cardTypePage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            cardTypePage = findByCrtMerchantNo(merchantNo,pageable);

        } else if ( filter.equalsIgnoreCase("name") ) {

            cardTypePage = findByCrtMerchantNoAndCrtNameLike(merchantNo, "%" + query + "%", pageable);

        }

        //return the result
        return cardTypePage;
    }

    @Override
    public CardType getCardTypeInfo(Long crtId,Long merchantNo) throws InspireNetzException {

        CardType cardType = findByCrtId(crtId);

        // Check if the cardType is found
        if ( cardType == null || cardType.getCrtId() == null) {

            // Log the response
            log.info("getCardTypeInfo - Response : No cardType information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( cardType.getCrtMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCardTypeInfo - Response : You are not authorized to view the cardType");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        return cardType;
    }

    @Override
    public CardType save(CardType cardType) {

        return cardTypeRepository.save(cardType);
    }

    @Override
    public List<CardType> findByCrtMerchantNo(Long crtMerchantNo) {
        return cardTypeRepository.findByCrtMerchantNo(crtMerchantNo);
    }

}
