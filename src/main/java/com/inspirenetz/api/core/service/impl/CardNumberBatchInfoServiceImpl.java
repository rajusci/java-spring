package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CardNumberBatchInfoRepository;
import com.inspirenetz.api.core.service.CardNumberBatchInfoService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * Created by ameen on 20/10/15.
 */
@Service
public class CardNumberBatchInfoServiceImpl extends BaseServiceImpl<CardNumberBatchInfo> implements CardNumberBatchInfoService {

    private static Logger log = LoggerFactory.getLogger(CardNumberInfoServiceImpl.class);

    @Autowired
    private CardNumberBatchInfoRepository cardNumberBatchInfoRepository;

    public CardNumberBatchInfoServiceImpl() {

        super(CardNumberBatchInfo.class);

    }

    @Override
    protected BaseRepository<CardNumberBatchInfo,Long> getDao() {
        return cardNumberBatchInfoRepository;
    }

    @Override
    public CardNumberBatchInfo saveBatchInfoInformation(CardNumberBatchInfo cardNumberBatchInfo) {
        return cardNumberBatchInfoRepository.save(cardNumberBatchInfo);
    }

    @Override
    public Page<CardNumberBatchInfo> searchCardNumberBatchInfo(Long cnbMerchantNo, String filter,String query, Date startDate, Date endDate, Pageable pageable) {
        
        Page<CardNumberBatchInfo> cardNumberBatchInfoPage =null;

        //check the name is null or not
        if(filter.equals("0") && query.equals("0")){
            
            //get the batch info based on merchant number
            cardNumberBatchInfoPage = findByCnbMerchantNoAndCnbDateBetween(cnbMerchantNo, startDate, endDate, pageable);
            
        }else if(filter.equalsIgnoreCase("name")){
            
            //apply name filter name condition 
            cardNumberBatchInfoPage =findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(cnbMerchantNo,startDate,endDate,"%"+query+"%",pageable);
        }
        
        
        return cardNumberBatchInfoPage;
    }

    @Override
    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(Long cnbMerchantNo, Date fromDate, Date toDate, String cnbName, Pageable pageable) {
        return cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbDateBetweenAndCnbNameLike(cnbMerchantNo,fromDate,toDate,cnbName,pageable);
    }

    @Override
    public Page<CardNumberBatchInfo> findByCnbMerchantNoAndCnbDateBetween(Long cnbMerchantNo, Date fromDate, Date toDate, Pageable pageable) {
        return cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbDateBetween(cnbMerchantNo,fromDate,toDate,pageable);
    }

    @Override
    public CardNumberBatchInfo validateAnSaveBatchInfoInformation(CardNumberBatchInfo cardNumberBatchInfo) throws InspireNetzException {
        
        //check the duplicate buplicateatch name
        boolean isDuplicate = isCardNumberBatchNameDuplicate(cardNumberBatchInfo);

        if(isDuplicate){

            log.info("Duplicate Batch Name exist: "+cardNumberBatchInfo.toString());

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);
        }



        return saveBatchInfoInformation(cardNumberBatchInfo);
    }

    @Override
    public boolean isCardNumberBatchNameDuplicate(CardNumberBatchInfo cardNumberBatchInfo) {
       
        // Get the cardNumberBatchInfo information
        CardNumberBatchInfo exCardNumberBatchInfo = cardNumberBatchInfoRepository.findByCnbMerchantNoAndCnbName(cardNumberBatchInfo.getCnbMerchantNo(),cardNumberBatchInfo.getCnbName());

        // If the brnId is 0L, then its a new cardNumberBatchInfo so we just need to check if there is ano
        // ther cardNumberBatchInfo code
        if ( cardNumberBatchInfo.getCnbId() == null || cardNumberBatchInfo.getCnbId() == 0L ) {

            // If the cardNumberBatchInfo is not null, then return true
            if ( exCardNumberBatchInfo != null ) {

                return true;

            }

        } else {

            // Check if the cardNumberBatchInfo is null
            if ( exCardNumberBatchInfo != null && cardNumberBatchInfo.getCnbId().longValue() != exCardNumberBatchInfo.getCnbId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public void delete(CardNumberBatchInfo cardNumberBatchInfo) {
        cardNumberBatchInfoRepository.delete(cardNumberBatchInfo);
    }
}
