package com.springcloud.edu.dataservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 11:14
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class DataServiceApplication {

}
