package com.ipaylinks.test.mock.server.controller;

import com.ipaylinks.test.mock.server.api.czcb.CzcbApi;
import com.ipaylinks.test.mock.server.api.czcb.dto.req.CzcbReqDTO;
import com.ipaylinks.test.mock.server.api.czcb.dto.resp.CzcbRespDTO;

import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CzcbController {

    @Autowired
    private CzcbApi czcbApi;

    @RequestMapping(value = {"/czcb"}, produces = {"application/json;charset=UTF-8"})
    public CzcbRespDTO processJSONRequest(@RequestBody CzcbReqDTO czcbReqDTO, HttpServletRequest httpServletRequest) throws CzcbException {
        return czcbApi.processJSONRequest(czcbReqDTO, httpServletRequest.getRequestURI());
    }

}
