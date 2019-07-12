package com.ipaylinks.test.mock.server.business.czcb;

import com.ipaylinks.test.mock.server.api.czcb.bo.req.*;
import com.ipaylinks.test.mock.server.api.czcb.bo.resp.*;

public interface CzcbBusinessService {
    CzcbResp<CzcbOd0007Resp> od0007(CzcbReq<CzcbOd0007Req> czcbOd0007Req, String accessPoint);

    CzcbResp<CzcbOa0003Resp> oa0003(CzcbReq<CzcbOa0003Req> czcbOa0003Req, String accessPoint);

    CzcbResp<CzcbOa0005Resp> oa0005(CzcbReq<CzcbOa0005Req> czcbOa0005Req, String accessPoint);

    CzcbResp<CzcbOa0015Resp> oa0015(CzcbReq<CzcbOa0015Req> czcbOa0015Req, String accessPoint);

    CzcbResp<CzcbOd0001Resp> od0001(CzcbReq<CzcbOd0001Req> czcbOd0001Req, String accessPoint);

    CzcbResp<CzcbOd0002Resp> od0002(CzcbReq<CzcbOd0002Req> czcbOd0002Req, String accessPoint);

    CzcbResp<CzcbOd0004Resp> od0004(CzcbReq<CzcbOd0004Req> czcbOd0004Req, String accessPoint);

    CzcbResp<CzcbOd0005Resp> od0005(CzcbReq<CzcbOd0005Req> czcbOd0005Req, String accessPoint);

    CzcbResp<CzcbOd0003Resp> od0003(CzcbReq<CzcbOd0003Req> czcbOd0003Req, String accessPoint);

    CzcbResp<CzcbOd0006Resp> od0006(CzcbReq<CzcbOd0006Req> czcbOd0006Req, String accessPoint);

    CzcbResp<CzcbOd0008Resp> od0008(CzcbReq<CzcbOd0008Req> czcbOd0008Req, String accessPoint);

    CzcbResp<CzcbOd0009Resp> od0009(CzcbReq<CzcbOd0009Req> czcbOd0009Req, String accessPoint);

}
