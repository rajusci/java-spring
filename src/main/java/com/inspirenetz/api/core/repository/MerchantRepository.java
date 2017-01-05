package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.dictionary.MerchantStatus;
import com.inspirenetz.api.core.domain.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantRepository extends  BaseRepository<Merchant,Long> {

    public Merchant findByMerMerchantNo(Long merMerchantNo);
    public Merchant findByMerMerchantNoAndMerMerchantNameLike(Long merMerchantNo,String query);
    public Merchant findByMerMerchantName(String merMerchantName);
    public Merchant findByMerUrlName(String merUrlName);
    public List<Merchant> findByMerMerchantNameOrMerUrlName(String merMerchantName,String merUrlName);
    public Page<Merchant> findByMerMerchantNameLike(String merMerchantName, Pageable pageable);
    public Page<Merchant> findByMerCityLike(String merCity, Pageable pageable);
    public Page<Merchant> findAll(Pageable pageable);
    public Page<Merchant> findByMerStatusAndMerMerchantNameLike(Integer merStatus,String merMerchantName, Pageable pageable);
    public Page<Merchant> findByMerMerchantNoAndMerStatusAndMerMerchantNameLike(Long merMerchantNo,Integer merStatus,String merMerchantName, Pageable pageable);
    public Merchant findByMerMerchantNoAndMerStatusNot(Long merMerchantNo,Integer merStatus);
    public List<Merchant> findByMerStatusNot(Integer merStatus);
    public List<Merchant> findListByMerMerchantNoAndMerStatusNot(Long merMerchantNo,Integer merStatus);

}
