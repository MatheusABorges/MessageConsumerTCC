package com.MessageConsumer.services;

import com.MessageConsumer.models.Metrics;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.MessageConsumer.protos.MessageWrapperOuterClass.MessageWrapper;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UdpReceiverService {

    private final int port = 9877; // The port to receive messages
    private Metrics metrics = new Metrics();

    @PostConstruct
    public void startReceiver() {
        new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(port)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    byte[] data = new byte[packet.getLength()];
                    System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());

                    ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());


                    MessageWrapper message = MessageWrapper.parseFrom(byteStream);
                    processMessage(message, System.currentTimeMillis());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processMessage(MessageWrapper message, long timestamp) {
        if(message.getIsWarmingUp()){
            return;
        }
        long latency = timestamp - message.getTimestamp();
        metrics.appendLatency(latency);
        metrics.appendTimestamp(timestamp);
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public void resetMetrics(){
        this.metrics = new Metrics();
    }
}
