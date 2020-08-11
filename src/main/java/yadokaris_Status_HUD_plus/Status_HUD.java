package yadokaris_Status_HUD_plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("yadokaris_status_hud_plus")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Status_HUD {

	private static String propFilePath;
	static ForgeConfigSpec conf;
	static Properties prop = new Properties();
	static String playerName;
	static ClientPlayerEntity player;
	static float totalKillCount, totalDeathCount, totalRate, ratekill, killCountBow, deathCount, rate, killCountSword=0, attackingKillCount, defendingKillCount;
	static int xp, totalXp, rankPoint, nexusDamage, repairPoint;
	static int color, colorCash;
	static String currentJob = "Civilian", rank = "UnKnown", team = "UnKnown";
	private static Field overlayMessageField = null;
	private static Field fpsField = null;
	static final String version = "1.7";
	static final String osName = System.getProperty("os.name").toLowerCase();
	static float multiple = 1, serverMultiple = 1;
	static boolean doCheck = false;
	public static final Logger LOGGER = LogManager.getLogger("yadokaris_status_hud_plus");

	@SubscribeEvent
	public void preInit(final FMLCommonSetupEvent event) {

		LOGGER.info("yadokari's Status HUD Plus loading");

		String colors = SHPConfig.colors.get();

		try {
			color = Integer.decode(colors);
		}
		catch (NumberFormatException e) {
			color = 0;
		}
		if (color > 16777215) color = 16777215;
		else if (color < 0) color = 0;
		colorCash = color;

		propFilePath = FMLPaths.CONFIGDIR.get().resolve("Status_HUD_Plus.xml").toString();
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

		overlayMessageField = ObfuscationReflectionHelper.findField(IngameGui.class, "field_73838_g");
		fpsField = ObfuscationReflectionHelper.findField(Minecraft.class, "field_71470_ab");
		//overlayMessageField = ObfuscationReflectionHelper.findField(IngameGui.class, "overlayMessage");
		//fpsField = ObfuscationReflectionHelper.findField(Minecraft.class, "debugFPS");
	}

	public Status_HUD() {
		System.setProperty("java.awt.headless", "false");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		Config.loadConfig(FMLPaths.CONFIGDIR.get().resolve("yadokaris_shp.toml").toString());

		ClientRegistry.registerKeyBinding(DevicePressEvent.resetKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.displayKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.settingKey);
		MinecraftForge.EVENT_BUS.register(new DevicePressEvent());
		MinecraftForge.EVENT_BUS.register(new Rendering());
		MinecraftForge.EVENT_BUS.register(new ChatEvent());
		MinecraftForge.EVENT_BUS.register(this);

		Timer timer = new Timer();
		TimerTask checkTask = new TimerTask() {

			@Override
			public void run() {
				if (doCheck && SHPConfig.doShow[Status.RankPoint.ordinal()].get()) Status_HUD.player.sendChatMessage("/multiplier");
			}
		};

		timer.scheduleAtFixedRate(checkTask, 1000, 5 * 1000 * 60);

		//for (Field f: Minecraft.class.getDeclaredFields()) System.out.println(f.getName());
		//for (Field f: IngameGui.class.getDeclaredFields()) System.out.println(f.getName());

		System.out.println(Minecraft.getInstance().getVersion());
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
			return overlayMessageField.get(Minecraft.getInstance().ingameGUI).toString();
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Get Message Overlay faild");
		}
	}

	public static String getFPS() {
		try {
			return fpsField.get(Minecraft.getInstance()).toString();
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Get FPS faild");
		}
	}
}
