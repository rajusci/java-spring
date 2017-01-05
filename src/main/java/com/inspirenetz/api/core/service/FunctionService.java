package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface FunctionService extends BaseService<Function> {


    public Function findByFncFunctionCode(Long fncFunctionCode);
    public Function findByFncFunctionName(String fncFunctionName);
    public Page<Function> findByFncFunctionNameLike(String fncFunctionName, Pageable pageable);
    public Page<Function> listFunctions(Pageable pageable);
    public boolean isDuplicateFunctionExisting(Function function);
    public List<Function> getFunctionsForUserType(Integer userType);

    public Function saveFunction(Function function);
    public boolean deleteFunction(Long fncFunctionCode);

    public List<Function> findByUserTypeAndFunctionNameLike(Integer userType,String fncFunctionName);

    public Page<Function> searchFunction(String fncName,Pageable pageable) throws InspireNetzException;

    public Function validateAndSave(Function function) throws InspireNetzException;

}
