package com.inspirenetz.api.util;


import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.MessageSpielChannel;
import com.inspirenetz.api.core.dictionary.MessageWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.UserType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sandheepgr on 29/5/14.
 */
@Component
public class GeneralUtils {


    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Environment environment;

    /**
     * Function to return the default merchant number
     *
     * @return  - 1L
     */
    public Long getDefaultMerchantNo() {

        return Long.parseLong(environment.getProperty("defaultMerchant"));

    }


    /**
     * Function to check if a particular value is present as token in the provided
     * string of values seperated by delim
     *
     * @param str       - The string which need to be tokenized
     * @param delim     - The delimiter character
     * @param value     - The value that need to be checked for the existence in str
     *
     *
     * @return          - Return true if the value was found as a token
     *                    Return false otherwise
     */
    public boolean isTokenizedValueExists(String str,String delim,String value ) {

        // Instantiate the StringTokenizer class
        StringTokenizer tokenizer = new StringTokenizer(str,delim);

        // Go through the tokens
        while( tokenizer.hasMoreTokens() ) {

            // Get the token
            String token = tokenizer.nextToken();

            // Check if the token is value
            if ( token.equals(value)) {

                return true;

            }
        }


        // Return false
        return false;
    }


    /**
     *
     * Function to get the digest authentication realm from the properties file
     *
     *
     * @return  - Return realm on success,
     *            Return null on failure
     */
    public String getDigestAuthenticationRealm() {

        // Open the resource
        Resource resource = new ClassPathResource("inconfig.properties");

        // // read the properties
        Properties props = new Properties();


        try {

            // Load the resource
            props.load(resource.getInputStream());

        } catch (IOException e) {

            // Print the stact trace
            e.printStackTrace();

            // Return null
            return null;

        }


        // Get the Property
        String realm = props.getProperty("realm");


        // Return
        return realm;


    }


    /**
     * functon to get the passed date a sstring in the yyyy-MM-dd format
     *
     * @param date  - The date to be formatted
     * @return      - The string represntation of the date in the yyyy-MM-dd format
     */
    public String getDateAsString(Date date) {

        // Create the DateFormatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse the date
        String strDate = dateFormat.format(date);

        // Return the strDate
        return strDate;

    }


    /**
     * Function to convert the date into the ISO format
     *
     * @param date  - The date to be formatted
     * @return      - The date in the ISO format as string type
     */
    public String convertToISOFormat(Date date ) {

        // Create the Date formatted
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Convert
        String strDate= dateFormat.format(date);

        // Return the date
        return strDate;

    }

    /**
     * Function to get the last date for the month
     * For eg: For August, we need to return as 31
     *
     *
     * @return  - Return the day of the month
     *
     */
    public Integer getLastDateOfMonth() {


        // Get the date as today
        Date today = new Date();

        // Create the Calendar
        Calendar calendar = Calendar.getInstance();

        // Set the time to today
        calendar.setTime(today);

        // Add 1 to the month
        calendar.add(Calendar.MONTH, 1);

        // Set the day of month to 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Reduce 1 day from the current date
        calendar.add(Calendar.DATE, -1);

        // Return the last day
        return calendar.get(Calendar.DAY_OF_MONTH);

    }


    /**
     * Function to convert the date to dd-mm format
     *
     * @param date     - The date that need to be formatted
     * @return         - Return the formated string
     */
    public String convertToDayMonthFormat(Date date) {

        // Create the SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");

        // convert the date to this formate
        String strDate = dateFormat.format(date);

        // Return the formated string
        return strDate;

    }


