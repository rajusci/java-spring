package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.inspirenetz.api.core.dictionary.Status;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

/**
 * Created by sandheepgr on 16/2/14.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseResource extends ResourceSupport implements Serializable {

}
