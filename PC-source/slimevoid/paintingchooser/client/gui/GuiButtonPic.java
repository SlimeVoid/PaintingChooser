package slimevoid.paintingchooser.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import slimevoid.paintingchooser.core.lib.GuiLib;

public class GuiButtonPic extends GuiButton {
	protected int p1;
	protected int p2;

	public GuiButtonPic(int id, int xPosition, int yPosition, int p1, int p2,
			int width, int height) {
		super(id, xPosition, yPosition, width, height, "");
		this.width = width;
		this.height = height;
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(Minecraft var1, int var2, int var3) {
		if (this.drawButton) {
			FontRenderer var4 = var1.fontRenderer;
			var1.renderEngine.func_110577_a(GuiLib.GUI_PAINTINGS);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean var5 = var2 >= this.xPosition && var3 >= this.yPosition
					&& var2 < this.xPosition + this.width
					&& var3 < this.yPosition + this.height;
			this.getHoverState(var5);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, this.p1,
					this.p2, this.width, this.height);
			this.mouseDragged(var1, var2, var3);
		}
	}
}
