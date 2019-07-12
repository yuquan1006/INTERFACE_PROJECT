package com.ipaylinks.test.mock.server.controller;

import com.ipaylinks.test.mock.server.api.fuiou.FuiouApi;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouOrderQueryPageRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouSubmitOrderRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouNotifyResponse;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouOrderQueryPageResponse;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouSubmitOrderResponse;
import com.ipaylinks.test.mock.server.api.fuiou.dto.req.FuiouRequestDTO;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/fuiou")
public class FuiouController {

    @Autowired
    private FuiouApi fuiouApi;

    @RequestMapping(value = {"/xml"})
    public String processXMLRequest(HttpServletRequest httpServletRequest){
        FuiouRequestDTO fuiouRequestDTO = new FuiouRequestDTO();
        fuiouRequestDTO.setReqStr(httpServletRequest.getParameter("reqStr"));
        fuiouRequestDTO.setSign(httpServletRequest.getParameter("sign"));
        return fuiouApi.processXMLRequest(fuiouRequestDTO, httpServletRequest.getRequestURI());
    }

    @RequestMapping(value = {"/json/notify.fuiou"}, produces = {"application/json;charset=UTF-8"})
    public FuiouNotifyResponse notify(HttpServletRequest httpServletRequest) throws Exception {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        FuiouNotifyRequest fuiouNotifyRequest = new FuiouNotifyRequest();
        BeanUtils.populate(fuiouNotifyRequest, parameterMap);
        return fuiouApi.notify(fuiouNotifyRequest, httpServletRequest.getRequestURI());
    }

    @RequestMapping(value = {"/json/submitOrder.fuiou"}, produces = {"application/json;charset=UTF-8"})
    public FuiouSubmitOrderResponse submitOrder(HttpServletRequest httpServletRequest) throws Exception {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        FuiouSubmitOrderRequest fuiouSubmitOrderRequest = new FuiouSubmitOrderRequest();
        BeanUtils.populate(fuiouSubmitOrderRequest, parameterMap);
        return fuiouApi.submitOrder(fuiouSubmitOrderRequest, httpServletRequest.getRequestURI());
    }

    @RequestMapping(value = {"/json/orderQueryPage.fuiou"}, produces = {"application/json;charset=UTF-8"})
    public FuiouOrderQueryPageResponse orderQueryPage(HttpServletRequest httpServletRequest) throws Exception {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        FuiouOrderQueryPageRequest fuiouOrderQueryPageRequest = new FuiouOrderQueryPageRequest();
        BeanUtils.populate(fuiouOrderQueryPageRequest, parameterMap);
        return fuiouApi.orderQueryPage(fuiouOrderQueryPageRequest, httpServletRequest.getRequestURI());
    }
}
