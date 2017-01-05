package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.CardNumberInfoRequest;
import com.inspirenetz.api.core.domain.CardNumberBatchInfo;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ameen on 20/10/15.
 */
public interface CardNumberInfoService extends BaseService<CardNumberInfo> {

    public Page<CardNumberInfo> findByCniMerchantNo(Long cniMerchantNo,Pageable pageable);
    public CardNumberInfo findByCniId(Long cniId);
    public CardNumberInfo findByCniMerchantNoAndCniCardNumber(Long cniMerchantNo, String cniCardNumber);
    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(Long cniMerchantNo,Long cniBatchId, String cniCardNumber,Pageable pageable);
    public void processBatchFile(CardNumberInfoRequest cardNumberInfoRequest) throws InspireNetzException;
    public CardNumberInfo saveCardNumberInfo(CardNumberInfo cardNumberInfo);
    public void processFile(String fileName, Long cniCardType, Long cniMerchantNo, CardNumberBatchInfo cardNumberBatchInfo);
    public Page<CardNumberInfo> searchCardNumberInfo(Long cniMerchantNo,Long cniBatchId,String filter,String query,Pageable pageable);
    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchId(Long cniMerchantNo,Long cniBatchId,Pageable pageable);

    public void delete(CardNumberInfo cardNumberInfo);

    public CardNumberInfo findByCniCardNumber(String cniCardNumber);

    public CardNumberInfo getValidatedCardDetails(String cniCardNumber,String pin) throws InspireNetzException;

    public CardNumberInfo getValidatedCardDetailsForPublic(String cniCardNumber,String mobile,String pin,Long merchantNo) throws InspireNetzException;

    public CardNumberInfo isValidCardNumber(CardNumberInfo cardNumberInfo,boolean isActivationPinValidate);

    public CardNumberInfo isCardNumberValid(CardNumberInfo cardNumberInfo);

    public CardNumberInfo createCardNumberInfoObject(String cardNumber,Long merchantNo,Long cardType,String pin);

    public CardNumberInfo getAvailableCardNumber(Long cniMerchantNo,Long cniCardType);

}
