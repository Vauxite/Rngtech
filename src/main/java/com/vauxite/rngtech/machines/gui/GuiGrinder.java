package com.vauxite.rngtech.machines.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.fusesource.jansi.Ansi.Color;

import com.vauxite.rngtech.machines.RngtechMachines;
import com.vauxite.rngtech.machines.net.PacketHandler;
import com.vauxite.rngtech.machines.net.PacketRequestMachine;
import com.vauxite.rngtech.machines.tileentity.TileEntityGrinder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.PacketLoggingHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer {

	
	private static final ResourceLocation GRINDER_GUI_TEXTURE = new ResourceLocation("rngtechmachines:textures/gui/machines/ggui.png");
	private IInventory playerInv;
	private TileEntityGrinder machineGrinder;
	
	public GuiGrinder(IInventory playerInv, TileEntityGrinder te) {
        super(new ContainerGrinder(playerInv, te));
        this.playerInv = playerInv;
        this.machineGrinder = te;
        this.xSize = 256;
        this.ySize = 256;
        
    }
	
	public void initGui(){
		super.initGui();
		
		
	}
	public void updateScreen(){
		super.updateScreen();
		
		
	}
	

	
	public void actionPerformed(GuiButton button) throws IOException{
		switch(button.id){
			case 0:
				break;
			case 1:
				increaseForce(1,1,false);
				PacketHandler.network.sendToServer(new PacketRequestMachine(this.machineGrinder.getPos(), 1, 2, (this.machineGrinder.getField(2, 1)-5),this.machineGrinder.getWorld().provider.getDimension()));
				break;
			case -1:
				increaseForce(1,1,true);
				PacketHandler.network.sendToServer(new PacketRequestMachine(this.machineGrinder.getPos(), 1, 2, (this.machineGrinder.getField(2, 1)+5),this.machineGrinder.getWorld().provider.getDimension()));
				break;
			case 2:
				increaseForce(2,1,false);
				PacketHandler.network.sendToServer(new PacketRequestMachine(this.machineGrinder.getPos(), 2, 2, (this.machineGrinder.getField(2, 2)-5),this.machineGrinder.getWorld().provider.getDimension()));
		
				break;
			case -2:
				increaseForce(2,1,true);
				PacketHandler.network.sendToServer(new PacketRequestMachine(this.machineGrinder.getPos(), 2, 2, (this.machineGrinder.getField(2, 2)+5),this.machineGrinder.getWorld().provider.getDimension()));
				break;
			default:
				return;
		}
	}
	private void increaseForce(int slot,int modifier,boolean positive){
		int currentForce = this.machineGrinder.getField(2, slot);
		int increment = 0;
		switch(modifier){
		case 1:
			increment = 5;
			break;
		case 2:
			increment = 10;
			break;
		default:
			increment = 1;
			break;
		}
			
		int newValue = currentForce+increment;
		if(!positive){
			newValue *=-1;
		}
		System.out.println("new value: "+ newValue+" for slot #"+slot +", old value="+currentForce);
		this.machineGrinder.setField(2,slot,newValue);
	}
	
	
	private void debugSlots(){
	    String OutputSlots	= "Output="		+ this.machineGrinder.getOutputSlots().toString();
	    String InputSlots	= "Input="		+ this.machineGrinder.getInputSlots().toString();
	    String UpgradeSlots = "Upgrade="	+ this.machineGrinder.getUpgradeSlots().toString();
    
	    
	    int xOffset = 100;
	    int yOffset = 130;
	    this.fontRendererObj.drawString(OutputSlots, xOffset,yOffset+40, 16777215);
	    this.fontRendererObj.drawString(InputSlots, xOffset, yOffset+60, 16777215);
	    this.fontRendererObj.drawString(UpgradeSlots, xOffset, yOffset+80, 16777215);		
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(new String(this.machineGrinder.getField(2,1)+"F"), 66,this.guiTop+53,16777215);
		this.fontRendererObj.drawString(new String(this.machineGrinder.getField(2,2)+"F"), 96,this.guiTop+53,16777215);
		

		
	}
		
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("rngtechmachines:textures/gui/machines/ggui.png"));
	    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;


	    
	    /* slot config **/
	    this.mc.getTextureManager().bindTexture(new ResourceLocation("rngtechmachines:textures/gui/machines/itemslot.png"));
	    
	    
        /* Input slots*/
        Iterator InputSlots = machineGrinder.getInputSlots().iterator();
        while (InputSlots.hasNext()){
        	int slot = (Integer) InputSlots.next(); 
        	int index = slot - machineGrinder.getInStart();
        	createInputSlot(i,j,index,slot);
        }   

        
        /* Core Modifier */
        this.drawTexturedModalRect(i+120-2, j+100-2, 0, 0, 20,20);
        
	    /* Output Slots */
        Iterator Outputslots = machineGrinder.getOutputSlots().iterator();
        while (Outputslots.hasNext()){
        	int slot = (Integer) Outputslots.next(); 
        	int index = slot - machineGrinder.getOutStart();
        	
        	this.drawModalRectWithCustomSizedTexture(60+i+(index)*30-2,j+140+3,0,0,20,20,20,20);
        	//this.drawTexturedModalRect(60+i+(index)*30-2, j+140+2, 0, 0, 20,20);
        }      	    
	    

        

	    
	}
	public void createInputSlot(int i,int j,int index,int slot){
		this.drawModalRectWithCustomSizedTexture(i+60+(index)*30-2, j+65-2,0,0,20,20,20,20);
		
    	int l = this.machineGrinder.getProgressScaled(20, slot);
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

                if (!GuiGrinder.GRINDER_GUI_TEXTURE.equals(this.iconTexture))
                	
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
	
	
    private int getProgressScaled(int pixels,int slot)
    {
        int curProcessTime = this.machineGrinder.getField(0, slot);
        int ProcessTime = this.machineGrinder.getField(1, slot);
        //System.out.println(curProcessTime + "/" + ProcessTime);
        return ProcessTime != 0 && curProcessTime != 0 ? curProcessTime * pixels / ProcessTime : 0;
    }

	

}
