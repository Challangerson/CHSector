package ch.challangerson.adapter.redis.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PacketHandler {

    private final HashMap<String, BiFunction<Packet, String, Void>> handlers = new HashMap<>();

    public void registerPacketHandler(String packetType, BiFunction<Packet, String, Void> handler) {
        handlers.put(packetType, handler);
    }

    public void handlePacket(Packet packet, String arg) {
        BiFunction<Packet, String, Void> handler = handlers.get(packet.getType());

        if (handler != null) {
            handler.apply(packet, arg);
        } else {
            System.err.println("No handler registered for packet type: " + packet.getType());
        }
    }
}
