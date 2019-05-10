package com.suitupmonkey.system.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

/**
 * database connection
 */
@Configuration
public class DBConfig {
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver}")
    private String driver;
    @Value("${spring.datasource.filters}")
    private String filters;


    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);

        try{
            dataSource.setFilters(filters);
            //允许批量操作
            WallConfig wallConfig = new WallConfig();
            wallConfig.setMultiStatementAllow(true);
            wallConfig.setNoneBaseStatementAllow(true);

            List<Filter> proxyFilters = dataSource.getProxyFilters();
            for (Filter filter : proxyFilters) {
                if(filter instanceof WallFilter){
                    ((WallFilter) filter).setConfig(wallConfig);//覆盖默认的wallconfig
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return dataSource;
    }



}
