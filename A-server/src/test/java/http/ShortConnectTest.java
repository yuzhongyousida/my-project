package http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author: wangteng
 * @description: http 短连接实验
 * @date:2018/6/19
 */
public class ShortConnectTest {

    public static void main(String[] args) {
        nioTest();
//        bioTest();

    }

    private static void nioTest(){
        try {

            IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                    .setIoThreadCount(8)
                    .build();

            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

            PoolingNHttpClientConnectionManager pcm = new PoolingNHttpClientConnectionManager(ioReactor);
            pcm.setMaxTotal(50000);
            pcm.setDefaultMaxPerRoute(50000);

            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000)
                    .setConnectTimeout(1000)
                    .build();

            CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.custom()
                    .setConnectionManager(pcm)
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            closeableHttpAsyncClient.start();

            long count = 1;
            long start = System.currentTimeMillis();
            String url = "http://10.72.221.172:8080/group/1k/a";
            while (true){
                if ((System.currentTimeMillis() - start) > 10000){
                    break;
                }
                HttpGet get = new HttpGet(url);

                closeableHttpAsyncClient.execute(get, new FutureCallback<HttpResponse>() {
                    @Override
                    public void completed(HttpResponse httpResponse) {
//                        try {
//                            System.out.println(EntityUtils.toString(httpResponse.getEntity()));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void failed(Exception e) {
//                        System.out.println("-------failed--------");
                    }

                    @Override
                    public void cancelled() {
//                        System.out.println("-------cancelled--------");
                    }
                });

                count++;
            }

            System.out.println("---------count--------" + count);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private static void bioTest(){
        String url = "http://10.72.221.172:8080/group/1k/a";
        long start = System.currentTimeMillis();
        long count = 1;
        while (true) {
            if ((System.currentTimeMillis() - start) > 10000) {
                break;
            }

            System.out.println(httpGet(url));
            count++;
        }
        System.out.println("---------count--------" + count );
    }

    private static String httpGet(String url) {
        String responseStr =  null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            System.out.println(method.getStatusLine());
            byte[] bytes = method.getResponseBody();
            responseStr = new String(bytes, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return responseStr;
    }

}
