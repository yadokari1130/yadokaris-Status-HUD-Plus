package yadokaris_Status_HUD_plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "yadokaris_status_hud_plus", name = "yadokari's Status HUD Plus", version = "1.4.1")
public class Status_HUD {

	private static String propFilePath;
	static Configuration conf;
	static Properties prop = new Properties();
	static String playerName;
	static EntityPlayer player;
	static float totalKillCount, totalDeathCount, totalRate, ratekill, CountSword, killCountBow, deathCount, rate, killCountSword;
	static int xp, totalXp, rankPoint, nexusDamage;
	static int color, colorCash, x, y;
	static boolean[] isShow = new boolean[14];
	static boolean isRender, isRainbow, isChangeTeamColor;
	static String currentJob = "Civilian", rank = "UnKnown", team = "UnKnown", text = "";
	

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		conf = new Configuration(event.getSuggestedConfigurationFile());
		conf.load();
		String colors = conf.getString("color", "render", "0x000000", "文字の色を設定します。16進法で指定してください。");

		try {
			color = Integer.decode(colors);
		}
		catch (NumberFormatException e) {
			color = 0;
		}
		if (color > 16777215) color = 16777215;
		else if (color < 0) color = 0;
		colorCash = color;

		isShow[0] = conf.getBoolean("isShowText", "render", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。");
		isShow[1] = conf.getBoolean("isShowSwordKill", "render", true, "剣キルの表示(true) / 非表示(false)を設定します。");
		isShow[2] = conf.getBoolean("isShowBowKill", "render", true, "弓キルの表示(true) / 非表示(false)を設定します。");
		isShow[3] = conf.getBoolean("isShowDeath", "render", true, "デス数の表示(true) / 非表示(false)を設定します。");
		isShow[4] = conf.getBoolean("isShowRate", "render", true, "K/Dレートの表示(true) / 非表示(false)を設定します。");
		isShow[5] = conf.getBoolean("isShowTotalRate", "render", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。");
		isShow[6] = conf.getBoolean("isShowNexusDamage", "render", true, "ネクサスダメージの表示(true) / 非表示(false)を設定します。");
		isShow[7] = conf.getBoolean("isShowXP", "render", true, "獲得xpの表示(true) / 非表示(false)を設定します。");
		isShow[8] = conf.getBoolean("isShowTotalXP", "render", true, "所持xpの表示(true) / 非表示(false)を設定します。");
		isShow[9] = conf.getBoolean("isShowRank", "render", true, "ランクの表示(true) / 非表示(false)を設定します。");
		isShow[10] = conf.getBoolean("isShowRankPoint", "render", true, "ランクポイントの表示(true) / 非表示(false)を設定します。");
		isShow[11] = conf.getBoolean("isShowJob", "render", true, "現在の職業の表示(true) / 非表示(false)を設定します。");
		isShow[12] = conf.getBoolean("isShowFPS", "render", true, "FPSの表示(true) / 非表示(false)を設定します。");
		isShow[13] = conf.getBoolean("isShowTeam", "render", true, "所属チームの表示(true) / 非表示(false)を設定します。");
		text = conf.getString("text", "render", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。");
		x = conf.getInt("x", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のx座標を設定します。");
		y = conf.getInt("y", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のy座標を設定します。");
		isRender = conf.getBoolean("isRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。");
		isRainbow = conf.getBoolean("isRainbow", "render", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。");
		isChangeTeamColor = conf.getBoolean("isChangeTeamColor", "render", false, "テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。");
		conf.save();
		
		propFilePath = conf.getConfigFile().getParent() + "\\Status_HUD_Plus.xml";
		File propFile = new File(propFilePath);
		
		if (!propFile.exists()) {
			prop.setProperty("killCount", "0");
			prop.setProperty("deathCount", "0");
			writeProperty();
		}
		else {
			try {
				prop.loadFromXML(new FileInputStream(propFile));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		totalKillCount = Float.valueOf(prop.getProperty("killCount", "0"));
		totalDeathCount = Float.valueOf(prop.getProperty("deathCount", "0"));
		totalRate = totalKillCount / (totalDeathCount + 1f);
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
	
	public static void writeProperty() {
		OutputStream writer = null;
		try {
			writer = new FileOutputStream(propFilePath);
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
