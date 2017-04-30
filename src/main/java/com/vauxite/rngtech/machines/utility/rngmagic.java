package com.vauxite.rngtech.machines.utility;

	import java.util.Random;

public abstract class rngmagic {
	
	
	public static int createInputSlots(int tier){
		Random random = new Random();
		return random.nextInt(tier)+1;
	}
	public static int createOutputSlots(int tier){
		Random random = new Random();
		return random.nextInt(tier)+1;
	}
	
	public static double getEnergyCap(int tier){
		return 20000;
	}
	public static int createUpgradeSlots(int tier){
		Random random = new Random();
		return random.nextInt(tier)+1;
	}
	public static boolean chance_item(int chance){
		Random random = new Random();		
		return random.nextInt(100) < chance;
	}
	
	
}
