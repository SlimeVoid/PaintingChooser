package slimevoid.paintingchooser.client.proxy;

import java.util.ArrayList;

import eurysmods.api.IPacketHandling;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import slimevoid.paintingchooser.EntityPaintings;
import slimevoid.paintingchooser.client.GuiPainting;
import slimevoid.paintingchooser.client.network.ClientPacketHandler;
import slimevoid.paintingchooser.proxy.PC_CommonProxy;

public class PC_ClientProxy extends PC_CommonProxy {
	
	@Override
	public void displayEntityGui(World world, EntityPlayer entityplayer, Entity entity, ArrayList list) {
		if (entity instanceof EntityPaintings) {
			if (world.isRemote) {
				System.out.println("Remote");
				super.displayEntityGui(world, entityplayer, entity, list);
			}
			ModLoader.openGUI(entityplayer, new GuiPainting((EntityPaintings)entity, list));
		}
	}

}
