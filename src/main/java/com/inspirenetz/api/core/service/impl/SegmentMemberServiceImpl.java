package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.SegmentMemberDetails;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SegmentMemberRepository;
import com.inspirenetz.api.core.service.CustomerSegmentService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class SegmentMemberServiceImpl extends BaseServiceImpl<SegmentMember> implements SegmentMemberService {

    private static Logger log = LoggerFactory.getLogger(SegmentMemberServiceImpl.class);

    @Autowired
    private SegmentMemberRepository segmentMemberRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerSegmentService customerSegmentService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    public SegmentMemberServiceImpl() {

        super(SegmentMember.class);

    }


    @Override
    protected BaseRepository<SegmentMember,Long> getDao() {
        return segmentMemberRepository;
    }


    @Override
    public SegmentMember findBySgmId(Long sgmId) {

        // Get the SegmentMember
        SegmentMember segmentMember = segmentMemberRepository.findBySgmId(sgmId);

        // Return the object
        return segmentMember;

    }

    @Override
    public SegmentMember findBySgmSegmentIdAndSgmCustomerNo(Long sgmSegmentId, Long sgmCustomerNo) {

        // Get the SegmentMember
        SegmentMember segmentMember = segmentMemberRepository.findBySgmSegmentIdAndSgmCustomerNo(sgmSegmentId,sgmCustomerNo);

        // Return the segmentMember
        return segmentMember;

    }

    @Override
    public Page<SegmentMember> findBySgmSegmentId(Long sgmSegmentId, Pageable pageable) {

        // Get the SegmentMember list
        Page<SegmentMember> segmentMemberPage = segmentMemberRepository.findBySgmSegmentId(sgmSegmentId,pageable);


        // Return the page
        return segmentMemberPage;

    }

    @Override
    public List<SegmentMember> findBySgmCustomerNo(Long sgmCustomerNo) {

        // Get the List of SegmentMembers
        List<SegmentMember> segmentMemberList = segmentMemberRepository.findBySgmCustomerNo(sgmCustomerNo);

        // Return the list
        return segmentMemberList;

    }

    @Override
    public Map<Long, SegmentMember> getSegmentMemberMapBySegmentId(List<SegmentMember> segmentMemberList) {

        // Create the Map object holding the segments by segment id
        Map<Long,SegmentMember> segmentMemberMap = new HashMap<>(0);

        // Go through each of the item in the SegmentMemberList and add to the map
        // with the key as the segment id and SegmentMember as the object
        for(SegmentMember segmentMember : segmentMemberList ) {

            // Add the item to the map
            segmentMemberMap.put(segmentMember.getSgmSegmentId(),segmentMember);

        }


        // Return the segmentMember
        return segmentMemberMap;

    }

    @Override
    public boolean isDuplicateSegmentMemberExisting(SegmentMember segmentMember) {

        // Get the segmentMember information
        SegmentMember exSegmentMember = segmentMemberRepository.findBySgmSegmentIdAndSgmCustomerNo(segmentMember.getSgmSegmentId(), segmentMember.getSgmCustomerNo());

        // If the sgmId is 0L, then its a new segmentMember so we just need to check if there is ano
        // ther segmentMember code
        if ( segmentMember.getSgmId() == null || segmentMember.getSgmId() == 0L ) {

            // If the segmentMember is not null, then return true
            if ( exSegmentMember != null ) {

                return true;

            }

        } else {

            // Check if the segmentMember is null
            if ( exSegmentMember != null && segmentMember.getSgmId().longValue() != exSegmentMember.getSgmId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public SegmentMember saveSegmentMember(SegmentMember segmentMember) throws InspireNetzException {

        // Save the segmentMember
        segmentMember = segmentMemberRepository.save(segmentMember);

        // Return the segmentMember object
        return segmentMember;

    }

    @Override
    public boolean deleteSegmentMember(Long catProductNo) throws InspireNetzException {


        // Delete the segmentMember
        segmentMemberRepository.delete(catProductNo);

        // Return true
        return true;
    }

    @Override
    public SegmentMember validateAndSaveSegmentMember(SegmentMember SegmentMember) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER_SEGMENT);

        return saveSegmentMember(SegmentMember);
    }

    @Override
    public boolean validateAndDeleteSegmentMember(Long sgmId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER_SEGMENT);

        return  deleteSegmentMember(sgmId);
    }

    @Override
    public boolean removeMemberForSegmentUpdations(CustomerSegment customerSegment, Customer customer) {

        //find segment member information
        SegmentMember segmentMember = segmentMemberRepository.findBySgmSegmentIdAndSgmCustomerNo(customerSegment.getCsgSegmentId(),customer.getCusCustomerNo());

        //remove segment member from member list if its exist
        if(segmentMember !=null){

            //delete segment member
            segmentMemberRepository.delete(segmentMember);
        }


        return true;

    }

    @Override
    public Page<SegmentMemberDetails> getMemberDetails(Long sgmSegmentId, Pageable pageable) {

        //find segment member based on given segment id
        Page<SegmentMember> segmentMembers = segmentMemberRepository.findBySgmSegmentIdAndSgmMerchantNo(sgmSegmentId,authSessionUtils.getMerchantNo(),pageable);

        //declare segment member details
        Page<SegmentMemberDetails> segmentMemberDetailsPage =null;

        //declare array list
        List<SegmentMemberDetails> segmentMemberDetailsList =new ArrayList<>();

        //declare customer member object
        SegmentMemberDetails segmentMemberDetails =null;

        //iterate segment member list
        for(SegmentMember segmentMember :segmentMembers){

          //find customer details
          Customer customer =getCustomerDetails(segmentMember.getSgmCustomerNo());

          if(customer !=null){

              //create new member details
              segmentMemberDetails = new SegmentMemberDetails();

              //set all values in segment member object
              segmentMemberDetails.setSmLoyaltyId(customer.getCusLoyaltyId());

              segmentMemberDetails.setSmFName(customer.getCusFName());

              segmentMemberDetails.setSmLName(customer.getCusLName());

              segmentMemberDetails.setSmEmail(customer.getCusEmail());

              segmentMemberDetails.setSmActive(customer.getCusStatus());

              //put into member details list
              segmentMemberDetailsList.add(segmentMemberDetails);
          }


        }

        //put list item ino page
        segmentMemberDetailsPage =new PageImpl<>(segmentMemberDetailsList);

        //return member page
        return segmentMemberDetailsPage;

    }

    private Customer getCustomerDetails(Long sgmCustomerNo) {

        return customerService.findByCusCustomerNo(sgmCustomerNo);
    }

    @Override
    public SegmentMember assignCustomerToSegment(String loyaltyId, String segmentName, Long merchantNo) throws InspireNetzException {

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        if(customer == null){

            //log error
            log.error("assignSegmentToCustomer : No customer information found LoyaltyID : "+loyaltyId+" Segment Name: "+ segmentName+ "Merchant No :"+merchantNo);

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

        }

        //get the segment information
        CustomerSegment customerSegment = customerSegmentService.findByCsgMerchantNoAndCsgSegmentName(merchantNo,segmentName);

        //check segment is valid
        if(customerSegment == null){

            //add new migrated segment
           customerSegment = customerSegmentService.addNewMigratedCustomerSegment(segmentName,merchantNo);

        }

        //check if the memeber segment already exists
        SegmentMember segmentMember = findBySgmSegmentIdAndSgmCustomerNo(customerSegment.getCsgSegmentId(),customer.getCusCustomerNo());

        if(segmentMember != null){

            //log error
            log.error("Customer is already a member of the segment LoyaltyID:"+loyaltyId+" SegmentName:"+segmentName+ " Merchant No"+merchantNo);

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        //get the segment member
        segmentMember = getSegmentMemberObject(customer, customerSegment);

        //save the segment member
        segmentMember = saveSegmentMember(segmentMember);

        Long segmentMemberCount = customerSegment.getCsgCustomerCount();

        customerSegment.setCsgCustomerCount(++segmentMemberCount);

        customerSegment = customerSegmentService.saveCustomerSegment(customerSegment);

        //return result
        return segmentMember;


    }

    private SegmentMember getSegmentMemberObject(Customer customer, CustomerSegment customerSegment) {

        SegmentMember segmentMember = new SegmentMember();

        segmentMember.setSgmCustomerNo(customer.getCusCustomerNo());
        segmentMember.setSgmSegmentId(customerSegment.getCsgSegmentId());
        segmentMember.setSgmMerchantNo(customer.getCusMerchantNo());

        return segmentMember;
    }

}
