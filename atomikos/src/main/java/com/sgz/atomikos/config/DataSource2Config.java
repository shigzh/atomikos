package com.sgz.atomikos.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Description:com.sgz.atomikos.test2下面的mapper访问的是db2数据库
 * @Auther:shigzh
 * @create 2019/5/20 17:42
 */
@Configuration
@MapperScan(basePackages = "com.sgz.atomikos.test2", sqlSessionTemplateRef = "db2SqlSessionTemplate")
public class DataSource2Config {

    /**
     * 创建Mybatis的连接会话工厂实例
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //这里加载对应的mybatis的xml配置文件，application.yml里就不用配置了，即使配置了也不起作用
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/db2/*.xml"));
        return bean.getObject();
    }


    @Bean
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
