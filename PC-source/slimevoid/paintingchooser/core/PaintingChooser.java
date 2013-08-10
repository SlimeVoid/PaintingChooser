package slimevoid.paintingchooser.core;

import slimevoidlib.ICommonProxy;
import slimevoid.paintingchooser.client.network.ClientPacketHandler;
import slimevoid.paintingchooser.core.lib.CoreLib;
import slimevoid.paintingchooser.network.CommonPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(
		modid = CoreLib.MOD_ID,
		name = CoreLib.MOD_NAME,
		version = CoreLib.MOD_VERSION,
		dependencies = CoreLib.MOD_DEPENDENCIES
)
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		clientPacketHandlerSpec = 
			@SidedPacketHandler(
				channels = { CoreLib.MOD_CHANNEL },
				packetHandler = ClientPacketHandler.class),
		serverPacketHandlerSpec =
			@SidedPacketHandler(
				channels = { CoreLib.MOD_CHANNEL },
				packetHandler = CommonPacketHandler.class)
)
public class PaintingChooser {

	@SidedProxy(
			clientSide = CoreLib.CLIENT_PROXY,
			serverSide = CoreLib.COMMON_PROXY)
	public static ICommonProxy proxy;

	@Instance("PaintingChooser")
	public static PaintingChooser instance;

	@EventHandler
	public static void PaintingChooserInit(FMLInitializationEvent event) {
		PCCore.initialize();
	}
}
