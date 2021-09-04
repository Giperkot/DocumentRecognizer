package ru.digitalsoft.document.service.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Service
public class HacatonConnectorSpringService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HacatonConnectorSpringService.class);

    @Value("${hacaton.server.url}")
    private String urlHacatonServer;

    public void sendInfoToHacatonServer() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        LOGGER.info("Content Type " + headers.getContentType());
        headers.add("Authorization","Basic U2FmZnJvbmVyOlNhZmZyb25lcmIzSA==");

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        FileSystemResource value = new FileSystemResource(new File("C:\\Users\\koldy\\Downloads\\Test.pdf"));
        map.add("createRequest", value);

        map.add("attachments", "{\"documentNomenclatureId\": \"4f501f4a-c665-4cc8-9715-6ed26e7819f2\",\n" +
                "\"inn\": \"1650032058\",\n" +
                "\"unrecognised\": false\n" +
                "}");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(urlHacatonServer, requestEntity, String.class);
        LOGGER.info("Ответа от сервера хакатон :" + response.toString());
    }
}
