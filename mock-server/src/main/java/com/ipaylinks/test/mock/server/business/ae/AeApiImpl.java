package com.ipaylinks.test.mock.server.business.ae;

import com.ipaylinks.test.mock.server.api.ae.AeApi;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AeApiImpl implements AeApi {
    @Autowired
    private AeBusinessService aeBusinessService;

    @Override
    public ISOMsg processISO8583Request(ISOMsg reqISOMsg, String requestURI) throws Exception {
        ISOMsg respISOMsg = null;
        String mti = reqISOMsg.getMTI();
        if (AeInterfaceEnum.AE_AUTHORIZATION.getCode().equals(mti)){
            respISOMsg= aeBusinessService.authorization(reqISOMsg, requestURI);
        }else if (AeInterfaceEnum.AE_REVERSAL_ADVICE.getCode().equals(mti)) {
            respISOMsg= aeBusinessService.reversalAdvice(reqISOMsg, requestURI);
        }
        return respISOMsg;
    }
}
