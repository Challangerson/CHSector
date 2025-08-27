package ch.challangerson.adapter.redis.packet;

import redis.clients.jedis.JedisPubSub;

public class PacketSubscriber extends JedisPubSub {

    private final PacketHandler packetHandler;

    public PacketSubscriber(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    @Override
    public void onMessage(String channel, String message) {
        Packet packet = PacketSerializer.deserialize(message);

        if (packet != null) {
            System.out.println("Received packet of type: " + packet.getType() + " on channel: " + channel);
            packetHandler.handlePacket(packet, channel);
        } else {
            System.err.println("Failed to deserialize packet from channel: " + channel);
        }
    }
}
