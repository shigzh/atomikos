package com.sgz.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/20 17:16
 */
@Transactional
@Service
public class TestService {
    @Autowired
    private Db1Service db1Service;

    public void testAdd(){
        db1Service.testAdd();
        //int a=1/0;
    }

}
