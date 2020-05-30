package yadokaris_Status_HUD_plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = "yadokaris_status_hud_plus", name = "yadokari's Status HUD Plus", version = "1.6.7", updateJSON = "https://raw.githubusercontent.com/yadokari1130/yadokaris-Status-HUD-Plus/master/update.json")
public class Status_HUD {

	private static String propFilePath;
	static Configuration conf;
	static Properties prop = new Properties();
	static String playerName;
	static EntityPlayer player;
	static float totalKillCount, totalDeathCount, totalRate, ratekill, CountSword, killCountBow, deathCount, rate, killCountSword, attackingKillCount, defendingKillCount;
	static int xp, totalXp, rankPoint, nexusDamage, repairPoint;
	static int color, colorCash, x, y;
	static boolean[] doShow = new boolean[18];
	static boolean doRender, isRainbow, doChangeTeamColor;
	static String currentJob = "Civilian", rank = "UnKnown", team = "UnKnown", text = "";
	static double fontSize;
	private static Field overlayMessageField = null;
	static final String version = "1.6.7";


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		conf = new Configuration(event.getSuggestedConfigurationFile());
		conf.load();
		String colors = conf.getString("color", "render", "0xFFFFFF", "文字の色を設定します。16進法で指定してください。");

		try {
			color = Integer.decode(colors);
		}
		catch (NumberFormatException e) {
			color = 0;
		}
		if (color > 16777215) color = 16777215;
		else if (color < 0) color = 0;
		colorCash = color;

		doShow[Status.Text.ordinal()] = conf.getBoolean("doShowText", "render", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。");
		doShow[Status.KillCountSword.ordinal()] = conf.getBoolean("doShowSwordKill", "render", true, "剣キルの表示(true) / 非表示(false)を設定します。");
		doShow[Status.KillCountBow.ordinal()] = conf.getBoolean("doShowBowKill", "render", true, "弓キルの表示(true) / 非表示(false)を設定します。");
		doShow[Status.AttackingKillCount.ordinal()] = conf.getBoolean("doShowAttackingKill", "render", true, "ネクサスキルの表示(true) / 非表示(false)を設定します。");
		doShow[Status.DefendingKillCount.ordinal()] = conf.getBoolean("doShowDefendingKill", "render", true, "防衛キルの表示(true) / 非表示(false)を設定します。");
		doShow[Status.DeathCount.ordinal()] = conf.getBoolean("doShowDeath", "render", true, "デス数の表示(true) / 非表示(false)を設定します。");
		doShow[Status.Rate.ordinal()] = conf.getBoolean("doShowRate", "render", true, "K/Dレートの表示(true) / 非表示(false)を設定します。");
		doShow[Status.TotalRate.ordinal()] = conf.getBoolean("doShowTotalRate", "render", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。");
		doShow[Status.NexusDamage.ordinal()] = conf.getBoolean("doShowNexusDamage", "render", true, "ネクサスダメージの表示(true) / 非表示(false)を設定します。");
		doShow[Status.RepairPoint.ordinal()] = conf.getBoolean("doShowRepairPoint", "render", true, "回復したネクサスポイントの表示(true) / 非表示(false)を表示します。");
		doShow[Status.XP.ordinal()] = conf.getBoolean("doShowXP", "render", true, "獲得xpの表示(true) / 非表示(false)を設定します。");
		doShow[Status.TotalXP.ordinal()] = conf.getBoolean("doShowTotalXP", "render", true, "所持xpの表示(true) / 非表示(false)を設定します。");
		doShow[Status.Rank.ordinal()] = conf.getBoolean("doShowRank", "render", true, "ランクの表示(true) / 非表示(false)を設定します。");
		doShow[Status.RankPoint.ordinal()] = conf.getBoolean("doShowRankPoint", "render", true, "ランクポイントの表示(true) / 非表示(false)を設定します。");
		doShow[Status.CurrentJob.ordinal()] = conf.getBoolean("doShowJob", "render", true, "現在の職業の表示(true) / 非表示(false)を設定します。");
		doShow[Status.FPS.ordinal()] = conf.getBoolean("doShowFPS", "render", true, "FPSの表示(true) / 非表示(false)を設定します。");
		doShow[Status.CPS.ordinal()] = conf.getBoolean("doShowCPS", "render", true, "CPSの表示(true) / 非表示(false)を設定します。");
		doShow[Status.Team.ordinal()] = conf.getBoolean("doShowTeam", "render", true, "所属チームの表示(true) / 非表示(false)を設定します。");
		text = conf.getString("text", "render", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。");
		x = conf.getInt("x", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のx座標を設定します。");
		y = conf.getInt("y", "render", 2, 0, Integer.MAX_VALUE, "ステータスの画面上のy座標を設定します。");
		fontSize = conf.getFloat("fontSize", "render", 1, 0, 100, "ステータスの文字サイズを設定します。");
		doRender = conf.getBoolean("doRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。");
		isRainbow = conf.getBoolean("isRainbow", "render", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。");
		doChangeTeamColor = conf.getBoolean("doChangeTeamColor", "render", false, "テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。");
		conf.save();

		propFilePath = conf.getConfigFile().getParent() + "\\Status_HUD_Plus.xml";
		File propFile = new File(propFilePath);

		if (!propFile.exists()) {
			prop.setProperty("killCount", "0");
			prop.setProperty("deathCount", "0");
			writeProperty();
		}
		else {
			try (InputStream reader = new FileInputStream(propFile)) {
				prop.loadFromXML(reader);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		totalKillCount = Float.valueOf(prop.getProperty("killCount", "0"));
		totalDeathCount = Float.valueOf(prop.getProperty("deathCount", "0"));
		totalRate = totalKillCount / (totalDeathCount + 1f);

		overlayMessageField = ReflectionHelper.findField(GuiIngame.class, "field_73838_g");
		//overlayMessageField = ReflectionHelper.findField(GuiIngame.class, "overlayMessage");
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(DevicePressEvent.resetKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.displayKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.settingKey);
		MinecraftForge.EVENT_BUS.register(new DevicePressEvent());
		MinecraftForge.EVENT_BUS.register(new Rendering());
		MinecraftForge.EVENT_BUS.register(new ChatEvent());
	}

	public static void writeProperty() {
		try (OutputStream writer = new FileOutputStream(propFilePath)) {
			prop.storeToXML(writer, "Comment");
			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static String getActionbar() {
		try {
			return overlayMessageField.get(Minecraft.getMinecraft().ingameGUI).toString();
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Get Message Overlay faild");
		}
	}
}
