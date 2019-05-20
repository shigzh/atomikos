package com.sgz.atomikos.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Auther:shigzh
 * @create 2019/5/7 17:51
 */
@Data
public class SgzEntity implements Serializable {
    //按alt+enter键 生成序列化id
    private static final long serialVersionUID = -3932087591646353874L;

    private Integer id; //主键
    private String userName; //名称
    private Date date;
    private String dbSource; // 来自哪个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
}
