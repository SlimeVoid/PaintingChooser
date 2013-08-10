package slimevoid.paintingchooser.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.world.World;
import slimevoidlib.network.PacketUpdate;
import slimevoid.paintingchooser.core.lib.CoreLib;

public class PacketPainting extends PacketUpdate {

	private int sender;

	public PacketPainting(int packetId) {
		super(packetId);
		this.setChannel(CoreLib.MOD_CHANNEL);
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(sender);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.sender = data.readInt();
	}

	@Override
	public boolean targetExists(World world) {
		return false;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}
}
