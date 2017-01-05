package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface RedemptionMerchantRepository extends  BaseRepository<RedemptionMerchant,Long> {

    public RedemptionMerchant findByRemNo(Long remNo);
    public Page<RedemptionMerchant> findAll(Pageable pageable);

    public RedemptionMerchant findByRemName(String remName);

    public List<RedemptionMerchant> findByRemNameLike(String remName);
    public Page<RedemptionMerchant> findByRemNameLike(String remName,Pageable pageable  );

    public RedemptionMerchant findByRemCode(String remCode);
    public Page<RedemptionMerchant> findByRemCodeLike(String  remCode, Pageable pageable);
    public RedemptionMerchant findByRemVenUrl(String remVenUrl);



}
