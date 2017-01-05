package com.inspirenetz.api.core.domain.validator;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by saneeshci on 30/9/14.
 */
public class PromotionalEventValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

        return PromotionalEvent.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {

        // cast the PromotionalEventObject
        PromotionalEvent promotionalEvent = (PromotionalEvent)target;

        // Check the promotionalEvent name
        if (  promotionalEvent.getPreEventCode() == null  ) {

            errors.rejectValue("preEventCode","{promotionalEvent.preEventCode.notnull}");

        }

        if (  promotionalEvent.getPreEventCode().length() >20  ) {

            errors.rejectValue("preEventCode","{promotionalEvent.preEventCode.size}");

        }

        // Check the promotionalEvent name
        if (  promotionalEvent.getPreEventName() == null  ) {

            errors.rejectValue("preEventName","{promotionalEvent.preEventName.notnull}");

        }

        if (  promotionalEvent.getPreEventName().length() >100  ) {

            errors.rejectValue("preEventName","{promotionalEvent.preEventName.size}");

        }

        if (  promotionalEvent.getPreDescription() != null && promotionalEvent.getPreDescription().length() >200  ) {

            errors.rejectValue("preDescription","{promotionalEvent.preDescription.size}");

        }

        // Check the promotionalEvent name
        if (  promotionalEvent.getPreEndDate() == null  ) {

            errors.rejectValue("preEndDate","{promotionalEvent.preEndDate.notnull}");

        }
        // Check the promotionalEvent name
        if (  promotionalEvent.getPreStartDate() == null  ) {

            errors.rejectValue("preStartDate","{promotionalEvent.preStartDate.notnull}");

        }

        // Check the promotionalEvent name
        if (  promotionalEvent.getPreLocation() == null  ) {

            errors.rejectValue("preLocation","{promotionalEvent.preLocation.notnull}");

        }




    }
}
