package com.inspirenetz.api.util.integration;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CodedValueIndex;
import com.inspirenetz.api.core.dictionary.SyncProcessStatus;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameen on 5/3/15.
 */

@Component
public class CustomerDataXMLParser  extends DefaultHandler {

    // create an instance of the logger
    private static Logger log = LoggerFactory.getLogger(CustomerDataXMLParser.class);


    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    CodedValueService codedValueService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerReferralService customerReferralService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    SyncStatusService syncStatusService;

    private List<Customer> customerList;

    private List<CustomerReferral> customerReferralList;

    private Set<CustomerProfile> customerProfileSet;

    private  Customer customer;

    private CustomerProfile customerProfile;

    private CustomerReferral customerReferral;


    Long usrMerchantNo;

    Long usrLocation;

    Long userNo;

    // Variable holding the tmpValue
    String tmpValue;

    String xmlFileName;

    String fileName;

    public  void CustomerDataXMLParser(String xmlFileName) throws InspireNetzException {

        this.xmlFileName =xmlFileName;

        this.usrMerchantNo =authSessionUtils.getMerchantNo();

        this.usrLocation =authSessionUtils.getUserLocation();

        this.userNo =authSessionUtils.getUserNo();

        customerList =new ArrayList<>();

        customerReferralList =new ArrayList<>();

        parseDocument();

    }


    /**
     * Function to parse the document
     * Function will create the parser object , get the batch index for the current batch
     * and the call the parse on the document referenced by the xmlFileName
     *
     */
    public void parseDocument() throws InspireNetzException {

        // Create a SAXParserFactory instance
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // Get the today Date
        Date today = new Date(new java.util.Date().getTime());

        File file=new File(this.xmlFileName);
        this.fileName=file.getName();

        //Create Sync Status Object
        SyncStatus syncStatus= new SyncStatus();
        syncStatus.setSysMerchantNo(this.usrMerchantNo);
        syncStatus.setSysLocation(this.usrLocation);

        Long lastBatchIndex=syncStatusService.getLastBatchIndex(this.usrMerchantNo, this.usrLocation, SyncType.CUSTOMERS, today);

        syncStatus.setSysBatch(lastBatchIndex!=null?lastBatchIndex+1L:1L);
        syncStatus.setSysDate(today);
        syncStatus.setSysBatchRef(this.fileName);
        syncStatus.setSysType(SyncType.CUSTOMERS);
        syncStatus.setSysStatus(SyncProcessStatus.ONGOING);
        syncStatus=syncStatusService.saveSyncStatus(syncStatus);

        try {

            // Get the parser
            SAXParser parser = factory.newSAXParser();

            // Call the parse on the parser for the filename
            parser.parse(xmlFileName,this);

            log.info("customerList"+customerList);

            boolean isSaveStatus=true;

            Integer savedCount=0;

            //iterate list and register customer
            for (Customer customer1:customerList){

                try{

                    customer1 =customerService.validateAndRegisterCustomerThroughXml(customer1);

                    savedCount++;


                }catch (InspireNetzException ex){

                    if(ex.getErrorCode() ==APIErrorCode.ERR_DUPLICATE_ENTRY){

                        savedCount++;

                        log.info("CustomerDataXMLParser:- Save  :-batch "+syncStatus.getSysBatch()+" skipped,duplicate entry");

                    }else{

                    isSaveStatus=false;

                    log.info("CustomerDataXMLParser:- Save  :-batch "+syncStatus.getSysBatch()+" failed");

                    ex.printStackTrace();
                    }

                }catch (Exception e){

                    isSaveStatus=false;

                    log.info("CustomerDataXMLParser:- Save  :-batch "+syncStatus.getSysBatch()+" failed");

                    e.printStackTrace();

                }



            }
            if(isSaveStatus){

                syncStatus.setSysStatus(SyncProcessStatus.COMPLETED);

            }else if(!isSaveStatus && savedCount>0){

                syncStatus.setSysStatus(SyncProcessStatus.PARTIALLY_COMPLETED);

            }else{

                syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            }

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            //after that process the customer referral list
            if(customerReferralList !=null && customerReferralList.size()>0){

                customerReferralService.saveReferralDataFromXmlFile(customerReferralList,userNo);

            }

        } catch (ParserConfigurationException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("Parser Config Error " + xmlFileName);
            e.printStackTrace();

        } catch (SAXException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("SAX Exception : XML not well formed " + xmlFileName);
            e.printStackTrace();

        } catch (IOException e) {

            syncStatus.setSysStatus(SyncProcessStatus.FAILED);

            syncStatus=syncStatusService.saveSyncStatus(syncStatus);

            log.error("IO Error " + xmlFileName);
            e.printStackTrace();

        }

    }


    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {


        // If the starting element is CUSTOMER tag then we need to
        if (elementName.equalsIgnoreCase("CUSTOMER")) {

           customer =new Customer();

           customerProfile =new CustomerProfile();

           customerReferral =new CustomerReferral();
        }
        tmpValue="";

    }

