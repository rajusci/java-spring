package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.DrawChance;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface PartnerCatalogueRepository extends  BaseRepository<PartnerCatalogue,Long> {

    public PartnerCatalogue findByPacId(Long pacId);
    public PartnerCatalogue findByPacCodeAndPacPartnerNo(String pacCode,Long pacPartnerNo);
    public Page<PartnerCatalogue> findByPacPartnerNo(Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacCategoryAndPacPartnerNo(Integer pacCategory,Long pacPartnerNo,Pageable pageable);
    public List<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName,Long pacPartnerNo);
    public Page<PartnerCatalogue> findByPacNameLikeAndPacPartnerNo(String pacName,Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacStatusAndPacPartnerNo(Integer pacStatus,Long pacPartnerNo,Pageable pageable);
    public Page<PartnerCatalogue> findByPacCategoryAndPacStatusAndPacPartnerNo(Integer pacCategory,Integer pacStatus,Long pacPartnerNo,Pageable pageable);

    public Page<PartnerCatalogue> findByPacCodeLikeAndPacPartnerNo(String pacCode,Long pacPartnerNo,Pageable pageable);


}
