
<div align="center">  

<img src="https://ws3.sinaimg.cn/large/006tNbRwly1fuvfxbc7y1j30go0e9aay.jpg" width="300"/> 
<br/>

[![Build Status](https://travis-ci.org/crossoverJie/cicada.svg?branch=master)](https://travis-ci.org/crossoverJie/cicada)
[![](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/top.crossoverjie.opensource/cicada-core/)
[![QQ群](https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg)](https://jq.qq.com/?_wv=1027&k=5HPYvQk)

[qq0groupsvg]: https://img.shields.io/badge/QQ%E7%BE%A4-787381170-yellowgreen.svg
[qq0group]: https://jq.qq.com/?_wv=1027&k=5HPYvQk

</div><br>




Lightweight HTTP service framework.


## 简介

基于 Netty4 实现的快速、轻量级 WEB 框架；没有过多的依赖，核心 jar 包仅 `30KB`。

## 特性

- [x] 代码简洁，没有过多依赖。
- [x] 一行代码即可启动 HTTP 服务。
- [x] 自定义拦截器。
- [x] 灵活的传参方式。
- [x] `json` 响应格式。
- [x] 基于 `jar` 启动。
- [ ] 多种路由风格。


## 快速启动

创建一个 maven 项目，引入核心依赖。

```java
<dependency>
    <groupId>top.crossoverjie.opensource</groupId>
    <artifactId>cicada-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

启动类：


<img src="https://ws4.sinaimg.cn/large/006tNbRwly1fuvijd84vhj30zk0fignz.jpg" width="300"/> 
<br/>

### 配置业务 Action

创建业务 Action 实现 `top.crossoverjie.cicada.server.action.WorkAction` 接口。

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

启动应用访问 [http://127.0.0.1:7317/cicada-example/demoAction?name=12345&id=10](http://127.0.0.1:7317/cicada-example/demoAction?name=12345&id=10)：

![](https://ws1.sinaimg.cn/large/006tNbRwly1fuvibn4xavj31ei074dgj.jpg)


## 自定义拦截器

实现 `top.crossoverjie.cicada.example.intercept.CicadaInterceptor` 


### 拦截适配器



## 联系作者



