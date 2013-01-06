package slimevoid.paintingchooser.client.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import eurysmods.network.packets.core.PacketIds;
import eurysmods.network.packets.core.PacketTileEntity;
import eurysmods.network.packets.core.PacketUpdate;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import slimevoid.paintingchooser.EntityPaintings;
import slimevoid.paintingchooser.PCInit;
import slimevoid.paintingchooser.api.IPCCommonProxy;
import slimevoid.paintingchooser.api.IPaintingPacketHandling;
import slimevoid.paintingchooser.network.CommonPacketHandler;
import slimevoid.paintingchooser.network.packets.PCPacketIds;
import slimevoid.paintingchooser.network.packets.PacketPainting;
import slimevoid.paintingchooser.network.packets.PacketPaintingGui;
import slimevoid.paintingchooser.network.packets.PacketUpdatePainting;


public class ClientPacketHandler implements IPacketHandler {

	public static void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (((PacketPainting)packet).getSender() == PCPacketIds.CLIENT) {
			CommonPacketHandler.handleGuiPacket(packet, entityplayer, world);
		}
		if (packet instanceof PacketPaintingGui) {
			PacketPaintingGui guiPacket = (PacketPaintingGui)packet;
			int entityId = guiPacket.getEntityId();
			Entity entity = PCInit.getEntityByID(world, entityId);
			if (entity != null && entity instanceof EntityPaintings) {
	        	((IPCCommonProxy) PCInit.PChooser.getProxy()).displayEntityGui(world, entityplayer, entity, guiPacket.getArtList());
			}
		}
	}

	public static void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (((PacketPainting)packet).getSender() == PCPacketIds.CLIENT) {
			CommonPacketHandler.handlePacket(packet, entityplayer, world);
		}
		if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting)packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			EntityPaintings painting = null;
			if (paintingPacket.getCommand().equals("FIRSTUPDATE")) {
				painting = new EntityPaintings(world, entityplayer, packet.xPosition, packet.yPosition, packet.zPosition, direction, "");
			}
			if (paintingPacket.getCommand().equals("UPDATEPAINTING")) {
				String artTitle = paintingPacket.getArtTitle();
				painting = new EntityPaintings(world, entityplayer, packet.xPosition, packet.yPosition, packet.zPosition, direction, artTitle);	
			}
			if (painting != null) {
				((WorldClient)world).addEntityToWorld(entityId, painting);
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
