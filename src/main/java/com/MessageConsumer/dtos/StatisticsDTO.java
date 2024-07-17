package com.MessageConsumer.dtos;

public class StatisticsDTO {

    private String carteiroUrl;

    private SubscriptionDTO subscriptionDTO;


    public SubscriptionDTO getSubscriptionDTO() {
        return subscriptionDTO;
    }

    public void setSubscriptionDTO(SubscriptionDTO subscriptionDTO) {
        this.subscriptionDTO = subscriptionDTO;
    }

    public String getCarteiroUrl() {
        return carteiroUrl;
    }

    public void setCarteiroUrl(String carteiroUrl) {
        this.carteiroUrl = carteiroUrl;
    }
}
