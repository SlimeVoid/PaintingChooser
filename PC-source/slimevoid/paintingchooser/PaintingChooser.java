package slimevoid.paintingchooser;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import eurysmods.api.ICommonProxy;

@Mod(
		modid="PaintingChooser",
		name="Painting Chooser",
		version=PCCore.version)

public class PaintingChooser {

	@SidedProxy(
			clientSide="slimevoid.paintingchooser.client.proxy.PC_ClientProxy",
			serverSide="slimevoid.paintingchooser.proxy.PC_CommonProxy")
	public static ICommonProxy proxy;
	
	@Init
	public static void PaintingChooserInit(FMLInitializationEvent event) {
		PCCore.initialize(proxy);
	}
}
