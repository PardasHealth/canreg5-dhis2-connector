package ir.pardas.dhis2.connector;

import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.AbstractHttpEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class DHIS2Connector<T>{

    private HttpClient client;
    private CredentialsProvider provider;
    private HttpClientContext context;
    private HttpResponse response;
    private Properties config;
    private URL url;

    public DHIS2Connector() {
        config = ConfigUtil.read("/dhis2.properties");
        try {
            url = new URL(config.getProperty("host"));
            provider = DHIS2ConnectorFactory.createCredentialsProvider(config.getProperty("user"), config.getProperty("password"));
            context = DHIS2ConnectorFactory.createHttpClientContext(url.getHost(),url.getPort(),url.getProtocol(), provider);
            client = DHIS2ConnectorFactory.createHttpClient(provider);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected String post(String path, String payload) {
        return post(path,null,payload);
    }

    protected String post(String path, String queryString, String payload) {
        try {
            AbstractHttpEntity entity = DHIS2ConnectorFactory.createJSONStringEntity(payload);

            URI uri = new URI(url.toString() + path + (queryString != null && !"".equals(queryString) ? "?" + queryString : ""));

            HttpPost request = DHIS2ConnectorFactory.createHttpPost(uri, entity);
            response = client.execute(request, context);
            return DHIS2ConnectorFactory.getResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String get(String path, String queryString) {
        try {
            URI uri = new URI(config.getProperty("host") + path + (queryString != null && !"".equals(queryString) ? "?" + queryString : ""));

            HttpGet request = DHIS2ConnectorFactory.createHttpGet(uri);
            response = client.execute(request, context);
            return DHIS2ConnectorFactory.getResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }
}