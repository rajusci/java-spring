package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ameenci on 8/9/14.
 */
public interface MessageSpielService  extends BaseService<MessageSpiel>{

    Page<MessageSpiel> searchMessageSpiel(String filter, String query,Pageable pageable);
    MessageSpiel findByMsiId(Long msiId);
    MessageSpiel findByMsiName(String msiName);

    public MessageSpiel validateAndSaveMessageSpiel(MessageSpiel messageSpiel ) throws InspireNetzException;
    public boolean isDuplicateMessageSpielExisting(MessageSpiel messageSpiel);

    MessageSpiel findByMsiNameAndMsiMerchantNo(String msiName, Long msiMerchantNo);

    public MessageSpiel saveMessageSpiel(MessageSpiel messageSpiel);
    public boolean validateAndDeleteMessageSpiel(Long msiId) throws InspireNetzException;
    public boolean deleteMessageSpiel(Long msiId);
}
