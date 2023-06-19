<div align="center">
    <p align="center">
        <img src="./snowy-admin-web/public/img/logo.png" height="150" alt="logo"/>
    </p>
</div>

## 框架介绍

Snowy-Cloud（SnowyAdmin微服务版）是国内首个国密前后端分离快速开发平台，集成国密加解密插件，
软件层面完全符合等保测评要求，同时实现国产化机型、中间件、数据库适配，是您的不二之选！
技术框架与密码结合，让更多的人认识密码，使用密码；更是让前后分离“密”不可分。


## 快速启动

全栈工程师推荐idea

### 前端支撑
| 插件 | 版本   | 用途 |
|--- | ----- | ----- |
| node.js | 最新版 |  JavaScript运行环境 |

### 启动前端

```
npm install
```
```
npm run dev
```
### 后端支撑
| 插件 | 版本 | 用途 |
| --- | ----- |  ----- |
| jdk | 11 / 1.8 |java环境 |
| lombok | idea内 |代码简化插件 |
| maven | 最新版 |包管理工具 |
| redis | 最新版 | 缓存库 |
| mysql | 8.0 / 5.7 | 数据库 |

### 启动后端
开发工具内配置好maven并在代码中配置数据库即可启动

## 代码结构

```
snowy
  |-snowy-admin-web == 前端
    |-public == 基础静态文件
    |-src == 前端源代码
      |-api == API接口转发
      |-assets == 静态文件
      |-components == VUE组件
      |-config == 基础配置
      |-layout == 基础布局
      |-locales == 多语言配置
      |-router == 基础路由配置
      |-store == Pinia缓存配置
      |-style == 样式风格配置
      |-utils == 工具类
      |-views == 所有视图界面
  |-snowy-base == 基础组件
    |-snowy-common == 基础通用模块
  |-snowy-modules == 应用组件
    |-snowy-biz-app == 业务应用模块
    |-snowy-web-app == 主应用模块
  |-snowy-plugin == 插件组件
    |-snowy-plugin-auth == 登录鉴权插件
        |-snowy-plugin-auth-api == 登录鉴权插件api接口
        |-snowy-plugin-auth-feign == 登录鉴权插件feign接口
        |-snowy-plugin-auth-func == 登录鉴权插件func实现
    |-snowy-plugin-biz == 业务功能插件
        |-snowy-plugin-biz-api == 业务功能插件api接口
        |-snowy-plugin-biz-func == 业务功能插件func实现
    |-snowy-plugin-client == C端功能插件
        |-snowy-plugin-client-api == C端功能插件api接口
        |-snowy-plugin-client-func == C端功能插件func实现
    |-snowy-plugin-dev == 开发工具插件
        |-snowy-plugin-dev-api == 开发工具插件api接口
        |-snowy-plugin-dev-feign == 开发工具插件feign接口
        |-snowy-plugin-dev-func == 开发工具插件func实现
    |-snowy-plugin-gen == 代码生成插件
        |-snowy-plugin-gen-api == 代码生成插件api接口
        |-snowy-plugin-gen-func == 代码生成插件func实现
    |-snowy-plugin-sys == 系统功能插件
        |-snowy-plugin-sys-api == 系统功能插件api接口
        |-snowy-plugin-sys-feign == 系统功能插件feign接口
        |-snowy-plugin-sys-func == 系统功能插件func实现
  |-snowy-server == 服务组件
    |-snowy-actuator-app == 监控服务模块
    |-snowy-gateway-app == 网关服务模块
    |-snowy-nacos-app == 注册中心/配置中心模块
    |-snowy-sentinel-app == 限流服务模块
    |-snowy-xxl-job-app == 分布式任务调度服务模块

```

## 架构原理图
* 业务架构
<p align="center">
    <img src="https://pan.xiaonuo.vip/?explorer/share/fileOut&shareID=9Mor3UYA&path=%7BshareItemLink%3A9Mor3UYA%7D%2F"/>
</p>

* 应用架构
<p align="center">
    <img src="https://pan.xiaonuo.vip/?explorer/share/fileOut&shareID=9MosIA7A&path=%7BshareItemLink%3A9MosIA7A%7D%2F"/>
</p>

* 数据架构
<p align="center">
    <img src="https://pan.xiaonuo.vip/?explorer/share/fileOut&shareID=9MoseTCw&path=%7BshareItemLink%3A9MoseTCw%7D%2F"/>
</p>

* 技术架构
<p align="center">
    <img src="https://pan.xiaonuo.vip/?explorer/share/fileOut&shareID=9MospSEQ&path=%7BshareItemLink%3A9MospSEQ%7D%2F"/>
</p>

* 部署架构
<p align="center">
    <img src="https://pan.xiaonuo.vip/?explorer/share/fileOut&shareID=9Mos0iCg&path=%7BshareItemLink%3A9Mos0iCg%7D%2F"/>
</p>


## 密码分步:fire:

| 功能                        | 算法类型          |
| ----------------------      | ------------- |
| 登录        | SM2前端加密，后端解密 |
| 登录登出日志        | SM2对登录登出日志做签名完整性保护存储    |
| 操作日志        | SM2对操作日志做签名完整性保护存储    |
| 用户密码        | SM3完整性保护存储，登录时做完整性校验    |
| 用户手机号        | SM4（cbc模式）加解密使用字段脱敏    |

