package com.ipaylinks.test.mock.server.business.ae;

import org.jpos.iso.ISOMsg;

public interface AeBusinessService {
    ISOMsg authorization(ISOMsg reqISOMsg, String accessPoint);

    ISOMsg reversalAdvice(ISOMsg reqISOMsg, String accessPoint);
}
