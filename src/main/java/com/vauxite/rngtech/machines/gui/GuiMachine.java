package com.vauxite.rngtech.machines.gui;

import com.vauxite.rngtech.machines.tileentity.TileEntityMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class GuiMachine extends GuiContainer {
	private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation("");
	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("");
	private IItemHandler playerInv;
	private TileEntityMachine machine;	
	public GuiStage stage;
	
	public GuiMachine(IItemHandler playerInv,TileEntityMachine machine) {
		super(new ContainerMachine(playerInv,machine));
		
		this.playerInv = playerInv;
		this.machine = machine;
		
		this.xSize = 256;
		this.ySize = 256;
		// TODO Auto-generated constructor stub
	}

	public void initGui(){
		super.initGui();		
	}
	public void updateScreen(){
		super.updateScreen();
	}
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		switch(this.stage){
			case processTab:
				break;
			case statTab:
				break;
			case upgradeTab:
				break;
			case configTab:
				break;
			case miscTab:
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int xOffset = 0;
		int yOffset = 0;
		
		drawBackground();
		
		switch(this.stage){
			case processTab:
				this.drawInput(xOffset, yOffset);
				this.drawCore(xOffset, yOffset);
				this.drawOutput(xOffset, yOffset);
				break;
			case statTab:
				break;
			case upgradeTab:
				break;
			case configTab:
				break;
			case miscTab:
				break;
			default:
				break;
		
		}

	}
	private void drawBackground(){
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	private void drawInput(int xOffset,int yOffset){
		for(int slot : machine.getInputSlots()){
			int index = slot - machine.getInStart();
        	createInputSlot(xOffset,yOffset,index,slot);
		}
			
	}
	private void drawStats(int xOffset,int yOffset){
		
	}
	private void drawCore(int xOffset,int yOffset){
		this.drawTexturedModalRect(xOffset+118, yOffset+98, 0,0,20,20);
	}

	
	private void drawOutput(int xOffset,int yOffset){
		
		
	}
	private void drawUpgrade(int xOffset,int yOffset){
		
		
	}
	
	
	private void createInputSlot(int i,int j,int index,int slot){
		this.drawModalRectWithCustomSizedTexture(i+60+(index)*30-2, j+65-2,0,0,20,20,20,20);
		
    	int l = this.machine.getProgressScaled(20, slot);
        this.drawTexturedModalRect(i+60+(index)*30, j+90, 176, 14, l + 1, 20);
        this.buttonList.add(new GuiGrinder.Button(slot, i+57+(index)*30, this.guiTop+52, new ResourceLocation("rngtechmachines:textures/gui/buttons/subtract.png"), 0, 0));
		this.buttonList.add(new GuiGrinder.Button(slot*-1, i+67+(index)*30, this.guiTop+52, new ResourceLocation("rngtechmachines:textures/gui/buttons/add.png"), 0, 0));
	}
	
    @SideOnly(Side.CLIENT)
    static class Button extends GuiButton{
        private final ResourceLocation iconTexture;
        private final int iconX;
        private final int iconY;
        private boolean selected;
        
        protected Button(int buttonId, int x, int y, ResourceLocation iconTextureIn, int iconXIn, int iconYIn){
            super(buttonId, x, y, 8, 8, "");
            this.iconTexture = iconTextureIn;
            this.iconX = iconXIn;
            this.iconY = iconYIn;
        }
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if (this.visible)
            {
        	    mc.getTextureManager().bindTexture(new ResourceLocation("rngtechmachines:textures/gui/machines/itemslot.png"));
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                int i = 219;
                int j = 0;

                if (!this.enabled)
                {
                    j += this.width * 2;
                }
                else if (this.selected)
                {
                    j += this.width * 1;
                }
                else if (this.hovered)
                {
                    j += this.width * 3;
                }

                //this.drawTexturedModalRect(this.xPosition, this.yPosition, j, 219, this.width, this.height);

                if (!GuiMachine.DEFAULT_TEXTURE.equals(this.iconTexture))
                	
                {
                    mc.getTextureManager().bindTexture(this.iconTexture);
                }
                this.drawModalRectWithCustomSizedTexture(this.xPosition + 2, this.yPosition + 2, this.iconX, this.iconY, 8, 8, 8,8);
            }
        }

        public boolean isSelected()
        {
            return this.selected;
        }

        public void setSelected(boolean selectedIn)
        {
            this.selected = selectedIn;
        }
        
        
        
    	
    }
	
}
