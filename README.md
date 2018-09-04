
<div align="center">  

<img src="https://ws3.sinaimg.cn/large/006tNbRwly1fuvfxbc7y1j30go0e9aay.jpg" width="300"/> 
<br/>

[![Build Status](https://travis-ci.org/crossoverJie/cicada.svg?branch=master)](https://travis-ci.org/crossoverJie/cicada)
[![](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/)
[![QQ群](https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg)](https://jq.qq.com/?_wv=1027&k=5HPYvQk)

[qq0groupsvg]: https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg
[qq0group]: https://jq.qq.com/?_wv=1027&k=5HPYvQk

</div><br>

[中文文档](https://github.com/crossoverJie/cicada/blob/master/README-ZH.md)

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
- [ ] Custom configuration.
- [ ] Multiple routing ways.
- [ ] Support `HTTPS`.
- [ ] Support `Cookie`.
- [ ] File Upload.


## Quick Start

Create a project with `Maven`, import core dependency.


```java
<dependency>
    <groupId>top.crossoverjie.opensource</groupId>
    <artifactId>cicada-core</artifactId>
    <version>1.0.1</version>
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

### Configuring business Action


Create business Action implement `top.crossoverjie.cicada.server.action.WorkAction` interface:

```java
@CicadaAction(value = "demoAction")
public class DemoAction implements WorkAction {


    private static final Logger LOGGER = LoggerBuilder.getLogger(DemoAction.class) ;

    private static AtomicLong index = new AtomicLong() ;

    @Override
    public WorkRes<DemoResVO> execute(Param paramMap) throws Exception {
        String name = paramMap.getString("name");
        Integer id = paramMap.getInteger("id");
        LOGGER.info("name=[{}],id=[{}]" , name,id);

        DemoResVO demoResVO = new DemoResVO() ;
        demoResVO.setIndex(index.incrementAndGet());
        WorkRes<DemoResVO> res = new WorkRes();
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        res.setDataBody(demoResVO) ;
        return res;
    }

}
```

Launch and apply access: [http://127.0.0.1:7317/cicada-example/demoAction?name=12345&id=10](http://127.0.0.1:7317/cicada-example/demoAction?name=12345&id=10)

```json
{
    "code": "9000",
    "dataBody": {
        "index": 1
    },
    "message": "成功"
}
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

![](https://ws3.sinaimg.cn/large/006tNbRwly1fuvheff4smj317m0mgdhs.jpg)

> Test Conditions: 300 concurrency for twice ;1G RAM/one CPU/1Mbps.

## Contact author


> crossoverJie#gmail.com

<img src="https://ws2.sinaimg.cn/large/006tKfTcly1fsa01u7ro1j30gs0howfq.jpg" width="300"/> 

