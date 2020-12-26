package yadokaris_Status_HUD_plus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("yadokaris_status_hud_plus")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Status_HUD {

	private static String propFilePath;
	static ForgeConfigSpec conf;
	static Properties prop = new Properties();
	static String playerName;
	static ClientPlayerEntity player;
	public static int color;
	private static Field overlayMessageField = null;
	private static Field fpsField = null;
	static final String version = "1.7.7";
	static final String osName = System.getProperty("os.name").toLowerCase();
	static float multiple = 1, serverMultiple = 1;
	static boolean doCheck = false;
	public static final Logger LOGGER = LogManager.getLogger("yadokaris_status_hud_plus");
	public static float totalKillCount, totalDeathCount;

	public Status_HUD() {

		LOGGER.info("yadokari's Status HUD Plus loading.");
		System.setProperty("java.awt.headless", "false");
		Config.loadConfig(FMLPaths.CONFIGDIR.get().resolve("yadokaris_shp.toml").toString());

		propFilePath = FMLPaths.CONFIGDIR.get().resolve("Status_HUD_Plus.xml").toString();
		EditGroupGUI.path = FMLPaths.CONFIGDIR.get().resolve("SHPGroups.xml").toString();
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
		float totalRate = totalKillCount / (totalDeathCount + 1f);
		Status.TotalRate.value = totalRate;
		Status.Text.text = SHPConfig.text.get();

		String colors = SHPConfig.colors.get();

		try {
			color = Integer.decode(colors);
		}
		catch (NumberFormatException e) {
			color = 0;
		}
		if (color > 16777215) color = 16777215;
		else if (color < 0) color = 0;

		Document doc = null;
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			try {
				doc = builder.parse(new File(EditGroupGUI.path));
			}
			catch (FileNotFoundException e) {
				doc = builder.newDocument();
				e.printStackTrace();
			}
			catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element root = doc.getDocumentElement();
		if (root != null) {
			NodeList rootList = root.getChildNodes();

			for (int i = 0; i < rootList.getLength(); i++) {
				NodeList childList = rootList.item(i).getChildNodes();
				String name = childList.item(0).getTextContent();
				float x = Float.parseFloat(childList.item(1).getTextContent());
				float y = Float.parseFloat(childList.item(2).getTextContent());
				boolean doShowName = Boolean.parseBoolean(childList.item(3).getTextContent());
				NodeList idList = childList.item(4).getChildNodes();
				List<String> ids = new ArrayList<>();
				for (int k = 0; k < idList.getLength(); k++) {
					ids.add(idList.item(k).getTextContent());
				}
				boolean isRainbow = Boolean.parseBoolean(childList.item(5) == null ? "false" : childList.item(5).getTextContent());
				boolean doChangeTeamColor = Boolean.parseBoolean(childList.item(6) == null ? "false" : childList.item(6).getTextContent());
				int color = Integer.parseInt(childList.item(7) == null ? "-1" : childList.item(7).getTextContent());
				boolean doRender = Boolean.parseBoolean(childList.item(8) == null ? "true" : childList.item(8).getTextContent());

				Rendering.groups.put(name, new StatusGroup(name, x, y, ids, doShowName, isRainbow, doChangeTeamColor, color, doRender));
			}
		}

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
				if (doCheck) Status_HUD.player.sendChatMessage("/multiplier");
			}
		};

		timer.scheduleAtFixedRate(checkTask, 1000, 5 * 1000 * 60);
		timer.scheduleAtFixedRate(new ItemChangeEvent(), 1000, 10);

		//for (Field f: Minecraft.class.getDeclaredFields()) System.out.println(f.getName());
		//for (Field f: IngameGui.class.getDeclaredFields()) System.out.println(f.getName());

		overlayMessageField = ObfuscationReflectionHelper.findField(IngameGui.class, "field_73838_g");
		fpsField = ObfuscationReflectionHelper.findField(Minecraft.class, "field_71470_ab");
		//overlayMessageField = ObfuscationReflectionHelper.findField(IngameGui.class, "overlayMessage");
		//fpsField = ObfuscationReflectionHelper.findField(Minecraft.class, "debugFPS");

		Status.Enchant.value = "";
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
