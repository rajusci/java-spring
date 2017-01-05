package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantModuleRepository;
import com.inspirenetz.api.core.service.MerchantModuleService;
import com.inspirenetz.api.core.service.ModuleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import sun.security.pkcs11.Secmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantModuleServiceImpl extends BaseServiceImpl<MerchantModule> implements MerchantModuleService {


    private static Logger log = LoggerFactory.getLogger(MerchantModuleServiceImpl.class);


    @Autowired
    MerchantModuleRepository merchantModuleRepository;

    @Autowired
    ModuleService moduleService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    public MerchantModuleServiceImpl() {

        super(MerchantModule.class);

    }


    @Override
    protected BaseRepository<MerchantModule,Long> getDao() {
        return merchantModuleRepository;
    }


    @Override
    public MerchantModule findByMemMerchantNoAndMemModuleId(Long memMerchantNo, Long memModuleId) {

        // Get the MerchantModule
        MerchantModule merchantModule = merchantModuleRepository.findByMemMerchantNoAndMemModuleId(memMerchantNo,memModuleId);

        // return the MerchantModule
        return merchantModule;

    }

    @Override
    public List<MerchantModule> findByMemMerchantNo(Long memMerchantNo) {

        // GEt the MerchantModule list
        List<MerchantModule> merchantModuleList =merchantModuleRepository.findByMemMerchantNo(memMerchantNo);

        // Return the MerchantModuleList
        return merchantModuleList;

    }

    @Override
    public HashMap<Long, String> getModulesAsMap(Long memMerchantNo) {

        // HashMap holding the values
        HashMap<Long,String> modulesMap = new HashMap<>(0);

        // Get the MerchantModule list
        List<MerchantModule> moduleList = merchantModuleRepository.findByMemMerchantNo(memMerchantNo);

        // Iterate through the merchantModuleList
        for( MerchantModule merchantModule : moduleList ) {

            // Now add the key value pair to the map
            modulesMap.put(merchantModule.getMemModuleId(), merchantModule.getMemEnabledInd().toString());

        }


        // REturn the modulesMap
        return modulesMap;
    }


    @Override
    public MerchantModule saveMerchantModule(MerchantModule merchantModule ) throws InspireNetzException {

        // Save the merchantModule
        return merchantModuleRepository.save(merchantModule);

    }

    @Override
    public boolean deleteMerchantModule(MerchantModule merchantModule) throws InspireNetzException {

        // Delete the merchantModule
        merchantModuleRepository.delete(merchantModule);

        // return true
        return true;

    }

    @Override
    public MerchantModule validateAndSaveMerchantModule(MerchantModule merchantModule) throws InspireNetzException {

        //check the access right for the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MODULE);

        return saveMerchantModule(merchantModule);
    }

    @Override
    public boolean validateAndDeleteMerchantModule(MerchantModule merchantModule) throws InspireNetzException {

        //check the access right for the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_MODULE);

        return deleteMerchantModule(merchantModule);
    }

    @Override
    public Page<MerchantModule> getMerchantModule(Long merchantNo,String filter,String query) throws InspireNetzException {

        //initialise merchant module
        Page<MerchantModule> merchantModulePage =null;

        //check the user is super admin or admin
        if(authSessionUtils.getUserType() !=1 && authSessionUtils.getUserType() !=2){

            log.info("MerchantSettingsService ->getMerchantSettingsForAdmin::Unauthorized user");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        List<Module> moduleList =null;
        //check filer conditions
        if(filter.equals("name")){

            //get module name based on filer condition
            moduleList =moduleService.searchModuleName("%"+query+"%");

        }else {

            //get default module
            moduleList =moduleService.findAll();
        }


        //initialise merchant module list
        MerchantModule merchantModule =null;

        //get module list from merchant
        List<MerchantModule> merchantModules =findByMemMerchantNo(merchantNo);

        //get module list from merchant
        List<MerchantModule> merchantModules1 =new ArrayList<>();

        //check merchant module is null or empty
        if(merchantModules ==null ||merchantModules.isEmpty()){

            //create merchant module list
            merchantModules =new ArrayList<>();

            for (Module module:moduleList){

                //convert module entry to merchant module
                merchantModule =new MerchantModule();

                //map module field into merchant settings
                merchantModule =setRelatedField(module,merchantModule,merchantNo);

                //add into list
                merchantModules.add(merchantModule);

            }

            //return merchant module list
            return new PageImpl<>(merchantModules);

        }

        //check present field
        boolean hasPresent= false;

        //else iterate module merchant module list
        for (Module module:moduleList){

            for (MerchantModule merchantModule1:merchantModules){

                //check if the module id is present  inthe merchant module
                if(module.getMdlId().intValue() == merchantModule1.getMemModuleId().intValue()){

                    hasPresent =true;

                    //set module name
                    merchantModule1.setMemModuleName(module.getMdlName());

                    //add into merchant list
                    merchantModules1.add(merchantModule1);

                }

            }

            //check if its present or not
            if(!hasPresent){

                //set related field
                merchantModule =new MerchantModule();

                merchantModule =setRelatedField(module,merchantModule,merchantNo);

                merchantModules1.add(merchantModule);

            }

            //set flag in false for next iteration
            hasPresent =false;
        }

        //return
        return new PageImpl<>(merchantModules1);
    }

    private MerchantModule setRelatedField(Module module,MerchantModule merchantModule,Long merchantNo) {

         //set related field
         merchantModule.setMemEnabledInd(0);

         //set merchant no
         merchantModule.setMemMerchantNo(merchantNo);

         //set module id
         merchantModule.setMemModuleId(module.getMdlId());

         //set module name
         merchantModule.setMemModuleName(module.getMdlName());

         //return module object
         return merchantModule;
    }

}
