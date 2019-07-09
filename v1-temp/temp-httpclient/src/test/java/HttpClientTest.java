import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/18
 */
public class HttpClientTest {

    @Test
    public void grapHTMLTest() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String url="http://localhost:9093/item/createHTMLById/3";
        HttpGet getRequest = new HttpGet(url);
        CloseableHttpResponse response = client.execute(getRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
//            HttpEntity entity = response.getEntity();
//            InputStream inputStream = entity.getContent();
//            byte[] bs = new byte[1024];
//            int len;
//            while ((len = inputStream.read(bs)) != -1) {
//                System.out.println(new String(bs,0,len));
//            }
            System.out.println(EntityUtils.toString(response.getEntity()));
        } else {
            System.out.println(statusCode);
        }
    }

    @Test
    public void utilsTest() {
//        String response = HttpClientUtils.doGet("http://localhost:9093/item/createHTMLById/3");
//        System.out.println(response);

        Map<String, String> map = new HashMap<String, String>();
        map.put("username","ghg");
        map.put("password", "123456");
        System.out.println(HttpClientUtils.doGet("http://localhost:9093/item/param", map));
    }
}