    /**
     * Function to convert the date
     * @param strDate - The string date
     * @return        - Return the date object corresponding to the date string
     */
    public Date convertToDate(String strDate) {

        // Create the SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // convert the date to this formate
        Date date = null;

        try {

            date = dateFormat.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Return the formated date
        return date;

    }


    /**
     * Function to get the first date of the current month
     *
     * @return  - Date object representing the first day of the month
     */
    public Date getFirstDateForCurrentMonth() {

        // Get the date as today
        Date today = new Date();

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(today);

        // Set the day of month to 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Return the date object with the time of calendar
        return calendar.getTime();

    }


    /**
     * Function to get the Date object for the last day of current month
     *
     * @return  - Date represneting last date
     */
    public Date getLastDateForCurrentMonth() {


        // Get the date as today
        Date today = new Date();

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(today);

        // Add 1 to the month
        calendar.add(Calendar.MONTH, 1);

        // Set the day of month to 1
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Reduce 1 day from the current date
        calendar.add(Calendar.DATE, -1);

        // Return the date object
        return calendar.getTime();

    }


    /**
     * Add specific number of days from today
     * and return the date object of the calculdate data
     *
     * @param days  - Number of days to be added to today
     * @return      - Return the date object after adding days
     */
    public Date addDaysToToday(int days) {

        // Get the date as today
        Date today = new Date();

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(today);

        // Add days to the calendar
        calendar.add(Calendar.DATE, days);

        // Return the date object from calendar
        return calendar.getTime();

    }


    /**
     * Function to add days to a given date and return the
     * resulting date object
     *
     * @param date  - The date to which days need to be added
     * @param days  - The number of days to be added
     *
     * @return      - The resulting date after the operation
     */
    public Date addDaysToDate(Date date, int days) {

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(date);

        // Add days to the calendar
        calendar.add(Calendar.DATE, days);

        // Return the date object from calendar
        return calendar.getTime();

    }

    public Date clearTimeStamp(Date date) {

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(date);

        // Return the date object from calendar
        return calendar.getTime();

    }

    /**
     * Function to get the fixed key for registrationAuthentication Key generation
     *
     * @return      - Fixed key for registration authentication
     */
    public String getFixedKeyForRegistrationAuthentication() {

        return "DlksdfjIEKSDF923445DSADSFEUSDF89238";

    }


    /**
     * Function to get the day of week for a specified date
     *
     * @param date  - Here we pass the date for which we need to get the day of week
     * @return      - The day of week for the passed date
     */
    public Integer getDayOfWeek(Date date) {

        // Create the Calendar instance
        Calendar calendar =  Calendar.getInstance();

        // Set the time to the date
        calendar.setTime(date);

        // Return the day of week field
        return calendar.get(Calendar.DAY_OF_WEEK);

    }


    /**
     * Function to format date to a given format.
     *
     * @param date      - The date to be formatted
     * @param format    - The format to which the date need to be parsed
     *
     * @return          - Formatted date
     */
    public String convertDateToFormat(Date date , String format) {

        // Create the SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        // Return the date
        return dateFormat.format(date);


    }


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @param numItems  The number of items in a page
     *
     * @return  - The Pageable object with specified values
     */
    public  Pageable constructCustomerPageSpecification(int pageIndex,int numItems) {

        // Create the Pageable object
        Pageable pageSpecification = new PageRequest(pageIndex, numItems, new Sort(Sort.Direction.ASC,"cusCustomerNo"));

        // return the pageSpecification
        return pageSpecification;

    }

    public  Pageable constructMerchantPageSpecification(int pageIndex,int numItems) {

        // Create the Pageable object
        Pageable pageSpecification = new PageRequest(pageIndex, numItems, new Sort(Sort.Direction.ASC,"melLocation"));

        // return the pageSpecification
        return pageSpecification;

    }


    /**
     * Function to get a unique id for a customer .
     * This will accept the loyalty id fo the customer and generate the
     * unique id based on the loyalty id of the customer.
     *
     * @param loyaltyId - The loyalty id of the customer
     *
     * @return          - The unique id for the customer
     */
    public String getUniqueId(String loyaltyId) {

        // If the loyalty id is null, then return null string
        if ( loyaltyId == null ) {

            return null;

        }

        // Get the last 3 characters of loyalty id
        String loyaltyId3Chars = loyaltyId.substring(loyaltyId.length() - 3);

        // Get the time in milliseconds
        Long millis = System.currentTimeMillis() % 1000;

        // Create a Random number generate
        Random rnd = new Random(System.currentTimeMillis());

        // Create a random 3 digit number
        Integer randNum = rnd.nextInt(900) + 100;

        // Create the unique id as
        String uniqueId = loyaltyId3Chars + "" + millis + "" +randNum;

        // Return the unique id
        return uniqueId;

    }


    /**
     * Function to get the formatted value for the passed double value
     * This will make the number without decimal points and also put comma at
     * thousands
     *
     * @param value - The double value that need to be formatted
     *
     * @return      - The value that is formatted with command and removed off decimal points
     */
    public String getFormattedValue(Double value) {

        // Format the double value to integer value
        String retValue = NumberFormat.getIntegerInstance().format(value);

        // Return the retValue
        return retValue;


    }

    /**
     * Function to get the formatted value for the passed double value
     * This will make the number without decimal points and also put comma at
     * thousands
     *
     * @param value - The double value that need to be formatted
     *
     * @return      - The value that is formatted with command and removed off decimal points
     */
    public String getFormattedValue(Double value,String format) {

        NumberFormat formatter = new DecimalFormat(format);

        String retValue = formatter.format(value);

        // Return the retValue
        return retValue;


    }


    /**
     * Function to get the quarter end date for a given date
     *
     * @param date - The date for which we need to find the end date
     *
     * @return  - The date object with the financial quarter date
     */
    public Date getFinancialQuarterEndDate(Date date) {

        // Get the calendar
        Calendar calendar = new GregorianCalendar();

        // Set the time as the date passed
        calendar.setTime(date);

        // Set the fact to be initially 0
        int factor = 0;

        // get the calendar month
        int month = calendar.get(Calendar.MONTH);

        // Set the factor based on the month
        if (month == Calendar.JANUARY
                || month == Calendar.APRIL
                || month == Calendar.JULY
                || month == Calendar.OCTOBER) {
            factor = 2;
        } else if (
                month == Calendar.FEBRUARY
                        || month == Calendar.MAY
                        || month == Calendar.AUGUST
                        || month == Calendar.NOVEMBER) {
            factor = 1;
        } else {
            factor = 0;
        }

        // Add factor months to calendar
        calendar.add(Calendar.MONTH, factor);

        // Set the date
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        // Return the date
        return calendar.getTime();
    }

    public String getLogTextForRequest() {

        //generate the log text
        String logText = "Requested User  # Login Id : "+authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress();

        //return data
        return logText;
    }

    public String getLogTextForResponse(APIResponseObject retData) {

        //generate the log text
        String logText = "Response : " + retData.toString();

        //return data
        return logText;

    }


    public java.sql.Date convertUtilDateToSqlDate(Date date) {

        return new java.sql.Date(date.getTime());

    }


    public UsernamePasswordAuthenticationToken getLocalUserSession() {

        // List holding the authorities
        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

        // Add the role of the user
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Create the authuser
        AuthUser user = new AuthUser("localipuser","", true, true, true , true, AUTHORITIES);

        // Set the usernumber
        user.setUserNo(1L);

        // Set the user loginid
        user.setUserLoginId("localipuser");

        // Set the user type
        user.setUserType(UserType.MERCHANT_USER);

        // Set the merchantNo to 1
        user.setMerchantNo(1L);

        // Set the location to 0
        user.setUserLocation(0L);


        // Create the UsernamePasswordAuthenticateToken object
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());


        // Return the object
        return authentication;

    }

