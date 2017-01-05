package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.BulkUploadMappingCodedValues;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.BrandFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.integration.BulkUploadXLSParser;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ameen on 4/9/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class BulkUploadServiceTest {

    private static Logger log = LoggerFactory.getLogger(BulkUploadServiceTest.class);



    UsernamePasswordAuthenticationToken principal;

    @Autowired
    UserService userService;

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

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CustomerReferralService customerReferralService;

    @Autowired
    BulkuploadRawdataService bulkuploadRawdataService;


    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<User> users =new HashSet<>();

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }


    @Test
    public void getHeader(){

        BulkUploadXLSParser bulkUploadService =new BulkUploadXLSParser();

        Map<String ,String> headerContent =bulkUploadService.getHeaderContent("/home/ameen/Downloads/as.xls");

        log.info("Map Header Content"+headerContent);

        log.info("Map Header Content"+headerContent);
    }

    @Test
    public void readXlsFile() throws IOException {

//        HashMap<String,String> map =new HashMap<>();
//
//        BulkUploadXLSParser bulkUploadService =new BulkUploadXLSParser();
//
//        Map<String ,String> headerContent =bulkUploadService.getHeaderContent("/home/ameen/Downloads/as.xls");
//
//        Map<String,Integer> headerModifiedContent =new HashMap<>();
//
//        for(Integer i=0;i<headerContent.size();i++){
//
//            if(i==12){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.cusFName);
//
//            }else if (i==17){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.cspCity);
//
//            }else if (i==18){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.cusMobile);
//            }else if (i==20){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.cspPinCode);
//            }else if(i==0) {
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.salDate);
//            }else if (i==1){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.salReference);
//            }else if (i==2){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.prdCode);
//            }else if (i==5){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.brdCode);
//            }else if (i==6){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.pcyCode);
//            }else if (i==7){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.prdName);
//            }else if(i==8){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.ssuQuantity);
//            }else if(i==11){
//
//                headerModifiedContent.put(i.toString(), BulkUploadMappingCodedValues.ssuPrice);
//            }else{
//
//                headerModifiedContent.put(i.toString(),0);
//            }
//
//
//        }
//
//        //remove file name
//        headerContent.remove("fileName");
//
//        //save user information
//        User user =new User();
//
//        user.setUsrMerchantNo(1L);
//        user.setUsrLoginId("asdfg");
//        user.setUsrLocation(12L);
//        user.setUsrFName("asdkd");
//
//        user =userService.saveUser(user);
//
//        users.add(user);
//
//        Long merchantNo =1L;
//
//            BulkUploadXLSParser bulkUploadXLSParser =new BulkUploadXLSParser
//                    (bulkuploadRawdataService,merchantLocationService,customerService,productService,
//                    brandService,saleService,bulkUploadBatchInfoService,productCategoryService,fileUploadService,generalUtils,"/home/ameen/Downloads/as.xls",map,merchantNo,user,
//                    userService,customerReferralService);
//
//        //parse bulk upload file
//        bulkUploadXLSParser.parseXlsFile("/home/ameen/Downloads/as.xls", headerModifiedContent,1L,user);
//
//        //parse the login
//        log.info("PARSING IS DONE");

    }

    @After
    public void tearDown() {

        for (User user:users){

            try{

                userService.deleteUser(user);

            }catch (InspireNetzException ex){

                log.info("Exception"+ex);
            }

        }
    }


}
