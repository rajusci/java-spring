package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.segmentation.Segmentation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by sandheepgr on 28/5/14.
 */
public interface SegmentationService {

    public boolean processSegmentation(CustomerSegment customerSegment) throws InspireNetzException;
    public boolean addSegmentMember(CustomerSegment customerSegment,Customer customer) throws InspireNetzException;
    public boolean processCustomer(CustomerSegment customerSegment,Customer customer,Segmentation segmentation);
    public void updateSegmentGenerationProgress(CustomerSegment customerSegment,Page<Customer> customerPage,Long customerCount) throws InspireNetzException;
    public void refreshCustomerMemberSegments(Customer customer) throws InspireNetzException;
    public void removeLowerTierSegments(Customer customer,CustomerSegment customerSegment,Map<Long,SegmentMember> segmentMemberMap) throws InspireNetzException;



}
