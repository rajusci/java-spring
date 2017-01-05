package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.OrderCatalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface OrderCatalogueRepository extends  BaseRepository<OrderCatalogue,Long> {



    public Page<OrderCatalogue> findByOrcMerchantNo(Long orcMerchantNo, Pageable pageable);
    public OrderCatalogue findByOrcProductNo(Long orcProductNo);
    public OrderCatalogue findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(Long orcMerchantNo, String orcProductCode,Long orcAvailableLocation);




    @Query("select O from OrderCatalogue O where O.orcMerchantNo = ?1 and ( O.orcAvailableLocation = ?2 or O.orcAvailableLocation = 0 ) ")
    public Page<OrderCatalogue> findByOrcMerchantNoAndOrcAvailableLocation(Long orcMerchantNo,Long orcAvailableLocation, Pageable pageable);

    @Query("select O from OrderCatalogue O where O.orcMerchantNo = ?1 and ( O.orcAvailableLocation = ?2 or O.orcAvailableLocation = 0 ) and O.orcProductCode LIKE ?3 ")
    public Page<OrderCatalogue> findByOrcMerchantNoAndOrcAvailableLocationAndOrcProductCodeLike(Long orcMerchantNo,Long orcAvailableLocation, String orcProductCode, Pageable pageable);

    @Query("select O from OrderCatalogue O where O.orcMerchantNo = ?1 and ( O.orcAvailableLocation = ?2 or O.orcAvailableLocation = 0 ) and O.orcDescription LIKE ?3 ")
    public Page<OrderCatalogue> findByOrcMerchantNoAndOrcAvailableLocationAndOrcDescriptionLike(Long orcMerchantNo,Long orcAvailableLocation, String orcDescription, Pageable pageable);

}
