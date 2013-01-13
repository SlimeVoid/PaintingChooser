package slimevoid.paintingchooser.proxy;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.lib.IPacketHandling;
import slimevoid.paintingchooser.api.IPCCommonProxy;
import slimevoid.paintingchooser.network.packets.PCPacketIds;
import slimevoid.paintingchooser.network.packets.PacketPaintingGui;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PC_CommonProxy implements IPCCommonProxy {

	@Override
	public void displayEntityGui(World world, EntityPlayer entityplayer,
			Entity entity, ArrayList list) {
		PacketPaintingGui packet = new PacketPaintingGui(
				(EntityPainting) entity, list);
		packet.setSender(PCPacketIds.SERVER);
		PacketDispatcher.sendPacketToPlayer(packet.getPacket(),
				(Player) entityplayer);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerTickHandler() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRenderInformation() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMinecraftDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockTextureFromMetadata(int meta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
	}

	@Override
	public IPacketHandling getPacketHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTileEntitySpecialRenderer(
			Class<? extends TileEntity> clazz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayTileEntityGui(EntityPlayer entityplayer,
			TileEntity tileentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld(NetHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager,
			Packet1Login login) {
		// TODO Auto-generated method stub

	}

}
