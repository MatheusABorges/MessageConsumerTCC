package com.MessageConsumer.models;

import java.util.ArrayList;
import java.util.List;

public class Metrics {

    private List<Long> timestamps;
    private List<Float> latencies;

    public Metrics() {
        this.timestamps = new ArrayList<>();
        this.latencies = new ArrayList<>();
    }

    public void appendTimestamp(long timestamp){
        this.timestamps.add(timestamp);
    }

    public void appendLatency(float latency){
        this.latencies.add(latency);
    }

    public List<Long> getTimestamps() {
        return timestamps;
    }

    public List<Float> getLatencies() {
        return latencies;
    }
}
