package ch.challangerson.adapter.redis.packet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PacketSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(MapperFeature.USE_STD_BEAN_NAMING);
        MAPPER.disable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    public static Packet deserialize(String bytes) {
        try {
            return MAPPER.readValue(bytes, Packet.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String serialize(Object packet) {
        try {
            return MAPPER.writeValueAsString(packet);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }
}

