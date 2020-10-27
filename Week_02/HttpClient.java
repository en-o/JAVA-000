import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author tn
 * @version 1
 * @ClassName HttpClient
 * @description 访问http
 * @date 2020/10/27 21:25
 */
public class HttpClient {


    public static void main(String[] args){
        CloseableHttpResponse resp = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8808/test");
            resp = httpClient.execute(httpGet);
            if(resp.getStatusLine().getStatusCode()==200){
                HttpEntity body = resp.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                String data = EntityUtils.toString(body, "utf-8");
                System.out.println(data);
            }
        }catch (Exception e){
            System.err.println("接口调用失败");
        }finally {
            //关闭资源
            try {
               if(null!=resp){
                   resp.close();
               }
            } catch (IOException e) {
                System.err.println("resp关闭失败");
            }
            try {
                if(null!=httpClient){
                    httpClient.close();
                }
            } catch (IOException e) {
                System.err.println("httpClient关闭失败");
            }
        }

    }

}
