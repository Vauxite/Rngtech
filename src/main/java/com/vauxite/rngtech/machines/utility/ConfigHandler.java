package com.vauxite.rngtech.machines.utility;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

    public static Configuration config;
    public static boolean test_setting;
	
	
    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        String category;

        category = "Crafting";
        config.addCustomCategoryComment(category, "Crafting settings");
        test_setting = config.getBoolean("test_name", category, true, "TEEEEEEEEEEEEEEEEEEEEST.");
    }
	
}
