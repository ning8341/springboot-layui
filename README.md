### 一、功能点以及实现的进度

> 本次搭建是基于springboot+layui+nginx实现的前后台分离的设计
>
> 1. orm依旧采用了熟悉的mybatisplus 3.1.2
> 2. 集成druid（账号密码admin），访问地址   http://localhost:8080/druid/index.html  
> 3. 集成shiro+jwt，详细查看第三段的图解说明
> 4. 配置nginx，指定前端代码
> 5. 集成redis，将用户信息以及权限记录到缓存数据库中
> 6. 前端集成layuicms，目前只是登录，《用户中心》模块简单的分页列表，其余模块均是假数据
> 7. 前端登录处理逻辑请自行跟踪代码，比较简单的流程
> 8. 后端设置缓存到期时间为30分钟，前端的缓存放到localstorage
> 9. 定制layui的table.js修改了请求自动添加headers(headers中带token)

### 二、环境准备

> - redis环境自行搭建，不再赘述。
> - mysql环境自行搭建，sql脚本在/resources/sql/bbs.sql
> - nginx自行搭建，配置如下

```
#nginx配置示例
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;	
    server {
        listen       8000;
        server_name  localhost;   
		charset utf-8;
		#指定前端代码所在路径
		location / {
		   root  E:/layui/frontend_springboot_layui;
		   index index.html index2.htm;
		}
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
        #拦截 /api开头的请求
		location /api {
			proxy_set_header Host $host;
			proxy_set_header X-Real-Ip $remote_addr;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			proxy_set_header X-Forworded-For $http_x_forwarded_for;
			# 后台服务路径，注意这个结尾的/
            proxy_pass   http://localhost:8080/;
        }		
    }
}
```



### 三、使用说明

> -  登录接口 POST  /sys/login 参数请参考下图；
> -  登录成功后包含token信息；
> -  服务端负责token生命周期的刷新(就是每次点登陆就生成一套token，依据规则放到redis中)
> -  用户权限的校验；

### 四、后端演示

> **使用正确的用户名密码进行登陆，登陆成功后返回token**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlLzc0NThkMGEyODYzZGI1YjY4ZGFhYWEzYzVjYmY4NjQzLnBuZw?x-oss-process=image/format,png)
> **使用错误的用户名密码进行登陆，登陆失败**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlLzIwMTFmOTkzMzkyY2ZlZDgzYjljZjIxZmUxMDA3YTY4LnBuZw?x-oss-process=image/format,png)
> **headers中携带正确的token访问接口**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlLzNlMDIxOTRlYTM4ZTdmYzFjZGY3NjQ4YTc0M2M4Y2ZhLnBuZw?x-oss-process=image/format,png)
> **headers中不携带token或者携带错误的token访问接口**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlLzdlZjBmZmY4NjA5MTUyNjE2MmRkYmMyM2IyZDJiNGI1LnBuZw?x-oss-process=image/format,png)
> **无权限的用户访问接口**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlL2E2OWI4Njg5ZWZkMGZlNzBjYjU5MjE5NGIzOWNlODEwLnBuZw?x-oss-process=image/format,png)
> **无需登陆token也可以访问的接口（在过滤器中将接口或者资源文件放开）**
> ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcuZ2F0aHViLmNuL2ltYWdlLzEyMjY2ZTE5MDljNjMxYjQ2ZmE3ZjVmYTAwZWFlYzllLnBuZw?x-oss-process=image/format,png)

### 五、前端演示

