package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.WalletMerchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 21/4/14.
 */
public interface WalletMerchantRepository extends BaseRepository<WalletMerchant,Long> {

    public Page<WalletMerchant> findAll(Pageable pageable);
    public Page<WalletMerchant> findByWmtLocation(int wmtLocation,Pageable pageable);
    public Page<WalletMerchant> findByWmtNameLike(String wmtName,Pageable pageable);
    public WalletMerchant findByWmtMerchantNo(Long wmtMerchantNo);
    public WalletMerchant findByWmtName(String wmtName);

}
