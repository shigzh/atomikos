package com.sgz.atomikos.service;

import com.sgz.atomikos.entity.SgzEntity;
import com.sgz.atomikos.mapper.db1.SgzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/20 17:59
 */
@Transactional
@Service
public class Db1Service {
    @Autowired
    private SgzMapper sgzMapper;

    public void testAdd(){
        SgzEntity sgzEntity = new SgzEntity();
        sgzEntity.setUserName("test");
        sgzEntity.setDate(new Date());
        sgzEntity.setDbSource("sanfront");
        sgzMapper.addProduct(sgzEntity);
    }
}
