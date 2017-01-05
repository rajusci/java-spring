package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.FileSavingLimit;
import com.inspirenetz.api.core.dictionary.VoucherCodeFileReadingStatus;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.VoucherCode;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.VoucherCodeRepository;
import com.inspirenetz.api.core.service.RedemptionVoucherSourceService;
import com.inspirenetz.api.core.service.VoucherCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alameen on 10/2/15.
 */
@Component
public class VoucherCodeServiceImpl extends BaseServiceImpl<VoucherCode> implements VoucherCodeService {

    private static Logger log = LoggerFactory.getLogger(VoucherCodeServiceImpl.class);

    @Autowired
    private VoucherCodeRepository voucherCodeRepository;

    @Autowired
    private RedemptionVoucherSourceService redemptionVoucherSourceService;

    public VoucherCodeServiceImpl() {

        super(VoucherCode.class);

    }

    @Override
    protected BaseRepository<VoucherCode, Long> getDao() {
        return voucherCodeRepository;
    }

    @Override
    public String getVoucherCode(RedemptionVoucherSource redemptionVoucherSource) {

        Long index = redemptionVoucherSource.getRvsIndex()+1;

        //find voucher code based on voucher source and index
        VoucherCode voucherCode = voucherCodeRepository.findByVocMerchantNoAndVocVoucherSourceAndVocIndex(redemptionVoucherSource.getRvsMerchantNo(),redemptionVoucherSource.getRvsId(),index);

        //set new index
        redemptionVoucherSource.setRvsIndex(index);

        //save the redemptionVoucherSource
        redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);

        //check voucher code is null
        if(voucherCode ==null){

            return null;
        }

        return voucherCode.getVocVoucherCode();
    }

    @Override
    public void delete(VoucherCode voucherCode) {

        voucherCodeRepository.delete(voucherCode);
    }

    @Override
    public VoucherCode save(VoucherCode voucherCode) {

        return voucherCodeRepository.save(voucherCode);
    }

    @Override
    @Async
    public void processBatchFile(String filePath, RedemptionVoucherSource redemptionVoucherSource){

        fileProcessing(filePath, redemptionVoucherSource);

    }

    public void fileProcessing(String filePath, RedemptionVoucherSource redemptionVoucherSource) {


        //read file from specified file path
        List voucherCodeBatch =new ArrayList(0);

        //create subList
        List<VoucherCode> subList= new ArrayList<>(0);

        //read file
        voucherCodeBatch=readFile(filePath,redemptionVoucherSource);

        //check batch file is null or not
        if(voucherCodeBatch.size() ==0){

            log.info("Voucher Code Service->readFile:File is not Found with specified location :"+filePath);

            redemptionVoucherSourceService.updateVoucherSourceStatus(redemptionVoucherSource, VoucherCodeFileReadingStatus.FAILED);

            return;

        }

        //initialize sub list
        Integer startIndex=0;

        //processing for
        Integer endIndex;

        boolean saveData;

        //check file data is smaller or equal to   splitting size then save directly other wise split
        if(voucherCodeBatch.size() <=FileSavingLimit.FILE_SAVING_LIMIT ||FileSavingLimit.FILE_SAVING_LIMIT==0){

            //saving subList
            saveData =saveData(voucherCodeBatch);

            //check save data status
            if(!saveData){

                //update status
                redemptionVoucherSourceService.updateVoucherSourceStatus(redemptionVoucherSource, VoucherCodeFileReadingStatus.FAILED);

                return;
            }
        }else {

            do{

                //adding end index for limit calculations
                Integer endIndexCal =startIndex+FileSavingLimit.FILE_SAVING_LIMIT ;

                //check index out of range
                endIndex = endIndexCal < voucherCodeBatch.size()?endIndexCal:voucherCodeBatch.size();

                //taking subList for saving purpose
                subList =voucherCodeBatch.subList(startIndex,endIndex);

                startIndex =endIndex;

                //saving subList
                saveData =saveData(subList);

                //check save data status
                if(!saveData){

                    //update status
                    redemptionVoucherSourceService.updateVoucherSourceStatus(redemptionVoucherSource, VoucherCodeFileReadingStatus.FAILED);

                    return;
                }


            }while (endIndex <(voucherCodeBatch.size()));
        }


        //updating status if success
        redemptionVoucherSourceService.updateVoucherSourceStatus(redemptionVoucherSource, VoucherCodeFileReadingStatus.SUCCESS);

    }

    private boolean saveData(List voucherCodeBatch) {

        //save the batch data
        List saveList=saveAll(voucherCodeBatch);

        //check save list is null or not if null return false
        if(saveList ==null){

            return false;
        }

        return true;

    }

    /**
     * @purpose:read data from file
     * @date:12-02-0215
     * @param file
     * @param redemptionVoucherSource
     * @return voucherCodes
     */
    private List readFile(String file,RedemptionVoucherSource redemptionVoucherSource)  {

        //initialize list
        List<VoucherCode> voucherCodes=new ArrayList<>(0);

        //declare saving Limit
        Integer count=0;

        try{

            //check file is exist in directory
            Path path = Paths.get(file);

            if(!Files.exists(path)){

                return voucherCodes;
            }



            //create buffer reader with buffer size is 102400 default buffer only 8kb
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            //declare string
            String voucherCodeFile;

            //set voc index is zero for incrementing each row
            Long vocIndex =redemptionVoucherSource.getRvsIndex()==null?0L:redemptionVoucherSource.getRvsIndex();

            while ((voucherCodeFile = br.readLine()) != null) {

                //skip reading if the data is empty
                if(voucherCodeFile.equals("")){

                    continue;
                }

                //create new object
                VoucherCode voucherCode1 =new VoucherCode();

                //increment vocIndex by 1
                vocIndex = vocIndex+1;

                //set voucher code and other information
                voucherCode1.setVocVoucherCode(voucherCodeFile);

                voucherCode1.setVocIndex(vocIndex);

                voucherCode1.setVocMerchantNo(redemptionVoucherSource.getRvsMerchantNo());

                voucherCode1.setVocVoucherSource(redemptionVoucherSource.getRvsId());

                //add object into list
                voucherCodes.add(voucherCode1);


            }

            //close buffer
            br.close();

            }catch (IOException e){

            log.info("Exception in file reading");

            e.printStackTrace();
        }

        return voucherCodes;
    }
}