    private Integer getCdvId(String cdvLabel,int cdvIndex) {


        CodedValue codedValue =codedValueService.findByCdvLabelAndCdvIndex(cdvLabel, cdvIndex);

        if(codedValue ==null){

            log.error("coded value is null for given label " + cdvLabel);

            return 0;
        }

        return codedValue.getCdvCodeValue();
    }


    @Override
    public void endElement(String s, String s1, String element) throws SAXException {

        // if end of sale element add to list
        if (element.equalsIgnoreCase("CUSTOMER")) {

            //add customer profile object into customer
            customerProfileSet =new HashSet<>(0);

            customerProfileSet.add(customerProfile);

            customer.setCspProfileRef(customerProfileSet);

            //check customer location null or 0 set user location
            if( (customer  !=null) &&(customer.getCusLocation() ==null || customer.getCusLocation().longValue() ==0L)){

                customer.setCusLocation(usrLocation);
            }
            // Add to the list
            customerList.add(customer);



            //Add referral information
            if(customer !=null){

                if (customer.getCusLoyaltyId() !=null || !customer.getCusLoyaltyId().equals("")){

                    //set customer loyalty id
                    customerReferral.setCsrLoyaltyId(customer.getCusLoyaltyId());

                    customerReferral.setCsrFName(customer.getCusFName());

                }else if(customer.getCusMobile() !=null || ! customer.getCusMobile().equals("")){

                    //find the customer information
                    Customer customer1 = customerService.findByCusMobileAndCusMerchantNo(customer.getCusMobile(),usrMerchantNo);

                    //check the customer null or not
                    if(customer1 ==null){

                        //set the mobile number as loyalty id
                        customerReferral.setCsrLoyaltyId(customer.getCusMobile());

                        customerReferral.setCsrFName(customer.getCusFName());

                    }else {

                        //set customer loyalty id
                        customerReferral.setCsrLoyaltyId(customer.getCusLoyaltyId());

                        customerReferral.setCsrFName(customer.getCusFName());
                    }

                }

                //after that check customer ref loyalty id is not null and mobile number null then add into list
                if(customerReferral !=null && (customerReferral.getCsrLoyaltyId() !=null && !customerReferral.getCsrLoyaltyId().equals("")) && (customerReferral.getCsrRefMobile() !=null && !customerReferral.getCsrRefMobile().equals(""))){

                    //added into list
                    customerReferral.setCsrMerchantNo(usrMerchantNo);

                    customerReferralList.add(customerReferral);



                }
            }

        }
        if(element.equalsIgnoreCase("CUSTOMER_NO")){

            //replacing special character from string
            String customerNo = tmpValue.replaceAll("[\\-\\+\\.\\^:,]","");

            customer.setCusCustomerNo(Long.parseLong(customerNo==""?"0":customerNo));
        }
        if(element.equalsIgnoreCase("LOYALTY_ID")){

            //replacing special character from string
            String loyaltyId = tmpValue.replaceAll("[\\-\\+\\.\\^:,]","");

            customer.setCusLoyaltyId(loyaltyId);
        }

        if(element.equalsIgnoreCase("FIRSTNAME")){

            customer.setCusFName(tmpValue);
        }

        if(element.equalsIgnoreCase("LASTNAME")){

            customer.setCusLName(tmpValue);
        }

        if(element.equalsIgnoreCase("EMAIL")){

            customer.setCusEmail(tmpValue);
        }

        if(element.equalsIgnoreCase("MOBILE")){

            customer.setCusMobile(tmpValue);
        }

        if(element.equalsIgnoreCase("ADDRESS")){

           customerProfile.setCspAddress(tmpValue);

        }

        if(element.equalsIgnoreCase("LOCATION")){

            String location = tmpValue==null?"":tmpValue.replace("\n","");

            if(!location.equals("")){

                Long customerLocation = getLocation(location.trim());

                if(customerLocation.longValue() !=0L){

                    customer.setCusLocation(customerLocation);

                }else {

                    customer.setCusLocation(usrLocation);
                }

            }else {

                customer.setCusLocation(usrLocation);
            }


        }

        if(element.equalsIgnoreCase("CITY")){

            customerProfile.setCspCity(tmpValue);
        }

        if(element.equalsIgnoreCase("STATE")){

            Integer stateId = getCdvId(tmpValue, CodedValueIndex.INDIA_STATE_LIST);

            customerProfile.setCspState(stateId);
        }

        if(element.equalsIgnoreCase("PINCODE")){

            customerProfile.setCspPincode(tmpValue);
        }

        if(element.equalsIgnoreCase("COUNTRY")){

            Integer countryId = getCdvId(tmpValue, CodedValueIndex.COUNTRY_LIST);

            customerProfile.setCspCountry(countryId);
        }

        if(element.equalsIgnoreCase("BIRTHDAY")){

            Date date = DBUtils.covertToSqlDate(tmpValue);

            customerProfile.setCspCustomerBirthday(date);
        }

        if(element.equalsIgnoreCase("ANNIVERSARY")){

            Date date = DBUtils.covertToSqlDate(tmpValue);

            customerProfile.setCspCustomerAnniversary(date);
        }

        if(element.equalsIgnoreCase("SPOUSE_NAME")){

            customerProfile.setCspFamilySpouseName(tmpValue);
        }

        if(element.equalsIgnoreCase("SPOUSE_BIRTHDAY")){

            Date date = DBUtils.covertToSqlDate(tmpValue);

            customerProfile.setCspFamilySpouseBday(date);
        }

        if(element.equalsIgnoreCase("CHILD1_NAME")){

            customerProfile.setCspFamilyChild1Name(tmpValue);
        }

        if(element.equalsIgnoreCase("CHILD1_BIRTHDAY")){

            Date date = DBUtils.covertToSqlDate(tmpValue);

            customerProfile.setCspFamilyChild1Bday(date);
        }

        if(element.equalsIgnoreCase("CHILD2_NAME")){

            customerProfile.setCspFamilyChild2Name(tmpValue);
        }

        if(element.equalsIgnoreCase("CHILD2_BIRTHDAY")){

            Date date = DBUtils.covertToSqlDate(tmpValue);

            customerProfile.setCspFamilyChild2Bday(date);
        }

        if(element.equalsIgnoreCase("REF_MOBILE")){

            customerReferral.setCsrRefMobile(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        if(element.equalsIgnoreCase("REF_NAME")){

            customerReferral.setCsrRefName(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        if(element.equalsIgnoreCase("REF_ADDRESS")){

            customerReferral.setCsrRefAddress(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        if (element.equalsIgnoreCase("REF_EMAIL")){

            customerReferral.setCsrRefEmail(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        if (element.equalsIgnoreCase("REF_USER")){

            Long userNo =getUserInformation(tmpValue==null?"":tmpValue.replace("\n",""));

            customerReferral.setCsrLocation(userNo);
        }

        if (element.equalsIgnoreCase("REF_PRODUCT")){

            String productCode = getProductInformation(tmpValue==null?"":tmpValue.replace("\n",""));

            if(!productCode.equals("")){

                customerReferral.setCsrProduct(productCode);
            }
        }

        if(element.equalsIgnoreCase("REF_LOCATION")){

            Long refLocation = getLocation(tmpValue==null?"":tmpValue.replace("\n",""));

            //set the location value
            if(refLocation.longValue() !=0L){

                customerReferral.setCsrLocation(refLocation);
            }else{

                customerReferral.setCsrLocation(usrLocation);
            }

        }
        if(element.equalsIgnoreCase("REF_NO")){

            customerReferral.setCsrRefNo(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        if(element.equalsIgnoreCase("REFERRAL_CODE")){

            customer.setReferralCode(tmpValue==null?"":tmpValue.replace("\n",""));
        }

        //add merchant no and location
        customer.setCusMerchantNo(usrMerchantNo);



    }

    private Long getLocation(String tmpValue) {

        MerchantLocation merchantLocation =merchantLocationService.findByMelMerchantNoAndMelLocation(usrMerchantNo,tmpValue==null?"":tmpValue);

        if(merchantLocation !=null){

            return merchantLocation.getMelId();

        }

        return 0L;
    }

    private String getProductInformation(String tmpValue) {

        Product product =productService.findByPrdMerchantNoAndPrdCode(usrMerchantNo,tmpValue==null?"":tmpValue);

        if(product !=null){

            return product.getPrdCode();
        }

        return "";
    }

    private Long getUserInformation(String tmpValue) {

        //get the user number
        User user =userService.findByUsrLoginId(tmpValue==null?"":tmpValue);

        if(user !=null){

            return user.getUsrUserNo();
        }

        //return
        return 0L;

    }


    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {

        tmpValue = new String(ac, i, j);

    }
}
