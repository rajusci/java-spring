package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.MembershipResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 */
public interface CustomerService extends BaseService<Customer>{

    public Customer findByCusLoyaltyIdAndCusMerchantNo(String cusLoyaltyId,Long cusMerchantNo);
    public Customer findByCusMobileAndCusMerchantNo(String cusMobile,Long cusMerchantNo);
    public Customer findByCusEmailAndCusMerchantNo(String cusEmail,Long cusMerchantNo);
    public Customer findByCusCustomerNo(Long cusCustomerNo);
    public Page<Customer> findByCusMerchantNo(Long cusMerchantNo, Pageable pageable);
    public  boolean updateCustomerUser(Long merchantNo,String loyaltyId ,Long cusUserNo );
    public Page<Customer> findByCusFNameLikeAndCusMerchantNo(String cusFName,Long cusMerchantNo,Pageable pageable);
    public Page<Customer> findByCusLNameLikeAndCusMerchantNo(String cusLName,Long cusMerchantNo,Pageable pageable);
    public List<Customer> findByCusUserNoOrCusEmailOrCusMobile(Long cusUserNo,String cusEmail,String cusMobile);
    public Tier getTierForCustomer(Customer customer);
    public Customer getCustomerInfoForSession() throws InspireNetzException;
    public Page<Customer> searchCustomers(String searchField, String query, Pageable pageable );
    public Customer saveCustomerDetails( CustomerResource customerResource, CustomerProfileResource customerProfileResource ) throws InspireNetzException;
    public List<Customer> getCustomerDetailsByLoyaltyId(String loyaltyId);
    public Customer getCustomerInfo(Long cusCustomerNo) throws InspireNetzException;
    public boolean updateLoyaltyStatus(String loyaltyId,Long merchantNo,Integer status) throws InspireNetzException;
    public boolean transferAccounts(String oldLoyaltyId, String newLoyaltyId ) throws InspireNetzException;
    public Merchant getMerchantForCustomer(Customer customer);
    public List<Customer> findByCusUserNo(Long cusUserNo);
    public List<Customer> getCustomerDetailsBasedOnUserNo(Long cusUserNo);
    public boolean isCustomerValidForTransaction( String loyaltyId , Long merchantNo );

    public List<Customer> getCustomerDetails(Long cusMerchantNo);

    public boolean isDuplicateCustomerExisting(Customer customer);

    public boolean isDuplicateMobileExisting(Customer customer);

    public boolean isDuplicateEmailExisting(Customer customer);

    public Customer saveCustomer(Customer customer);
    public boolean deleteCustomer(Long cusCustomerNo) throws InspireNetzException;

    public Customer validateAndSaveCustomer(Customer customer) throws InspireNetzException;
    public boolean validateAndDeleteCustomer(Long cusCustomerNo) throws InspireNetzException;
    public boolean validateCustomerServiceDetails(Customer customer) throws InspireNetzException;


    public boolean registerCustomerCompatible(String loyaltyId,String password, String firstName, String lastName,String regKey) throws InspireNetzException;
    boolean registerCustomer(String loyaltyId, String password, String firstName, String lastName) throws InspireNetzException;
    boolean confirmCustomerRegistration(String loyaltyId, String otpCode,Long merchantNo) throws InspireNetzException;

    boolean changeNotificationStatus(String loyaltyId,Long merchantNo) throws InspireNetzException;
    boolean whiteListRetailer(String loyaltyId,Long merchantNo) throws InspireNetzException;

    public boolean  isCustomerValidForDTAwarding(CustomerProfile customerProfile,Integer dtType);


    List<Map<String, Object>> getCustomerMemberShips(Long merchantNo, String loyaltyId);

    boolean isRegistrationKeyValid(String loyaltyId, String password, String regKey) throws InspireNetzException;

    public List<Customer> findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(Long cusMerchantNo,String cusLoyaltyId,String cusEmail,String cusMobile);

    public Customer getCustomerProfileCompatible(String cusLoyaltyId,String cusEmail,String cusMobile) throws InspireNetzException;

    public boolean isDuplicateCustomerExistCompatible(Long merchantNo,String cusLoyaltyId,String cusEmail,String cusMobile,Long customerNo);

