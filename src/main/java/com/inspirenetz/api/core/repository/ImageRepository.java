package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ImageRepository extends  BaseRepository<Image,Long> {

   
    public Image findByImgImageId(Long imgImageId);


}
