package ch.challangerson;

import ch.challangerson.adapter.redis.RedisManager;
import ch.challangerson.adapter.redis.packet.PacketHandler;
import ch.challangerson.adapter.redis.packet.PacketSender;
import ch.challangerson.adapter.redis.packet.PacketSubscriber;

public class Shared {

    private static Shared shared;
    private final RedisManager redisManager;
    private final PacketHandler packetHandler;
    private final PacketSender packetSender;

    public Shared() {
        shared = this;
        this.packetHandler = new PacketHandler();
        this.redisManager = new RedisManager("localhost", 6379, null, new PacketSubscriber(this.packetHandler));
        this.packetSender = new PacketSender(this.redisManager);
    }

    public void shutdown() {
        this.redisManager.disconnect();
    }

    public static Shared getShared() {
        return shared;
    }

    public RedisManager getRedisManager() {
        return this.redisManager;
    }

    public PacketHandler getPacketHandler() {
        return this.packetHandler;
    }

    public PacketSender getPacketSender() {
        return this.packetSender;
    }
}