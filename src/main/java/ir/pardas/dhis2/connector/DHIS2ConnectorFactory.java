package ir.pardas.dhis2.connector;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DHIS2ConnectorFactory {
    public static final AuthCache authCache = new BasicAuthCache();
    ;

    public static CredentialsProvider createCredentialsProvider(String user, String secret) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(user, secret);
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

    public static HttpClient createHttpClient(CredentialsProvider provider) {
        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        return client;
    }

    public static HttpClientContext createHttpClientContext(String host, int port, String protocol, CredentialsProvider provider) {
        HttpHost targetHost = new HttpHost(host, port, protocol);
        authCache.put(targetHost, new BasicScheme());
        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);
        context.setAuthCache(authCache);
        return context;
    }

    public static HttpGet createHttpGet(URI uri) {
        return new HttpGet(uri);
    }

    public static HttpPost createHttpPost(URI uri, List<NameValuePair> nameValuePairs) {
        HttpPost post = new HttpPost(uri);
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json;charset=UTF-8");
            return post;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpPost createHttpPost(URI uri, AbstractHttpEntity entity) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json;charset=UTF-8");
        return post;
    }

    public static List<NameValuePair> createNameValuePairs() {
        return new ArrayList<NameValuePair>();
    }

    public static BasicNameValuePair createBasicNameValuePair(String name, String value) {
        return new BasicNameValuePair(name, value);
    }

    public static StringEntity createJSONStringEntity(String json) {
        return new StringEntity(json, ContentType.APPLICATION_JSON);
    }

    public static String getResponse(HttpResponse response) {
        // Get the response
        StringBuilder textView = new StringBuilder();
        BufferedReader rd = null;
        try {
            rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                textView.append(line);
            }
            return textView.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
