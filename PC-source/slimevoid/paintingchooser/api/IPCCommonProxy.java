package slimevoid.paintingchooser.api;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import eurysmods.api.ICommonProxy;

public interface IPCCommonProxy extends ICommonProxy {

	public void displayEntityGui(World world, EntityPlayer entityplayer, Entity entity, ArrayList list);
}