    /**
     * Function to convert string of values seperated by delimiter to integer array
     *
     * @param str       - The string which need to be converted
     * @param delim     - The delimiter character
     *
     *
     * @return          - Return true if the value was found as a token
     *                    Return false otherwise
     */
    public ArrayList<Integer> ConvertStringToIntegerList(String str,String delim){

        // Instantiate the StringTokenizer class
        StringTokenizer tokenizer = new StringTokenizer(str,delim);

        //Integer List
        ArrayList<Integer> result=new ArrayList<>();

        // Go through the tokens
        while( tokenizer.hasMoreTokens() ) {

            // Get the token
            String token = tokenizer.nextToken();

            try{

                //add token after convert into integer
                result.add(Integer.parseInt(token));

            }catch(Exception ex){

            }
        }

        //return result
        return result;
    }

    /**
     * Function to convert string of values seperated by delimiter to integer array
     *
     * @param str       - The string which need to be converted
     * @param delim     - The delimiter character
     *
     *
     * @return          - Return true if the value was found as a token
     *                    Return false otherwise
     */
    public ArrayList<Long> ConvertStringToLongList(String str,String delim){

        // Instantiate the StringTokenizer class
        StringTokenizer tokenizer = new StringTokenizer(str,delim);

        //Integer List
        ArrayList<Long> result=new ArrayList<>();

        // Go through the tokens
        while( tokenizer.hasMoreTokens() ) {

            // Get the token
            String token = tokenizer.nextToken();

            try{

                //add token after convert into integer
                result.add(Long.parseLong(token));

            }catch(Exception ex){

            }
        }

        //return result
        return result;
    }

