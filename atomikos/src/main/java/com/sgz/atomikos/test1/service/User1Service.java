package com.sgz.atomikos.test1.service;

import com.sgz.atomikos.test1.entity.User1Entity;
import com.sgz.atomikos.test1.mapper.User1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/21 9:52
 */
@Transactional
@Service
public class User1Service {
    @Autowired
    private User1Mapper user1Mapper;

    public void testAdd(){
        User1Entity user1Entity = new User1Entity();
        user1Entity.setUserName("user1");
        user1Mapper.addUser(user1Entity);
    }
}
