package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.VoucherCode;

/**
 * Created by alameen on 10/2/15.
 */
public interface VoucherCodeRepository extends  BaseRepository<VoucherCode,Long> {

    public VoucherCode findByVocId(Long vocId);

    public VoucherCode findByVocMerchantNoAndVocVoucherSourceAndVocIndex(Long vocMerchantNo,Long vocVoucherSource,Long vocIndex );

}
