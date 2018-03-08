package com.etrip.util.common;

import freemarker.template.utility.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http同步请求工具类
 * 使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可：
 * 1、创建CloseableHttpClient对象。
 * 2、创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 3、如果需要发送请求参数，可可调用setEntity(HttpEntity entity)方法来设置请求参数。setParams方法已过时（4.4.1版本）。
 * 4、调用HttpGet、HttpPost对象的setHeader(String name, String value)方法设置header信息，或者调用setHeaders(Header[] headers)设置一组header信息。
 * 5、调用CloseableHttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个CloseableHttpResponse。
 * 6、调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容；调用CloseableHttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头。
 * 7、释放连接。无论执行方法是否成功，都必须释放连接
 */
public class HttpUtil {

    private static final Logger LOGGER = Logger.getLogger(HttpUtil.class);

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final int DEFAULT_CODE = 200;


    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {
        String result = null;
        try {
            // 创建HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // 参数封装
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, String> entry : param.entrySet()){
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }

            // 创建httpGet对象,
            URI uri = uriBuilder.build();
            HttpGet httpGet = new HttpGet(uri);

            // 封装header
            buildHeader(headers, httpGet);

            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // 解析response
            if (response!=null && response.getStatusLine().getStatusCode()==DEFAULT_CODE) {
                result = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }


    public static String doPost(String url, Map<String, String> param, Map<String, String> headers, String encoding) {
        String result = null;
        try {
            // 创建HttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // 闯将post方式请求对象
            HttpPost httpPost = new HttpPost(url);

            // 封装参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : param.entrySet()){
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 封装header
            buildHeader(headers, httpPost);

            // 将请求参数封装到entity属性中
            httpPost.setEntity(new UrlEncodedFormEntity(params, StringUtils.isEmpty(encoding)? DEFAULT_ENCODING : encoding));

            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            // 解析response
            if (response!=null && response.getStatusLine().getStatusCode()==DEFAULT_CODE) {
                result = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }



    private static void buildHeader(Map<String, String> headers, HttpRequest httpRequest){
        if(MapUtils.isEmpty(headers)){
            return;
        }

        for (Map.Entry<String, String> entry : headers.entrySet()){
            httpRequest.addHeader(entry.getKey(), entry.getValue());
        }
    }

}
