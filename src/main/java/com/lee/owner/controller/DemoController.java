package com.lee.owner.controller;

import com.lee.owner.register.demo.Demo;
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

    private final Demo demo;

    public DemoController(Demo demo) {
        this.demo = demo;
    }

    @RequestMapping("/hello")
    public String hello() {
        demo.doSomething();
        demo.test();
        return "hello";
    }

}
