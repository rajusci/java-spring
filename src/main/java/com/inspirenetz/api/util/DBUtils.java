package com.inspirenetz.api.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by sandheepgr on 3/5/14.
 */
public class DBUtils {


    // String to be used to pass for a mandatory argument but need to be
    // ignored ( never used value)
    public static final String STRING_IGNORE_COMPARE_FIELD_CONTENT = "#$#$#$#";

    public static final Long   LONG_IGNORE_COMPARE_FIELD_CONTENT = -987654321L;



    /**
     * Function to get the default date that need to be store in the database
     * This is the largest date the database system can handle
     *
     * @return - The Largest date the database system can handle
     *
     * @throws ParseException
     */
    public static Date getDefaultDate() throws ParseException {

        // Create the SimpleDateFormatObject
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse the largest date ( 9999-12-31)
        java.util.Date utilDate = dateFormat.parse("9999-12-31");

        // Return the sql date with the date as utilDate
        return  new Date(utilDate.getTime());

    }

    /**
     * @purpose:convert system date into sql date only and timestamp is 00:00:00
     *
     * @return
     */
    public static Date getSystemDate(){


        java.util.Calendar cal = Calendar.getInstance();

        //system date
        java.util.Date utilDate = new java.util.Date();

        cal.setTime(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        //create sql date
        Date sqlDate =new Date(cal.getTime().getTime());

        return sqlDate;
    }

    /**
     * Function to convert as date string in yyyy-MM-dd format to the
     * java.sql.Date object
     *
     * @param date  - The date string in the yyyy-MM-dd format
     *
     * @return      = Return the java.sql.Date object for the corresponding date passed
     */
    public static Date covertToSqlDate(String date) {

        // Create the SimpleDateFormatObject
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse the date
        java.util.Date utilDate = null;

        try {

            utilDate = dateFormat.parse(date);

        } catch (ParseException e) {

            return null;

        }

        // Return the sql date with the date as utilDate
        return  new Date(utilDate.getTime());

    }


    /**
     *  Function to convert the time string in HH:mm:ss format to the java.sql.Time object
     *
     * @param time  -  Time in HH:mm:ss object
     *
     * @return      - Return the java.sql.Time object
     */
    public static Time convertToSqlTime(String time) {

        // Create the SimpleDateFormat object
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Parse the date
        java.util.Date utilDate = null;

        try {

            utilDate = dateFormat.parse(time);

        } catch (ParseException e) {

            return null;

        }

        // Return the sql date with the date as utilDate
        return  new Time(utilDate.getTime());

    }


    /**
     * Function to convert a date string to a java.sql.Timestamp object.
     *
     *
     * @param timestamp - The string representing the timestamp object
     * @return          - Return null on exception
     *                    Return timestamp object on success.
     */
    public static Timestamp convertToSqlTimestamp(String timestamp) {

        // Create the SimpleDateFormat object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Timestamp variable
        Timestamp sqlTimestamp;

        try {

            // Get the data object
            java.util.Date utilDate = dateFormat.parse(timestamp);

            // Convert to sql timestamp
            sqlTimestamp = new Timestamp(utilDate.getTime());


        } catch (ParseException e) {

            return null;

        }

        // Return the timestamp
        return sqlTimestamp;

    }

    public static long getUniqueId() {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("");
        EntityManager manager = factory.createEntityManager();

        long type = 1;
        String q = "call GetNextUniqueId(?p1)";

        Object o = manager.createNamedQuery(q).setParameter("p1",type).getSingleResult();

        return (long)o;

    }

    public static  String getDateFromTimeStamp(Timestamp timestamp){

        //date format
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));

        String formatDate="";

        try {

            java.util.Date date =  sdfDate.parse(timestamp.toString());

            formatDate =sdfDate.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        //return date object
        return formatDate;
    }


    public static  String getTimeFromTimeStamp(Timestamp timestamp){


        //date format
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));

        String formatDate="";

        try {


            formatDate =sdfTime.format(timestamp);

        } catch (Exception e) {
            e.printStackTrace();
        }



        //return date object
        return formatDate;
    }


}
