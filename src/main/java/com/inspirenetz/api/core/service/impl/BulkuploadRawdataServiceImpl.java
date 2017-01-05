package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.BulkUploadMapping;
import com.inspirenetz.api.core.dictionary.BulkUploadRawdataStatus;
import com.inspirenetz.api.core.domain.BulkuploadRawdata;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.BulkuploadRawdataRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.integration.BulkUploadXLSParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class BulkuploadRawdataServiceImpl extends BaseServiceImpl<BulkuploadRawdata> implements BulkuploadRawdataService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    BulkuploadRawdataRepository bulkuploadRawdataRepository;

    @Autowired
    CustomerReferralService customerReferralService;

    @Autowired
    UserService userService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    SaleService saleService;

    @Autowired
    BrandService brandService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    BulkUploadBatchInfoService bulkUploadBatchInfoService;

    @Autowired
    FileUploadService fileUploadService;




    public BulkuploadRawdataServiceImpl() {

        super(BulkuploadRawdata.class);

    }


    @Override
    protected BaseRepository<BulkuploadRawdata,Long> getDao() {
        return bulkuploadRawdataRepository;
    }


    @Override
    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo,int brdBatchIndex) {

        // Get the data from the repository and store in the list
        List<BulkuploadRawdata> bulkuploadRawdataList = bulkuploadRawdataRepository.findByBrdMerchantNoAndBrdBatchIndex(brdMerchantNo, brdBatchIndex);

        // Return the list
        return bulkuploadRawdataList;

    }

    @Override
    public List<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndexAndBrdStatus(Long brdMerchantNo,int brdBatchIndex,BulkUploadRawdataStatus brdStatus) {

        // Get the data from the repository and store in the list
        List<BulkuploadRawdata> bulkuploadRawdataList = bulkuploadRawdataRepository.findByBrdMerchantNoAndBrdBatchIndexAndBrdStatus(brdMerchantNo,brdBatchIndex,brdStatus);

        // Return the list
        return bulkuploadRawdataList;

    }


    @Override
    public List<BulkuploadRawdata> listBulkUploads(Long brdMerchantNo) {

        // Get the data from the repository and store in the list
        List<BulkuploadRawdata> bulkuploadRawdataList = bulkuploadRawdataRepository.listBulkUploads(brdMerchantNo);

        // Return the list
        return bulkuploadRawdataList;

    }

    @Override
    public HashMap<String, String> getHeaderContent(String fileName) {

        BulkUploadXLSParser bulkUploadXLSParser =new BulkUploadXLSParser(fileUploadService);

        HashMap<String,String> headerValues = bulkUploadXLSParser.getHeaderContent(fileName);

        return headerValues;
    }

    @Override
    @Async
    public void processingBulkUpload(final String fileName, Set<BulkUploadMapping> bulkUploadMappingSet,final User user) {

        final  Map <String,Integer> map =new HashMap<>();

        final Long merchantNo =user.getUsrMerchantNo();

        try{

                  for (BulkUploadMapping bulkUploadMapping:bulkUploadMappingSet){

                   //put field into map
                   map.put(bulkUploadMapping.getColumnIndex(),bulkUploadMapping.getMappingIndex());

                }

            BulkUploadXLSParser bulkUploadXLSParser1 =new BulkUploadXLSParser(this,merchantLocationService,customerService,productService,
                    brandService,saleService,bulkUploadBatchInfoService,productCategoryService,fileUploadService,generalUtils,fileName,map,merchantNo,user,
                    userService,customerReferralService);


        }catch (Exception e){

            log.info("Parsing Exception :"+e);
        }



    }

    @Override
    public BulkuploadRawdata saveRawData(BulkuploadRawdata bulkuploadRawdata) {

        bulkuploadRawdata =bulkuploadRawdataRepository.save(bulkuploadRawdata);

        return bulkuploadRawdata;
    }

    @Override
    public Page<BulkuploadRawdata> findByBrdMerchantNoAndBrdBatchIndex(Long brdMerchantNo, Long brdBatchIndex, Pageable pageable) {
        return bulkuploadRawdataRepository.findByBrdMerchantNoAndBrdBatchIndex(brdMerchantNo,brdBatchIndex,pageable);
    }


}
