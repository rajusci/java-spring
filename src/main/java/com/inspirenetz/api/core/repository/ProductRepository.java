package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ProductRepository extends  BaseRepository<Product,Long> {

    public Product findByPrdId(Long prdId);

    @Query("select P from Product P where P.prdMerchantNo = ?1 and ( ?2 = 0L or P.prdLocation = ?2 ) ")
    public Page<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo,Long prdLocation,Pageable pageable);

    public Product findByPrdMerchantNoAndPrdCode(Long prdMerchantNo, String prdCode);

    @Query("select P from Product P where P.prdMerchantNo = ?1 and ( ?2 = 0L or P.prdLocation = ?2 ) and P.prdCode like ?3")
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(Long prdMerchantNo,Long prdLocation,String prdCode,Pageable pageable);


    @Query("select P from Product P where P.prdMerchantNo = ?1 and ( ?2 = 0L or P.prdLocation = ?2 ) and P.prdName like ?3")
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdNameLike(Long prdMerchantNo,Long prdLocation,String prdName,Pageable pageable);


    @Query("select P from Product P where P.prdMerchantNo = ?1 and ( ?2 = 0L or P.prdLocation = ?2 ) ")
    public List<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo,Long prdLocation);
}
