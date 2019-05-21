package com.sgz.atomikos;

import com.sgz.atomikos.test1.service.User1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtomikosApplicationTests {
    @Autowired
    private User1Service user1Service;

    @Test
    public void contextLoads() {
        user1Service.testAdd();
    }

}
