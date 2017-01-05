package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.AccountArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccountArchiveService extends BaseService<AccountArchive> {

    public AccountArchive findByAarId(Long aarId);
    public boolean isDuplicateAccountArchiveExisting(AccountArchive accountArchive);

    public AccountArchive saveAccountArchive(AccountArchive accountArchive);
    public boolean deleteAccountArchive(Long aarId);


}
