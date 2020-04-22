package com.lee.owner.repository.conf;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * 数据源
 * @author joseph.li
 */
@Configuration
@MapperScan(basePackages = "com.lee.owner.repository.mapper", sqlSessionFactoryRef = "demoSqlSessionFactory")
public class DemoDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.demo")
    public DataSource demoDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory demoSqlSessionFactory(DataSource demoDataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(demoDataSource);
        return bean.getObject();
    }


    @Bean
    public DataSourceTransactionManager demoTransactionManager(DataSource demoDataSource) {
        return new DataSourceTransactionManager(demoDataSource);
    }


    @Bean
    public SqlSessionTemplate demoSqlSessionTemplate(SqlSessionFactory demoSqlSessionFactory) {
        return new SqlSessionTemplate(demoSqlSessionFactory);
    }
}
