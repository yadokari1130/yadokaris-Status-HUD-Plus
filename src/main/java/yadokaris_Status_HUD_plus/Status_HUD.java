package yadokaris_Status_HUD_plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod (modid = "yadokaris_status_hud_plus", name = "yadokari's Status HUD Plus", version = "1.2")
public class Status_HUD {

	private static File confFile;
	static float totalKillCount, totalDeathCount;
	static String cfgFile;
	static Properties prop = new Properties();
	static int color, x, y;
	static boolean[] isShow = new boolean[13];
	static String text = "";
	static Configuration cfg;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		cfg = new Configuration(event.getSuggestedConfigurationFile());
		cfg.load();
		cfgFile = cfg.getConfigFile().getParent() + "\\Status_HUD_Plus.xml";
		String colors = cfg.getString("color", "render", "0xFF0000", "文字の色を設定します。16進法で指定してください。");

		try {
			color = Integer.decode(colors);
		}
		catch (NumberFormatException e) {
			color = 0;
		}
		if (color > 16777215) color = 16777215;
		else if (color < 0) color = 0;

		ColorSetting.colorcash = color;

		isShow[0] = cfg.getBoolean("isShowText", "render", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。");
		isShow[1] = cfg.getBoolean("isShowSwordKill", "render", true, "剣キルの表示(true) / 非表示(false)を設定します。");
		isShow[2] = cfg.getBoolean("isShowBowKill", "render", true, "弓キルの表示(true) / 非表示(false)を設定します。");
		isShow[3] = cfg.getBoolean("isShowDeath", "render", true, "デス数の表示(true) / 非表示(false)を設定します。");
		isShow[4] = cfg.getBoolean("isShowRate", "render", true, "K/Dレートの表示(true) / 非表示(false)を設定します。");
		isShow[5] = cfg.getBoolean("isShowTotalRate", "render", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。");
		isShow[6] = cfg.getBoolean("isShowNexusDamage", "render", true, "ネクサスダメージの表示(true) / 非表示(false)を設定します。");
		isShow[7] = cfg.getBoolean("isShowXP", "render", true, "獲得xpの表示(true) / 非表示(false)を設定します。");
		isShow[8] = cfg.getBoolean("isShowTotalXP", "render", true, "所持xpの表示(true) / 非表示(false)を設定します。");
		isShow[9] = cfg.getBoolean("isShowRank", "render", true, "ランクの表示(true) / 非表示(false)を設定します。");
		isShow[10] = cfg.getBoolean("isShowRankPoint", "render", true, "ランクポイントの表示(true) / 非表示(false)を設定します。");
		isShow[11] = cfg.getBoolean("isShowJob", "render", true, "現在の職業の表示(true) / 非表示(false)を設定します。");
		isShow[12] = cfg.getBoolean("isShowFPS", "render", true, "FPSの表示(true) / 非表示(false)を設定します。");
		text = cfg.getString("text", "render", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。");
		x = cfg.getInt("x", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のx座標を設定します。");
		y = cfg.getInt("y", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のy座標を設定します。");
		Rendering.isRender = cfg.getBoolean("isRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。");
		Rendering.isRainbow = cfg.getBoolean("isRainbow", "render", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。");
		cfg.save();

		Rendering.totalRate = totalKillCount / (totalDeathCount + 1f);

		File conf = new File(cfgFile);
		if (!conf.exists()) {
			prop.setProperty("killCount", "0");
			prop.setProperty("deathCount", "0");

			OutputStream writer = null;
			try {
				writer = new FileOutputStream(cfgFile);
				prop.storeToXML(writer, "Comment");
				writer.flush();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				if (writer != null) {
					try {
						writer.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

        else {
        	InputStream reader = null;
			try {
				reader = new FileInputStream(cfgFile);
				prop.loadFromXML(reader);
			}
			catch (IOException e) {
				e.printStackTrace();
				return;
			}
			finally {
				if (reader != null) {
					try {
						reader.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			for (Object keyobj : prop.keySet()) {
				String key = keyobj.toString();
				String value = prop.getProperty(key).toString();
				prop.setProperty(key, value);
			}
        }

		totalKillCount = Float.valueOf(prop.getProperty("killCount", "0"));
		totalDeathCount = Float.valueOf(prop.getProperty("deathCount", "0"));

	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
    	ClientRegistry.registerKeyBinding(KeyPressEvent.resetKey);
    	ClientRegistry.registerKeyBinding(KeyPressEvent.displayKey);
    	ClientRegistry.registerKeyBinding(KeyPressEvent.settingKey);
		FMLCommonHandler.instance().bus().register(new KeyPressEvent());
		MinecraftForge.EVENT_BUS.register(new Rendering());
		MinecraftForge.EVENT_BUS.register(new ChatEvent());
	}
}
