package com.vauxite.rngtech.machines.gui;

import java.util.Iterator;

import org.fusesource.jansi.Ansi.Color;

import com.vauxite.rngtech.machines.tileentity.TileEntityFrame;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFrame extends GuiContainer {

	private IInventory playerInv;
	private TileEntityFrame te;
	public GuiFrame(IInventory playerInv, TileEntityFrame te) {
        super(new ContainerFrame(playerInv, te));
        this.playerInv = playerInv;
        this.te = te;
        this.xSize = 256;
        this.ySize = 256;
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
	    String s = "Grinder";
	    this.fontRendererObj.drawString(s, 28 - this.fontRendererObj.getStringWidth(s) / 2, 12, 4210752);
	    
	    
	    String OutputSlots	= "Output="		+ this.te.getOutputSlots().toString();
	    String InputSlots	= "Input="		+ this.te.getInputSlots().toString();
	    String UpgradeSlots = "Upgrade="	+ this.te.getUpgradeSlots().toString();
	    	    
	    
	    int xOffset = 100;
	    int yOffset = 130;
	    this.fontRendererObj.drawString(OutputSlots, xOffset,yOffset+40, 16777215);
	    this.fontRendererObj.drawString(InputSlots, xOffset, yOffset+60, 16777215);
	    this.fontRendererObj.drawString(UpgradeSlots, xOffset, yOffset+80, 16777215);
	    
	    
	    //this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("rngtechmachines:textures/gui/machines/GGui.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
	    /* slot config **/
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("rngtechmachines:textures/gui/machines/itemslot.png"));
	    
        /* Input slots*/
        Iterator InputSlots = te.getInputSlots().iterator();
        while (InputSlots.hasNext()){
        	int slot = (Integer) InputSlots.next(); 
        	int index = slot - te.getInStart();
        	this.drawTexturedModalRect(60+i+(index)*30-2, j+65, 0, 0, 20,20);
        }   

        
        /* Core Modifier */
        this.drawTexturedModalRect(i+120-2, j+100-2, 0, 0, 20,20);
        
	    /* Output Slots */
        Iterator Outputslots = te.getOutputSlots().iterator();
        while (Outputslots.hasNext()){
        	int slot = (Integer) Outputslots.next(); 
        	int index = slot - te.getOutStart();

        	this.drawTexturedModalRect(60+i+(index)*30-2, j+140+2, 0, 0, 20,20);
        }      	    
	    

        
        int l = this.getProgressScaled(24);

        this.drawTexturedModalRect(i+ 145, j+100, 176, 14, l + 1, 20);
	    
	}
	
    private int getProgressScaled(int pixels)
    {
        int curProcessTime = this.te.getField(0);
        int ProcessTime = this.te.getField(1);

    	//System.out.println("I= =" +i); 
        //System.out.println("I="+i*pixels); 
        //System.out.println("TotalProcessTime= "+ ProcessTime);
        return ProcessTime != 0 && curProcessTime != 0 ? curProcessTime * pixels / ProcessTime : 0;
    }

	

}
