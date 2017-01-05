package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface BrandRepository extends  BaseRepository<Brand,Long> {

    public Page<Brand> findByBrnMerchantNo(Long brnMerchantNo,Pageable pageable);
    public Brand findByBrnId(Long brnId);
    public Brand findByBrnMerchantNoAndBrnCode(Long brnMerchantNo, String brnCode);
    public Page<Brand> findByBrnMerchantNoAndBrnNameLike(Long brnMerchantNo, String brnName,Pageable pageable);

    Page<Brand> findByBrnMerchantNoAndBrnCodeLike(Long brnMerchantNo, String brnCode, Pageable pageable);
    public List<Brand> findByBrnMerchantNo(Long brnMerchantNo);
}
