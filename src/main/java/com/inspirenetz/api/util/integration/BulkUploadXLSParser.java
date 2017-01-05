package com.inspirenetz.api.util.integration;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.util.GeneralUtils;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by ameen on 7/9/15.
 */

@Component
public class BulkUploadXLSParser implements HSSFListener {

    private static Logger log = LoggerFactory.getLogger(BulkUploadXLSParser.class);

    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;

    private FormatTrackingHSSFListener formatListener;

    private boolean outputFormulaValues = true;

    private SSTRecord sstrec;

    private int rowCounter =0;

    private int noRow=0;

    //creating Array list of object
    private List mappingValue=new ArrayList();

    //string payment reference
    private String tempPaymentReference ="";

    //assign
    private boolean firstRecordProcessing =false;

    //bulk upload batch info information
    private BulkUploadBatchInfo bulkUploadBatchInfo;

    private int counter =0;

    private boolean lastAttemptCustomer=true;

    //string
    private int numeric1;
    private int string1;

    private List<Product> productList;

    private List<Brand> brandList;

    private List<ProductCategory> productCategoryList;

    private List<Sale> saleList;

    private Map<String,SaleSKU> saleSKUMap;

    private Map<String,Integer> mappingGrammar;

    private List<Map> saleSkuMap;

    private User user;

    private Long merchantNo;


    private GeneralUtils generalUtils;

    private BulkuploadRawdataService bulkuploadRawdataService;


    private MerchantLocationService merchantLocationService;


    private CustomerService customerService;


    private ProductService productService;


    private SaleService saleService;


    private BrandService brandService;


    private BulkUploadBatchInfoService bulkUploadBatchInfoService;


    private ProductCategoryService productCategoryService;


    private FileUploadService fileUploadService;

    private CustomerReferralService customerReferralService;

    private UserService userService;

    public BulkUploadXLSParser(){


    }

