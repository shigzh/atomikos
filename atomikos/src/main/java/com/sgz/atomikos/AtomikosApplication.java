package com.sgz.atomikos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//扫描mybatis使用的mapper接口
//@MapperScan(value="com.sgz.**.mapper")  //扫描mybatis的mapper文件,多数据源就不在这里配置扫描mapper了
@SpringBootApplication
public class AtomikosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtomikosApplication.class, args);
    }

}
