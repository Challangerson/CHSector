package ch.challangerson.adapter.redis.packet;

import ch.challangerson.adapter.redis.RedisManager;

public class PacketSender {

    private final RedisManager redisManager;

    public PacketSender(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public void sendPacket(String channel, Packet packet) {
        this.redisManager.publish(channel, PacketSerializer.serialize(packet));
    }
}
