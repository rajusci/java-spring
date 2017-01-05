package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.CodedValueMap;
import com.inspirenetz.api.core.domain.CodedValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CodedValueService extends BaseService<CodedValue> {

    public CodedValue findByCdvCodeId(Long cdvCodeId);
    public Page<CodedValue> findByCdvIndex(int cdvIndex, Pageable pageable);
    public CodedValue findByCdvIndexAndCdvCodeValue(int cdvIndex, int cdvCodeValue);
    public boolean isDuplicateCodedValueExisting(CodedValue codedValue);
    public HashMap<Integer,String> getCodedValueMapForIndex(Integer cdvIndex);
    public HashMap<Integer,List<CodedValueMap>> getCodedValueMap();

    public HashMap<Integer, List<CodedValueMap>> getCodedValueMapByIndex(Integer cdvIndex);

    public CodedValue saveCodedValue(CodedValue codedValue);
    public boolean deleteCodedValue(Long cdvCodeId);
    public List<CodedValue> getCompatableCdvIndex();

    public CodedValue getCodedValueId(Integer cdvIndex,String cdvCodeLabel);

    public CodedValue findByCdvCodeLabel(String cdvCodeLabel);

    public CodedValue findByCdvLabelAndCdvIndex(String cdvLabel,int cdvIndex);

    public List<CodedValue> findByCdvIndex(int cdvIndex);


}
