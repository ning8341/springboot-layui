# 后台管理系统

    ├─.idea
    ├─src --------主目录
    │  ├─main
    │  │  ├─java
    │  │  │  ├─com
    │  │  │  │  └─blog    
    │  │  │  │      └─manager
    │  │  │  │          ├─common   ---- 存放工具类
    │  │  │  │          │  └─utils
    │  │  │  │          ├─config   ---- 配置
    │  │  │  │          ├─controller  --- controller控制器
    │  │  │  │          │  └─system
    │  │  │  │          ├─dao        ---- dao层
    │  │  │  │          ├─dto    ----个人理解算是包装实体类的辅助类吧
    │  │  │  │          ├─filter  ----filter
    │  │  │  │          ├─pojo    ---- entity
    │  │  │  │          ├─response  ---- 这个目录结构应该怼到utils里
    │  │  │  │          ├─service  ---- service
    │  │  │  │          │  └─impl
    │  │  │  │          └─shiro   ---- realm 存放处
    │  │  │  └─tk  --- 这个是tk mybatis集成的要求，具体的可以看看tk的文档
    │  │  │      └─mapper
    │  │  └─resources
    │  │      ├─config    ----config
    │  │      ├─mapper    ----mybatis的mapper.xmls存放位置，在配置文件中指定该目录
    │  │      ├─sql       ------sql
    │  │      ├─static    -----js+css+images
    │  │      │  ├─css
    │  │      │  ├─images
    │  │      │  ├─js
    │  │      └─templates  ----- htmls


## 后端：
SpringBoot  2.1.0  
tk.mybatis  2.0.2  
（有时间了把这个tk换成mybatisPlus）
Shiro  1.3.1  
（有的系统也设计成了shiro+jwt返回token的形式）
## 前端：
Layui  2.4.5  
JQuery  3.3.1
（虽然不如react、vue等人气多，我觉得还行，上手快）
## 渲染模板
Thymeleaf 

## 部署
 blog.sql脚本
 application.yml 数据库连接信息改成你自己的
 application.java 启动
 端口号8080  地址   http://localhost:8080/login
 账号密码都是 admin
 ![在这里插入图片描述](http://a1.qpic.cn/psc?/V10tkViL1pv1uh/w47sCHZ1vIeYe.9hWkknXQPnkOGAAdYWrf.dL5K34e7f.QEpuJZ7*momxhmAiZoh7xf*2iATxBcqXAHJjhYFiw!!/c&ek=1&kp=1&pt=0&bo=WgfxAwAAAAADR80!&tl=1&vuin=875881505&tm=1593079200&sce=60-2-2&rf=viewer_4&t=5)

##  吐槽
### 1.PageDataResult这个类需要重写，请求过来直接加入
### HttpServeletRequest 对象来获取分页的参数以及其他查询条件
### 不要在controller中去判断去给分页默认值，返回格式需要重新封
### 装下，我这边没有重新封装
### 2.它这个demo对数据库设计建议把id生成策略改成UUID 
### 3.该系统没有添加统一异常处理
### 4.还有一个时间的处理，居然把时间设计成了varchar，应该改成datetime
### 然后实体类中配置springboot的时间的注解，然后配置文件中配置jackson时间的
### 格式化类型
### 5.登录页加了个live2D，怕登录页孤单
### 6.只写了一个article列表，还没想好要写什么



