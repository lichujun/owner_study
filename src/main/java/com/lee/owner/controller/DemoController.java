package com.lee.owner.controller;

import com.lee.owner.context.DataConverter;
import com.lee.owner.pojo.dto.DemoDTO;
import com.lee.owner.pojo.req.DemoReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author joseph.li
 * @date 2019-10-09 14:15
 */
@Slf4j
@RestController
public class DemoController {

    private final DataConverter<DemoDTO, DemoReq> dataConverter;

    public DemoController(DataConverter<DemoDTO, DemoReq> dataConverter) {
        this.dataConverter = dataConverter;
    }

    @RequestMapping("/converterBackDemo")
    public Object converterBackDemo() {
        DemoReq demoReq = new DemoReq()
                .setName("joseph");
        DemoDTO demoDTO = dataConverter.transBackData(demoReq);
        log.info("{}", dataConverter.getTransBackData());
        return demoDTO;
    }

    @RequestMapping("/converterForDemo")
    public Object converterForDemo() {
        DemoDTO demoDTO = new DemoDTO()
                .setName("joseph")
                .setAge(12);
        DemoReq demoReq = dataConverter.transForData(demoDTO);
        log.info("{}", dataConverter.getTransForData());
        return demoReq;
    }
}
