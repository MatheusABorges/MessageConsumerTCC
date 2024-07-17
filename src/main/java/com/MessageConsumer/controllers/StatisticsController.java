package com.MessageConsumer.controllers;


import com.MessageConsumer.models.Metrics;
import com.MessageConsumer.services.ApiService;
import com.MessageConsumer.services.UdpReceiverService;
import com.MessageConsumer.dtos.StatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatisticsController {

    @Autowired
    private UdpReceiverService udpReceiverService;

    @Autowired
    private ApiService apiService;

    @GetMapping("/statistics")
    public Statistics getStatistics() {
        return new Statistics(udpReceiverService.getMessageCount(), udpReceiverService.getFilteredCount());
    }

    @PostMapping("/benchmark")
    public String startBenchmark(@RequestBody StatisticsDTO statisticsDTO){
        String response = apiService.sendSubscriptionRequest(statisticsDTO.getCarteiroUrl(), statisticsDTO.getSubscriptionDTO());
        udpReceiverService.resetMetrics();
        //udpReceiverService.startReceiver();
        return response;
    }

    @GetMapping("/results")
    @ResponseStatus(HttpStatus.OK)
    public Metrics getResults(){
        return (udpReceiverService.getMetrics());
    }

    static class Statistics {
        private final int messageCount;
        private final int filteredCount;

        public Statistics(int messageCount, int filteredCount) {
            this.messageCount = messageCount;
            this.filteredCount = filteredCount;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public int getFilteredCount() {
            return filteredCount;
        }
    }
}