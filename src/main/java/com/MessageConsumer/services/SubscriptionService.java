package com.MessageConsumer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SubscriptionService {

    @Autowired
    private RestTemplate restTemplate;

    public void subscribe(String mailmanHost, int mailmanPort, String filter, int messageRate, String receiverHost, int receiverPort) {
        String url = "http://" + mailmanHost + ":" + mailmanPort + "/subscribe";
        SubscriptionRequest request = new SubscriptionRequest(receiverHost, receiverPort, filter, messageRate);
        restTemplate.postForObject(url, request, Void.class);
    }

    static class SubscriptionRequest {
        private final String host;
        private final int port;
        private final String filter;
        private final int messageRate;

        public SubscriptionRequest(String host, int port, String filter, int messageRate) {
            this.host = host;
            this.port = port;
            this.filter = filter;
            this.messageRate = messageRate;
        }

        // Getters and setters (or use Lombok)
    }
}