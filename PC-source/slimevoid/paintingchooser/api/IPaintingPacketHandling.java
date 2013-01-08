package slimevoid.paintingchooser.api;

import slimevoid.lib.IPacketHandling;
import slimevoid.lib.network.PacketUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IPaintingPacketHandling extends IPacketHandling {
	public void handlePacket(PacketUpdate packetUpdate,
			EntityPlayer entityplayer, World world);
}
