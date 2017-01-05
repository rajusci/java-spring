package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.WalletMerchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 21/4/14.
 */
public interface WalletMerchantService extends BaseService<WalletMerchant> {

    public Page<WalletMerchant> findAll(Pageable pageable);
    public Page<WalletMerchant> findByWmtLocation(int wmtLocation,Pageable pageable);
    public Page<WalletMerchant> findByWmtNameLike(String wmtName,Pageable pageable);
    public WalletMerchant findByWmtMerchantNo(Long wmtMerchantNo);
    public WalletMerchant findByWmtName(String wmtName);
    public boolean isWalletMerchantExisting(WalletMerchant walletMerchant);

    public WalletMerchant saveWalletMerchant(WalletMerchant walletMerchant);
    public boolean deleteWalletMerchant(Long wmtMerchantNo);

}
