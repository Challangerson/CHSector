package ch.challangerson.adapter.redis;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisManager {

    private final JedisPool jedisPool;
    private final JedisPubSub pubSub;

    public RedisManager(String host, int port, String password, JedisPubSub pubSub) {
        JedisPoolConfig jedisPoolConfig = this.getJedisPoolConfig();

        if(password == null || password.isEmpty()) {
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port);
        } else {
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port, 2000, password);
        }

        this.pubSub = pubSub;
    }

    private JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(1);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setNumTestsPerEvictionRun(10);
        return jedisPoolConfig;
    }

    public Jedis getConnection() {
        return this.jedisPool.getResource();
    }

    public void disconnect() {
        this.getConnection().close();
        if (this.jedisPool != null) {
            this.jedisPool.close();
        }
    }

    public void subscribe(String... channels) {
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.subscribe(this.pubSub, channels);
            }
        }).start();
    }

    public void publish(String channel, String message) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.publish(channel, message);
            System.out.println("Published message to channel " + channel);
        } catch (Exception ignored) {
        }
    }


}
