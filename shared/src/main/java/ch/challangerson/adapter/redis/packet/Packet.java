package ch.challangerson.adapter.redis.packet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS
)
public interface Packet {
    String getType();
}