    public boolean saveCustomerCompatible(Long customerNo,String loyaltyId, String firstName, String lastName, String email, String mobile, String address, String city, String pincode,String birthday,String anniversary ,String referralCode) throws InspireNetzException;

    /*public boolean readCustomerMasterXml(InputStream inputStream) throws InspireNetzException;*/

    public List<Customer> findByCusMobile(String cusMobile);

    public List<Customer> findByCusUserNoAndCusStatus(Long cusUserNo,Integer cusStatus);

    public List<Customer> findByCusUserNoAndCusRegisterStatus(Long cusUserNo,Integer cusRegisterStatus);

    public List<Customer> getUserMemberships(Long cusMerchantNo,Long cusUserNo,Integer cusStatus);

    public Customer validateAndRegisterCustomerThroughXml(Customer customer) throws InspireNetzException;

    public Customer findByCusUserNoAndCusMerchantNoAndCusStatus(Long cusUserNo,Long cusMerchantNo,int cusStatus);

    public void processReferralAwarding(Customer customer) throws InspireNetzException;

    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegistered(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,Pageable pageable);
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusFNameLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,String cusFName,Pageable pageable);
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLNameLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,String cusLName,Pageable pageable);
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusMobileLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,String cusMobile,Pageable pageable);
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusEmailLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,String cusEmail,Pageable pageable);
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLoyaltyIdLike(Long cusMerchantNo,Long cusLocation,Long cusMerchantUserRegistered,String cusLoyaltyId,Pageable pageable);

    public Page<Customer> findByCusMerchantNoAndCusRegisterStatusAndCusLocation(Long cusMerchantNo, Integer cusRegisterStatus,Long cusLocation,Pageable pageable);
    public Page<Customer> findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusFName,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Pageable pageable);
    public Page<Customer> findByCusLNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusLName,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Pageable pageable);
    public Page<Customer> findByCusMobileLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusMobile,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Pageable pageable);
    public Page<Customer> findByCusEmailLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusEmail,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Pageable pageable);
    public Page<Customer> findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusLoyaltyId,Long cusMerchantNo,Integer cusRegisterStatus,Long cusLocation,Pageable pageable);

    public void portalActivationForCustomer(Long merchantNo) throws InspireNetzException;
    public List<Customer> findByCusMerchantNo(Long cusMerchantNo);
    public void processSignUpBonus(Customer customer) throws InspireNetzException;
    public Customer findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatus(String cusLoyaltyId,Long cusMerchantNo,Integer cusRegisterStatus);
    public boolean unRegisterLoyaltyCustomer(String loyaltyId,Long merchantNo,Integer status,String mobileNo, Integer userRequest) throws InspireNetzException;
    public Customer connectMerchant(Long merchantNo,Customer customer,Integer cusRegisterType) throws InspireNetzException;
    public Customer createCustomerObjectForLoyaltyId(String loyaltyId, String mobile,String email,String firstName ,Long merchantNo,Long location, Long usrRegistered,String referralCode);
    public Customer connectMerchantThroughPortal(Long merchantNo,Long usrUserNo,String referralCode) throws InspireNetzException;
    public Customer findByCusUserNoAndCusMerchantNo(Long cusUserNo,Long cusMerchantNo);

    public Customer findByCusUserNoAndCusMerchantNoAndCusRegisterStatus(Long cusUserNo,Long cusMerchantNo,Integer cusRegisterStatus);

    List<String> getEmailInformationForMerchant(Long ntcMerchantNo);

    List<String> getSMSInformationForMerchant(Long ntcMerchantNo);

    public Customer updateCustomerBulkUpload(Customer customer,Long merchantNo);

    public Customer saveCustomerDetailsFromXl(CustomerResource customerResource, CustomerProfileResource customerProfileResource,Long merchantNo,Long userNo,String usrLoginId,Long userLocation) throws InspireNetzException;

    public Customer saveCustomerDetailsGeneric( CustomerResource customerResource, CustomerProfileResource customerProfileResource ) throws InspireNetzException;

    public boolean updateCustomerStatus(Long merchantNo, String LoyaltyId, int cusStatus) throws InspireNetzException;

    List<MembershipResource> getCustomerMemberships(String mobile);
}
