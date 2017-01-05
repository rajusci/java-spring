package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RewardCurrencyRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class RewardCurrencyServiceImpl extends BaseServiceImpl<RewardCurrency> implements RewardCurrencyService {

    private static Logger log = LoggerFactory.getLogger(RewardCurrencyServiceImpl.class);


    @Autowired
    RewardCurrencyRepository rewardCurrencyRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    public RewardCurrencyServiceImpl() {

        super(RewardCurrency.class);

    }


    @Override
    protected BaseRepository<RewardCurrency,Long> getDao() {

        return rewardCurrencyRepository;

    }

    @Override
    public Page<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<RewardCurrency> rewardCurrencyList = rewardCurrencyRepository.findByRwdMerchantNo(rwdMerchantNo,pageable);

        // Return the list
        return rewardCurrencyList;

    }

    @Override
    public List<RewardCurrency> findByRwdMerchantNo(Long rwdMerchantNo) {

        // Get the data from the repository and store in the list
        List<RewardCurrency> rewardCurrencyList = rewardCurrencyRepository.findByRwdMerchantNo(rwdMerchantNo);

        // Return the list
        return rewardCurrencyList;

    }

    @Override
    public RewardCurrency findByRwdCurrencyId(Long rwdCurrencyId) {

        // Get the rewardCurrency for the given rewardCurrency id from the repository
        RewardCurrency rewardCurrency = rewardCurrencyRepository.findByRwdCurrencyId(rwdCurrencyId);

        // Return the rewardCurrency
        return rewardCurrency;


    }

    @Override
    public Page<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<RewardCurrency> rewardCurrencyList = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyNameLike(rwdMerchantNo, rwdName, pageable);

        // Return the list
        return rewardCurrencyList;

    }

    @Override
    public RewardCurrency findByRwdMerchantNoAndRwdCurrencyName(Long rwdMerchantNo, String rwdName) {

        // Get the rewardCurrency for the given rewardCurrency id from the repository
        RewardCurrency rewardCurrency = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyName(rwdMerchantNo, rwdName);

        // Return the rewardCurrency
        return rewardCurrency; 
    }

    @Override
    public boolean isDuplicateRewardCurrencyNameExisting(RewardCurrency rewardCurrency) {

        // Get the rewardCurrency object for the given name
        RewardCurrency exRewardCurrency = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyName(rewardCurrency.getRwdMerchantNo(),rewardCurrency.getRwdCurrencyName());

        // If the rwdId is 0L, then its a new rewardCurrency so we just need to check if there is ano
        // ther rewardCurrency code
        if ( rewardCurrency.getRwdCurrencyId() == null || rewardCurrency.getRwdCurrencyId() == 0L ) {

            // If the rewardCurrency is not null, then return true
            if ( exRewardCurrency != null ) {

                return true;

            }

        } else {

            // Check if the rewardCurrency is null
            if ( exRewardCurrency != null && rewardCurrency.getRwdCurrencyId().longValue() != exRewardCurrency.getRwdCurrencyId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public HashMap<Long, RewardCurrency> getRewardCurrencyKeyMap(Long rwdMerchantNo) {

        // HashMap holding the data
        HashMap<Long,RewardCurrency> rewardCurrencyHashMap = new HashMap<Long,RewardCurrency>();

        // Get the list of reward currencies for the merchant
        List<RewardCurrency> rewardCurrencyList = rewardCurrencyRepository.findByRwdMerchantNo(rwdMerchantNo);

        // Go through the list and then add the RewardCurrency to the HashMap
        for (RewardCurrency rewardCurrency: rewardCurrencyList) {

            // Add to the HashMap
            rewardCurrencyHashMap.put(rewardCurrency.getRwdCurrencyId(),rewardCurrency);

        }


        // Return the HashMap
        return rewardCurrencyHashMap;

    }


    @Override
    public double getCashbackValue(RewardCurrency rewardCurrency, double rwdQty) {

        // Check if the reward currecy has got the cashbackIndicator set
        if ( rewardCurrency.getRwdCashbackIndicator() == IndicatorStatus.NO ) {

            return 0;

        }

        // Get the ratioDeno
        double ratioDeno = rewardCurrency.getRwdCashbackRatioDeno();

        // If the deno is 0, then return 0
        if ( ratioDeno == 0 ) {

            return 0;

        }

        // Calculate the cashbackValue;
        double cashbackValue = ( 1 * rwdQty ) / ratioDeno;

        // Return the value
        return cashbackValue;

    }

    @Override
    public double getCashbackQtyForAmount(RewardCurrency rewardCurrency, double amount) {

        // Check if the reward currency supports cashback
        if ( rewardCurrency.getRwdCashbackIndicator() == IndicatorStatus.NO ) {

            return -1;

        }


        // Get the ratioDeno
        double ratioDeno = rewardCurrency.getRwdCashbackRatioDeno();

        // Get the reward qty
        double rewardQty = amount * ratioDeno;

        // Return the reward qty
        return rewardQty;

    }

    @Override
    public RewardCurrency saveRewardCurrency(RewardCurrency rewardCurrency ) throws InspireNetzException {


        // Save the rewardCurrency
        return rewardCurrencyRepository.save(rewardCurrency);

    }

    @Override
    public boolean deleteRewardCurrency(Long rwdId) throws InspireNetzException {


        // Delete the rewardCurrency
        rewardCurrencyRepository.delete(rwdId);

        // return true
        return true;

    }

    @Override
    public RewardCurrency validateAndSaveRewardCurrency(RewardCurrency rewardCurrency) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_REWARD_CURRENCY);

        return saveRewardCurrency(rewardCurrency);
    }

    @Override
    public boolean validateAndDeleteRewardCurrency(Long rwdCurrencyId) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_REWARD_CURRENCY);

        return deleteRewardCurrency(rwdCurrencyId);
    }

    @Override
    public Date getRewardExpiryDate(RewardCurrency rewardCurrency, Date rewardDate) {

        // Get the expiry option
        if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.EXPIRY_DATE ) {

            // return the date
            return rewardCurrency.getRwdExpiryDate();


        } else if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.EXPIRY_DAYS ) {

            // Get the Calendar Instance
            Calendar cal = Calendar.getInstance();

            // Increment the date
            cal.add(Calendar.DATE, (rewardCurrency.getRwdExpiryDays() - 1));

            // Get the date after adding the expiry days
            Date date = new Date(cal.getTimeInMillis());

            // Check the expiry period option and if its quarter end, then get the
            // nearest quarter end date for the calculated date
            if ( rewardCurrency.getRwdExpiryPeriod() == RewardCurrencyExpiryPeriod.END_OF_QUARTER ) {

                date = generalUtils.getFinancialQuarterEndDate(date);

            }

            // Return the date for the calendar
            return date;

        } else if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.EXPIRY_MONTHS ) {

            // Get the Calendar Instance
            Calendar cal = Calendar.getInstance();

            // Increment the date
            cal.add(Calendar.MONTH, rewardCurrency.getRwdExpiryDays());

            // Return the date for the calendar
            return new Date(cal.getTimeInMillis());

        } else if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.NO_EXPIRY ) {

            // Get the default date for the db
            try {

                // Get the default date
                Date defaultDate = DBUtils.getDefaultDate();

                // Return the default date
                return defaultDate;


            } catch (ParseException e) {

                return null;

            }

        }


        // If nothing matches, then return null
        return null;

    }

    @Override
    public List<RewardCurrency> findByRwdMerchantNoAndRwdCurrencyNameLike(Long rwdMerchantNo, String rwdName) {

        // Get the data from the repository and store in the list
        List<RewardCurrency> rewardCurrencyList = rewardCurrencyRepository.findByRwdMerchantNoAndRwdCurrencyNameLike(rwdMerchantNo, rwdName);

        // Return the list
        return rewardCurrencyList;

    }

    @Override
    public List<RewardCurrency> listRewardCurrenciesForUser(String usrLoginId,Long rwdMerchantNo, String filter,String query) {

        // Get the data from the repository and store in the list
        List<RewardCurrency> rewardCurrencyList = new ArrayList<>(0);

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);


        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");

            //throw exception
            return null;
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(rwdMerchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return null;

        }
        List<RewardCurrency> rewardCurrencies=null;

        for(Customer customer:customers){

            //check the filter and query
            if ( filter.equals("0") && query.equals("0") ) {

                // Get the page data
                rewardCurrencies = findByRwdMerchantNo(customer.getCusMerchantNo());


            } else if ( filter.equalsIgnoreCase("name") ) {

                // Get the page data
                rewardCurrencies = findByRwdMerchantNoAndRwdCurrencyNameLike(customer.getCusMerchantNo(),"%"+query +"%");


            }

            rewardCurrencyList.addAll(rewardCurrencies);

        }

        // Return the list
        return rewardCurrencyList;

    }

}
