package com.sgz.atomikos.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Auther:shigzh
 * @Description: com.sgz.atomikos.test1下面的mapper访问的是db1数据库
 * 这个是主数据源，所以要加@Primary注解
 * @create 2019/2/6 16:55
 */
@Configuration
@MapperScan(basePackages = "com.sgz.atomikos.test1", sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class DataSource1Config {

    /**
     * 创建Mybatis的连接会话工厂实例
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    @Primary
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //这里加载对应的mybatis的xml配置文件，application.yml里就不用配置了，即使配置了也不起作用
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/db1/*.xml"));
        return bean.getObject();
    }


    @Bean
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}






