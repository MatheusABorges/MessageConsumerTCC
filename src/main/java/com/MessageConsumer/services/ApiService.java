package com.MessageConsumer.services;

import com.MessageConsumer.dtos.SubscriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    public String sendSubscriptionRequest(String url, SubscriptionDTO subscriptionDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SubscriptionDTO> requestEntity = new HttpEntity<>(subscriptionDTO, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
    }
}