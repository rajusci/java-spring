package com.inspirenetz.api.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Created by sandheepgr on 29/5/14.
 */
@Component
public class SegmentationUtils {



    /**
     * Function to get the quarter for a given month
     *
     * @param month - The month number ( 1 to 12)
     *
     *
     * @return - Return the quarter number for the month
     */
    public  int getQuarterForMonth(int month) {

        // Check if month is 0
        if ( month == 0 )  return 0;

        // Else do the calculation
        int quarter = (month/3) + 1;

        // Return the quarter
        return quarter;
    }

}

