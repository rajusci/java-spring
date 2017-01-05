package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardTypeService extends BaseService<CardType> {

    public Page<CardType> findByCrtMerchantNo(Long crtMerchantNo, Pageable pageable);
    public CardType findByCrtId(Long crtId);
    public Page<CardType> findByCrtMerchantNoAndCrtNameLike(Long crtMerchantNo, String crtName, Pageable pageable);    
    public boolean isDuplicateCardTypeExisting(CardType cardType);
    public CardType findByCrtMerchantNoAndCrtName(Long crtMerchantNo, String crtName);
    public boolean isCardNumberValid(String cardNumber, CardType cardType);
    public Date getExpiryDateForCardType(CardType cardType);
    public boolean isCardValueValid(CardType cardType,Double amount,Double currBalance,Integer transactionType);

    public CardType validateAndSaveCardType(CardType cardType) throws InspireNetzException;
    public CardType saveCardType(CardType cardType) throws InspireNetzException;

    public boolean validateAndDeleteCardType(Long crtId,Long merchantNo) throws InspireNetzException;
    public boolean deleteCardType(Long crtId,Long merchantNo) throws InspireNetzException;


    public Page<CardType> searchCardTypes(String filter, String query, Long merchantNo,Pageable pageable);

    public CardType getCardTypeInfo(Long crtId,Long merchantNo) throws InspireNetzException;

    public CardType save(CardType cardType);


    public List<CardType> findByCrtMerchantNo(Long crtMerchantNo);

    public boolean checkDuplicateRange(String crtCardNoRangeFrom, String crtCardNoRangeTo, String startRange,String endRange);

    public void checkCardNumberRangeValid(CardType cardType) throws InspireNetzException;
}
