package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.AccountArchive;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.AccountArchiveRepository;
import com.inspirenetz.api.core.service.AccountArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class AccountArchiveServiceImpl extends BaseServiceImpl<AccountArchive> implements AccountArchiveService {


    private static Logger log = LoggerFactory.getLogger(AccountArchiveServiceImpl.class);


    @Autowired
    AccountArchiveRepository accountArchiveRepository;


    public AccountArchiveServiceImpl() {

        super(AccountArchive.class);

    }


    @Override
    protected BaseRepository<AccountArchive,Long> getDao() {
        return accountArchiveRepository;
    }



    @Override
    public AccountArchive findByAarId(Long aarId) {

        // Get the accountArchive for the given accountArchive id from the repository
        AccountArchive accountArchive = accountArchiveRepository.findByAarId(aarId);

        // Return the accountArchive
        return accountArchive;


    }




    @Override
    public boolean isDuplicateAccountArchiveExisting(AccountArchive accountArchive) {

        // Get the accountArchive information
        AccountArchive exAccountArchive = accountArchiveRepository.findByAarMerchantNoAndAarOldLoyaltyIdAndAarNewLoyaltyId(accountArchive.getAarMerchantNo(), accountArchive.getAarOldLoyaltyId(), accountArchive.getAarNewLoyaltyId());

        // If the aarId is 0L, then its a new accountArchive so we just need to check if there is ano
        // ther accountArchive code
        if ( accountArchive.getAarId() == null || accountArchive.getAarId() == 0L ) {

            // If the accountArchive is not null, then return true
            if ( exAccountArchive != null ) {

                return true;

            }

        } else {

            // Check if the accountArchive is null
            if ( exAccountArchive != null && accountArchive.getAarId().longValue() != exAccountArchive.getAarId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public AccountArchive saveAccountArchive(AccountArchive accountArchive ){

        // Save the accountArchive
        return accountArchiveRepository.save(accountArchive);

    }

    @Override
    public boolean deleteAccountArchive(Long aarId) {

        // Delete the accountArchive
        accountArchiveRepository.delete(aarId);

        // return true
        return true;

    }

}
