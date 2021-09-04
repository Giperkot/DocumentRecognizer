package ru.digitalsoft.document.service.connect;

import java.io.File;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class HacatonConnectorApacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HacatonConnectorApacheService.class);

    @Value("${hacaton.server.url}")
    private String urlHacatonServer;


    public void sendInfoToHacatonServer() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        File file = new File("C:\\Users\\koldy\\Downloads\\Test.xls");
        HttpPost post = new HttpPost(urlHacatonServer);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addTextBody("attachments", "{\"documentNomenclatureId\": \"4f501f4a-c665-4cc8-9715-6ed26e7819f2\",\n" +
                "\"inn\": \"1650032058\",\n" +
                "\"unrecognised\": false\n" +
                "}");
        builder.addBinaryBody("createRequest", file);
        HttpEntity entity = builder.build();


        post.addHeader("Content-Type", "multipart/form-data");
        post.addHeader("Authorization", "Basic U2FmZnJvbmVyOlNhZmZyb25lcmIzSA==");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");

        LOGGER.info("Ответа responseEntity от сервера хакатон :" + responseString);
    }
}
