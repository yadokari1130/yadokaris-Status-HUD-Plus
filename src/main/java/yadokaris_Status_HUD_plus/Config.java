package yadokaris_Status_HUD_plus;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {

	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec CLIENT;

	static {
		SHPConfig.init(CLIENT_BUILDER);
		CLIENT = CLIENT_BUILDER.build();
	}

	public static void loadConfig(String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		file.load();
		CLIENT.setConfig(file);
	}

	public static void saveConfig(String path) {
		CLIENT.save();
	}
}
