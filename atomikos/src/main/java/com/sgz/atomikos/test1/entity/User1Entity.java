package com.sgz.atomikos.test1.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/21 9:43
 */
@Data
public class User1Entity implements Serializable {
    //按alt+enter键 生成序列化id
    private static final long serialVersionUID = -3932087591641353874L;
    private Integer id; //主键
    private String userName; //名称
    private Date date;
    private String dbSource; // 来自哪个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
}
