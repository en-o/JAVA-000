# 工作原因没有细究，只是完成了作业任务
------------------------
[被调用方客户端](/test_client)

# 周四作业：整合你上次作业的httpclient/okhttp
[源代码——1](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_1)
> 请求：http://localhost:1212/ ——> 代理 http://localhost:8801/
> 返回：8801的数据/hello,nio


# 周四作业（可选）:使用netty实现后端http访问（代替上一步骤）
[源代码——2](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_2)
> 请求：http://localhost:1212/ ——> 代理 http://localhost:8801/
> 返回：8801的数据/hello,nio
>   - 我把 HttpOutboundHandler 中的调用第三方接口的内容 改 成了 源代码——1 中http的请求了

# 周六作业：实现过滤器
[源代码——3](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_3)
> 请求：http://localhost:1212/ ——> 代理 http://localhost:8801/
[HttpRequestFilterImpl](/week03_3/filter/HttpRequestFilterImpl.java)

![添加header](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_3/image/添加header.png)

![请求8801是再一次查看header](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_3/image/请求8801是再一次查看header.png)

![客户端中消息头中没有我设置的参数](https://github.com/en-o/JAVA-000/tree/main/Week_03/week03_3/image/被调用方.png)

```java
  ## 我手动在 httpGet 请求是加入 nio , 别调用的请求头中才出现了 nio的数据
  private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        String nio = inbound.headers().get("nio");
        httpGet.setHeader("nio",nio);
    
    }

    请求头里内容是：null
    请求头里内容是：null
    请求头里内容是：tanning

```

# 周六作业（可选）：实现路由
##没来记得做

