package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.TierValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.TierRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.*;
import java.util.Date;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class TierServiceImpl extends BaseServiceImpl<Tier> implements TierService {


    private static Logger log = LoggerFactory.getLogger(TierServiceImpl.class);

    // Maximum customers to be fetched in a Page
    private static final int MAX_ITEMS_IN_PAGE = 50;



    @Autowired
    TierRepository tierRepository;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private Environment environment;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    CustomerActivityService customerActivityService;

    // Maximum customers to be fetched in a Page
    private static final int MAX_CUSTOMERS_IN_PAGE = 50;


    public TierServiceImpl() {

        super(Tier.class);

    }



    @Override
    protected BaseRepository<Tier,Long> getDao() {
        return tierRepository;
    }


    @Override
    public List<Tier> listTiersForGroup(Long merchantNo) {

        // GEt the Tier Groups for the merchant
        List<TierGroup> tierGroupList = tierGroupService.findByTigMerchantNo(merchantNo);

        // The List holding the data to return
        List<Tier> tierList = new ArrayList<>(0);

        // Go through the items in the tierGroup List and add the tierSEt to list
        for(TierGroup tierGroup : tierGroupList ) {

            // Add the set to the list
            tierList.addAll(tierGroup.getTierSet());

        }


        // REturn the list
        return tierList;

    }

    @Override
    public Tier findByTieName(String tieName) {

        // Get the tier for the given name
        Tier tier = tierRepository.findByTieName(tieName);

        // Return the tier
        return tier;

    }

    @Override
    public Tier findByTieId(Long tieId) {

        // Get the tier for the given tier id from the repository
        Tier tier = tierRepository.findByTieId(tieId);

        // Return the tier
        return tier;


    }

    @Override
    public Tier findByTieParentGroupAndTieName(Long tieParentGroup, String tieName) {

        // Get the tier
        Tier tier = tierRepository.findByTieParentGroupAndTieName(tieParentGroup,tieName);

        // Return the tier
        return tier;

    }

    @Override
    public List<Tier> findByTieParentGroup(Long tieParentGroup) {

        // Get the tier list
        List<Tier> tierList = tierRepository.findByTieParentGroup(tieParentGroup);

        // return the tierList
        return tierList;

    }

    @Override
    public boolean isTierCodeDuplicateExisting(Tier tier) {

        //get merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        // Get the tier information
        Tier exTier = tierRepository.findByTieName(tier.getTieName());

        // If the tieId is 0L, then its a new tier so we just need to check if there is ano
        // ther tier code
        if ( tier.getTieId() == null || tier.getTieId() == 0L ) {

            // If the tier is not null, then return true
            if ( exTier != null ) {

                return true;

            }

        } else {

            // Check if the tier is null
            if ( exTier != null && tier.getTieId().longValue() != exTier.getTieId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public boolean isTierRequestAuthorized(Long merchantNo, Long location, Integer userType, Long tieParentGroup) throws InspireNetzException {

        // Flag holding whehter the data is vlaid
        boolean isValid = true;

        // If the tier is null or the parent group is not specified, set isValid to false
        if (tieParentGroup == null || tieParentGroup == 0l) {

            // Log the information
            log.info("isTierRequestAuthorized -> No tier group specified") ;

            // Set as false
            isValid = false;
        }


        // Get the information for the parent
        TierGroup tierGroup = tierGroupService.findByTigId(tieParentGroup);

        // If the tierGroup is null, set the isValie to false
        if ( tierGroup ==  null ) {

            // Log the information
            log.info("isTierRequestAuthorized -> Tier group not found") ;

            // Set as false
            isValid = false;


        }


        // Check if the merchant number is same
        if ( tierGroup.getTigMerchantNo() != merchantNo ) {

            // Log the information
            log.info("isTierRequestAuthorized -> Merchant number does not belong to the group") ;

            // Set as false
            isValid = false;

        }


        // If the userType is not merchant admin we need to make sure that
        // the location is same
        if (userType != UserType.MERCHANT_ADMIN ) {

            // Check if the user location and tierGroup location are same
            if ( location != tierGroup.getTigLocation() ) {

                // Log the information
                log.info("isTierRequestAuthorized -> User is not merchant admin and the tier group is for different location") ;

                // Set as false
                isValid = false;

            }

        }




        if ( !isValid ) {

            // Log the information
            log.info("isTierRequestAuthorized -> Throwing exception") ;

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Return the isValid flag
        return isValid;
    }

    @Override
    public boolean isByCalendarTimeValid(Integer period) {

        // Get the current Date
        Date currDate = new Date();

        // Create the Calendar
        Calendar c = Calendar.getInstance();

        // Set the time as the currDate
        c.setTime(currDate);



        // Check the period and check
        //
        // If the period is daily, then we return true as we are running the evaulation check every day
        if ( period == TierEvaluationPeriod.DAILY ) {

            return true;

        } else if ( period == TierEvaluationPeriod.WEEKLY ) {


            // Get the day of the week for today
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            // if the da of week is saturday ( or a day set in the settings)
            if ( dayOfWeek == Calendar.SATURDAY ){

                return true;

            }

        } else if ( period == TierEvaluationPeriod.MONTHLY ) {

            // Get the last day of the current month
            int lastDayOfMonth = generalUtils.getLastDateOfMonth();

            // Check if the current day of month is lastDayOfMonth
            if ( c.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth ) {

                return true;

            }

        } else if ( period == TierEvaluationPeriod.QUARTERLY ) {

            // convert the current date to day month
            String dayMonth = generalUtils.convertToDayMonthFormat(currDate);

            // Get the dayMonth format for the current quarter
            String dayMonthForQuarter = getDayMonthForCurrentQuarter();

            // Check if both are same
            if ( dayMonth.equals(dayMonthForQuarter) ) {

                return true;

            }

        } else if ( period == TierEvaluationPeriod.YEARLY ){

            // Get the day month for current date
            String dayMonth = generalUtils.convertToDayMonthFormat(currDate);

            // Check if its equal to 31-12
            if ( dayMonth.equals("31-12") ) {

                return true;

            }

        }


        // return false
        return false;

    }

    @Override
    public boolean isByCustomerSpecificDateValid(Customer customer,Integer period) {

        // Get the seedDate
        Date seedDate = new Date(customer.getCusTierLastEvaluated().getTime());

        // Current date
        Date today = new Date();

        // Check the period
        if( period == TierEvaluationPeriod.DAILY ) {

            // return true
            return true;

        } else if ( period == TierEvaluationPeriod.WEEKLY ) {

            // Add 7 days to seedDate and check if the current date is greater than or equal
            // to the resultant date
            if ( generalUtils.addDaysToDate(seedDate,7).compareTo(today) <= 0  ) {

                return true;

            }

        } else if ( period == TierEvaluationPeriod.MONTHLY ) {

            // Add 30 days to seedDate and check if the current date is greater than or equal
            // to the resultant date
            if( generalUtils.addDaysToDate(seedDate,30).compareTo(today) <= 0 ) {

                return true;

            }

        } else if ( period == TierEvaluationPeriod.QUARTERLY ) {

            // Add 90 days to the seedDate and check if the current date is greater than or
            // equal to the resultant date
            if( generalUtils.addDaysToDate(seedDate,90).compareTo(today) <= 0 ) {

                return true;

            }

        } else if ( period == TierEvaluationPeriod.YEARLY ) {

            // Add 365 days to the seedDate and check if the current date is greater than or
            // equal to the resultant date
            if( generalUtils.addDaysToDate(seedDate,365).compareTo(today) <= 0 ) {

                return true;

            }

        }


        // Finally return false
        return false;

    }

    /**
     * Function to check if the tier evaulation period is valid for the periond and tierEvaluation type
     *
     * @param period                    - The period of evaulation
     * @param tierEvaluationPeriodType  - The evaultaion type ( by calenar or customer specific)
     * @return                          - Return true if the period is valid
     *                                    Return false otherwise
     */
    protected boolean isTierEvaluationPeriodValid(Customer customer,Integer period, Integer tierEvaluationPeriodType ) {

        // Check if the period type is calender
        if ( tierEvaluationPeriodType == TierEvaluationPeriodType.BY_CALENDAR ) {

            return isByCalendarTimeValid(period);

        } else if ( tierEvaluationPeriodType == TierEvaluationPeriodType.BY_CUSTOMER_SPECIFIC_DATE ) {

            return isByCustomerSpecificDateValid(customer,period);

        }



        // return false;
        return false;

    }

    @Override
    public boolean isTierValidByApplicableGroup(Customer customer, String tigApplicableGroup ) {

        // Get the subscriptions for customer
        List<CustomerSubscription> customerSubscriptionList =  customerSubscriptionService.findByCsuCustomerNo(customer.getCusCustomerNo());

        // if the list is null, return false
        if ( customerSubscriptionList ==  null || customerSubscriptionList.isEmpty() ) {

            return false;

        }


        // Iterate through each of the list item and then get the Product information
        for(CustomerSubscription customerSubscription : customerSubscriptionList ) {

            // Get the product information for the product code in the customersubscription
            Product product = productService.findByPrdMerchantNoAndPrdCode(customer.getCusMerchantNo(), customerSubscription.getCsuProductCode());

            // If the product is not null, then check check if cateogry code is matching
            if ( product != null ) {

                // Check if any of the category codes are matching
                if ( product.getPrdCategory1().equals(tigApplicableGroup) ||
                     product.getPrdCategory2().equals(tigApplicableGroup) ||
                     product.getPrdCategory3().equals(tigApplicableGroup)
                   ) {

                    // Return true
                    return true;
                }

            }
        }


        // If nothing matches, return false
        return false;

    }


    /**
     * Function to run the tier evaluation for the default merchant
     *
     */
    @Override
    @Scheduled(cron = "${scheduled.tierevaluation}")
    public void runTierEvaluation() {

        // Get All merchant from the system
        List<Merchant> merchantList =merchantService.findAll();

        //iterate merchant and run tier evaluation
        for (Merchant merchant:merchantList){

            // Call the startTierEvaluation for the merchant
            startTierEvaluation(merchant.getMerMerchantNo());

        }


    }

    /**
     * Function to intiate the tier evaulation for all the customers for a merchant
     * @param merchantNo - The merchant number of the merchant
     *
     * @return
     */
    @Override
    public boolean startTierEvaluation(Long merchantNo) {

        // Log the information
        log.info("startTierEvaluation -> Starting tier evaluation for " + merchantNo);

        // Set the pageindex to 0
        int pageIndex = 0;

        // Get the customers for the merchantNo
        Page<Customer> customerPage = customerService.findByCusMerchantNo(merchantNo , generalUtils.constructCustomerPageSpecification(pageIndex, MAX_CUSTOMERS_IN_PAGE));

        // Get the tierGroups for the Merchant
        List<TierGroup> tierGroupList = tierGroupService.findByTigMerchantNo(merchantNo);

        // Check if there are any customers returned
        if ( customerPage == null || !customerPage.hasContent()) {

            // Log the information as no customers for the merchant
            log.info("startTierEvaluation ->  Exiting, no customers");

            // Return true as completed processing
            return true;
        }



        // Repeat the loop till the page has got content
        while( customerPage !=  null && customerPage.hasContent() ) {

            // Repeat through the items in the page as an iterable
            for(Customer customer: customerPage) {

                // Log the customer information being processing
                log.info("startTierEvaluation -> Processing for customer:"+ customer.toString());

                // Process the customer
                evaluateTierForCustomer(customer, tierGroupList);

                // Log the completion
                log.info("startTierEvaluation -> processTierEvaluation : Processing completed for customer no:"+customer.getCusCustomerNo());


            }


            // if there is no next page, then break the loop
            if ( !customerPage.hasNextPage() ) break;

            // Get the next page of customers
            customerPage = customerService.findByCusMerchantNo(merchantNo, generalUtils.constructCustomerPageSpecification(++pageIndex, MAX_CUSTOMERS_IN_PAGE));

        }


        // Finally return true
        return true;
    }



    /**
     * Function to evaulate the tier for a customer
     * Here the function accepts the Customer to be checked and the list of tier groups
     * defined for the merchant
     *
     * @param customer      - The customer object to be evaulated
     * @param tierGroupList - The list of TierGroup objects that are available
     *
     */
    @Override
    public void evaluateTierForCustomer(Customer customer, List<TierGroup> tierGroupList) {


        //get the initial tier
        Long initialTier = customer.getCusTier() == null?0L:customer.getCusTier();

        // Log the staring of processing
        log.info("evaluateTierForCustomer -> Starting processing for customer : " + customer.toString());

        // Get the tierGroup applicable for customer
        TierGroup tierGroup =  getApplicableTierGroupForCustomer(customer,tierGroupList);

        // If there is no tierGroup, then we need to exit and log the error
        if ( tierGroup == null ) {

            // Log the information
            log.info("evaluateTierForCustomer -> No valid tier group found for customer");

            // Return the control
            return ;


        }

        // Log the tierGroup
        log.info("evaluateTierForCustomer -> Selected tier group" + tierGroup.toString());


        // Tier List
        List<Tier> tierList = Lists.newArrayList((Iterable<Tier>)tierGroup.getTierSet());

        // Get the currentTier for the customer
        Tier currTier = getCurrentTierForCustomer(customer, tierList);

        // Current tier for the customer
        log.info("evaluateTierForCustomer -> Current Tier" + currTier);

        // Get the calculatedTier for customer
        Tier calcTier = getCalculatedTierForCustomer(customer,tierList,tierGroup);

        // Current tier for the customer
        log.info("evaluateTierForCustomer -> Calculated  Tier" + calcTier);

        // If the currTier and calcTier is null, then there is something wrong
        if ( currTier == null && calcTier == null ) {

            // Log the information
            log.info("evaluateTierForCustomer -> No tier available for the customer");

            // return the control
            return;

        }


        // If the currTier is null, then we need to update the tier to calcTier
        if ( currTier == null ) {

            // Log the information
            log.info("evaluateTierForCustomer -> Update the custoemr tier to calculated tier " + calcTier.toString());

            // Update the customer tier to calculated tier
            updateCustomerTier(customer, calcTier);

        } else if ( calcTier == null ) {

            // Log the information
            log.info("evaluateTierForCustomer -> No calculated tier available for the customer");

        } else {

            // Get the TierEvaulationResult
            Integer tierEvalResult = getTierEvaluationResult(currTier,calcTier,tierList);

            // Variable holding the evaluation period
            Integer period = 0 ;

            // Check if the result is downgrade
            if ( tierEvalResult == TierEvaluationResult.DOWNGRADE ) {

                // Print the tier eval result
                log.info("evaluateTierForCustomer -> Tier Evaulation Result is downgrade");

                // set the period to downgrade check period
                period = tierGroup.getTigDowngradeCheckPeriod();

                // If the type of evaluation is downgrade, then we need to identify the
                // next lower tier to current tier as the calc tier
                calcTier = getNextLowerTier(currTier,tierList);

            } else if ( tierEvalResult == TierEvaluationResult.UPGRADE ) {

                // Print the tier eval result
                log.info("evaluateTierForCustomer -> Tier Evaulation Result is upgrade");

                period = tierGroup.getTigUpgradeCheckPeriod();

            }

            // Check if its valid time for the operation
            if ( isTierEvaluationPeriodValid(customer,period,tierGroup.getTigEvaluationPeriodCompType()) ) {

                // Print the tier eval result
                log.info("evaluateTierForCustomer -> Tier Evaulation Period is valid, updating customer tier");

                // Update the customer tier
                updateCustomerTier(customer,calcTier);

                //check if the customer's tier has changed
                if(initialTier.longValue() != calcTier.getTieId()){

                    //get the cuaParam value
                    String activityRef = calcTier.getTieId()+"#"+calcTier.getTieName();

                    //add activity for downgrade
                    try {

                            //if changed, log activities
                            if(tierEvalResult == TierEvaluationResult.DOWNGRADE){

                                //add activity for downgrade

                                    //log activity for downgrade
                                    customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.TIER_DOWNGRADE,"Tier has been downgraded to "+calcTier.getTieName(),customer.getCusMerchantNo(),activityRef);


                            } else {

                                //log activity for downgrade
                                customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.TIER_UPGRADE,"Tier has been upgraded to "+calcTier.getTieName(),customer.getCusMerchantNo(),activityRef);                         }

                    } catch (InspireNetzException e) {

                        e.printStackTrace();

                    }

                }


                // If the period comp type is customer specific, then update the
                // last tier evaulated date
                if ( tierGroup.getTigEvaluationPeriodCompType() == TierEvaluationPeriodType.BY_CUSTOMER_SPECIFIC_DATE ) {

                    // set the customers' cusTierLastEvaluated to current date
                    customer.setCusTierLastEvaluated(new java.sql.Date(new Date().getTime()));

                    // Save the customer
                    customer = customerService.saveCustomer(customer);

                }

                // Print the tier eval result
                log.info("evaluateTierForCustomer -> Resetting the accumulated reward balance");

                // Reset the accumulated reward balance
                resetAccumulatedRewardBalance(customer, tierGroup.getTigRewardCurrency());
                

            } else {

                // Log the information
                log.info("evaluateTierForCustomer-> Tier evaulation period is not valid.");
            }
        }
    }


    /**
     * Function to get the applicable tier group for the customer based on the service cateogry
     * customer is availed and the applicable group in the tier group
     *
     * @param customer      - The customer object being evaulated
     * @param tierGroupList - The list of the tier groups that are available to the merchant
     * @return              - The TierGroup object to which the customer is a belonging to
     */
    @Override
    public TierGroup getApplicableTierGroupForCustomer(Customer customer,List<TierGroup> tierGroupList ) {

        // Go through the list of TierGroups
        for ( TierGroup tierGroup : tierGroupList ) {

            // Check if the tier group is valid by the applicable group
           // boolean isValid = isTierValidByApplicableGroup(customer,tierGroup.getTigApplicableGroup());
            // If the group is valid and the location of group is same as customer location,
            // then return the group
            Long location = customer.getCusLocation() ==null?0L:customer.getCusLocation();

            //check tier group exist current location the n return tier group else return default
            if (  tierGroup.getTigLocation().longValue() == location.longValue() ) {

                return tierGroup;

            }

        }

        //else return default tier with group location is none.
        // Go through the list of TierGroups
        for ( TierGroup tierGroup : tierGroupList ) {

           if(tierGroup.getTigLocation().longValue() ==0L){

               return tierGroup;
           }

        }


        // Return null
        return null;

    }


    /**
     * Function to return the tier objecr for the current tier to which the customer belongs
     *
     * @param customer  - The customer object being evaulated
     * @param tierList  - The list of tiers from which we need to find the current tier
     * @return          - REturn the tier if the tier is found in the list
     *                    if the tier is not found, then the customer hs been moved to a different tier group
     *                    and we return null for that.
     */
    @Override
    public Tier getCurrentTierForCustomer(Customer customer, List<Tier> tierList ) {

        // Check if the customer tier is null, then return null
        if ( customer.getCusTier() == null || customer.getCusTier() == 0L ) {

            return null;

        }


        // Go through the list of the tierList and check the tier matcing the
        // tier id in cusTier field
        for(Tier tier : tierList ) {

            // Check if the tieId is matching
            if ( tier.getTieId().longValue() == customer.getCusTier().longValue() ) {

                return tier;

            }

        }


        // Return null
        return null;

    }

    /**
     * Function to get the currently valid tier for the customer
     * This function will apply the rules for the tier and then return tier that is matching
     * If none of the rules are matching, then the tier returned will be the lower tier in the list
     *
     * @param customer  - Customer object being evaulated
     * @param tierList  - The list of tiers to be evalulated
     * @param tierGroup - The parent tier group for the tier list
     * @return          - Return tier that is valide for the customer
     *                    If nothing matches, return the lower tier in the list
     */
    @Override
    public Tier getCalculatedTierForCustomer(Customer customer, List<Tier> tierList,TierGroup tierGroup ) {

        // Order the tier List
        tierList = orderTierList(tierList);

        // We need to start the evaluation from the higher tier first, hence we loop with
        // index as the end and then --
        for ( int i = tierList.size() -1 ; i > 0 ; i-- ) {

            // Get the current tier
            Tier tier = tierList.get(i);

            // Check if the tier is Valid by balance
            boolean isValidByBalance = isTierValidByRewardBalance(customer,tierGroup,tier);

            // Check if the tier is valid by amount
            boolean isValidByAmount = isTierValidByAmount(customer,tierGroup,tier);


            // Check the ruleApplication Type and see
            if ( tier.getTieRuleApplicationType() == RuleApplicationType.EITHER ){

                if ( isValidByBalance || isValidByAmount ) {

                    return tier;

                }

            } else if ( tier.getTieRuleApplicationType() == RuleApplicationType.ALL ) {

                if ( isValidByBalance && isValidByAmount ) {

                    return tier;

                }

            }

        }

        // If nothing matches, then return the lowest tier
        return tierList.get(0);


    }


    /**
     * Function to check if the tier is valid for the customer for the given reward balance
     * The operations are done on the accumulated reward balnace table
     *
     * @param customer      - The customer that need to be checked
     * @param tierGroup     - The parent tier group
     * @param tier          - The tier to be evaulated
     * @return              - True if the tier is valid by amount
     *                        False if the tier is not valid
     */
    @Override
    public boolean isTierValidByRewardBalance(Customer customer,TierGroup tierGroup, Tier tier ) {

        // If the tier comp by reward balance ind is not valid, then we need to return true
        if ( tier.getTiePointInd() != IndicatorStatus.YES ) {

            return false;

        }

        // Check if the point ind is enabled, if not, we
        // return true as the tier is valid for the customer without considering the balance
        if ( tier.getTiePointInd() == IndicatorStatus.NO ) {

            return true;

        }

        // Get the AccumulatedRewradBalance for the customer
        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),tierGroup.getTigRewardCurrency());

        // If the accumulateRewardBalance is null, return false
        if ( accumulatedRewardBalance == null ) {

            return false;

        }


        // check the compType
        if ( tier.getTiePointCompType() == TierCompType.ABOVE ) {

            // Check if the balance is greater than then required tier
            if ( accumulatedRewardBalance.getArbRewardBalance() >= tier.getTiePointValue1() ) {

                return true;

            }

        } else if ( tier.getTiePointCompType() == TierCompType.BETWEEN ) {

            // Check if the balance is between the range
            if ( accumulatedRewardBalance.getArbRewardBalance() > tier.getTiePointValue1() &&
                 accumulatedRewardBalance.getArbRewardBalance() < tier.getTiePointValue2() ) {

                return true;

            }
        }


        // If nothing matches
        return false;

    }


    /**
     * Function to check if the tier is valid for the customer for the amount
     * Here the function will check for the amount calculated for the selected
     * service category against the tiering requirements
     *
     * @param customer      - The customer that need to be checked
     * @param tierGroup     - The parent tier group
     * @param tier          - The tier to be evaulated
     * @return              - True if the tier is valid by amount
     *                        False if the tier is not valid
     */
    @Override
    public boolean isTierValidByAmount(Customer customer, TierGroup tierGroup, Tier tier ) {

        // If the tier comp by amount ind is not valid, then we need to return true
        if ( tier.getTieAmountInd() != IndicatorStatus.YES ) {

            return false;

        }

        // Variable storing the amount
        double amount = 0.0;

        // Get the  customer list
        List<Customer> customerList = accountBundlingUtils.getLinkedCustomers(customer);

        // Get the dateRanges
        Map<String,java.sql.Date> dateMap = getEvaluationDateRange(customer,tierGroup);

        // String prepaid category code
        String prepaidCategory = environment.getProperty("sku.category.prepaid-category-code");

        // String postpaid category code
        String postpaidCategory = environment.getProperty("sku.category.postpaid-category-code");


        // Check the type of the category for tier
        if ( tierGroup.getTigApplicableGroup().equals(prepaidCategory) ) {

            // Get the prepaid topup product code
            String topupProductCode = environment.getProperty("sku.product.prepaid-topup-product-code");

            // Get the consolidated amount for the prepaid
            amount = saleService.getConsolidatedSaleAmountForCustomer(customerList,dateMap.get("startDate"),dateMap.get("endDate"),topupProductCode);

        } else if ( tierGroup.getTigApplicableGroup().equals(postpaidCategory) ) {

            // Get the postpaid bill payment product code
            String billPaymentProductCode = environment.getProperty("sku.product.postpaid-billpayment-product-code");

            // Get the consolidate amount for the postpaid
            amount = saleService.getConsolidatedAmountExceedingMsfForCustomers(customerList,dateMap.get("startDate"),dateMap.get("endDate"),postpaidCategory);

        }


        // check the compType
        if ( tier.getTieAmountCompType() == TierCompType.ABOVE ) {

            // Check if the amount is greater than then required tier
            if ( amount > tier.getTieAmountValue1() ) {

                return true;

            }

        } else if ( tier.getTieAmountCompType() == TierCompType.BETWEEN ) {

            // Check if the amount is between the range
            if ( amount > tier.getTiePointValue1() &&
                 amount < tier.getTiePointValue2() ) {

                return true;

            }
        }

        // Finally return false
        return false;

    }


    /**
     * Function to get the evaulation date ranges for the  customer and tier group
     * being currently evaluated
     *
     * @param customer      - The Customer object being evauluated
     * @param tierGroup     - The tierGroup that is currently evaluated
     *
     * @return              - Map containing the following keys (startDate, endDate)
     */
    protected Map<String, java.sql.Date> getEvaluationDateRange(Customer customer,TierGroup tierGroup) {

        // The Map to return the value
        Map<String,java.sql.Date> dateRanges = new HashMap<String,java.sql.Date>(0);

        // Get the type of the evaluation for the tier upgrade
        Integer period = tierGroup.getTigUpgradeCheckPeriod();

        // Get the tier computation type for the tier upgrade
        Integer periodCompType = tierGroup.getTigEvaluationPeriodCompType();


        // Variable holding the startDate
        Date startDate = null;

        // Variable holding the endDate
        Date endDate = null;

        // Store the seedDate
        Date seedDate = null;

        // Check if the periodCompType is calendar
        if ( periodCompType == TierEvaluationPeriodType.BY_CALENDAR ) {

            // Set the seedDate as today
            seedDate = new Date();

        } else {

            // Set the seedDate as the customer register timestamp
            seedDate = new Date(customer.getCusTierLastEvaluated().getTime());

        }




        // check the period
        if ( period == TierEvaluationPeriod.DAILY ) {

            // Set the startDate and endDate as currentDate
            startDate = endDate = new Date();

        } else if ( period == TierEvaluationPeriod.WEEKLY ) {

            if ( periodCompType == TierEvaluationPeriodType.BY_CALENDAR ) {

                // Set the startDate as 7 days before seed date
                startDate = generalUtils.addDaysToDate(seedDate,-7);

                // Set the endDate as the seedDate
                endDate = seedDate;

            } else {

                // Set the startDate as the seedDate
                startDate = seedDate;

                // Set the enddate as 7 days from seedDAte
                endDate = generalUtils.addDaysToDate(seedDate,7);

            }

        } else if ( period == TierEvaluationPeriod.MONTHLY ) {

            if ( periodCompType == TierEvaluationPeriodType.BY_CALENDAR ) {

                // Set the startDate as 30 days before seed date
                startDate = generalUtils.addDaysToDate(seedDate,-30);

                // Set the endDate as the seedDate
                endDate = seedDate;

            } else {

                // Set the startDate as the seedDate
                startDate = seedDate;

                // Set the enddate as 30 days from seedDAte
                endDate = generalUtils.addDaysToDate(seedDate,30);

            }

        } else if ( period == TierEvaluationPeriod.QUARTERLY ) {

            if ( periodCompType == TierEvaluationPeriodType.BY_CALENDAR ) {

                // Set the startDate as 90 days before seed date
                startDate = generalUtils.addDaysToDate(seedDate,-90);

                // Set the endDate as the seedDate
                endDate = seedDate;

            } else {

                // Set the startDate as the seedDate
                startDate = seedDate;

                // Set the enddate as 30 days from seedDAte
                endDate = generalUtils.addDaysToDate(seedDate,90);

            }

        } else if ( period == TierEvaluationPeriod.YEARLY ) {

            if ( periodCompType == TierEvaluationPeriodType.BY_CALENDAR ) {

                // Set the startDate as 365 days before seed date
                startDate = generalUtils.addDaysToDate(seedDate,-365);

                // Set the endDate as the seedDate
                endDate = seedDate;

            } else {

                // Set the startDate as the seedDate
                startDate = seedDate;

                // Set the enddate as 365 days from seedDAte
                endDate = generalUtils.addDaysToDate(seedDate,365);

            }

        }



        // Put the dates in the map
        dateRanges.put("startDate",new java.sql.Date(startDate.getTime()));

        dateRanges.put("endDate",new java.sql.Date(endDate.getTime()));


        // Return the dateRanges
        return dateRanges;

    }


    /**
     * Function to get the tier evaulation result for the current tier for the customer
     * and the tier to which the customer currently belongs
     *
     * @param currTier  - Current tier of the customer
     * @param calcTier  - The calculated tier for the customer
     * @param tierList  - The list of tiers
     *
     *
     * @return          - Returns the evaulation result ( downgrade/upgrade) based on the
     *                    position of the indexes for respective tiers in the ordered list
     *
     */
    @Override
    public Integer getTierEvaluationResult(Tier currTier, Tier calcTier, List<Tier> tierList ) {

        // Order the list first
        tierList = orderTierList(tierList);

        // The variable holding the index of the currTier ( position)
        int currTierIndex = -1;

        // The variable holding the index of the calculated tier ( position )
        int calcTierIndex = -1;



        // Go through the list of the tiers and then store the index
        for ( int i =0 ; i < tierList.size(); i++ ) {

            // Get the tier at i
            Tier tier = tierList.get(i);

            // Check if the tier is currTier and store the index
            if ( tier.getTieId().longValue() == currTier.getTieId().longValue() ) {

                currTierIndex = i;

            }


            // Check if the tier is calcTierIndex and store the index
            if ( tier.getTieId().longValue() == calcTier.getTieId().longValue() ) {

                calcTierIndex = i;

            }
        }


        // Compare the indexes and then return the evaulation result
        if ( currTierIndex > calcTierIndex ) {

            return TierEvaluationResult.DOWNGRADE;

        } else {

            return TierEvaluationResult.UPGRADE;

        }

    }

    /**
     * Function to order the list of tiers according to the comparator
     *
     * @param tierList  - The list of tiers to be ordered
     * @return          - The ordered list of tiers with lowest comp value at the top
     */
    @Override
    public List<Tier> orderTierList(List<Tier> tierList) {

        // Get the criteriaType
        Integer criteriaType = getTierCriteriaType(tierList);

        // Get the comparator
        TierComparator  tierComparator = new TierComparator(criteriaType);

        // order the list
        Collections.sort(tierList,tierComparator);

        // Return the sortedList
        return tierList;

    }

    /**
     * Function to check if the list of Tier passed is valid to tbe saved
     * This function will check for
     *
     * a) List is not null
     * b) Parent group for all the items in the list are same
     * c) The point rules all have the same comp type
     * d) The amount rules all have the same comp type
     * e) Make sure that if amount field is selected , it is so for all the items
     * f) Make sure that if point field is selected, it is so for all the items in the list
     * @param tierList
     * @return - True if everthing is valid
     *           False if any of the items does not comply
     */
    @Override
    public boolean isTierListValid(List<Tier> tierList) {



        // Check if the list is null
        if ( tierList == null || tierList.isEmpty() ) {

            // Log the information
            log.info("isTierListValid -> List is empty");

            // Return false
            return false;

        }



        // Take the first item as reference itme
        Tier referenceTier = tierList.get(0);

        // Log the reference tier information
        log.info("isTierListValid -> Reference Tier " + referenceTier);

        // Check if the referenceTier value is set for the parentGrup
        if ( referenceTier.getTieParentGroup() == null || referenceTier.getTieParentGroup() == 0L) {

            // Log the information
            log.info("isTierListValid -> Parent group is not specified");

            // Return false
            return false;

        }



        // Go through the list of the remaining items and then check the rules
        for(int i = 1; i < tierList.size() ; i++ ) {

            // Get the current tier
            Tier currentTier = tierList.get(i);

            // Current Tier
            log.info("isTierListValid -> Current Tier" + currentTier.toString());

            // Compare the parentGroup
            if ( currentTier.getTieParentGroup().longValue() != referenceTier.getTieParentGroup().longValue() ) {

                // Log the information
                log.info("isTierListValid -> Parent group does not match");

                // Return false
                return false;

            }



            // Check if the pointInd is same
            if ( currentTier.getTiePointInd().intValue() != referenceTier.getTiePointInd().intValue() ) {

                // Log the information
                log.info("isTierListValid -> Tier point indicator does not match");

                // Return false
                return false;

            }


            // Check if the amountInd is same
            if ( currentTier.getTieAmountInd().intValue() != referenceTier.getTieAmountInd().intValue() ) {

                // Log the information
                log.info("isTierListValid -> Tier amount indicator does not match");

                // Return false
                return false;

            }


            // Check if the point compType is same
            if ( currentTier.getTiePointCompType().intValue() != referenceTier.getTiePointCompType().intValue() ) {

                // Log the information
                log.info("isTierListValid -> Tier point comp type does not match");

                // Return false
                return false;

            }


            // Check if the amount compType is same
            if ( currentTier.getTieAmountCompType().intValue() != referenceTier.getTieAmountCompType().intValue() ) {

                // Log the information
                log.info("isTierListValid -> Tier amount comp type does not match");

                // Return false
                return false;

            }

        }


        // Finally return true
        return true;

    }


    /**
     * Function to clear the accumuldated reward balance for the customer
     * This is usaully done during a tier reset
     *
     * @param customer
     * @param rwdCurrencyId
     * @return
     */
    @Override
    public boolean resetAccumulatedRewardBalance(Customer customer, Long rwdCurrencyId) {

        // Get the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),rwdCurrencyId);

        // Check if the accumulated balance is not null ( this could happen when we are re-registering
        // and the previous unregister would have deleted the table entry
        if ( accumulatedRewardBalance != null ) {

            // Set the balance to 0
            accumulatedRewardBalance.setArbRewardBalance(0.0);

            // Save the balance
            accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);


        }

        // Return true
        return true;

    }


    /**
     * Function to update the customer tier for the customer
     * Here the function accepts the Customer object and the tier object
     * and set the customer cus_tier to tier's tieId
     *
     * @param customer      - The customer object which need to be updated
     * @param tier          - The tier to which customer tier need to be updated
     * @return              - Return true if the update was successfull
     *
     */
    @Override
    public boolean updateCustomerTier(Customer customer, Tier tier) {

        // Set the tier id for the customer
        customer.setCusTier( tier.getTieId() );

        // Save the customer
        customer = customerService.saveCustomer(customer);

        // Return true
        return true;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class,InspireNetzException.class})
    public boolean saveTierList(List<Tier> tierList, Long userNo) throws InspireNetzException {

        // First check if the tierlist is valid
        boolean isValid = isTierListValid(tierList);

        // If the tierList is not valid, then throw exception
        if ( !isValid ) {

            // Log the response
            log.info("saveTierList - Tier list is not valid ");

            // throw new exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }




        // Go through the list of tiers and  then check the validity of data
        for(Tier tier : tierList ) {

            // Create the BeanValidation
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(tier,"tier");

            // Create the validation
            TierValidator validator = new TierValidator();

            // Validate the object
            validator.validate(tier,bindingResult);

            // If the bindingResult has errors, then we need throw the exception
            if ( bindingResult.hasErrors() ) {

                // Log the response
                log.info("saveTierList - Tier is not valid " + tier.toString() );

                // throw new exception
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

            }

        }


        //for deleting for tier Level if its not  in new list
        deleteTierLevel(tierList);

        // Save each tier and see
        for(Tier tier : tierList ) {


            // Set of update the auditDetails
            if ( tier.getTieId() == null ){

                tier.setCreatedBy(Long.toString(userNo));

            } else{

                tier.setUpdatedBy(Long.toString(userNo));
            }

            // Call the saveTier
            tier = saveTier(tier);

            // log the information
            log.info("saveTierList -> Tier object saved : " +tier.toString());

            // Check if the tier is save
            if ( tier.getTieId() == null ) {

                // Log the information
                log.info("saveTierList -> Object was not saved");

                // Throw the error
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

            }

        }


        // Return true
        return true;

    }

    /**
     * @purpose:delete tier level if its not present in new list
     * @param tierList
     */

    private void deleteTierLevel(List<Tier> tierList) {

        //get tier group parent id, all the  parent id in the list is  same so picking the first id
        Tier tierParent = tierList.get(0);

        List<Tier> deletedTierList =new ArrayList<>();

        boolean toDelete=true;

        if(tierParent !=null){

            //get baseTier level list  based on tier parent groupid
            List<Tier> baseList = tierRepository.findByTieParentGroup(tierParent.getTieParentGroup());

            //check new List and parent list is empty or not
            if(baseList !=null && tierList !=null){

                //compare new list and  parent list tier
                for(Tier tierBase : baseList){


                    for(Tier tierNew : tierList){

                        //check tier is present in base List and new list  then update other wise put into delete List
                        Long tierId =tierNew.getTieId() ==null ?0L:tierNew.getTieId();

                        if(tierId !=0L){

                            if(tierBase.getTieId().longValue()==tierNew.getTieId().longValue()){

                                toDelete=false;

                                break;


                            }else{

                                toDelete=true;

                            }

                        }else{


                            continue;

                        }

                    }

                    //if delete flag is true so that tier level is not present in new list and put into delete list
                    if(toDelete==true){

                        deletedTierList.add(tierBase);

                    }

                }

            }

        }

        //log deleting list
        log.info("Deleting  Tier List:----------"+deletedTierList);

        //delete tier level
        for(Tier tier:deletedTierList){


            tierRepository.delete(tier);

        }
    }


    @Override
    public Tier saveTier(Tier tier ) throws InspireNetzException {

        // Check if the tier is existing
        boolean isExist = isTierCodeDuplicateExisting(tier);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveTier - Response : Tier code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }


        // Save the tier
        return tierRepository.save(tier);

    }

    @Override
    public boolean deleteTier(Long tieId) throws InspireNetzException {

        // Delete the tier
        tierRepository.delete(tieId);

        // return true
        return true;

    }

    @Override
    public Tier validateAndSaveTier(Tier tier) throws InspireNetzException {

        //check the user access right
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_TIER_DEFENITIONS);

        return saveTier(tier);
    }

    @Override
    public boolean validateAndDeleteTier(Long tieId) throws InspireNetzException {

        //check the user access right
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_TIER_DEFENITIONS);

        return deleteTier(tieId);
    }


    protected String getDayMonthForCurrentQuarter() {

        // Get the Calendar instance
        Calendar c = Calendar.getInstance();

        // Get the current quarter
        int quarter = (c.get(Calendar.MONTH) / 3 ) + 1;

        // the last date of the quarter
        Date quarterLastDate = null ;

        // Check the quarter
        switch (quarter) {

            case 1 :
                quarterLastDate = generalUtils.convertToDate(c.get(Calendar.YEAR)+"-03-31");
                break;

            case 2 :
                quarterLastDate = generalUtils.convertToDate(c.get(Calendar.YEAR)+"-06-30");
                break;

            case 3 :
                quarterLastDate = generalUtils.convertToDate(c.get(Calendar.YEAR)+"-09-30");
                break;

            case 4 :
                quarterLastDate = generalUtils.convertToDate(c.get(Calendar.YEAR)+"-12-31");
                break;
        }


        // Convert the quarterLastDate to dd-mm format
        return generalUtils.convertToDayMonthFormat(quarterLastDate);

    }

    protected Integer getTierCriteriaType(List<Tier> tierList ) {

        // Get the first item in the list
        Tier referenceTier = tierList.get(0);

        // If the referenceTier has got the balance field set, then
        // return the criteria as balance
        if ( referenceTier.getTiePointInd() == IndicatorStatus.YES ){

            return TierCriteriaType.REWARD_BALANCE;

        } else {

            return TierCriteriaType.AMOUNT;

        }
    }


    /**
     * Function to get the next lower tier for the given tier
     * Function will check the position of the current tier passed and will return the
     * tier lower than that if there is one.
     * If no lower tier exists, then same tier is returned to the calling function
     *
     * @param currTier  - The current tier for the customer
     * @param tierList  - The list of tiers from which the tier need to be identified
     *
     * @return          - Tier with lower value than the current one passed
     */
    public Tier getNextLowerTier( Tier currTier,List<Tier> tierList ) {

        // Order the tiers
        tierList = orderTierList(tierList);

        // Index of current tier
        int currIndex = 0;

        // Iterate through the list and get the lowest tier
        for( Tier tier : tierList ) {

            // Check if the tier is currTier
            if ( tier.getTieId().longValue() == currTier.getTieId().longValue() ) {

                // Set the currIndex as the index
                currIndex = tierList.indexOf(tier);

            }

        }

        // If the currIndex is 0, then there is no lower tier and we need to
        // return the same tier
        if ( currIndex == 0 ) {

            return currTier;

        }

        // Return the tier at the next position
        return tierList.get(currIndex - 1);

    }


    /**
     * The tier comparator for comparing the tiers based on the CriteriaType
     * selected
     */
    private class TierComparator  implements Comparator<Tier> {

        // Variable holding the tierCriteriaType
        private Integer tierCriteriaType;


        public TierComparator(Integer tierCriteriaType) {

            this.tierCriteriaType = tierCriteriaType;

        }


        @Override
        public int compare(Tier a, Tier b) {

            // Check the criteriaType and do the comparison
            if ( tierCriteriaType == TierCriteriaType.REWARD_BALANCE ) {

                // Set the comparing rules
                if ( a.getTiePointValue1() < b.getTiePointValue1() ) {

                    return -1;

                } else if ( a.getTiePointValue1() > b.getTiePointValue1() ) {

                    return 1;

                } else {

                    return 0;

                }

            } else if (tierCriteriaType == TierCriteriaType.AMOUNT ) {


                // Set the comparing rules for the amount
                if ( a.getTieAmountValue1() < b.getTieAmountValue1() ) {

                    return -1;

                } else if ( a.getTieAmountValue1() > b.getTieAmountValue1() ) {

                    return 1;

                } else {

                    return 0;

                }
            }


            // return 0
            return 0;

        }
    }
}
