package net.mcft.copy.betterstorage.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.mcft.copy.betterstorage.container.ContainerBetterStorage;
import net.mcft.copy.betterstorage.misc.Constants;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiBetterStorage extends GuiContainer {
	
	private final ContainerBetterStorage container;
	private final String title;
	
	protected final int columns;
	protected final int rows;
	
	public GuiBetterStorage(ContainerBetterStorage container) {
		super(container);
		
		this.container = container;
		IInventory inv = container.inventory;
		title = (inv.isInvNameLocalized() ? inv.getInvName() : StatCollector.translateToLocal(inv.getInvName()));
		columns = container.columns;
		rows = container.rows;
		
		xSize = 14 + columns * 18;
		ySize = 114 + rows * 18;
		
		container.setUpdateGui(this);
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, IInventory inventory) {
		this(new ContainerBetterStorage(player, inventory, columns, rows));
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, String name, boolean localized) {
		this(player, columns, rows, new InventoryBasic(name, localized, columns * rows));
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, String name) {
		this(player, columns, rows, name, false);
	}
	
	protected String getTexture() {
		if (columns <= 9) return "/gui/container.png";
		else return Constants.reinforcedChestContainer;
	}
	
	public void update(int par1, int par2) {  }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(title, 8, 6, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8 + (xSize - 176) / 2, ySize - 94, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(getTexture());
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize - 107);
		drawTexturedModalRect(x, y + ySize - 107, 0, 115, xSize, 107);
	}
	
}
