package slimevoid.paintingchooser.client.proxy;

import java.util.ArrayList;

import cpw.mods.fml.client.registry.RenderingRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import slimevoid.paintingchooser.client.gui.GuiPainting;
import slimevoid.paintingchooser.client.render.RenderPaintings;
import slimevoid.paintingchooser.entity.EntityPaintings;
import slimevoid.paintingchooser.proxy.PC_CommonProxy;

public class PC_ClientProxy extends PC_CommonProxy {

	@Override
	public void displayEntityGui(World world, EntityPlayer entityplayer,
			Entity entity, ArrayList list) {
		if (entity != null) {
			if (!world.isRemote) {
				super.displayEntityGui(world, entityplayer, entity, list);
			}
			ModLoader.openGUI(entityplayer, new GuiPainting(
					(EntityPainting) entity, list));
		}
	}

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityPaintings.class,
				new RenderPaintings());
	}
}
