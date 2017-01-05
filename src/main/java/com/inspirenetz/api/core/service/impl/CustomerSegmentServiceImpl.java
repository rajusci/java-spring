package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CodedValue;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerSegmentRepository;
import com.inspirenetz.api.core.segmentation.DynamicSegmentation;
import com.inspirenetz.api.core.segmentation.Segmentation;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.SegmentationUtils;
import com.inspirenetz.api.core.segmentation.StaticSegmentation;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CustomerSegmentServiceImpl extends BaseServiceImpl<CustomerSegment> implements CustomerSegmentService {

    private static Logger log = LoggerFactory.getLogger(CustomerSegmentServiceImpl.class);


    @Autowired
    CustomerSegmentRepository customerSegmentRepository;

    @Autowired
    SegmentMemberService segmentMemberService;

    @Autowired
    SegmentationService segmentationService;



    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CodedValueService codedValueService;

    @Autowired
    private SegmentationUtils segmentationUtils;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerSummaryArchiveService customerSummaryArchiveService;




    public CustomerSegmentServiceImpl() {

        super(CustomerSegment.class);

    }

    @Override
    protected BaseRepository<CustomerSegment,Long> getDao() {
        return customerSegmentRepository;
    }

    @Override
    public Page<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo, Pageable pageable) {

        // Get the CustomerSegment page
        Page<CustomerSegment> customerSegmentPage = customerSegmentRepository.findByCsgMerchantNo(csgMerchantNo,pageable);

        // Return the customerSegmentPage
        return customerSegmentPage;

    }

    @Override
    public List<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo) {

        // Get the CustomerSegemnt
        List<CustomerSegment> customerSegmentList = customerSegmentRepository.findByCsgMerchantNo(csgMerchantNo);

        // Return the list
        return customerSegmentList;

    }

    @Override
    public Map<Long, CustomerSegment> getCustomerSegmentMap(List<CustomerSegment> customerSegmentList) {

        // Initialize the map variable holding the data with the key as the segment id and the
        // value as the CustomerSegment.
        Map<Long,CustomerSegment> customerSegmentMap = new HashMap<>(0);

        // Go through each of the List item and then add the items to the map
        for(CustomerSegment customerSegment : customerSegmentList ) {

            // Add the item
            customerSegmentMap.put(customerSegment.getCsgSegmentId(),customerSegment);
        }


        // Return the map
        return customerSegmentMap;

    }

    @Override
    public CustomerSegment findByCsgSegmentId(Long csgSegmentId) {

        // Get the CustomerSegmentInformation
        CustomerSegment customerSegment = customerSegmentRepository.findByCsgSegmentId(csgSegmentId);

        // Return the CustomerSegment
        return customerSegment;

    }

    @Override
    public CustomerSegment findByCsgMerchantNoAndCsgSegmentName(Long csgMerchantNo, String csgSegmentName) {

        // Get the CustomerSegment
        CustomerSegment customerSegment = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentName(csgMerchantNo,csgSegmentName);

        // Return the customersegment
        return customerSegment;

    }

    @Override
    public Page<CustomerSegment> findByCsgMerchantNoAndCsgSegmentNameLike(Long csgMerchantNo, String query, Pageable pageable) {

        // Get the CustomerSegment Page
        Page<CustomerSegment> customerSegmentPage = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentNameLike(csgMerchantNo,query,pageable);

        // Return the customerSegmentPage
        return customerSegmentPage;

    }

    @Override
    public boolean isDuplicateSegmentNameExisting(CustomerSegment customerSegment) {

        // Get the customerSegment information
        CustomerSegment exCustomerSegment = customerSegmentRepository.findByCsgMerchantNoAndCsgSegmentName(customerSegment.getCsgMerchantNo(), customerSegment.getCsgSegmentName());

        // If the csgId is 0L, then its a new customerSegment so we just need to check if there is ano
        // ther customerSegment id
        if ( customerSegment.getCsgSegmentId() == null || customerSegment.getCsgSegmentId() == 0L ) {

            // If the customerSegment is not null, then return true
            if ( exCustomerSegment != null ) {

                return true;

            }

        } else {

            // Check if the customerSegment is null
            if ( exCustomerSegment != null && customerSegment.getCsgSegmentId().longValue() != exCustomerSegment.getCsgSegmentId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Segmentation getSegmentationForCustomerSegment(CustomerSegment customerSegment) {

        // Check the type of the CustomerSegment and the return the Segmentation
        if ( customerSegment.getCsgSegmentType() == CustomerSegmentType.STATIC ) {

            return new StaticSegmentation(customerProfileService,generalUtils);

        } else if ( customerSegment.getCsgSegmentType() == CustomerSegmentType.DYNAMIC ) {

            return new DynamicSegmentation(customerSummaryArchiveService,segmentationUtils);

        }


        // If nothing matches, return null
        return null;

    }



    @Override
    public List<CustomerSegment> getUpgradeTiers(CustomerSegment customerSegment) {


        // Get all the CustomerSegments for the merchant
        List<CustomerSegment> merCustomerSegmentList =  customerSegmentRepository.findByCsgMerchantNo(customerSegment.getCsgMerchantNo());

        // List holding the segments having same type and tier defenitions
        List<CustomerSegment> customerSegmentList = new ArrayList<>(0);

        // Add the current segment to the list
        customerSegmentList.add(customerSegment);

        // Variable holding the comparison criteria
        int compCriteria = getCustomerSegmentComparisonCriteria(customerSegment) ;

        // Go through each of the customer segments defined for the merchant
        for(CustomerSegment merCustomerSegment : merCustomerSegmentList ) {

            // TODO - If the customer segment does not have the auto upgrade option, then continue
            // If we are evaluating the same segment, then we need to skip the iteration
            if ( customerSegment.getCsgSegmentId() == merCustomerSegment.getCsgSegmentId() ) {

                continue;
            }

            // if the current customer segment's auto upgrade tier is not set, then we need to
            // skip the item
            if ( merCustomerSegment.getCsgAutoUpgradeSegment() == IndicatorStatus.NO) {

                continue;

            }



            // If the segmenttype, segment defenition type and criteria type is not matching,
            // then we need to skip the item
            if (    merCustomerSegment.getCsgSegmentType() != customerSegment.getCsgSegmentType() ||
                    merCustomerSegment.getCsgCriteriaType() != customerSegment.getCsgCriteriaType() ||
                    merCustomerSegment.getCsgSegDefType() != customerSegment.getCsgSegDefType() ) {

                // iterate the loop
                continue;

            }



            // Check if the amount type is comparable.
            if (    customerSegment.getCsgAmountCompType() == CustomerSegmentComparisonType.BETWEEN &&
                    customerSegment.getCsgAmountCompType() == merCustomerSegment.getCsgAmountCompType() ) {

                // Add to the list
                customerSegmentList.add(merCustomerSegment);

            } else if ( customerSegment.getCsgVisitCompType() == CustomerSegmentComparisonType.BETWEEN &&
                        customerSegment.getCsgVisitCompType() == merCustomerSegment.getCsgVisitCompType() ) {

                // add to the list
                customerSegmentList.add(merCustomerSegment);

            }  else if ( customerSegment.getCsgQtyCompType() == CustomerSegmentComparisonType.BETWEEN &&
                         customerSegment.getCsgQtyCompType() == merCustomerSegment.getCsgQtyCompType() ) {

                // add to the list
                customerSegmentList.add(merCustomerSegment);

            }

        }


        // Order the list by the comparion criteria
        Collections.sort(customerSegmentList, new CustomerSegmentCriteriaComparator(compCriteria));

        // Return the list
        return customerSegmentList;

    }

    @Override
    public List<CustomerSegment> getLowerTierSegments(List<CustomerSegment> csgTierOrderedList,CustomerSegment customerSegment ) {

        // Create the list holding the segments with lower tier values
        List<CustomerSegment> customerSegmentList = new ArrayList<>(0);

        // Flag showing whether item need to be added
        boolean flgAddItems = false;

        // iterate the loop over the items in the list and then add all the items past
        // customerSegment
        for(CustomerSegment customerSegment1 : csgTierOrderedList ) {

            // Check if the current segment is customerSegment
            // If true, then we don't need to add any more segments to the list
            // as all the coming one's are larger than the passed one
            if ( customerSegment1.getCsgSegmentId().longValue() == customerSegment.getCsgSegmentId().longValue() ) {

              // break the loop
              break;

            }


            // Add the item to the list
            customerSegmentList.add(customerSegment1);

        }


        // Return the list
        return customerSegmentList;

    }

    @Override
    public int getCustomerSegmentComparisonCriteria(CustomerSegment customerSegment) {

        // Check the comparision type and return the ComparitionCriteria
        if (    customerSegment.getCsgAmountCompType() != null &&
                customerSegment.getCsgAmountCompType() != 0 ) {

            return CustomerSegmentComparisonCriteria.AMOUNT;

        } else if ( customerSegment.getCsgVisitCompType() != null &&
                    customerSegment.getCsgVisitCompType() != 0 ) {

            return CustomerSegmentComparisonCriteria.VISIT;

        } else if ( customerSegment.getCsgQtyCompType() != null &&
                    customerSegment.getCsgQtyCompType() != 0 ) {

            return CustomerSegmentComparisonCriteria.QTY;
        }


        return 0;
    }




    @Override
    public CustomerSegment saveCustomerSegment(CustomerSegment customerSegment ) throws InspireNetzException {
        // Save the customerSegment
        return customerSegmentRepository.save(customerSegment);

    }

    @Override
    public boolean deleteCustomerSegment(Long csgId) throws InspireNetzException {
        // Delete the customerSegment
        customerSegmentRepository.delete(csgId);

        // return true
        return true;

    }

    @Override
    public CustomerSegment validateAndSaveCustomerSegment(CustomerSegment customerSegment) throws InspireNetzException {


        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER_SEGMENT);

        //get merchant number form authSession
        Long merchantNo = authSessionUtils.getMerchantNo();

        //save customer segment
        customerSegment =saveCustomerSegment(customerSegment);

        //after saving we need to process customer segmentation
        processCustomerSegment(customerSegment,merchantNo);

        return customerSegment;

    }

    @Override
    public void processCustomerSegment(CustomerSegment customerSegment,Long merchantNo) throws InspireNetzException {


      if(customerSegment.getCsgSegmentType() == CustomerSegmentType.EXPLICIT){

          return;

      }
      //find all customer based on merchant number
      List<Customer> customerList =customerService.getCustomerDetails(merchantNo);

      //get segmentation for customer
      Segmentation segmentation =getSegmentationForCustomerSegment(customerSegment);

      //initialize customer member count
      Long memberCount =0L;

      //iterate customer list and process customer segment with given segment
      for (Customer customer:customerList){

          //process the segment with rule
          boolean processSegment = segmentationService.processCustomer(customerSegment,customer,segmentation);

          //if the process segment is true then update the customer count
          if(processSegment){

            //increment member count if its valid
            memberCount = memberCount+1;

          }else {

              //if its invalid we need to remove this customer exist in segment member list with given segment
              boolean removeMember = segmentMemberService.removeMemberForSegmentUpdations(customerSegment, customer);

              if(removeMember){

                  log.info("CustomerServiceImpl ->Process Customer Segment : remove segment member for segment updations");
              }
          }

      }

      //update member count in customer segment entity
      customerSegment.setCsgCustomerCount(memberCount.longValue());

      //save member count in segment
      saveCustomerSegment(customerSegment);

    }


    @Override
    public boolean validateAndDeleteCustomerSegment(Long csgSegmentId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_CUSTOMER_SEGMENT);

        return deleteCustomerSegment(csgSegmentId);
    }



    /**
     * The comparatory class for comparing two segments based on the criteria type
     * Here we pass the criteria type and the compareTo function will show the
     * result
     */
    private class CustomerSegmentCriteriaComparator  implements Comparator<CustomerSegment> {

        // Variable holding the criterial
        private int compCriteria = 0;

        public CustomerSegmentCriteriaComparator(int compCriteria ) {

            this.compCriteria = compCriteria;

        }

        @Override
        public int compare(CustomerSegment a, CustomerSegment b) {

            // Check the criteria type and do the comparison
            if ( compCriteria == CustomerSegmentComparisonCriteria.AMOUNT ) {

                //  compare the amount values and return the integer represneting the values
                if (    a.getCsgAmountCompValue1() < b.getCsgAmountCompValue1() &&
                        a.getCsgAmountCompValue2() < b.getCsgAmountCompValue2() ) {

                    return -1;

                } else if ( a.getCsgAmountCompValue1() > b.getCsgAmountCompValue1() &&
                            a.getCsgAmountCompValue2() > b.getCsgAmountCompValue2() ) {

                    return 1;

                } else {

                    return 0;

                }

            } else if ( compCriteria == CustomerSegmentComparisonCriteria.VISIT ) {

                // Compare the visit values and return the integer representing the values
                if (    a.getCsgVisitCompValue1() < b.getCsgVisitCompValue1() &&
                        a.getCsgVisitCompValue2() < b.getCsgVisitCompValue2() ) {

                    return -1;

                } else if ( a.getCsgVisitCompValue1() > b.getCsgVisitCompValue1() &&
                            a.getCsgVisitCompValue2() > b.getCsgVisitCompValue2() ) {

                    return 1;

                } else {

                    return 0;

                }

            } else if ( compCriteria == CustomerSegmentComparisonCriteria.QTY ) {

                // Compare the quantity values and return the integer representing the values
                if (    a.getCsgQtyCompValue1() < b.getCsgQtyCompValue1() &&
                        a.getCsgQtyCompValue2() < b.getCsgQtyCompValue2() ) {

                    return -1;

                } else if ( a.getCsgQtyCompValue1() > b.getCsgQtyCompValue1() &&
                            a.getCsgQtyCompValue2() > b.getCsgQtyCompValue2() ) {

                    return 1;

                } else {

                    return 0;

                }
            }


            return 0;

        }
    }

    @Override
    public boolean refreshCustomerSegment() throws InspireNetzException {

        //we need to refresh customer segment why because  otherwise segment data is always constant its only updated for saving
        //get merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        //find all segment for given merchant
        List<CustomerSegment>  customerSegments =findByCsgMerchantNo(merchantNo);

        //iterate list
        for (CustomerSegment customerSegment:customerSegments){

            //process customer segment
            processCustomerSegment(customerSegment,merchantNo);
        }

        log.info("refreshCustomerSegment ->customer segment refresh is successfully completed");

        return  true;

    }

    @Override
    public CustomerSegment addNewMigratedCustomerSegment(String segmentName, Long merchantNo) throws InspireNetzException {

        //get new segment object
        CustomerSegment customerSegment = new CustomerSegment();

        //set values to the object
        customerSegment.setCsgSegmentName(segmentName);
        customerSegment.setCsgMerchantNo(merchantNo);
        customerSegment.setCsgSegmentType(CustomerSegmentType.EXPLICIT);

        //save the segment
        customerSegment = saveCustomerSegment(customerSegment);

        return customerSegment;
    }


}
