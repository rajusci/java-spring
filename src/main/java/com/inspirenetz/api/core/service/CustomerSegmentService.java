package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.segmentation.Segmentation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerSegmentService extends BaseService<CustomerSegment> {


    public Page<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo, Pageable pageable);
    public List<CustomerSegment> findByCsgMerchantNo(Long csgMerchantNo);
    public Map<Long,CustomerSegment> getCustomerSegmentMap(List<CustomerSegment> customerSegmentList);
    public CustomerSegment findByCsgSegmentId(Long csgSegmentId);
    public CustomerSegment findByCsgMerchantNoAndCsgSegmentName(Long csgMerchantNo, String csgSegmentName);
    public Page<CustomerSegment> findByCsgMerchantNoAndCsgSegmentNameLike(Long csgMerchantNo, String query, Pageable pageable);
    public boolean isDuplicateSegmentNameExisting(CustomerSegment customerSegment);
    public Segmentation getSegmentationForCustomerSegment(CustomerSegment customerSegment);


    public List<CustomerSegment> getUpgradeTiers(CustomerSegment customerSegment);
    public List<CustomerSegment> getLowerTierSegments(List<CustomerSegment> csgTierOrderedList,CustomerSegment customerSegment );
    public int getCustomerSegmentComparisonCriteria(CustomerSegment customerSegment);


    public CustomerSegment saveCustomerSegment(CustomerSegment customerSegment) throws InspireNetzException;
    public boolean deleteCustomerSegment(Long csgSegmentId) throws InspireNetzException;

    public CustomerSegment validateAndSaveCustomerSegment(CustomerSegment customerSegment) throws InspireNetzException;
    public boolean validateAndDeleteCustomerSegment(Long csgSegmentId) throws InspireNetzException;

    public void processCustomerSegment(CustomerSegment customerSegment,Long merchantNo) throws InspireNetzException;

    public boolean refreshCustomerSegment() throws InspireNetzException;

    public CustomerSegment addNewMigratedCustomerSegment(String segmentName, Long merchantNo) throws InspireNetzException;
}
