package week03_2.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author tn
 * @version 1
 * @ClassName HttpOutboundHandler
 * @description httpClient
 * @date 2020/10/31 14:59
 */
public class HttpOutboundHandler {
    private String backendUrl;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    // invoking
    public void requset(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
        final String url = this.backendUrl + fullRequest.uri();
        invoking(fullRequest, ctx, url);
    }


    // invoking
    private String invoking(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url){
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse resp = httpClient.execute(httpGet) ) {
            handleResponse(inbound, ctx, resp);

        }catch (Exception e){
            System.err.println("接口调用失败");
            e.printStackTrace();
        }
        return "接口调用失败";
    }

    private  void  handleResponse(final FullHttpRequest request, final ChannelHandlerContext ctx, final HttpResponse endpointResponse){
        FullHttpResponse response = null;
        try {

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(requset()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

        }catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }

    // invoking
    private static byte[] requset(){
        HttpGet httpGet = new HttpGet("http://localhost:8801");
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse resp = httpClient.execute(httpGet) ) {
            if(resp.getStatusLine().getStatusCode()==200){
                HttpEntity body = resp.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
//                String data = EntityUtils.toString(body, "utf-8");
                byte[] bytes = EntityUtils.toByteArray(body);
                return bytes;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("接口调用失败");
        }
        return null;
    }


}
