package slimevoid.paintingchooser.client.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;

import slimevoid.lib.entity.EntityHelper;
import slimevoid.lib.network.PacketIds;
import slimevoid.lib.network.PacketUpdate;
import slimevoid.paintingchooser.api.IPCCommonProxy;
import slimevoid.paintingchooser.core.PCInit;
import slimevoid.paintingchooser.network.CommonPacketHandler;
import slimevoid.paintingchooser.network.packets.PCPacketIds;
import slimevoid.paintingchooser.network.packets.PacketPaintingGui;
import slimevoid.paintingchooser.network.packets.PacketUpdatePainting;

public class ClientPacketHandler implements IPacketHandler {

	public static void handleGuiPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (((PacketPaintingGui) packet).getSender() == PCPacketIds.CLIENT) {
			CommonPacketHandler.handleGuiPacket(packet, entityplayer, world);
		} else if (packet instanceof PacketPaintingGui) {
			PacketPaintingGui guiPacket = (PacketPaintingGui) packet;
			int entityId = guiPacket.getEntityId();
			Entity entity = world.getEntityByID(entityId);
			if (entity != null) {
				((IPCCommonProxy) PCInit.PChooser.getProxy()).displayEntityGui(
						world, entityplayer, entity, guiPacket.getArtList());
			}
		}
	}

	public static void handlePacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (((PacketUpdatePainting) packet).getSender() == PCPacketIds.CLIENT) {
			CommonPacketHandler.handlePacket(packet, entityplayer, world);
		} else if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting) packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			Entity entity = world.getEntityByID(entityId);
			if (entity != null && entity instanceof EntityPainting) {
				EnumArt art = EnumArt.values()[paintingPacket.getArtConstant()];
				((EntityPainting) entity).art = art;
				((EntityPainting) entity).setDirection(direction);
			}
		}
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		EntityPlayer entityplayer = (EntityPlayer) player;
		World world = entityplayer.worldObj;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				packet.data));
		try {
			int packetID = data.read();
			switch (packetID) {
			case PacketIds.UPDATE:
				PacketUpdatePainting packetPainting = new PacketUpdatePainting();
				packetPainting.readData(data);
				handlePacket(packetPainting, entityplayer, world);
				break;
			case PacketIds.GUI:
				PacketPaintingGui packetPaintingGui = new PacketPaintingGui();
				packetPaintingGui.readData(data);
				handleGuiPacket(packetPaintingGui, entityplayer, world);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
