package com.ipaylinks.test.mock.server.business.bosc;

import com.ipaylinks.test.mock.server.api.bosc.bo.req.*;
import com.ipaylinks.test.mock.server.api.bosc.bo.resp.*;

public interface BoscBusinessService {
    /**
     *  子账户开户
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000001ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000001RespBody>
     */
    BoscRespDocument<IBPS_OPENACCT_000001RespBody> openacct000001(BoscReqDocument<IBPS_OPENACCT_000001ReqBody> reqDocument, String accessPoint);

    /**
     *  账户信息修改
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000002ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000002RespBody>
     */
    BoscRespDocument<IBPS_OPENACCT_000002RespBody> openacct000002(BoscReqDocument<IBPS_OPENACCT_000002ReqBody> reqDocument, String accessPoint);

    /**
     *  账户信息查询
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000004ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000004RespBody>
     */
    BoscRespDocument<IBPS_OPENACCT_000004RespBody> openacct000004(BoscReqDocument<IBPS_OPENACCT_000004ReqBody> reqDocument, String accessPoint);

    /**
     *  账户信息签约
     * @param reqDocument BoscReqDocument<IBPS_IBS_140002ReqBody>
     * @return BoscRespDocument<IBPS_IBS_140002RespBody>
     */
    BoscRespDocument<IBPS_IBS_140002RespBody> ibs140002(BoscReqDocument<IBPS_IBS_140002ReqBody> reqDocument, String accessPoint);

    /**
     *  代付交易状态查询
     * @param reqDocument BoscReqDocument<IBPS_IBS_110018ReqBody>
     * @return BoscRespDocument<IBPS_IBS_110018RespBody>
     */
    BoscRespDocument<IBPS_IBS_110018RespBody> ibs110018(BoscReqDocument<IBPS_IBS_110018ReqBody> reqDocument, String accessPoint);

}
