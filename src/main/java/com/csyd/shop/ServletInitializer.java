package com.csyd.shop;

/**
 * Created by ChengShanyunduo
 * 2018/8/7
 */
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CsydShopApplication.class);
    }

}