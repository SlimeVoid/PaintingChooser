package slimevoid.paintingchooser.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;
import eurysmods.network.packets.core.PacketIds;
import eurysmods.network.packets.core.PacketTileEntity;
import eurysmods.network.packets.core.PacketUpdate;
import slimevoid.paintingchooser.EntityPaintings;
import slimevoid.paintingchooser.PCCore;
import slimevoid.paintingchooser.PCInit;
import slimevoid.paintingchooser.api.IPaintingPacketHandling;
import slimevoid.paintingchooser.network.packets.PacketPaintingGui;
import slimevoid.paintingchooser.network.packets.PacketUpdatePainting;

public class CommonPacketHandler implements IPacketHandler {
	
	public static void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketPaintingGui) {
			PacketPaintingGui guiPacket = (PacketPaintingGui)packet;
			if (guiPacket.getKillCode() == 999) {
				Entity entity = PCInit.getEntityByID(world, guiPacket.getEntityId());
				if (entity instanceof EntityPaintings) {
					EntityPaintings entitypainting = (EntityPaintings)entity;
					entitypainting.setDead();
					entitypainting.worldObj.spawnEntityInWorld(new EntityItem(entitypainting.worldObj, entitypainting.posX, entitypainting.posY, entitypainting.posZ, new ItemStack(PCCore.itemPaintings)));
				}
			}
		}
	}
	
	public static void handlePacket(PacketUpdate packet, EntityPlayer entityplayer,
			World world) {
		if (packet instanceof PacketUpdatePainting) {
			PacketUpdatePainting paintingPacket = (PacketUpdatePainting)packet;
			int entityId = paintingPacket.getEntityId();
			int direction = paintingPacket.getDirection();
			Entity entity = PCInit.getEntityByID(world, entityId);
			if (entity != null && entity instanceof EntityPaintings) {
				EntityPaintings entitypainting = (EntityPaintings)entity;
				EnumArt[] enumart = EnumArt.values();
				for (int i = 0; i < enumart.length; i++) {
					if (enumart[i].title.equals(paintingPacket.getArtTitle())) {
						entitypainting.art = enumart[i];
						entitypainting.setDirection(direction);
						notifyPlayers(entitypainting);
					}
				}
			}
		}
	}

	public static void notifyPlayers(EntityPaintings entitypainting) {
		if (entitypainting != null && entitypainting.art != null) {
			PacketUpdatePainting paintingPacket = new PacketUpdatePainting(entitypainting, "UPDATEPAINTING");
			paintingPacket.setArtTitle(entitypainting.art.title);
			PacketDispatcher.sendPacketToAllAround(entitypainting.xPosition, entitypainting.yPosition, entitypainting.zPosition, 16, entitypainting.worldObj.provider.dimensionId, paintingPacket.getPacket());
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
