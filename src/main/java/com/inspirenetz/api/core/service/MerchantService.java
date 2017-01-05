package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantService extends BaseService<Merchant> {

    public Merchant findByMerMerchantNo(Long merMerchantNo);
    public Merchant findByMerMerchantNoAndMerMerchantNameLike(Long merMerchantNo,String query);
    public Merchant findByMerMerchantName(String merMerchantName);
    public Merchant findByMerUrlName(String merUrlName);
    public Page<Merchant> searchMerchants(String filter, String query, Pageable pageable);
    public void checkAdminRequestValid(Integer userType) throws InspireNetzException;
    public boolean isDuplicateMerchantExist(Merchant merchant);


    public Merchant saveMerchant(Merchant merchant) throws InspireNetzException;
    public boolean deleteMerchant(Long merId) throws InspireNetzException;

    public Merchant validateAndSaveMerchant(Merchant merchant) throws InspireNetzException;
    public boolean validateAndDeleteMerchant(Long merId) throws InspireNetzException;

    public Page<Merchant> getActiveMerchants(Long merMerchantNo,String query,Pageable pageable);

    public List<Merchant> getConnectedMerchant(Long merchantNo);

    public Merchant findActiveMerchantsByMerMerchantNo(Long merMerchantNo);

    public List<Merchant> findActiveMerchants(Long merchantNo);




}
