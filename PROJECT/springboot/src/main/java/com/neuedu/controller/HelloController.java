package com.neuedu.controller;

import com.neuedu.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class HelloController {
 /*   @Value("${limit.minMoney}")
    private BigDecimal minMoney;
    @Value("${limit.maxMoney}")
    private  BigDecimal maxMoney;
*/
 @Autowired
 AppConfig appConfig;


    @RequestMapping(value = {"/hello","/hello2"})
  public  String hello(@RequestParam(value = "username",required = false,defaultValue = "admin") String username){
      return  username;
  }

}
