package com.lee.owner.context.impl;

import com.lee.owner.context.AbstractConverterContext;
import com.lee.owner.pojo.dto.DemoDTO;
import com.lee.owner.pojo.req.DemoReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author joseph.li
 * @date 2019-10-08 19:33
 */
@Component
public class DemoDTOToReqConverter extends AbstractConverterContext<DemoDTO, DemoReq> {

    @Override
    protected DemoReq doRealTransData(DemoDTO demoDTO) {
        DemoReq demoReq = new DemoReq();
        BeanUtils.copyProperties(demoDTO, demoReq);
        return demoReq;
    }

    @Override
    protected DemoDTO doRealTransBackData(DemoReq demoReq) {
        DemoDTO demoDTO = new DemoDTO();
        BeanUtils.copyProperties(demoReq, demoDTO);
        return demoDTO;
    }
}
