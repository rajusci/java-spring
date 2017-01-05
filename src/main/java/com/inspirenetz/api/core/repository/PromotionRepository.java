package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PromotionRepository extends  BaseRepository<Promotion,Long> {

    public Page<Promotion> findByPrmMerchantNo(Long prmMerchantNo, Pageable pageable);
    public Promotion findByPrmId(Long prmId);
    public Promotion findByPrmMerchantNoAndPrmName(Long prmMerchantNo, String prmName);
    public Page<Promotion> findByPrmMerchantNoAndPrmNameLike(Long prmMerchantNo, String prmName, Pageable pageable);
    public List<Promotion> findByPrmMerchantNo(Long prmMerchantNo);
    public List<Promotion> findByPrmMerchantNoAndPrmNameLike(Long prmMerchantNo,String prmName);
    public Page<Promotion> findByPrmTargetedOptionAndPrmNameLike(Integer prmTargetedOption,String query, Pageable pageable);
    public Page<Promotion> findByPrmMerchantNoAndPrmTargetedOptionAndPrmNameLike(Long prmMerchantNo,Integer prmTargetedOption,String query, Pageable pageable);

}
