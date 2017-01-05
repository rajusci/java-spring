package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PartnerCatalogueResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface PartnerCatalogueService extends BaseService<PartnerCatalogue> {


    public PartnerCatalogue findByPacId(Long pacId) throws InspireNetzException;
    public PartnerCatalogue findByPacCodeAndPacPartnerNo(String pacCode,Long pacPartnerNo);
    public Page<PartnerCatalogue> findByPacPartnerNo(Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacCategoryAndPacPartnerNo(Integer pacCategory,Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacStatusAndPacPartnerNo(Integer pacStatus,Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacCategoryAndPacStatusAndPacPartnerNo(Integer pacCategory,Integer pacStatus,Long pacPartnerNo,Pageable pageable);
    public List<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName,Long pacPartnerNo);
    public Page<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName,Long pacPartnerNo,Pageable pageable);

    public Page<PartnerCatalogue> findByPacCodeLikeAndPacPartnerNo(String pacCode,Long pacPartnerNo,Pageable pageable);
    public PartnerCatalogue validateAndSavePartnerCatalogue(PartnerCatalogue partnerCatalogueResource ) throws InspireNetzException;


    boolean isDuplicateCatalogueExisting(PartnerCatalogue partnerCatalogue);

    public PartnerCatalogue savePartnerCatalogue(PartnerCatalogue partnerCatalogue);

    public boolean deletePartnerCatalogue(Long rolId);

    public boolean updatePartnerCatalogueStatus(Long pacId, Integer pacStatus, Long pacPartnerNo, Long userNo) throws InspireNetzException;

    Page<PartnerCatalogue> getPartnerCatalogue(String filter, String query, String userLoginId, Pageable pageable);

    Page<PartnerCatalogue> searchPartnerCatalogues(Integer category, Long productNo, String userLoginId, Pageable pageable) throws InspireNetzException;

    Page<PartnerCatalogue> searchPartnerCatalogueForMerchant(Integer category, Long partnerNo, Pageable pageable) throws InspireNetzException;

    public void deductStockFromPartnerCatalogue(Long pacId,Long catAvailableStock) throws InspireNetzException;
}