    /**
     * Function to convert string of values seperated by delimiter to integer array
     *
     * @param spielName   -Spiel name to send
     * @param loyaltyId   -Customer loyalty id
     * @param mobile      -Customer mobile no
     * @param merchantNo  - Merchant no
     * @param fields      - parameters to send
     * @return          - Return message wrapper object
     *                    Return false otherwise
     */
    /*public MessageWrapper getMessageWrapperObject(String spielName,String loyaltyId,String mobile,Long merchantNo,HashMap<String, String> fields){

        //create MessageWrapper object
        MessageWrapper messageWrapper = new MessageWrapper();

        //set parameters
        messageWrapper.setLoyaltyId(loyaltyId);

        if((mobile == null)){

            messageWrapper.setMobile("");
        }
        messageWrapper.setMobile(mobile);
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setSmsParams(fields);
        messageWrapper.setChannel(MessageSpielChannel.SMS);
        messageWrapper.setSpielName(spielName);


        return messageWrapper;

    }*/

    public MessageWrapper getMessageWrapperObject(String spielName,String loyaltyId,String mobile,String email,String subject,Long merchantNo,HashMap<String, String> fields,Integer channel,Integer isCustomer){

        //create MessageWrapper object
        MessageWrapper messageWrapper = new MessageWrapper();

        //set parameters
        messageWrapper.setLoyaltyId(loyaltyId);

        if((mobile == null)){

            messageWrapper.setMobile("");
        }
        messageWrapper.setMobile(mobile);
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setParams(fields);
        messageWrapper.setChannel(channel);
        messageWrapper.setSpielName(spielName);
        messageWrapper.setEmailId(email);
        messageWrapper.setEmailSubject(subject);
        messageWrapper.setIsCustomer(isCustomer);


        return messageWrapper;

    }

    public String generateUniqueCode(int count){

        String uniqueCode=RandomStringUtils.random(6,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");

        return  uniqueCode;
    }


    /**
     * Add specific number of days from today
     * and return the date object of the calculdate data
     *
     * @param months  - Number of days to be added to today
     * @return      - Return the date object after adding days
     */
    public Date addMonthsToDate(Date date, int months) {

        // Get the Calendar instance
        Calendar calendar = Calendar.getInstance();

        //  Set the calendar time to today
        calendar.setTime(date);

        // Add days to the calendar
        calendar.add(Calendar.MONTH, months);

        // Return the date object from calendar
        return calendar.getTime();

    }

}
