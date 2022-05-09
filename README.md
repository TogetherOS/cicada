
<div align="center">  

<img src="https://i.loli.net/2020/02/23/oTrqAsjxV4wH3IE.png"  /> 
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
- [x] [Custom interceptor](#custom-interceptor).
- [x] [Custom exception handle](#custom-exception-handle).
- [x] Flexible parameters way.
- [x] Response `json`.
- [x] Start with `jar`.
- [x] [Custom configuration](#custom-configuration).
- [x] Multiple response ways.
- [x] Pluggable `IOC` beanFactory。
- [x] [Support `Cookie`](#cookie).
- [ ] File Upload.


## Quick Start

Create a project with `Maven`, import core dependency.


```xml
<dependency>
    <groupId>top.crossoverjie.opensource</groupId>
    <artifactId>cicada-core</artifactId>
    <version>x.y.z</version>
</dependency>
```

Of course, it is recommended to introduce an additional `IOC` container plugin:
```xml
<dependency>
    <groupId>top.crossoverjie.opensource</groupId>
    <artifactId>cicada-ioc</artifactId>
    <version>2.0.4</version>
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


## Cookie Support

### Set Cookie

```java
Cookie cookie = new Cookie() ;
cookie.setName("cookie");
cookie.setValue("value");
CicadaContext.getResponse().setCookie(cookie);
```

### Get Cookie

```java
Cookie cookie = CicadaContext.getRequest().getCookie("cookie");
logger.info("cookie = " + cookie.toString());
```


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
    public boolean before(Param param) {
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void after(Param param) {
        end = System.currentTimeMillis();

        LOGGER.info("cast [{}] times", end - start);
    }
}
```

## Custom exception handle

You can define global exception handle,like this:

```java
@CicadaBean
public class ExceptionHandle implements GlobalHandelException {
    private final static Logger LOGGER = LoggerBuilder.getLogger(ExceptionHandle.class);

    @Override
    public void resolveException(CicadaContext context, Exception e) {
        LOGGER.error("Exception", e);
        WorkRes workRes = new WorkRes();
        workRes.setCode("500");
        workRes.setMessage(e.getClass().getName());
        context.json(workRes);
    }
}
```


## Performance Test

![](https://user-images.githubusercontent.com/15684156/50533885-5dd41900-0b6e-11e9-925f-6ee563664f85.png)

> Test Conditions: 100 threads and 100 connections ;1G RAM/4 CPU

**Nearly 10W requests per second.**


## ChangeLog

### v2.0.2
- fix [#40](https://github.com/TogetherOS/cicada/issues/40) 
- add global handle exception interface.
- get bean by class type.

### v2.0.1
- Logo.
- Cookie Support.
- Beautify the log.

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

![qrcode_for_gh_3a954a025f10_258.jpg](https://i.loli.net/2019/07/09/5d245f3e955ce61699.jpg) 

## Special thanks

- [Netty](https://github.com/netty/netty)
- [blade](https://github.com/lets-blade/blade)

