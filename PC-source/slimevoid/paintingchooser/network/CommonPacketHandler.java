package slimevoid.paintingchooser.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;
import slimevoidlib.network.PacketIds;
import slimevoidlib.network.PacketUpdate;
import slimevoid.paintingchooser.core.PCCore;
import slimevoid.paintingchooser.core.lib.CommandLib;
import slimevoid.paintingchooser.network.packets.PCPacketIds;
import slimevoid.paintingchooser.network.packets.PacketPaintingGui;
import slimevoid.paintingchooser.network.packets.PacketUpdatePainting;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class CommonPacketHandler implements IPacketHandler {

	public static void handleGuiPacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet instanceof PacketPaintingGui) {
			PacketPaintingGui guiPacket = (PacketPaintingGui) packet;
			if (guiPacket.getKillCode() == 999) {
				Entity entity = world.getEntityByID(guiPacket.getEntityId());
				if (entity instanceof EntityPainting) {
					EntityPainting entitypainting = (EntityPainting) entity;
					entitypainting.setDead();
					if (!entityplayer.capabilities.isCreativeMode
							|| !world.getWorldInfo().getGameType().isCreative())
						entitypainting.worldObj
								.spawnEntityInWorld(new EntityItem(
										entitypainting.worldObj,
										entitypainting.posX,
										entitypainting.posY,
										entitypainting.posZ, new ItemStack(
												PCCore.itemPaintings)));
				}
			}
		}
	}

	public static void handlePacket(PacketUpdate packet,
			EntityPlayer entityplayer, World world) {
		if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting) packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			Entity entity = world.getEntityByID(entityId);
			if (entity != null && entity instanceof EntityPainting) {
				EntityPainting entitypainting = (EntityPainting) entity;
				EnumArt[] enumart = EnumArt.values();
				EnumArt art = enumart[paintingPacket.getArtConstant()];
				if (art != null) {
					entitypainting.art = art;
					entitypainting.setDirection(direction);
					notifyPlayers(entitypainting);
				}
			}
		}
	}

	public static void notifyPlayers(EntityPainting entitypainting) {
		if (entitypainting != null && entitypainting.art != null) {
			PacketUpdatePainting paintingPacket = new PacketUpdatePainting(
					entitypainting, CommandLib.paintingUpdateCommand);
			paintingPacket.setArtConstant(entitypainting.art.ordinal());
			paintingPacket.setSender(PCPacketIds.SERVER);
			PacketDispatcher.sendPacketToAllAround(entitypainting.xPosition,
					entitypainting.yPosition, entitypainting.zPosition, 16,
					entitypainting.worldObj.provider.dimensionId,
					paintingPacket.getPacket());
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
