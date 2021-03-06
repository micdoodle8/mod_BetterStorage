package net.mcft.copy.betterstorage.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.block.ChestMaterial;
import net.mcft.copy.betterstorage.block.TileEntityReinforcedChest;
import net.mcft.copy.betterstorage.utils.DirectionUtils;
import net.mcft.copy.betterstorage.utils.RenderUtils;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class TileEntityReinforcedChestRenderer extends TileEntitySpecialRenderer {
	
	private ModelChest chestModel = new ModelChest();
	private ModelChest largeChestModel = new ModelLargeChest();

	private ItemStack lock = new ItemStack(BetterStorage.lock);
	
	public void renderTileEntityAt(TileEntityReinforcedChest chest, double x, double y, double z, float par8) {
		
		ChestMaterial material = ChestMaterial.get(chest.getBlockMetadata());
		boolean large = chest.isConnected();
		if (large && !chest.isMain()) return;
		
		ModelChest model = (large ? largeChestModel : chestModel);
		bindTextureByName(material.getTexture(large));
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		int rotation = DirectionUtils.getRotation(chest.orientation);
		if ((rotation == 180) && large)
			GL11.glTranslatef(1.0F, 0.0F, 0.0F);
		if ((rotation == 270) && large)
			GL11.glTranslatef(0.0F, 0.0F, -1.0F);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		float angle = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * par8;
		angle = 1.0F - angle;
		angle = 1.0F - angle * angle * angle;
		model.chestLid.rotateAngleX = -(float)(angle * Math.PI / 2.0);
		model.renderAll();
		
		// Render lock
		if (chest.getLock() != null) {
			GL11.glPushMatrix();
			GL11.glTranslatef((large ? 1.0F : 0.5F), 0.25F + 5 / 32.0F, 1 / 32.0F);
			GL11.glScalef(0.5F, 0.5F, 1.5F);
			RenderUtils.renderItemIn3d(chest.getLock());
			GL11.glPopMatrix();
		}
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float par8) {
		renderTileEntityAt((TileEntityReinforcedChest) entity, x, y, z, par8);
	}
	
}
