package net.scandicraft.http;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.util.List;

public class HTTPClient {

    private final Header[] headers;

    public HTTPClient() {
        this.headers = new Header[]{
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
        };
    }

    public HTTPClient(final String api_token) {
        this.headers = new Header[]{
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + api_token),
        };
    }

    public HTTPReply get(String endpoint) {
        return get(endpoint, null);
    }

    public HTTPReply get(String endpoint, List<NameValuePair> params) {
        try {
            if (params != null) {
                endpoint += "?" + URLEncodedUtils.format(params, "UTF-8");
            }
            HttpGet httpGet = new HttpGet(endpoint);
            httpGet.setHeaders(this.headers);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return new HTTPReply(httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new HTTPReply(null);
        }
    }

    public HTTPReply post(String endpoint, Object entity) {
        try {
            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.setHeaders(this.headers);

            if (entity != null) {
                Gson gson = new Gson();
                String json = gson.toJson(entity);
                httpPost.setEntity(new StringEntity(json));
            }

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            return new HTTPReply(httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new HTTPReply(null);
        }
    }

    public void postAsync(String endpoint, List<NameValuePair> params) {
        new Thread() {
            @Override
            public void run() {
                post(endpoint, params);
            }
        }.start();
    }

}
