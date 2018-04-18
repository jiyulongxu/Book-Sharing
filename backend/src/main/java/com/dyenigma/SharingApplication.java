package com.dyenigma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * backend/com.dyenigma
 *
 * @Description : 启动类
 * @Author : dingdongliang
 * @Date : 2018/4/3 8:30
 */
@SpringBootApplication
@ServletComponentScan
public class SharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharingApplication.class, args);
    }
}
