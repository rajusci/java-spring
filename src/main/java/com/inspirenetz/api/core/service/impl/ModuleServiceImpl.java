package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.ModuleRepository;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module> implements ModuleService {


   private static Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);


    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public ModuleServiceImpl() {

        super(Module.class);

    }


    @Override
    protected BaseRepository<Module,Long> getDao() {
        return moduleRepository;
    }


    @Override
    public Module findByMdlId(Long mdlId) {

        // Get the Module for the given Module id from the repository
        Module module = moduleRepository.findByMdlId(mdlId);

        // Return the Module
        return module;


    }

    @Override
    public Module findByMdlName(String mdlName) {

        // Get the modules
        Module module = moduleRepository.findByMdlName(mdlName);

        // Return the module
        return module;

    }

    @Override
    public Page<Module> findByMdlNameLike(String mdlName, Pageable pageable) {
        
        // Get the module page
        Page<Module> modulePage = moduleRepository.findByMdlNameLike(mdlName,pageable);

        // return the modulepage
        return modulePage;

    }

    @Override
    public Page<Module> listModules(Pageable pageable) {

        // Get the modules
        Page<Module> modulePage = moduleRepository.findAll(pageable);

        // Return the page
        return modulePage;

    }


    @Override
    public boolean isDuplicateModuleExisting(Module module) {

        // Get the Module information
        Module exModule = moduleRepository.findByMdlName(module.getMdlName());

        // If the mdlId is 0L, then its a new Module so we just need to check if there is ano
        // ther Module code
        if ( module.getMdlId() == null || module.getMdlId() == 0L ) {

            // If the Module is not null, then return true
            if ( exModule != null ) {

                return true;

            }

        } else {

            // Check if the Module is null
            if ( exModule != null && module.getMdlId().longValue() != exModule.getMdlId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Page<Module> searchModules(String filter, String query, Pageable pageable) {

        // The Page object to return
        Page<Module> modulePage;


        // Check the filter return the date
        if ( filter.equals("name") ) {

            modulePage = moduleRepository.findByMdlNameLike("%"+query+"%",pageable);

        } else {

            modulePage = moduleRepository.findAll(pageable);

        }


        // Return the modulePage
        return modulePage;

    }

    @Override
    public boolean isUserValidForOperation(AuthUser user) throws InspireNetzException {

        // If the user is not admin / super admin, throw InspireNetzException
        if ( user.getUserType() != UserType.ADMIN && user.getUserType() != UserType.SUPER_ADMIN ) {

            // log information
            log.info("isUserValidForOperation -> You are not allowed for the operation");

            // throw InspireNetzException
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Else return true
        return true;

    }

    @Override
    public List<Module> searchModuleName(String moduleName) {
        return moduleRepository.findByMdlNameLike(moduleName);
    }


    @Override
    public Module saveModule(Module Module ) throws InspireNetzException {

        // Save the Module
        return moduleRepository.save(Module);

    }

    @Override
    public boolean deleteModule(Long mdlId) throws InspireNetzException{

        // Delete the Module
        moduleRepository.delete(mdlId);

        // return true
        return true;

    }

    @Override
    public Module validateAndSaveModule(Module module) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MODULE);

        return saveModule(module);
    }

    @Override
    public boolean validateAndDeleteModule(Long mdlId) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_MODULE);

        return deleteModule(mdlId);
    }

}
