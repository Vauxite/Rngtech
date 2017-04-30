package com.vauxite.rngtech.machines.render;

import org.lwjgl.opengl.GL11;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.render.models.ModelWindTurbine;
import com.vauxite.rngtech.machines.tileentity.TileEntityWindTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class TESRWindTurbine extends TileEntitySpecialRenderer<TileEntityWindTurbine> {
	private ModelWindTurbine model = new ModelWindTurbine();
    
	@Override
	public void renderTileEntityAt(TileEntityWindTurbine tileentity, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
		
		bindTexture( new ModelResourceLocation(RngtechMachines.modId + ":" + "WindTurbine"));

		GlStateManager.rotate(180, 0F, 0F, 1F);

		double angle = tileentity.angle;
		
		angle = (tileentity.angle+((tileentity.getPos().getY()+4F)/tileentity.speed)*partialTicks)%360;
		

		model.render(0.0625F, angle);
		GlStateManager.popMatrix();
	}

}
