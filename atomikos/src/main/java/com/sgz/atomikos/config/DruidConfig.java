package com.sgz.atomikos.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.servlet.Filter;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 网上查找都说 atomikos这套方案性能太差了，做性能测试的时候并发完全压不上去，高并发的系统千万不要用。简单了解学习使用
 *
 * XA 是一个分布式事务协议，由Tuxedo 提出，所以分布式事务也称为XA 事务
 * @Description: 单独整个配置，配置druid数据源监控
 * @Auther:shigzh
 * @create 2019/5/21 13:48
 */
@Configuration
public class DruidConfig {

    @Autowired
    private  Environment env;

    //绑定数据源配置
    @Primary
    @Bean
    public DataSource db1DataSource() {
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();//分布式数据源(AtomikosNonXADataSourceBean 非分布式数据源)
        Properties prop = build("spring.datasource.db1.");
        dataSourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        dataSourceBean.setUniqueResourceName("db1");//该值要唯一
        dataSourceBean.setPoolSize(5);
        dataSourceBean.setXaProperties(prop);
        return dataSourceBean;
    }

    @Bean
    public DataSource db2DataSource() {
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();//分布式数据源
        Properties prop = build("spring.datasource.db2.");
        dataSourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        dataSourceBean.setUniqueResourceName("db2"); //该值要唯一
        dataSourceBean.setPoolSize(5);
        dataSourceBean.setXaProperties(prop);
        return dataSourceBean;
    }

    private Properties build(String prefix) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name", ""));
        prop.put("initialSize", env.getProperty(prefix + "initialSize", Integer.class));
        prop.put("maxActive", env.getProperty(prefix + "maxActive", Integer.class));
        prop.put("minIdle", env.getProperty(prefix + "minIdle", Integer.class));
        prop.put("maxWait", env.getProperty(prefix + "maxWait", Integer.class));
        prop.put("poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));
        prop.put("maxPoolPreparedStatementPerConnectionSize",env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("maxPoolPreparedStatementPerConnectionSize",env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("validationQuery", env.getProperty(prefix + "validationQuery"));
        //prop.put("validationQueryTimeout", env.getProperty(prefix + "validationQueryTimeout", Integer.class));
        prop.put("testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
        prop.put("testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
        prop.put("timeBetweenEvictionRunsMillis",env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("filters", env.getProperty(prefix + "filters"));
        return prop;
    }

    /**
     * 注入事物管理器
     * 由于我们只使用一个事务管理器：DataSource1Config.DataSource2Config.java中配置的事务管理器了
     * @return
     */
    @Bean
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }


    /**
     * 配置Druid监控
     * 1. 配置一个管理后台的Servlet
     * 2. 配置一个监控的filter
     */
    @Bean // 1. 配置一个管理后台的Servlet
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        //StatViewServlet是 配置管理后台的servlet
        ServletRegistrationBean<StatViewServlet> bean =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //配置初始化参数
        Map<String, String> initParam = new HashMap<>();
        //访问的用户名密码
        initParam.put(StatViewServlet.PARAM_NAME_USERNAME, "root");
        initParam.put(StatViewServlet.PARAM_NAME_PASSWORD, "123");
        //允许访问的ip，默认所有ip访问
        initParam.put(StatViewServlet.PARAM_NAME_ALLOW, "");
        //禁止访问的ip
        initParam.put(StatViewServlet.PARAM_NAME_DENY, "192.168.10.1");
        //监控配置界面中 是否能够重置数据（点了重置所有监控数据就没有了）
        //initParam.put(StatViewServlet.PARAM_NAME_RESET_ENABLE,"false");
        initParam.put(StatViewServlet.PARAM_NAME_RESET_ENABLE,"true");
        bean.setInitParameters(initParam);
        return bean;
    }
    //2. 配置一个监控的filter
    @Bean
    public FilterRegistrationBean<Filter> filter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        //配置初始化参数
        Map<String, String> initParam = new HashMap<>();
        //排除请求
        initParam.put(WebStatFilter.PARAM_NAME_EXCLUSIONS, "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParam);
        //拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
