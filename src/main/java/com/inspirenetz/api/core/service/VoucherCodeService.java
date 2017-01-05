package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.VoucherCode;

/**
 * Created by alameen on 10/2/15.
 */
public interface VoucherCodeService extends BaseService<VoucherCode> {

    public String getVoucherCode(RedemptionVoucherSource redemptionVoucherSource);

    public void delete(VoucherCode voucherCode);

    public VoucherCode save(VoucherCode voucherCode);

    public void processBatchFile(String filePath,RedemptionVoucherSource redemptionVoucherSource);


}
