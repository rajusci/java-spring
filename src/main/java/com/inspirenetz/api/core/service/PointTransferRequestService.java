package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface PointTransferRequestService extends BaseService<PointTransferRequest> {

    public PointTransferRequest findByPtrId(Long ptrId);
    public Page<PointTransferRequest> findByPtrMerchantNo(Long ptrMerchantNo,Pageable pageable);
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrStatus(Long ptrMerchantNo,Integer ptrStatus,Pageable pageable);
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrStatus(Long ptrMerchantNo,String ptrSource,Integer ptrStatus,Pageable pageable);
    public List<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(Long ptrMerchantNo,String ptrSource,String ptrDestination,Integer ptrStatus);



    public PointTransferRequest savePointTransferRequest(PointTransferRequest pointTransferRequest) throws InspireNetzException;
    public boolean deletePointTransferRequest(Long pcyId) throws InspireNetzException;

    public PointTransferRequest validateAndSavePointTransferRequest(PointTransferRequest pointTransferRequest) throws InspireNetzException;
    public boolean validateAndDeletePointTransferRequest(Long pcyId) throws InspireNetzException;

    public void updateTransferRequestStatus(Long ptrId,Integer status) throws InspireNetzException;
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSource(Long ptrMerchantNo,String ptrSource,Pageable pageable);
    public Page<PointTransferRequest> searchPointTransferRequest(String filter,String query,Long ptrMerchantNo,Pageable pageable);
}
