package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.TransferRequestStatus;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.domain.validator.PointTransferRequestValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PointTransferRequestRepository;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PointTransferRequestService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class PointTransferRequestServiceImpl extends BaseServiceImpl<PointTransferRequest> implements PointTransferRequestService {


    private static Logger log = LoggerFactory.getLogger(PointTransferRequestServiceImpl.class);


    @Autowired
    PointTransferRequestRepository pointTransferRequestRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public PointTransferRequestServiceImpl() {

        super(PointTransferRequest.class);

    }


    @Override
    protected BaseRepository<PointTransferRequest,Long> getDao() {
        return pointTransferRequestRepository;
    }

    @Override
    public PointTransferRequest findByPtrId(Long ptrId) {

        PointTransferRequest pointTransferRequest = pointTransferRequestRepository.findByPtrId(ptrId);

        return pointTransferRequest;
    }

    @Override
    public Page<PointTransferRequest> findByPtrMerchantNo(Long ptrMerchantNo, Pageable pageable) {

        Page<PointTransferRequest> pointTransferRequests = pointTransferRequestRepository.findByPtrMerchantNo(ptrMerchantNo, pageable);

        return pointTransferRequests;
    }

    @Override
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrStatus(Long ptrMerchantNo, Integer ptrStatus, Pageable pageable) {

        Page<PointTransferRequest> pointTransferRequests = pointTransferRequestRepository.findByPtrMerchantNoAndPtrStatus(ptrMerchantNo, ptrStatus, pageable);

        return pointTransferRequests;
    }

    @Override
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrStatus(Long ptrMerchantNo, String ptrSource, Integer ptrStatus, Pageable pageable) {

        Page<PointTransferRequest> pointTransferRequests = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSourceAndPtrStatus(ptrMerchantNo, ptrSource, ptrStatus, pageable);

        return pointTransferRequests;
    }

    @Override
    public List<PointTransferRequest> findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(Long ptrMerchantNo, String ptrSource, String ptrDestination, Integer ptrStatus) {

        List<PointTransferRequest> pointTransferRequests = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSourceAndPtrDestinationAndPtrStatus(ptrMerchantNo,ptrSource,ptrDestination,ptrStatus);

        return pointTransferRequests;


    }

    @Override
    public PointTransferRequest savePointTransferRequest(PointTransferRequest pointTransferRequest) throws InspireNetzException { 
        PointTransferRequest pointTransferRequest1 = pointTransferRequestRepository.save(pointTransferRequest);

        return pointTransferRequest;
    }

    @Override
    public boolean deletePointTransferRequest(Long pcyId) throws InspireNetzException {

        pointTransferRequestRepository.delete(pcyId);

        // return true
        return true;
    }

    @Override
    public PointTransferRequest validateAndSavePointTransferRequest(PointTransferRequest pointTransferRequest) throws InspireNetzException {

        return null;
    }

    @Override
    public boolean validateAndDeletePointTransferRequest(Long pcyId) throws InspireNetzException {

        return false;
    }

    @Override
    public void updateTransferRequestStatus(Long ptrId, Integer status) throws InspireNetzException {

        //get the transfer request object
        PointTransferRequest pointTransferRequest = findByPtrId(ptrId);

        if(pointTransferRequest == null ){

            //log error
            log.error("updateTransferRequestStatus : No request found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        } else {

            //set status
            pointTransferRequest.setPtrStatus(status);

            //save the request
            savePointTransferRequest(pointTransferRequest);

        }

    }

    @Override
    public Page<PointTransferRequest> findByPtrMerchantNoAndPtrSource(Long ptrMerchantNo, String ptrSource, Pageable pageable) {

        Page<PointTransferRequest> pointTransferRequests = pointTransferRequestRepository.findByPtrMerchantNoAndPtrSource(ptrMerchantNo, ptrSource, pageable);

        return pointTransferRequests;
    }
    @Override
    public Page<PointTransferRequest> searchPointTransferRequest(String filter, String query, Long ptrMerchantNo, Pageable pageable) {

        //for get page number and page size from pageable request and add sorting parameter for displaying last created one into top
        int page = pageable.getPageNumber();

        int pageSize =pageable.getPageSize();

        //create request with sorting parameter

        Pageable pageRequest = new PageRequest(page,pageSize, new Sort(Sort.Direction.DESC,"ptrId"));

        //get the draw chances list
        Page<PointTransferRequest> pointTransferRequests = null;

        if(filter.equals("0")){

            pointTransferRequests=pointTransferRequestRepository.findByPtrMerchantNo(ptrMerchantNo,pageRequest);

        } else if(filter.equals("source")) {

            pointTransferRequests=pointTransferRequestRepository.findByPtrMerchantNoAndPtrSource(ptrMerchantNo,query,pageRequest);

        }

        // Return request object
        return pointTransferRequests;
    }


}



