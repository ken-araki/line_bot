package com.linebot.client;

import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
@AllArgsConstructor
public class WebClient {
    private RestTemplate restTemplate;

    public <V, P> V post(String uri, P param, Class<V> resultClazz) {
        return this.exchange(this.postRequestEntity(uri, param), resultClazz);
    }

    public <V, P> V get(String uri, Class<V> resultClazz) {
        return this.exchange(this.getRequestEntity(uri), resultClazz);
    }

    public RequestEntity getRequestEntity(String uri) {
        return Utils.uncheck(() -> {
            return RequestEntity.get(new URI(uri)).build();
        });
    }

    public <P> RequestEntity postRequestEntity(String uri, P param) {
        return Utils.uncheck(() -> {
            return RequestEntity
                    .post(new URI(uri))
                    .body(param);
        });
    }

    public <V> V exchange(RequestEntity requestEntity, Class<V> clazz) {
        try {
            ResponseEntity<V> responseEntity = restTemplate.exchange(requestEntity, clazz);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                log.error("Response status code is not OK(200). responseEntity: {}", responseEntity);
                throw new RuntimeException("Response status code is not OK(200)");
            }
        } catch (HttpServerErrorException e) {
            log.error("HttpServerErrorException: {}", e);
            throw new RuntimeException(e);
        }
    }

    /*
    基本的にCollectionでデータを取得する時はこちらを利用したいが、
    これを利用するとLinkedHashMapのListになってしまう。
    うまいこと解消できなかったので、これは利用せず配列をListへ変換するように各Clientで実装する
    public <V> V exchange(RequestEntity requestEntity) {
        try {
            ResponseEntity<V> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<V>() {
            });
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                log.error("Response status code is not OK(200). responseEntity: {}", responseEntity);
                throw new RuntimeException("Response status code is not OK(200)");
            }
        } catch (HttpServerErrorException e) {
            log.error("HttpServerErrorException: {}", e);
            throw new RuntimeException(e);
        }
    }
    */
}
