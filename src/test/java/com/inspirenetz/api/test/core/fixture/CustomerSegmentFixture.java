package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.CustomerSegmentType;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.test.core.builder.CustomerSegmentBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 19/5/14.
 */
public class CustomerSegmentFixture {

    public static CustomerSegment standardCustomerSegment() {

        CustomerSegment customerSegment = CustomerSegmentBuilder.aCustomerSegment()
                .withCsgSegmentName("Dynamic Segment 1")
                .withCsgDescription("a dynamic segment for all the customers")
                .withCsgMerchantNo(1L)
                .withCsgSegmentType(CustomerSegmentType.DYNAMIC)
                .build();

        return customerSegment;

    }



    public static CustomerSegment updatedStandardCustomerSegment(CustomerSegment customerSegment) {

        customerSegment.setCsgAmountCompType(CustomerSegmentComparisonType.LESS_THAN);
        return customerSegment;

    }




    public static Set<CustomerSegment> standardCustomerSegments() {

        Set<CustomerSegment> customerSegmentSet = new HashSet<>();

        CustomerSegment customerSegment = CustomerSegmentBuilder.aCustomerSegment()
                .withCsgSegmentName("Dynamic Segment 1")
                .withCsgDescription("a dynamic segment for all the customers")
                .withCsgMerchantNo(1L)
                .withCsgSegmentType(CustomerSegmentType.STATIC)
                .build();

        customerSegmentSet.add(customerSegment);


        CustomerSegment customerSegment2 = CustomerSegmentBuilder.aCustomerSegment()
                .withCsgSegmentName("Static Segment 1")
                .withCsgDescription("a static segment for all the customers")
                .withCsgMerchantNo(1L)
                .withCsgSegmentType(CustomerSegmentType.STATIC)
                .build();

        customerSegmentSet.add(customerSegment2);


        return customerSegmentSet;

    }
}
