package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.ValidationRequest;
import com.inspirenetz.api.core.dictionary.ValidationResponse;
import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ValidationService {

    ValidationResponse isValid(ValidationRequest validationRequest) throws InspireNetzException;

}
