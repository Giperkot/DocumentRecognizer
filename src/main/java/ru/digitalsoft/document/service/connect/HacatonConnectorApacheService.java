package ru.digitalsoft.document.service.connect;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class HacatonConnectorApacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HacatonConnectorApacheService.class);

    @Value("http://elib-hackathon.psb.netintel.ru/elib/api/service/documents")
    private String urlHacatonServer;


    public String sendInfoToHacatonServer() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        File file = new File("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset\\ПАО НКХП 2315014748\\Финансовое досье\\2020\\4 квартал\\Бухгалтерская отчетность\\Форма 1.pdf");
        HttpPost post = new HttpPost(urlHacatonServer);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

        String textPart = "{\"documentNomenclatureId\": \"4f501f4a-c665-4cc8-9715-6ed26e7819f2\",\n" +
                "\"inn\": \"2315014748\",\n" +
                "\"unrecognised\": false\n" +
                "}";

        builder.addPart("attachments", fileBody);
        builder.addPart("createRequest", new StringBody(textPart, ContentType.MULTIPART_FORM_DATA));

        HttpEntity entity = builder.build();

        post.addHeader("Authorization", "Basic U2FmZnJvbmVyOlNhZmZyb25lcmIzSA==");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity, "UTF-8");


        LOGGER.info("Ответа responseEntity от сервера хакатон :" + responseString);
        return responseString;
    }
}
