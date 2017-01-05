package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantLoyaltyIdRepository;
import com.inspirenetz.api.core.service.MerchantLoyaltyIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantLoyaltyIdServiceImpl extends BaseServiceImpl<MerchantLoyaltyId> implements MerchantLoyaltyIdService {



    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    MerchantLoyaltyIdRepository merchantLoyaltyIdRepository;

    @Autowired
    private DataSource dataSource;



    public MerchantLoyaltyIdServiceImpl() {

        super(MerchantLoyaltyId.class);

    }


    @Override
    protected BaseRepository<MerchantLoyaltyId,Long> getDao() {
        return merchantLoyaltyIdRepository;
    }



    @Override
    public MerchantLoyaltyId findByMliMerchantNo(Long mliMerchantNo) {

        // Get the MerchantLoyaltyId
        MerchantLoyaltyId merchantLoyaltyId = merchantLoyaltyIdRepository.findByMliMerchantNo(mliMerchantNo);

        // Return the object
        return merchantLoyaltyId;

    }

    @Override
    public Long getNextLoyaltyId(Long mliMerchantNo) {

        // Create the LoyaltyIdGenerator object
        LoyaltyIdGenerator generator = new LoyaltyIdGenerator(dataSource);

        // Get the loyaltyid
        Long loyaltyId = generator.getNextLoyaltyId(mliMerchantNo);

        // Return the loyaltyId
        return loyaltyId;

    }


    @Override
    public MerchantLoyaltyId saveMerchantLoyaltyId(MerchantLoyaltyId merchantLoyaltyId ){

        // Save the merchantLoyaltyId
        return merchantLoyaltyIdRepository.save(merchantLoyaltyId);

    }

    @Override
    public boolean deleteMerchantLoyaltyId(Long mliId) {

        // Delete the merchantLoyaltyId
        merchantLoyaltyIdRepository.delete(mliId);

        // return true
        return true;

    }


    // Private class that will be used to call the stored procedure for getting the
    // result from StoredProcedure
    private class LoyaltyIdGenerator extends StoredProcedure {

        // Variable holding the string procedure namefor executing
        private static final String SPROC_NAME = "GetNextLoyaltyIdJava";

        public LoyaltyIdGenerator(DataSource dataSource) {

            super(dataSource, SPROC_NAME);

            // set the parameter for the stored procedure
            declareParameter(new SqlParameter("var_param", Types.INTEGER));

            // Compile the call
            compile();

        }


        /**
         * Function to get the next unique id from the database by calling the
         * procedure.
         * Here we pass the uniqueIdType and get the value
         *
         * @param mliMerchantNo - The merhcant number of the merchant for whom the index need to be found
         * @return              - Return -1 if an id was not able to be generated
         *                       Return the long non zero value representing the net
         */
        public  long  getNextLoyaltyId(Long mliMerchantNo) {

            // Create the HashMap object storing the parameters
            HashMap<String, Object> hmap = new HashMap<String, Object>();

            // Set the var param
            hmap.put("var_param", mliMerchantNo);

            // Get the result
            Map<String, Object> results = execute(hmap);

            // check if the results contacts #result-set-1 key
            if ( !results.containsKey("#result-set-1") ) {

                return -1;

            }

            // Get the Map
            List<HashMap<String,Object>> resultList = (ArrayList) results.get("#result-set-1");

            // If the resultList is empty, then return -1;
            if ( resultList.isEmpty() ) {

                return -1;

            }

            // Get the retValue as HashMap
            HashMap<String,Object> retValue = resultList.get(0);

            // Check if the var_uni_id_value field is present and if not, return -1
            if ( !retValue.containsKey("var_next_loyalty_id") ) {

                return -1;

            }

            // Return the parsed Long value of var_uni_id_value
            return Long.parseLong(retValue.get("var_next_loyalty_id").toString());

        }

    }

}
