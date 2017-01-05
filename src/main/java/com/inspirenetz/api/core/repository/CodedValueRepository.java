package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CodedValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CodedValueRepository extends  BaseRepository<CodedValue,Long> {

    public List<CodedValue> findByCdvIndex(int cdvIndex);
    public Page<CodedValue> findByCdvIndex(int cdvIndex, Pageable pageable);
    public CodedValue findByCdvIndexAndCdvCodeValue(int cdvIndex, int cdvCodeValue);
    public CodedValue findByCdvCodeId(Long cdvCodeId);

    @Query("select C from CodedValue C order by C.cdvIndex,C.cdvCodeValue asc")
    public List<CodedValue> getOrderedCodedValues();

    public CodedValue findByCdvCodeLabel(String cdvCodeLabel);

    public CodedValue findByCdvCodeLabelAndCdvIndex(String cdvLabel, int cdvIndex);


}
