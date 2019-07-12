package com.ipaylinks.test.mock.server.api.ae;

import org.jpos.iso.ISOMsg;

public interface AeApi {
    ISOMsg processISO8583Request(ISOMsg reqISOMsg, String requestURI) throws Exception;
}
