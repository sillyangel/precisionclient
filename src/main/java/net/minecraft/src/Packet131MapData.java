package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet131MapData extends Packet {
    public short itemID;

    /**
     * Contains a unique ID for the item that this packet will be populating.
     */
    public short uniqueID;

    /**
     * Contains a buffer of arbitrary data with which to populate an individual item
     * in the world.
     */
    public byte[] itemData;

    public Packet131MapData() {
        this.isChunkDataPacket = true;
    }

    public Packet131MapData(short par1, short par2, byte[] par3ArrayOfByte) {
        this.isChunkDataPacket = true;
        this.itemID = par1;
        this.uniqueID = par2;
        this.itemData = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
        this.itemID = par1DataInputStream.readShort();
        this.uniqueID = par1DataInputStream.readShort();
        this.itemData = new byte[par1DataInputStream.readUnsignedShort()];
        par1DataInputStream.readFully(this.itemData);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.itemID);
        par1DataOutputStream.writeShort(this.uniqueID);
        par1DataOutputStream.writeShort(this.itemData.length);
        par1DataOutputStream.write(this.itemData);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler) {
        par1NetHandler.handleMapData(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize() {
        return 4 + this.itemData.length;
    }
}
