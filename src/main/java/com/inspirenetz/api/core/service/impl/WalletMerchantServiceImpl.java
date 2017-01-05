package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.domain.WalletMerchant;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.WalletMerchantRepository;
import com.inspirenetz.api.core.service.WalletMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sandheepgr on 21/4/14.
 */
@Service
public class WalletMerchantServiceImpl extends BaseServiceImpl<WalletMerchant> implements WalletMerchantService {


    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    WalletMerchantRepository walletMerchantRepository;


    public WalletMerchantServiceImpl() {

        super(WalletMerchant.class);

    }


    @Override
    protected BaseRepository<WalletMerchant,Long> getDao() {
        return walletMerchantRepository;
    }


    @Override
    public WalletMerchant findByWmtMerchantNo(Long wmtMerchantNo) {

        // Get the data from the repository and store in the list
        WalletMerchant walletMerchantList = walletMerchantRepository.findByWmtMerchantNo(wmtMerchantNo);

        // Return the list
        return walletMerchantList;

    }



    @Override
    public Page<WalletMerchant> findAll(Pageable pageable) {

        // Get the list of merchants
        Page<WalletMerchant> walletMerchants = walletMerchantRepository.findAll(pageable);

        // Return the list
        return walletMerchants;

    }


    @Override
    public Page<WalletMerchant> findByWmtLocation(int wmtLocation,Pageable pageable) {

        // Get the walletMerchant for the given walletMerchant id from the repository
        Page<WalletMerchant> walletMerchantList = walletMerchantRepository.findByWmtLocation(wmtLocation,pageable);

        // Return the walletMerchant
        return walletMerchantList;


    }



    @Override
    public Page<WalletMerchant> findByWmtNameLike(String wmtName,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<WalletMerchant> walletMerchantList = walletMerchantRepository.findByWmtNameLike(wmtName,pageable);

        // Return the list
        return walletMerchantList;

    }


    @Override
    public WalletMerchant findByWmtName(String wmtName) {

        // Get the data from the repository and store in the list
        WalletMerchant walletMerchant = walletMerchantRepository.findByWmtName(wmtName);

        // Return the list
        return walletMerchant;

    }


    @Override
    public boolean isWalletMerchantExisting(WalletMerchant walletMerchant) {

        // Get the walletMerchant information
        WalletMerchant exWalletMerchant = walletMerchantRepository.findByWmtName(walletMerchant.getWmtName());

        // If the wmtId is 0L, then its a new walletMerchant so we just need to check if there is ano
        // ther walletMerchant code
        if ( walletMerchant.getWmtMerchantNo() == null || walletMerchant.getWmtMerchantNo() == 0L ) {

            // If the walletMerchant is not null, then return true
            if ( exWalletMerchant != null ) {

                return true;

            }

        } else {

            // Check if the walletMerchant is null
            if ( exWalletMerchant != null && walletMerchant.getWmtMerchantNo().longValue() != exWalletMerchant.getWmtMerchantNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }


    @Override
    public WalletMerchant saveWalletMerchant(WalletMerchant walletMerchant ){

        // Save the walletMerchant
        return walletMerchantRepository.save(walletMerchant);

    }

    @Override
    public boolean deleteWalletMerchant(Long wmtId) {

        // Delete the walletMerchant
        walletMerchantRepository.delete(wmtId);

        // return true
        return true;

    }


}
