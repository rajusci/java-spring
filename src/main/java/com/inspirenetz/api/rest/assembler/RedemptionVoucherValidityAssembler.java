package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.rest.controller.RedemptionVoucherController;
import com.inspirenetz.api.rest.resource.RedemptionVoucherResource;
import com.inspirenetz.api.rest.resource.RedemptionVoucherValidityResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by alameen on 10/2/15.
 */
@Component
public class RedemptionVoucherValidityAssembler extends ResourceAssemblerSupport<RedemptionVoucher,RedemptionVoucherValidityResource> {


    @Autowired
    private Mapper mapper;

    public RedemptionVoucherValidityAssembler() {

        super(RedemptionVoucherController.class,RedemptionVoucherValidityResource.class);

    }

    @Override
    public RedemptionVoucherValidityResource toResource(RedemptionVoucher redemptionVoucher) {

        RedemptionVoucherValidityResource redemptionVoucherValidityResource =mapper.map(redemptionVoucher,RedemptionVoucherValidityResource.class);

        return redemptionVoucherValidityResource;
    }
}
