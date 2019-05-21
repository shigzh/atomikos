package com.sgz.atomikos.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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
    //绑定数据源配置
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Bean
    public DataSource db1DataSource() {
        return new DruidDataSource();
    }

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

    /**
     * 创建该数据源的事务管理
     * @param dataSource
     * @return
     */
    @Bean
    @Primary
    public DataSourceTransactionManager db1TransactionManager(@Qualifier("db1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}






