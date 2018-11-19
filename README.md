
<div align="center">  

<img src="https://ws2.sinaimg.cn/large/006tNbRwly1fxd6xibgkej30p00e30u8.jpg"  /> 
<br/>

[![Build Status](https://travis-ci.org/crossoverJie/cicada.svg?branch=master)](https://travis-ci.org/crossoverJie/cicada)
[![](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/)
[![QQ群](https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg)](https://jq.qq.com/?_wv=1027&k=5HPYvQk)

[qq0groupsvg]: https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg
[qq0group]: https://jq.qq.com/?_wv=1027&k=5HPYvQk


📘[Features](#features) |🌁[Quick Start](#quick-start) | 🏖[Performance Test](#performance-test) | 🌈[ChangeLog](#changelog) | 💡 [Contact Author](#contact-author)|🇨🇳[中文文档](https://github.com/TogetherOS/cicada/blob/master/README-ZH.md)



</div><br>



---

## Introduction

Fast, lightweight Web framework based on Netty; without too much dependency, and the core jar package is only `30KB`.

If you are interested, please click [Star](https://github.com/crossoverJie/cicada/stargazers).

## Features

- [x] Clean code, without too much dependency.
- [x] One line of code to start the HTTP service.
- [x] Custom interceptor.
- [x] Flexible parameters way.
- [x] Response `json`.
- [x] Start with `jar`.
- [x] Custom configuration.
- [x] Multiple response ways.
- [x] Pluggable `IOC` beanFactory。
- [ ] Support `Cookie`.
- [ ] File Upload.


## Quick Start

Create a project with `Maven`, import core dependency.


```java
<dependency>
    <groupId>top.crossoverjie.opensource</groupId>
    <artifactId>cicada-core</artifactId>
    <version>2.0.0</version>
</dependency>
```

start class:

```java
public class MainStart {

    public static void main(String[] args) throws InterruptedException {
        CicadaServer.start(MainStart.class,"/cicada-example") ;
    }
}
```

### Configuring Business Action


```java
@CicadaAction("routeAction")
public class RouteAction {

    private static final Logger LOGGER = LoggerBuilder.getLogger(RouteAction.class);


    @CicadaRoute("getUser")
    public void getUser(DemoReq req){

        LOGGER.info(req.toString());
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("hello =" + req.getName());
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    @CicadaRoute("getInfo")
    public void getInfo(DemoReq req){

        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getInfo =" + req.toString());
        CicadaContext.getContext().json(reqWorkRes) ;
    }

    @CicadaRoute("getReq")
    public void getReq(CicadaContext context,DemoReq req){

        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("getReq =" + req.toString());
        context.json(reqWorkRes) ;
    }



}
```

Launch and apply access: [http://127.0.0.1:5688/cicada-example/routeAction/getUser?id=1234&name=zhangsan](http://127.0.0.1:5688/cicada-example/routeAction/getUser?id=1234&name=zhangsan)

```json
{"message":"hello =zhangsan"}
```



## Cicada Context

Through `context.json(), context.text()`, you can choose different response ways.

```java
@CicadaAction("routeAction")
public class RouteAction {

    private static final Logger LOGGER = LoggerBuilder.getLogger(RouteAction.class);

    @CicadaRoute("getUser")
    public void getUser(DemoReq req){

        LOGGER.info(req.toString());
        WorkRes<DemoReq> reqWorkRes = new WorkRes<>() ;
        reqWorkRes.setMessage("hello =" + req.getName());
        CicadaContext.getContext().json(reqWorkRes) ;
    }
    
    @CicadaRoute("hello")
    public void hello() throws Exception {
        CicadaContext context = CicadaContext.getContext();

        String url = context.request().getUrl();
        String method = context.request().getMethod();
        context.text("hello world url=" + url + " method=" + method);
    }    


}
```

![](https://ws1.sinaimg.cn/large/006tNbRwly1fvxvvo8yioj313i0tudij.jpg)

At the same time, you can also get other information in the request context through `context.request()`.

![](https://ws2.sinaimg.cn/large/006tNbRwly1fvxvxmpsjcj30yy0yo77h.jpg)

## Custom configuration

By default, the configuration file under the `classpath` is read.

You can also customize the configuration file.

Just need to extends `top.crossoverjie.cicada.server.configuration.AbstractCicadaConfiguration`
class.

Write the name of the configuration file at the same time.

Like this:

```java
public class RedisConfiguration extends AbstractCicadaConfiguration {


    public RedisConfiguration() {
        super.setPropertiesName("redis.properties");
    }

}

public class KafkaConfiguration extends AbstractCicadaConfiguration {

    public KafkaConfiguration() {
        super.setPropertiesName("kafka.properties");
    }


}
```

![](https://ws3.sinaimg.cn/large/0069RVTdgy1fv5mw7p5nvj31by0fo76t.jpg)



### Get configuration information

Get the configuration infomation, follow this:

```java
KafkaConfiguration configuration = (KafkaConfiguration) getConfiguration(KafkaConfiguration.class);
RedisConfiguration redisConfiguration = (RedisConfiguration) ConfigurationHolder.getConfiguration(RedisConfiguration.class);
ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) ConfigurationHolder.getConfiguration(ApplicationConfiguration.class);

String brokerList = configuration.get("kafka.broker.list");
String redisHost = redisConfiguration.get("redis.host");
String port = applicationConfiguration.get("cicada.port");

LOGGER.info("Configuration brokerList=[{}],redisHost=[{}] port=[{}]",brokerList,redisHost,port);
```

### External configuration file

Configuration files can also be read in multiple environments, just add VM parameters, also ensure that the parameter name and file name are consistent.

```shell
-Dapplication.properties=/xx/application.properties
-Dkafka.properties=/xx/kakfa.properties
-Dredis.properties=/xx/redis.properties
```

## Custom interceptor

Implement `top.crossoverjie.cicada.example.intercept.CicadaInterceptor` interface.

```java
@Interceptor(value = "executeTimeInterceptor")
public class ExecuteTimeInterceptor implements CicadaInterceptor {

    private static final Logger LOGGER = LoggerBuilder.getLogger(ExecuteTimeInterceptor.class);

    private Long start;

    private Long end;

    @Override
    public void before(Param param) {
        start = System.currentTimeMillis();
    }

    @Override
    public void after(Param param) {
        end = System.currentTimeMillis();

        LOGGER.info("cast [{}] times", end - start);
    }
}
```

### Interceptor Adapter

If you only want to implement one of the methods ,only extends `top.crossoverjie.cicada.server.intercept.AbstractCicadaInterceptorAdapter` abstract class.

```java
@Interceptor(value = "loggerInterceptor")
public class LoggerInterceptorAbstract extends AbstractCicadaInterceptorAdapter {

    private static final Logger LOGGER = LoggerBuilder.getLogger(LoggerInterceptorAbstract.class) ;

    @Override
    public void before(Param param) {
        LOGGER.info("logger param=[{}]",param.toString());
    }

}
```

## Performance Test

![](https://ws4.sinaimg.cn/large/006tNbRwly1fv4luap7w0j31kw0iwdnu.jpg)

> Test Conditions: 100 threads and 100 connections ;1G RAM/4 CPU

**Nearly 10W requests per second.**


## ChangeLog

### v2.0.0
- Fixed [#12](https://github.com/TogetherOS/cicada/issues/12) [#22](https://github.com/TogetherOS/cicada/issues/22) [#28](28)
- Flexible routing ways.
- Pluggable `IOC` beanFactory.

### v1.0.3

- Fixed [#9](https://github.com/TogetherOS/cicada/issues/9)
- Fixed [#8](https://github.com/TogetherOS/cicada/issues/8),Multiple response ways.
- Refactoring core code and add `Cicada Context`.
- Elegant closing service.

### v1.0.2

- Fixed [#6](https://github.com/TogetherOS/cicada/issues/6)
- Customize the configuration file.
- Using flexible.
- Refactor the code.


## Contact author


> crossoverJie#gmail.com

<img src="https://ws2.sinaimg.cn/large/006tKfTcly1fsa01u7ro1j30gs0howfq.jpg" width="300"/> 

## Special thanks

- [Netty](https://github.com/netty/netty)
- [blade](https://github.com/lets-blade/blade)

