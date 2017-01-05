package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.PointTransferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface PointTransferRequestRepository extends  BaseRepository<PointTransferRequest,Long> {

    public PointTransferRequest findByPtrId(Long ptrId);
    public Page<PointTransferRequest> findByPtrMerchantNo(Long ptrMerchantNo,Pageable pageable);
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrStatus(Long ptrMerchantNo,Integer ptrStatus,Pageable pageable);
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrStatus(Long ptrMerchantNo,String ptrSource,Integer ptrStatus,Pageable pageable);
    public List<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(Long ptrMerchantNo,String ptrSource,String ptrDestination,Integer ptrStatus);
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSource(Long ptrMerchantNo,String ptrSource,Pageable pageable);

}
