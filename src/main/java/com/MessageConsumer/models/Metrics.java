package com.MessageConsumer.models;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Metrics {

    private AtomicInteger messageCount;
    private AtomicLong totalLatency;
    private AtomicLong startTime = null;
    private AtomicLong lastReceivedTime = new AtomicLong();

    public Metrics() {
        this.messageCount = new AtomicInteger(0);
        this.totalLatency = new AtomicLong(0);
    }

    public void incrementMessageCount() {
        messageCount.incrementAndGet();
    }

    public int getMessageCount() {
        return messageCount.get();
    }

    public void addLatency(long latency) {
        totalLatency.addAndGet(latency);
    }

    public long getTotalLatency() {
        return totalLatency.get();
    }

    public Long getStartTime() {
        return startTime == null ? null : startTime.get();
    }
    public void setStartTime(Long startTime) {
        this.startTime = new AtomicLong(startTime);
    }

    public double getThroughput() {
        long elapsedTime = System.currentTimeMillis() - startTime.get();
        return (messageCount.get() / (elapsedTime / 1000.0));
    }

    public double getAverageLatency() {
        return messageCount.get() == 0 ? 0 : (double) totalLatency.get() / messageCount.get();
    }

    public AtomicLong getLastReceivedTime() {
        return lastReceivedTime;
    }

    public void setLastReceivedTime(Long lastReceivedTime) {
        this.lastReceivedTime.set(lastReceivedTime);
    }
}
