package com.sgz.atomikos.controller;

import com.sgz.atomikos.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/20 17:16
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/testAdd")
    public void testAdd(){
        testService.testAdd();
    }
}
