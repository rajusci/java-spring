package com.inspirenetz.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sandheepgr on 12/5/14.
 */
public class DBUniqueIdGenerator extends StoredProcedure {

    // Variable holding the string procedure namefor executing
    private static final String SPROC_NAME = "GetNextUniqueId";

    public DBUniqueIdGenerator(DataSource dataSource) {

        super(dataSource, SPROC_NAME);

        // set the parameter for the stored procedure
        declareParameter(new SqlParameter("var_ret_param", Types.INTEGER));

        // Compile the call
        compile();

    }


    /**
     * Function to get the next unique id from the database by calling the
     * procedure.
     * Here we pass the uniqueIdType and get the value
     *
     * @param uniqueIdType - The unique id type for which the next unique id need to be generated
     * @return             - Return -1 if an id was not able to be generated
     *                       Return the long non zero value representing the net
     */
    public  long  getNextUniqueId(int uniqueIdType) {

        // Create the HashMap object storing the parameters
        HashMap<String, Object> hmap = new HashMap<String, Object>();

        // Set the var param
        hmap.put("var_ret_param", uniqueIdType);

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
        if ( !retValue.containsKey("var_uni_id_value") ) {

            return -1;

        }

        // Return the parsed Long value of var_uni_id_value
        return Long.parseLong(retValue.get("var_uni_id_value").toString());

    }

}
