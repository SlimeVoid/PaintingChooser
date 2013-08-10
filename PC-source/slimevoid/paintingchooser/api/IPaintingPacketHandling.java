package slimevoid.paintingchooser.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoidlib.IPacketHandling;
import slimevoidlib.network.PacketUpdate;

public interface IPaintingPacketHandling extends IPacketHandling {
	@Override
	public void handlePacket(PacketUpdate packetUpdate,
			EntityPlayer entityplayer, World world);
}
