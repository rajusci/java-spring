package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.domain.MessageSpiel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ameenci on 8/9/14.
 */
public interface MessageSpielRepository extends  BaseRepository<MessageSpiel,Long> {

    public Page<MessageSpiel> findByMsiMerchantNoAndMsiNameLike(Long merchantNo,String msiName,Pageable pageable);
    public Page<MessageSpiel> findAll(Pageable pageable);

    public MessageSpiel findByMsiId(Long msiId);
    public MessageSpiel findByMsiNameAndMsiMerchantNo(String msiName,Long merchantNo);

    public Page<MessageSpiel> findByMsiMerchantNo(Long merchantNo,Pageable pageable);


}
