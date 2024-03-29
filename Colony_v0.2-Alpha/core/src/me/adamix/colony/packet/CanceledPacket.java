package me.adamix.colony.packet;

public interface CanceledPacket extends Packet {

    boolean isCanceled = false;
    boolean isIsCanceled();

}
