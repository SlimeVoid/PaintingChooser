package slimevoid.paintingchooser.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import eurysmods.api.IPacketHandling;
import eurysmods.network.packets.core.PacketUpdate;

public interface IPaintingPacketHandling extends IPacketHandling {
	public void handlePacket(PacketUpdate packetUpdate,
			EntityPlayer entityplayer, World world);
}
