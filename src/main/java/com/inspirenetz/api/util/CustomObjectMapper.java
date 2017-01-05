package com.inspirenetz.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class CustomObjectMapper extends ObjectMapper {

    private static final Logger log = LoggerFactory.getLogger(CustomObjectMapper.class);

    public CustomObjectMapper(){

    }
}
