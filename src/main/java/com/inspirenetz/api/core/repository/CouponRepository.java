package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CouponRepository extends  BaseRepository<Coupon,Long> {

    public Page<Coupon> findByCpnMerchantNo(Long cpnMerchantNo, Pageable pageable);
    public Coupon findByCpnCouponId(Long cpnCouponId);
    public Coupon findByCpnMerchantNoAndCpnCouponName(Long cpnMerchantNo,String cpnCouponName);


    @Query("select c from Coupon c where c.cpnMerchantNo=?1 and ( c.cpnCouponCode=?2 or ?2 between c.cpnCouponCodeFrom and c.cpnCouponCodeTo)")
    public Coupon findByCpnMerchantNoAndCpnCouponCode(Long cpnMerchantNo, String cpnCouponCode);
    @Query("select c from Coupon c where c.cpnMerchantNo=?1 and ( (c.cpnCouponCode=?2 or c.cpnCouponCode=?3 ) or ( ?2 between c.cpnCouponCodeFrom and c.cpnCouponCodeTo ) or ( ?3 between c.cpnCouponCodeFrom and c.cpnCouponCodeTo ))")
    public List<Coupon> findSameRangeCoupons(Long cpnMerchantNo, String cpnCouponCodeFrom,String cpnCouponCodeTo);


    public Page<Coupon> findByCpnMerchantNoAndCpnCouponNameLike(Long cpnMerchantNo, String cpnCouponName, Pageable pageable);


}
