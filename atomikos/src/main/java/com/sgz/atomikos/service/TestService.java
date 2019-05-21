package com.sgz.atomikos.service;

import com.sgz.atomikos.test1.service.User1Service;
import com.sgz.atomikos.test2.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/20 17:16
 */
@Transactional(value="db2TransactionManager")
@Service
public class TestService {
    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;

    public void testAdd(){
        user1Service.testAdd();
        user2Service.testAdd();
    }
}
