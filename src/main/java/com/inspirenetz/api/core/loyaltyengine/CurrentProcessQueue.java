package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.CurrentProcessQueueItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sandheepgr on 29/4/16.
 */
@Component
public class CurrentProcessQueue extends ConcurrentHashMap<String,CurrentProcessQueueItem> {

    private static final Logger log = LoggerFactory.getLogger(CurrentProcessQueue.class);

    public CurrentProcessQueue() {

        super();

    }


    /**
     * Function to add the current customer to queue
     * This will create the CurrentProcessQueueItem and add to the map
     * if its not already existing
     *
     * @param key   : The key for the item
     */
    public synchronized boolean addCurrentItem(String key) {

        // Check if the item is already exist
        if ( isItemProcessing(key) ) {

            return false;

        }

        // Create the item
        CurrentProcessQueueItem currentProcessQueueItem = new CurrentProcessQueueItem();
        currentProcessQueueItem.setKey(key);
        currentProcessQueueItem.setExpiry(getExpiryDate());

        // Add the item to the queue
        putIfAbsent(key, currentProcessQueueItem);

        // return true
        return true;


    }

    /**
     * Method to check if
     * @param key
     * @return
     */
    public boolean isItemProcessing(String key) {

        // Check if the item is contained
        // If no key exists, return false
        if ( !containsKey(key) ) {

            return false;

        }

        // If the key exists, get the entry
        CurrentProcessQueueItem currentProcessQueueItem = get(key);

        // Check if expired
        if ( isExpired(currentProcessQueueItem) ) {

            // remove the entry
            remove(key);

            // return false;
            return false;

        }


        // return true;
        return true;

    }

    /**
     * Method to remove the item from the queue
     *
     * @param key   : The key of the item to be removed.
     */
    public synchronized void removeItem(String key) {

        // Remove the item
        remove(key);

    }

    /**
     * Method to get the expiry date for the entry
     * The method returns a Date object with the time as 5 minutes added to the current time
     *
     * @return: Date object with the time as 5 minutes added to the current time
     */
    public Date getExpiryDate() {

        // Get the calendar instance
        Calendar cal = Calendar.getInstance();

        // Set the time as the current date
        cal.setTime(new Date());

        // Add the minutes
        cal.add(Calendar.MINUTE, 5);

        // Return the new date with the time from the cal
        return cal.getTime();

    }

    /**
     * Method to check if the item is expired
     *
     * @param currentProcessQueueItem  : The currentProcessQueueItem
     * @return                          : Return true if expired
     *                                    Return false if not expired
     */
    public boolean isExpired(CurrentProcessQueueItem currentProcessQueueItem) {

        // Check if the expiry date is less than the current date
        if ( currentProcessQueueItem.getExpiry().compareTo(new Date()) < 0 ) {

            return true;

        } else {

            return false;

        }

    }

}

