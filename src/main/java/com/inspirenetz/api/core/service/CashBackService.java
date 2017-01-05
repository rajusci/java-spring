package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.CashBackRequest;
import com.inspirenetz.api.core.dictionary.CashBackResponse;
import com.inspirenetz.api.rest.exception.InspireNetzException;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CashBackService {

    CashBackResponse doCashBack(CashBackRequest cashBackRequest) throws InspireNetzException;

    CashBackRequest getCashbackRequestObjectForPartner(String mobile,String userLoginId, Long merchantNo, String reference, String amount, Integer channel, String otpCode) throws InspireNetzException;

    CashBackResponse doCashbackFromPartner(String mobile,String userLoginId, Long merchantNo, String amount, Integer channel, String reference, String otpCode) throws InspireNetzException;
}
