package com.MessageConsumer.models;

import java.util.*;

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

    public double computeAverageLatency(List<Float> latencies) {
        if (latencies.isEmpty()) {
            return 0.0;
        }

        double totalLatency = 0.0;

        // Sum all latencies
        for (double latency : latencies) {
            totalLatency += latency;
        }

        // Calculate average latency
        return totalLatency / latencies.size();
    }

    public double computeAverageThroughput() {
        if (timestamps.size() < 2) {
            return 0.0;
        }
        long startTime = timestamps.getFirst();
        long endTime = timestamps.getLast();
        double durationInSeconds = (endTime - startTime) / 1000.0;
        return timestamps.size() / durationInSeconds;
    }

    private double getPercentile(double percentile) {
        int index = (int) Math.ceil((percentile / 100) * latencies.size()) - 1;
        return latencies.get(index);
    }

    public Map<String, Double> computeLatencyPercentiles() {
        Map<String, Double> percentiles = new HashMap<>();
        Collections.sort(latencies);

        percentiles.put("50", getPercentile(50));
        percentiles.put("75", getPercentile(75));
        percentiles.put("90", getPercentile(90));
        percentiles.put("99", getPercentile(99));
        percentiles.put("99.9", getPercentile(99.9));
//        percentiles.put("99.999", getPercentile(99.999));
        percentiles.put("100", getPercentile(100));

        return percentiles;
    }

    public String generateJsonStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("averageThroughput", computeAverageThroughput());
        statistics.put("averageLatency", computeAverageLatency(latencies));
        statistics.put("latencyPercentiles", computeLatencyPercentiles());
        statistics.put("totalMessages",latencies.size());
        statistics.put("totalMessagesProducer",latencies.size());
        return new com.google.gson.Gson().toJson(statistics);
    }
}
