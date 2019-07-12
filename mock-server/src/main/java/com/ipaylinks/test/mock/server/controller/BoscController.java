package com.ipaylinks.test.mock.server.controller;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.api.bosc.BoscApi;

import com.ipaylinks.test.mock.server.api.bosc.dto.req.BoscReqDTO;
import com.ipaylinks.test.mock.server.api.bosc.dto.resp.BoscRespDTO;
import com.ipaylinks.test.mock.server.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RestController
public class BoscController {

    @Autowired
    private BoscApi boscApi;

    @RequestMapping(value = {"/bosc"}, produces = {"application/json;charset=UTF-8"})
    public BoscRespDTO processJSONRequest(HttpServletRequest httpServletRequest) throws Exception {
        InputStream is = httpServletRequest.getInputStream();
        String requestString = ServletUtils.convertStreamToString(is);
        BoscReqDTO boscReqDTO = JSON.parseObject(requestString, BoscReqDTO.class);
        return boscApi.processJSONRequest(boscReqDTO, httpServletRequest.getRequestURI());
    }

}