    public BulkUploadXLSParser(BulkuploadRawdataService bulkuploadRawdataService, MerchantLocationService merchantLocationService,
                               CustomerService customerService,ProductService productService,BrandService brandService,
                               SaleService saleService,BulkUploadBatchInfoService bulkUploadBatchInfoService,
                               ProductCategoryService productCategoryService,FileUploadService fileUploadService,GeneralUtils generalUtils, String fileName,Map<String,Integer> mappingGrammar,Long merchantNo,User user,
                               UserService userService,CustomerReferralService customerReferralService
    ){

        this.bulkuploadRawdataService =bulkuploadRawdataService;
        this.merchantLocationService =merchantLocationService;
        this.customerService =customerService;
        this.productService =productService;
        this.brandService =brandService;
        this.saleService =saleService;
        this.bulkUploadBatchInfoService =bulkUploadBatchInfoService;
        this.productCategoryService  =productCategoryService;
        this.fileUploadService =fileUploadService;
        this.generalUtils =generalUtils;
        this.rowCounter =0;
        this.userService =userService;
        this.customerReferralService =customerReferralService;


        this.productList =new ArrayList<>();

        this.brandList =new ArrayList<>();

        this.productCategoryList =new ArrayList<>();

        this.saleList =new ArrayList<>();


        this.saleSkuMap = new ArrayList<>();



        try {
            parseXlsFile(fileName,mappingGrammar,merchantNo,user);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BulkUploadXLSParser(FileUploadService fileUploadService){

        this.fileUploadService =fileUploadService;
    }

    public BulkUploadXLSParser(BulkuploadRawdataService bulkuploadRawdataService, MerchantLocationService merchantLocationService, CustomerService customerService, ProductService productService, BrandService brandService, SaleService saleService, BulkUploadBatchInfoService bulkUploadBatchInfoService, ProductCategoryService productCategoryService, FileUploadService fileUploadService, GeneralUtils generalUtils, String fileName, HashMap<String, String> map, Long merchantNo, User user, UserService userService, CustomerReferralService customerReferralService) {


        this.bulkuploadRawdataService =bulkuploadRawdataService;
        this.merchantLocationService =merchantLocationService;
        this.customerService =customerService;
        this.productService =productService;
        this.brandService =brandService;
        this.saleService =saleService;
        this.bulkUploadBatchInfoService =bulkUploadBatchInfoService;
        this.productCategoryService  =productCategoryService;
        this.fileUploadService =fileUploadService;
        this.generalUtils =generalUtils;
        this.rowCounter =0;
        this.userService =userService;
        this.customerReferralService =customerReferralService;

    }


    @Override
    public void processRecord(Record record) {

        //create map
        Map valueMap ;

        switch (record.getSid()){

            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:

                BOFRecord bof = (BOFRecord) record;

                break;

            case BoundSheetRecord.sid:

                BoundSheetRecord bsr = (BoundSheetRecord) record;

                break;
            case RowRecord.sid:

                RowRecord rowrec = (RowRecord) record;

                log.info(rowrec.getRowNumber()+"rowNumber");
                if (firstRecordProcessing ==true){

                    recordProcessing();

                    firstRecordProcessing =false;

                }

                break;
            case NumberRecord.sid:
                //get numerical record
                NumberRecord numrec = (NumberRecord) record;

                valueMap=new HashMap();

                //format if the numerical value is date
                String value =formatListener.formatNumberDateCell(numrec);

                numeric1 =numrec.getRow();

               /* valueMap.put("rowIndex", numrec.getRow());
                valueMap.put("columnIndex",numrec.getColumn());*/
                valueMap.put(numrec.getColumn(),numrec.getRow()+"&@"+value);

                if(numrec.getRow() != 0){

                    mappingValue.add(valueMap);
                }


                if(firstRecordProcessing ==false){


                    firstRecordProcessing =true;
                }

                break;

            // SSTRecords store a array of unique strings used in Excel.
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                break;

            case LabelSSTRecord.sid:

                LabelSSTRecord lrec = (LabelSSTRecord) record;

                valueMap=new HashMap();

                string1 =lrec.getRow();

                valueMap.put(lrec.getColumn(),lrec.getRow()+"&@"+sstrec.getString(lrec.getSSTIndex()));

                //check if the row number is 0 if it is 0 add the row to mapping value
                if(lrec.getRow()!= 0){

                    mappingValue.add(valueMap);
                }


                if(firstRecordProcessing ==false){

                    firstRecordProcessing =true;
                }

                break;

        }


    }

    public void recordProcessing(){

        //process record parsing and update
        bulkUploadSystemFieldMapping(mappingValue,mappingGrammar);

        mappingValue =new ArrayList();

    }


    public void parseXlsFile(String fileName,Map<String,Integer> mappingGrammar,Long merchantNo,User user) throws FileNotFoundException,IOException {



            this.mappingGrammar =mappingGrammar;
            this.merchantNo =merchantNo;
            this.user=user;
            saveBulkBatchInfo(fileName,user.getUsrMerchantNo(), BulkUploadBatchInfoStatus.PROCESSING);

            try{

                MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);

                //format listener is used to differentiate numeric and date value
                formatListener = new FormatTrackingHSSFListener(listener);

                //get file upload path
                String uploadRoot =fileUploadService.getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

                // create a new file input stream with the input file specified
                // at the command line
                FileInputStream fin = new FileInputStream(uploadRoot+fileName);

                // create a new org.apache.poi.poifs.filesystem.Filesystem
                POIFSFileSystem poifs = new POIFSFileSystem(fin);

                // get the Workbook (excel part) stream in a InputStream
                InputStream din = poifs.createDocumentInputStream("Workbook");

                // construct out HSSFRequest object
                HSSFRequest req = new HSSFRequest();

                if(outputFormulaValues) {
                    req.addListenerForAllRecords(formatListener);

                } else {

                    workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
                    req.addListenerForAllRecords(workbookBuildingListener);
                }


                // create our event factory
                HSSFEventFactory factory = new HSSFEventFactory();

                // process our events based on the document input stream
                factory.processEvents(req, din);

                // once all the events are processed close our file input stream
                fin.close();

                // and our document input stream (don't want to leak these!)
                din.close();

                //check if any content is existing in the map then process
                if (lastAttemptCustomer ==true){

                    if (mappingValue.size()>0){

                        //bulkupload field mapping at the last record
                        bulkUploadSystemFieldMapping(mappingValue,mappingGrammar);
                    }

                    //finally check the last sale list empty or not
                    if(saleList.size()>0){

                        processSalesAndSaleSku(saleList,saleSkuMap,brandList,productCategoryList,productList);

                    }

                    lastAttemptCustomer =false;

                }

            }finally {

                //completed process
                saveBulkBatchInfo(fileName,user.getUsrMerchantNo(), BulkUploadBatchInfoStatus.COMPLETED);

            }



    }

    private void saveBulkBatchInfo(String fileName, Long usrMerchantNo, int processing) {

        bulkUploadBatchInfo =bulkUploadBatchInfoService.findByBrnMerchantNoAndBlkBatchName(usrMerchantNo,fileName);

        if (bulkUploadBatchInfo ==null){

            bulkUploadBatchInfo =new BulkUploadBatchInfo();

            java.util.Date date= new java.util.Date();

            Timestamp timestamp =new Timestamp(date.getTime());

            bulkUploadBatchInfo.setBlkBatchDate(new java.sql.Date(new Date().getTime()));
            bulkUploadBatchInfo.setBlkTime(new Time(new java.util.Date().getTime()));
            bulkUploadBatchInfo.setBlkMerchantNo(usrMerchantNo);
            bulkUploadBatchInfo.setBlkProcessingStatus(processing);
            bulkUploadBatchInfo.setBlkBatchName(fileName);


        }else {

            bulkUploadBatchInfo.setBlkProcessingStatus(processing);
        }

       this.bulkUploadBatchInfo =bulkUploadBatchInfoService.saveBulkUploadBatchInfo(bulkUploadBatchInfo);
    }


    /**
     * @pupose for mapping parameter
     * @param xlsContent
     * @param mappingGrammar
     */
    private void bulkUploadSystemFieldMapping(List<Map> xlsContent,Map<String,Integer> mappingGrammar){


        try {

              //get content
              short c =0;

              //row counter
              Integer counter =0;

              //column size is equal to mapping size
              int columnLength =mappingGrammar.size();

              //declare array list for mapping content
              List rowBasedArray =new ArrayList();

              //create hash map for storing row based content
              Map rowBased =new HashMap();

              for (Map map:xlsContent){

                  //declare object value
                  Object value;

                   //do while is tracing null value field
                   do {
                           //get value from map
                           value =  map.get(c);

                           //add into
                           rowBasedArray.add(value);

                           //increment flag value
                           c= (short) (c+1);

                      }while (value ==null && c<=columnLength);

                  //after reaching one then start next
                  if(c >columnLength){

                      //put into array
                      rowBased.put(counter,rowBasedArray);

                      //counter
                      counter =counter+1;

                      c=0;

                      rowBasedArray =new ArrayList();

                      do {

                          value =  map.get(c);

                          rowBasedArray.add(value);

                          c= (short) (c+1);

                      }while (value ==null && c<=columnLength);

                  }

                  //get the size of the batches of records
                  int arraySize = (xlsContent.size()/(columnLength-1))-1;

                  //check if the counter is equal to records size then process the last records in the batch.
                  if(counter == arraySize){

                      for(int i=0;i<columnLength;i++){

                              if(map.get((short)i) != null){

                                  if(!rowBasedArray.contains(map.get((short)i))){

                                      rowBasedArray.add(map.get((short)i));

                                  }

                              }

                      }

                      if(rowBasedArray.size() == columnLength-1){

                          rowBased.put(counter,rowBasedArray);
                      }
                  }


              }

            //update customer master
            fieldMappingAndProcessing(rowBased, mappingGrammar);

        }catch (Exception e){

            e.printStackTrace();
        }


    }

    private void fieldMappingAndProcessing(Map rowBasedArray, Map<String, Integer> mappingGrammar) {

        for (int j=0;j<rowBasedArray.size();j++){

          try{

                //create new customer object for each row
                Customer customer =new Customer();

                //customer referral object
                CustomerReferral customerReferral =new CustomerReferral();

                //create map
                saleSKUMap =new HashMap<String,SaleSKU>();

                //sale object
                Sale sale =new Sale();

                //create sale sku object
                SaleSKU saleSKU =new SaleSKU();

                //product object
                Product product =new Product();

                //brand object
                Brand brand =new Brand();

                //category object
                ProductCategory productCategory =new ProductCategory();

                //customer profile object
                CustomerProfile customerProfile =new CustomerProfile();

                List rowArray = (List) rowBasedArray.get(j);

                for (Integer i=0;i<rowArray.size();i++){

                    try{

                        //first cell content
                        Integer cellContent =mappingGrammar.get(i.toString())==null?0:mappingGrammar.get(i.toString());

                        //cell values
                        Object cellValues1=rowArray.get(i.intValue());

                        Object cellValues = getCellValues(cellValues1);

                        if (cellContent.intValue() !=0){

                            if(cellContent.intValue() == BulkUploadMappingCodedValues.cusFName){

                                customer.setCusFName(cellValues==null?"":cellValues.toString().trim());

                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.cusLName){

                                customer.setCusLName(cellValues==null?"":cellValues.toString().trim());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cusLoyaltyId){

                                customer.setCusLoyaltyId(cellValues==null?"":cellValues.toString().trim());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cusMobile){

                                customer.setCusMobile(cellValues==null?"":cellValues.toString().trim());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cusEmail){

                                customer.setCusEmail(cellValues==null?"":cellValues.toString().trim());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cusLocation){

                                //get customer location
                                Long cusLocation = getCustomerLocation(cellValues ==null?"":cellValues.toString().trim(),merchantNo);

                                //customer location information
                                cusLocation =(cusLocation ==0L?user.getUsrLocation():cusLocation);

                                customer.setCusLocation(cusLocation);

                                product.setPrdLocation(cusLocation);

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cusMobileCountryCode){

                                customer.setCusMobileCountryCode(cellValues==null?"":cellValues.toString().trim());

                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.cspAnniversary){

                                Date anniversary = null;

                                if (cellValues !=null){

                                    anniversary =generalUtils.convertToDate(cellValues.toString());

                                    //convert string to anniversary
                                    customerProfile.setCspCustomerAnniversary((java.sql.Date) anniversary);
                                }



                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cspGender){

                                customerProfile.setCspGender(cellValues==null?"":cellValues.toString());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cspBirthDay){

                                Date birthDay = null;

                                if (cellValues !=null){

                                    birthDay =generalUtils.convertToDate(cellValues.toString());

                                    customerProfile.setCspCustomerBirthday((java.sql.Date) birthDay);
                                }


                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cspCity){

                                customerProfile.setCspCity(cellValues==null?"":cellValues.toString());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.cspPinCode){

                                customerProfile.setCspPincode(cellValues == null ? "" : cellValues.toString());
                            }else if (cellContent.intValue()== BulkUploadMappingCodedValues.cspAddress){

                                customerProfile.setCspAddress(cellValues==null?"":cellValues.toString());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.salAmount){

                                sale.setSalAmount(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.salReference){

                                if (!tempPaymentReference.equals("")){

                                    if (!tempPaymentReference.equals(cellValues==null?"":cellValues.toString())){

                                        processSalesAndSaleSku(saleList, saleSkuMap, brandList, productCategoryList, productList);

                                        tempPaymentReference =cellValues==null?"":cellValues.toString();
                                    }

                                }

                                //temp reference
                                if(tempPaymentReference.equals("")){

                                    tempPaymentReference =cellValues==null?"":cellValues.toString();

                                }

                                sale.setSalPaymentReference(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.salQuantity){

                                sale.setSalQuantity(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.salDate){

                                Date saleDate = null;

                                if (cellValues !=null){

                                    saleDate =generalUtils.convertToDate(cellValues.toString());

                                    sale.setSalDate( new java.sql.Date(saleDate.getTime()));
                                }

                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.prdCode){

                                product.setPrdCode(cellValues==null?"":cellValues.toString());

                                saleSKU.setSsuProductCode(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.prdName){

                                product.setPrdName(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.brdCode){

                                brand.setBrnCode(cellValues==null?"":cellValues.toString());

                                saleSKU.setSsuBrand(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.brdName){

                                brand.setBrnName(cellValues==null?"":cellValues.toString());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.pcyCode){

                                productCategory.setPcyCode(cellValues==null?"":cellValues.toString());

                                saleSKU.setSsuCategory1(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.pcyName){

                                productCategory.setPcyName(cellValues==null?"":cellValues.toString());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.ssuQuantity){

                                saleSKU.setSsuQty(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.ssuPrice){

                                saleSKU.setSsuPrice(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));
                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.ssuDiscount){

                                saleSKU.setSsuPrice(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));
                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.ssuDiscount){

                                saleSKU.setSsuPrice(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));
                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.salTime){

                                DateFormat sdf = new SimpleDateFormat("hh:mm");
                                String time =cellValues==null?"":cellValues.toString();

                                try {
                                    Date date1 = sdf.parse(time);
                                    Time saleTime =new Time(date1.getTime());

                                    sale.setSalTime(saleTime);

                                } catch (ParseException e) {

                                    log.info("parse exception"+e);

                                }
                                saleSKU.setSsuPrice(cellValues==null?0.0:Double.parseDouble(cellValues.toString()));
                            }else if (cellContent.intValue() ==BulkUploadMappingCodedValues.csrRefMobile){

                                customerReferral.setCsrRefMobile(cellValues ==null ?"":cellValues.toString().trim());
                            }else if (cellContent.intValue() ==BulkUploadMappingCodedValues.csrRefName){

                                customerReferral.setCsrRefName(cellValues ==null?"":cellValues.toString().trim());
                            }else if (cellContent.intValue() ==BulkUploadMappingCodedValues.csrRefEmail){

                                customerReferral.setCsrRefEmail(cellValues ==null?"":cellValues.toString().trim());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.csrRefAddress){

                                customerReferral.setCsrRefAddress(cellValues ==null?"":cellValues.toString().trim());
                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.csrUserNo){

                                //get user information of referral
                                Long userInfo = getUserInformation(cellValues == null ? "" : cellValues.toString().trim(), merchantNo);

                                //check the user info null or not
                                if(userInfo.longValue() ==0L){

                                    //if its null or zero set current user info
                                    customerReferral.setCsrUserNo(user.getUsrUserNo());

                                }else{

                                    //else set the referral user
                                    customerReferral.setCsrUserNo(userInfo);
                                }

                            }else if(cellContent.intValue() == BulkUploadMappingCodedValues.csrRefNo){

                                //set the referral unit code
                                customerReferral.setCsrRefNo(cellValues ==null?"0":cellValues.toString().trim());

                            }else if (cellContent.intValue() == BulkUploadMappingCodedValues.csrProduct){

                                //get the Product information
                                customerReferral.setCsrProduct(cellValues ==null?"":cellValues.toString().trim());

                            }else if (cellContent.intValue() ==BulkUploadMappingCodedValues.csrLocation){

                                //get the user location
                                Long csrLocation = getCustomerLocation(cellValues ==null?"":cellValues.toString().trim(),merchantNo);

                                //if location is 0 then set user location
                                if (csrLocation ==0L){

                                    customerReferral.setCsrLocation(user.getUsrLocation());
                                }else {

                                    customerReferral.setCsrLocation(csrLocation);
                                }

                            }else if (cellContent.intValue() ==BulkUploadMappingCodedValues.cusRefCode){

                                customer.setReferralCode(cellValues ==null?"":cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cusMerchantUser){

                                //get user login information
                                Long userNo = getUserInformation(cellValues ==null?"":cellValues.toString().trim(),merchantNo);

                                //if user number is present set
                                if(userNo.longValue() !=0L){


                                    customer.setCusMerchantUserRegistered(userNo);
                                }


                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cusIdentificationNo ){

                                //set the customer identification number information
                                customer.setCusIdNo(cellValues == null ? "" : cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cspAddRef1 ){

                                //set the customer additional information
                                customerProfile.setCspAddRef1(cellValues == null ? "" : cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cspAddRef2 ){

                                //set the customer additional   information
                                customerProfile.setCspAddRef2(cellValues == null ? "" : cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cspAddRef3 ){

                                //set the customer additional   information
                                customerProfile.setCspAddRef3(cellValues == null ? "" : cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cspAddRef4 ){

                                //set the customer additional   information
                                customerProfile.setCspAddRef4(cellValues == null ? "" : cellValues.toString().trim());

                            }else if(cellContent.intValue() ==BulkUploadMappingCodedValues.cspAddRef5 ){

                                //set the customer additional   information
                                customerProfile.setCspAddRef5(cellValues ==null?"":cellValues.toString().trim());

                            }




                            //set merchant number
                            customer.setCusMerchantNo(merchantNo);


                    }

                }catch(Exception ex){

                        int rowNumber =rowCounter+1;

                        log.info("Exception Regarding Parsing Xl content  with Row ="+rowNumber +"Column ="+i+ex.getMessage());

                        continue;
                 }

                }

                //first check the customer information for updating sales
                if (customer !=null && customer.getCusLoyaltyId().equals("") && customer.getCusMobile().equals("")){

                    int rowNumber =rowCounter+1;

                    log.info("we can't process this row:"+rowNumber+"due to customer loyalty id and mobile field is null");

                    saveBulkRawData(BulkUploadRawdataStatus.FAILED,"Mobile No and Loyalty Id is null, We need at least one information for processing ","");

                    continue;

                }

              //set customer profile
              customer.setCustomerProfile(customerProfile);

              //check customer mobile is null or not
                if (customer.getCusLoyaltyId().equals("")&& !customer.getCusMobile().equals("")){

                    //check the customer exist or not
                    Customer customer1 =customerService.findByCusMobileAndCusMerchantNo(customer.getCusMobile(),merchantNo==null?0:merchantNo);

                    //if the customer
                    if (customer1 ==null){

                        //first connect merchant with loyalty id is mobile number
                        customer.setCusLoyaltyId(customer.getCusMobile());


                    }else {

                        //set customer loyalty id
                        customer.setCusLoyaltyId(customer1.getCusLoyaltyId());

                        //set the customer number for updating customer data
                        customer.setCusCustomerNo(customer1.getCusCustomerNo());

                        //check the merchant user null or not
                        if(customer.getCusMerchantUserRegistered() ==null || customer.getCusMerchantUserRegistered().longValue() ==0L){

                            customer.setCusMerchantUserRegistered(customer1.getCusMerchantUserRegistered());
                        }

                    }
                }else if(customer.getCusLoyaltyId() !=null && !customer.getCusLoyaltyId().equals("")){

                    //check the customer is existing or not
                    Customer customer3 = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),merchantNo);

                    if(customer3 !=null){

                       //set all details
                       customer.setCusCustomerNo(customer3.getCusCustomerNo());
                       customer.setCusMobile(customer3.getCusMobile());
                       customer.setCusLoyaltyId(customer3.getCusLoyaltyId());
                    }
                }

                //save customer details
                try{

                       Customer customer2 = saveCustomerDetails(customer,customerProfile);

                       if (customer2 !=null){

                           String processingComment = "Customer Updated Successfully with the loyalty id is :"+customer.getCusLoyaltyId();
                           saveBulkRawData(BulkUploadRawdataStatus.PROCESSED_SUCCESSFULLY,processingComment,customer.getCusLoyaltyId());
                       }

                   }catch (InspireNetzException ex){

                       String processingComment =errorCodeToString(ex.getErrorCode())+" for updating customer data with  loyaltyId is "+customer.getCusLoyaltyId();
                       saveBulkRawData(BulkUploadRawdataStatus.WARNING,processingComment,customer.getCusLoyaltyId());

                }


                //save customer referral information
                updateCustomerReferralInfo(customer,customerReferral);

                //add sale information
                if(customer.getCusLocation() ==null || customer.getCusLocation().longValue()==0){

                    //set sale location
                    sale.setSalLocation(user.getUsrLocation() ==null?0L:user.getUsrLocation());
                }else {

                    sale.setSalLocation(customer.getCusLocation());
                }


                //add loyalty id
                sale.setSalLoyaltyId(customer.getCusLoyaltyId());

                //check sale time is null or not
                if(sale.getSalTime() ==null){

                    Timestamp salTimestamp = new Timestamp(System.currentTimeMillis());

                    sale.setSalTime(new Time(salTimestamp.getTime()));
                }

                //set sale merchant no
                sale.setSalMerchantNo(merchantNo);

                //add sale into list
                saleList.add(sale);

                //get the payment reference
                sale.setSalPaymentReference(sale.getSalPaymentReference() ==null?"":sale.getSalPaymentReference());

                //add sku into map
                if (sale.getSalPaymentReference()!=null && !sale.getSalPaymentReference().equals("")){

                    saleSKUMap.put(sale.getSalPaymentReference(),saleSKU);
                }

                //add into map
                saleSkuMap.add(saleSKUMap);

                //add product info into list
                productList.add(product);

                //brand list
                brandList.add(brand);

                //add category list
                productCategoryList.add(productCategory);


          }catch(Exception e){

              int rowNumber =rowCounter+1;

              saveBulkRawData(BulkUploadRawdataStatus.FAILED, "Invalid Data, Possible Pls Check Row No:" +rowNumber, "");

              continue;
          }


        }


    }

    private void updateCustomerReferralInfo(Customer customer, CustomerReferral customerReferral) {

        //update customer referral information first check the customer is already present or not
        Customer customer1 = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),merchantNo);

        //check the customer information if customer information not null
        if(customer1 !=null & customerReferral !=null && customerReferral.getCsrRefMobile() !=null && !customerReferral.getCsrRefMobile().equals("")){

            //set the referrer information and save the data
            customerReferral.setCsrLoyaltyId(customer.getCusLoyaltyId());
            customerReferral.setCsrFName(customer.getCusFName());
            customerReferral.setCsrMerchantNo(merchantNo);

            // check if the customer referral is existing
            CustomerReferral customerReferral2 = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(customerReferral.getCsrMerchantNo(),customerReferral.getCsrLoyaltyId(),customerReferral.getCsrRefMobile());

            //if customer referral exists get the csr id
            if(customerReferral2 != null){

                //set the customer referral id
                customerReferral.setCsrId(customerReferral2.getCsrId());
            }

            //get the row number
            int rowNo =rowCounter+1;

            try{

                //validate and save the customer referral information
                CustomerReferral customerReferral1 = customerReferralService.validateCustomerReferral(customerReferral, user);

                if (customerReferral1 !=null){

                    String processingComment ="Referral data successfully updated, for your information Referrer Loyalty Id : "+customer.getCusLoyaltyId()+" Referee Mobile Number: "+customerReferral1.getCsrRefMobile();
                    saveBulkRawData(BulkUploadRawdataStatus.PROCESSED_SUCCESSFULLY,processingComment,customer.getCusLoyaltyId());

                }

            }catch (InspireNetzException ex){

                log.info("Exception  For updating customer referral information ,pls check row Number"+rowNo+"Exception is:"+ex);

                String processingComment =errorCodeToString(ex.getErrorCode())+" possible pls check the  the referral Data referrer Loyalty Id :"+customer.getCusLoyaltyId();
                saveBulkRawData(BulkUploadRawdataStatus.WARNING,processingComment,customer.getCusLoyaltyId());


            }catch (Exception e){

                log.info("Exception  For updating customer referral information ,pls check row Number"+rowNo+e);

                saveBulkRawData(BulkUploadRawdataStatus.FAILED,"Possible pls check the referral data, Row Number :"+rowNo,customer.getCusLoyaltyId());
            }


        }


    }


    private Long getUserInformation(String userLoginId, Long merchantNo) {

        //check the user name is null or not
        if(userLoginId.equals("")){

            return 0L;
        }

        //get user information
        User user =userService.findByUsrLoginId(userLoginId);

        if(user ==null){

            return 0L;
        }

        return user.getUsrUserNo();
    }

    private Object getCellValues(Object cellValues1) {

        if (cellValues1 !=null){

            String content =cellValues1.toString();
            Object p[];
            p = content.split("&@");

            rowCounter =Integer.parseInt(p[0].toString());

            return p[1];
        }

        return cellValues1;
    }

    private void saveBulkRawData(BulkUploadRawdataStatus status, String processingComment,String loyaltyId) {

        BulkuploadRawdata bulkuploadRawdata1 =new BulkuploadRawdata();

        bulkuploadRawdata1.setBrdMerchantNo(merchantNo);
        bulkuploadRawdata1.setBrdRowindex(rowCounter+1);
        bulkuploadRawdata1.setBrdStatus(status);
        bulkuploadRawdata1.setBrdProcessingComment(processingComment);
        bulkuploadRawdata1.setBrdBatchIndex(bulkUploadBatchInfo.getBlkBatchIndex());
        bulkuploadRawdata1.setBrdLoyaltyId(loyaltyId);
        bulkuploadRawdata1.setBrdDate(new java.sql.Date(new Date().getTime()));
        bulkuploadRawdata1.setBrdTime(new Time(new java.util.Date().getTime()));
        bulkuploadRawdata1.setBrdUserNo(user.getUsrUserNo());

        //save the raw data
        bulkuploadRawdataService.saveRawData(bulkuploadRawdata1);


    }

    /**
     * @purpose:map the sku object and update all other information
     * @param saleList1
     * @param saleSkuMap1
     * @param brandList1
     * @param productCategories1
     * @param productList1
     */

    private void processSalesAndSaleSku(List<Sale> saleList1, List<Map> saleSkuMap1,List<Brand> brandList1,List<ProductCategory> productCategories1,List<Product> productList1){

        Sale sale1 = null;

        Timestamp salTimestamp = new Timestamp(System.currentTimeMillis());

            List<Sale> saleList2 =new ArrayList<>();

            for(Sale sale:saleList1){


                //create sale list after adding sale sku
                Set<SaleSKU> saleSKUSet =new HashSet<>();

                //get sale reference
                String saleReference =sale.getSalPaymentReference()==null?"":sale.getSalPaymentReference();

                //Aggregate field for sku based count amount
                boolean aggregateSku =false;

                double saleQuantity=0;

                double saleAmount=0;

                //if not null iterate sku
                if (!saleReference.equals("")){

                    //get the sale sku information
                    for (Map<String,SaleSKU> map:saleSkuMap){

                        //get map information and put into set
                        try{

                            SaleSKU saleSKU = map.get(saleReference);

                            //check item code is available or not
                            if (!saleSKU.getSsuProductCode().equals("")){

                                //update product information
                                boolean updateProduct = checkSkuUpdation(productList,saleSKU.getSsuProductCode());

                                //if product updation is fine then process the other information like category,brand
                                if (updateProduct){

                                    //update brand and category
                                    updateBrand(brandList,saleSKU.getSsuBrand());

                                    //update category
                                    updateCategory(productCategories1,saleSKU.getSsuCategory1());

                                    //update product
                                    updateProduct(productList, saleSKU.getSsuProductCode(), saleSKU.getSsuBrand(), saleSKU.getSsuCategory1());

                                    //add the sale
                                    saleAmount =saleAmount+saleSKU.getSsuPrice();

                                    //add quantity into
                                    saleQuantity =saleQuantity+saleSKU.getSsuQty();

                                    aggregateSku =true;
                                }

                                //add sale sku in the sku list
                                saleSKUSet.add(saleSKU);
                            }



                        }catch (Exception e){

                            continue;


                        }


                    }

                    //add aggregate quantity into sale object
                    if (aggregateSku){

                        sale.setSalAmount(saleAmount);
                        sale.setSalQuantity(saleQuantity);
                    }

                }

                //add sale sku in sale
                if (saleSKUSet !=null && saleSKUSet.size() >0){

                    sale.setSaleSKUSet(saleSKUSet);
                }


                sale1 =sale;

                //check payment reference
                if(sale.getSalPaymentReference() !=null && !sale.getSalPaymentReference().equals("")){

                    //add into list
                    saleList2.add(sale);
                }


                //break sale information we need to process only one record
              //  break;



        }

        log.info("Start Sales Bulk Upload process");

        try {

            if(saleList2 !=null && saleList2.size()>0){

                String auditDetails =user.getUsrUserNo() +""+user.getUsrLoginId();
                saleService.saveSalesAll(saleList2,auditDetails);
                String processingComment ="Sales Data Added Successfully with the reference no :"+sale1.getSalPaymentReference();
                saveBulkRawData(BulkUploadRawdataStatus.PROCESSED_SUCCESSFULLY,processingComment,sale1.getSalLoyaltyId());
            }



        } catch (InspireNetzException e) {

            String processingComment =errorCodeToString(e.getErrorCode())+" pls check the  the reference no :"+sale1.getSalPaymentReference();
            saveBulkRawData(BulkUploadRawdataStatus.WARNING,processingComment,sale1.getSalLoyaltyId());

        }catch (Exception e){

            int rowNumber =rowCounter+1;

            saveBulkRawData(BulkUploadRawdataStatus.FAILED, "Invalid Data In Sale Data, Possible Pls Check Row No:" + rowNumber, "");
        }

        saleList =new ArrayList<>();
        brandList =new ArrayList<>();
        productCategoryList =new ArrayList<>();
        productList=new ArrayList<>();
        saleSkuMap =new ArrayList<>();

    }

    private void updateProduct(List<Product> productList, String ssuProductCode, String ssuBrand, String ssuCategory1) {

        //find the product code is existing in the in db
       // Long merchantNo =authSessionUtils.getMerchantNo();

        //get product information
        Product product =productService.findByPrdMerchantNoAndPrdCode(merchantNo,ssuProductCode);

        if (product !=null){

            //return true
            return;
        }

        for(Product product1:productList){

           try {

               //if get product information
               if (product1.getPrdCode().equals(ssuProductCode)){

                   product1.setPrdBrand(ssuBrand);

                   product1.setPrdCategory1(ssuCategory1);

                   product1.setPrdName(product1.getPrdName());

                   product1.setPrdMerchantNo(merchantNo);

                   product1.setPrdLocation(product1.getPrdLocation() ==null?user.getUsrLocation():product1.getPrdLocation());

                   //save product information
                   productService.saveProduct(product1);

                   break;
               }
           }catch (InspireNetzException ex){

               log.info("InspireNetz Exception"+ex);
           }catch (Exception e){
               log.info("Normal Exception"+e);
           }

        }


    }

    private void updateCategory(List<ProductCategory> productCategories, String ssuCategory1) {

        //find the product code is existing in the in db
       // Long merchantNo =authSessionUtils.getMerchantNo();

        //check ssuBrand
        ssuCategory1 =ssuCategory1 ==null?"":ssuCategory1;

        if(!ssuCategory1.equals("")){

            //check brand existing or not if existing no need to update
            ProductCategory productCategory =productCategoryService.findByPcyMerchantNoAndPcyCode(merchantNo, ssuCategory1);

            //if not null return else update the brand
            if (productCategory !=null){

                return;
            }

            for(ProductCategory productCategory1 :productCategories){

                //check brand code is existing
                if (productCategory1.getPcyCode().equals(ssuCategory1)){

                    //set pcy name is null if null set brand  code into the brand name
                    String pcyName = (productCategory1.getPcyName() ==null||productCategory1.getPcyName().equals(""))?ssuCategory1:productCategory1.getPcyName();

                    //set brand name
                    productCategory1.setPcyName(pcyName);

                    //set category name
                    productCategory1.setPcyMerchantNo(merchantNo);

                    try{

                        //save brand information
                        productCategoryService.saveProductCategory(productCategory1);

                        //break
                        break;

                    }catch (InspireNetzException ex){

                        log.info("InspireNetz Exception Category Saving :"+ex);
                    }catch (Exception e){

                        log.info("Normal Exception "+e);
                    }



                }
            }
        }
    }

    private void updateBrand(List<Brand> brandList, String ssuBrand) {

        //find the product code is existing in the in db
      //  Long merchantNo =authSessionUtils.getMerchantNo();

        //check ssuBrand
        ssuBrand =ssuBrand ==null?"":ssuBrand;

        if(!ssuBrand.equals("")){

            //check brand existing or not if existing no need to update
            Brand brand =brandService.findByBrnMerchantNoAndBrnCode(merchantNo,ssuBrand);

            //if not null return else update the brand
            if (brand !=null){

                return;
            }

            for(Brand brand1 :brandList){

                //check brand code is existing
                if (brand1.getBrnCode().equals(ssuBrand)){

                    //set brand name is null if null set brand  code into the brand name
                    String brandName = (brand1.getBrnName() ==null||brand1.getBrnName().equals(""))?ssuBrand:brand1.getBrnName();

                    //set brand name
                    brand1.setBrnName(brandName);

                    //set brand merchantno
                    brand1.setBrnMerchantNo(merchantNo);

                    try{

                        //save brand information
                        brandService.saveBrand(brand1);

                    }catch (InspireNetzException ex){

                        log.info("InspireNetz Exception occure for brand saving:"+ex);
                    }catch (Exception e){

                        log.info("Normal Exception "+e);
                    }

                    //break
                    break;

                }
            }
        }



    }

    private boolean checkSkuUpdation(List<Product> productList, String ssuProductCode) {



        //get product information
        Product product =productService.findByPrdMerchantNoAndPrdCode(merchantNo,ssuProductCode);

        if (product !=null){

            //return true
            return true;
        }

        //check product code and product name is existing
        for(Product product1:productList){

            if (product1.getPrdCode().equals(ssuProductCode)){

                String prdName =product1.getPrdName() ==null?"":product1.getPrdName();

                //check product name is existing or not
                if (!prdName.equals("")){

                    //return
                    return true;
                }
            }
        }

        //return false
        return false;

    }

    private Long getCustomerLocation(String cusLocation,Long merchantNo) {

        //get merchant location
        MerchantLocation merchantLocation =merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo, cusLocation);

        if (merchantLocation !=null){

            return merchantLocation.getMelId();
        }

        return 0L;
    }


    private Customer saveCustomerDetails(Customer customer,CustomerProfile customerProfile) throws InspireNetzException {

         Customer customer1 =null;
         //connect merchant

            CustomerResource customerResource =new CustomerResource();

            //check customer number if its exist update customer details
            if (customer !=null && customer.getCusCustomerNo() !=null){

                //set customer number into customer resource for calling update customer
                customerResource.setCusCustomerNo(customer.getCusCustomerNo());

            }

            //check customer not null and merchant user not null then
            if(customer !=null && customer.getCusMerchantUserRegistered() !=null){

                customerResource.setCusMerchantUserRegistered(customer.getCusMerchantUserRegistered());
            }

            customerResource.setCusLoyaltyId(customer.getCusLoyaltyId());
            customerResource.setCusFName(customer.getCusFName());
            customerResource.setCusLName(customer.getCusLName());
            customerResource.setCusMobile(customer.getCusMobile());
            customerResource.setCusEmail(customer.getCusEmail());
            customerResource.setCusMobileCountryCode(customer.getCusMobileCountryCode());
            customerResource.setReferralCode(customer.getReferralCode());


            CustomerProfileResource customerProfileResource =new CustomerProfileResource();
            customerProfileResource.setCspGender(customerProfile.getCspGender());
            customerProfileResource.setCspCustomerAnniversary(customerProfile.getCspCustomerAnniversary());
            customerProfileResource.setCspAddress(customerProfile.getCspAddress());
            customerProfileResource.setCspCustomerBirthday(customerProfile.getCspCustomerBirthday());
            customerProfileResource.setCspCity(customerProfile.getCspCity());
            customerProfileResource.setCspPincode(customerProfile.getCspPincode());
            customerProfileResource.setCspAddRef1(customerProfile.getCspAddRef1());
            customerProfileResource.setCspAddRef2(customerProfile.getCspAddRef2());
            customerProfileResource.setCspAddRef3(customerProfile.getCspAddRef3());
            customerProfileResource.setCspAddRef4(customerProfile.getCspAddRef4());
            customerProfileResource.setCspAddRef5(customerProfile.getCspAddRef5());


            //save customer information
            customer1 =customerService.saveCustomerDetailsFromXl(customerResource,customerProfileResource,merchantNo,user.getUsrUserNo(),user.getUsrLoginId(),user.getUsrLocation());

           return customer1;
    }

    public HashMap<String, String> getHeaderContent(String fileName) {

        //declare map
        HashMap<String,String> headerContent =new HashMap<>();

        //declare counter
        Integer counter =0;

        try
        {

            //After Uploading get Header field
            String uploadRoot = fileUploadService.getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

            FileInputStream file = new FileInputStream(new File(uploadRoot+fileName));

            //Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while(rowIterator.hasNext()) {

                Row row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()) {

                    counter =counter+1;

                    Cell cell = cellIterator.next();

                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            headerContent.put(counter+"",cell.getStringCellValue()+"");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            headerContent.put(counter+"",cell.getNumericCellValue()+"");
                            break;

                        case Cell.CELL_TYPE_STRING:
                            headerContent.put(counter+"",cell.getStringCellValue()+"");

                            break;
                    }
                }

                //break the application
                break;


            }
            //put file name
            headerContent.put("filePath",fileName);

            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return headerContent;
    }




    public String errorCodeToString(APIErrorCode apiErrorCode){

        String errorCode ="";

        if (apiErrorCode ==APIErrorCode.ERR_DUPLICATE_ENTRY){

            errorCode ="Your request causes a duplicate entry in the system";
        }else if (apiErrorCode ==APIErrorCode.ERR_INVALID_INPUT){

            errorCode ="Your request has invalid input. Please check the data";
        }else if (apiErrorCode ==APIErrorCode.ERR_INVALID_INPUT){

            errorCode ="Your request has invalid input. Please check the data";
        }else if(apiErrorCode ==APIErrorCode.ERR_NO_LOYALTY_ID){

            errorCode ="No loyalty id";
        }else if (apiErrorCode ==APIErrorCode.ERR_REFEREE_DUPLICATE){

            errorCode ="Referee is already referred";

        }else if (apiErrorCode ==APIErrorCode.ERR_INVALID_REFERRAL_CODE){

            errorCode ="Invalid Referral Code";
        }


        return errorCode;

    }



}
