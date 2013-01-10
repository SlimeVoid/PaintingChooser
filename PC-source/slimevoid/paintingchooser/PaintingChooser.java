package slimevoid.paintingchooser;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import slimevoid.lib.ICommonProxy;
import slimevoid.paintingchooser.client.network.ClientPacketHandler;
import slimevoid.paintingchooser.network.CommonPacketHandler;

@Mod(
		modid = "PaintingChooser",
		name = "Painting Chooser",
		version = PCCore.version,
		dependencies = "after:SlimevoidLib")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "PChooser" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "PChooser" }, packetHandler = CommonPacketHandler.class))
public class PaintingChooser {

	@SidedProxy(clientSide = "slimevoid.paintingchooser.client.proxy.PC_ClientProxy", serverSide = "slimevoid.paintingchooser.proxy.PC_CommonProxy")
	public static ICommonProxy proxy;

	@Instance("PaintingChooser")
	public static PaintingChooser instance;

	@Init
	public static void PaintingChooserInit(FMLInitializationEvent event) {
		PCCore.initialize(proxy);
	}
}
