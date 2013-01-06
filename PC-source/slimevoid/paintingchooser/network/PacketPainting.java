package slimevoid.paintingchooser.network;

import slimevoid.paintingchooser.client.PaintingChooser;
import net.minecraft.src.EurysMods.network.PacketUpdate;

public class PacketPainting extends PacketUpdate {
	public PacketPainting(int packetId) {
		super(packetId);
		this.channel = PaintingChooser.PChooser.getModChannel();
	}
}
