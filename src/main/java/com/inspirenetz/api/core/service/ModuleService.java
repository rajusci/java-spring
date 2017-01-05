package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ModuleService extends BaseService<Module> {


    public Module findByMdlId(Long mdlId);
    public Module findByMdlName(String mdlName);
    public Page<Module> findByMdlNameLike(String mdlName, Pageable pageable);
    public Page<Module> listModules(Pageable pageable);
    public boolean isDuplicateModuleExisting(Module module);
    public Page<Module> searchModules(String filter,String query, Pageable pageable);
    public boolean isUserValidForOperation(AuthUser user) throws InspireNetzException;
    public List<Module> searchModuleName(String moduleName);

    public Module saveModule(Module module) throws InspireNetzException;
    public boolean deleteModule(Long mdlId) throws InspireNetzException;

    public Module validateAndSaveModule(Module module) throws InspireNetzException;
    public boolean validateAndDeleteModule(Long mdlId) throws InspireNetzException;



}
