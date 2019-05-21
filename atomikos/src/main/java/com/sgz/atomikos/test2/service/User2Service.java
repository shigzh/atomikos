package com.sgz.atomikos.test2.service;

import com.sgz.atomikos.test2.entity.User2Entity;
import com.sgz.atomikos.test2.mapper.User2Mapper;
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
public class User2Service {
    @Autowired
    private User2Mapper user2Mapper;

    public void testAdd(){
        User2Entity user2Entity = new User2Entity();
        user2Entity.setUserName("user2");
        user2Mapper.addUser(user2Entity);
    }
}
