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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = "yadokaris_status_hud_plus", name = "yadokari's Status HUD Plus", version = Status_HUD.version, updateJSON = "https://raw.githubusercontent.com/yadokari1130/yadokaris-Status-HUD-Plus/master/update.json")
public class Status_HUD {

	private static String propFilePath;
	static Configuration conf;
	static Properties prop = new Properties();
	static String playerName;
	static EntityPlayer player;
	static int color;
	static boolean doRender, isRainbow, doChangeTeamColor, doRenderEnchantment, doRenderWhenStart;
	static double fontSize;
	private static Field overlayMessageField = null;
	private static Field visibleField = null;
	static final String version = "1.7.10";
	static final String osName = System.getProperty("os.name").toLowerCase();
	static float multiple = 1, serverMultiple = 1;
	static boolean doCheck = false;
	public static float totalKillCount, totalDeathCount;

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

		Status.Text.text = conf.getString("text", "render", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。");
		fontSize = conf.getFloat("fontSize", "render", 1, 0, 100, "ステータスの文字サイズを設定します。");
		doRender = conf.getBoolean("doRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。");
		doRenderWhenStart = doRender;
		isRainbow = conf.getBoolean("isRainbow", "render", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。");
		doChangeTeamColor = conf.getBoolean("doChangeTeamColor", "render", false, "テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。");
		doRenderEnchantment = conf.getBoolean("doRenderEnchantment", "render", true, "エンチャント内容を表示する(true) / 表示しない(false)を設定します。");
		conf.save();

		propFilePath = conf.getConfigFile().getParent() + "\\Status_HUD_Plus.xml";
		EditGroupGUI.path = conf.getConfigFile().getParent() + "\\SHPGroups.xml";
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

		totalKillCount = Float.valueOf(prop.getProperty("killCount", "0"));
		totalDeathCount = Float.valueOf(prop.getProperty("deathCount", "0"));
		float totalRate = totalKillCount / (totalDeathCount + 1f);
		Status.TotalRate.value = totalRate;

		//for (Field f : GuiPlayerTabOverlay.class.getDeclaredFields()) System.out.println(f.getName());

		overlayMessageField = ReflectionHelper.findField(GuiIngame.class, "field_73838_g");
		visibleField = ReflectionHelper.findField(GuiPlayerTabOverlay.class, "field_175254_k");
		//overlayMessageField = ReflectionHelper.findField(GuiIngame.class, "overlayMessage");
		//visibleField = ReflectionHelper.findField(GuiPlayerTabOverlay.class, "isBeingRendered");

		Status.Enchant.value = "";
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(DevicePressEvent.resetKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.displayKey);
		ClientRegistry.registerKeyBinding(DevicePressEvent.settingKey);
		MinecraftForge.EVENT_BUS.register(new DevicePressEvent());
		MinecraftForge.EVENT_BUS.register(new Rendering());
		MinecraftForge.EVENT_BUS.register(new ChatEvent());

		Timer timer = new Timer();
		TimerTask checkTask = new TimerTask() {

			@Override
			public void run() {
				if(doCheck) ((EntityPlayerSP)Status_HUD.player).sendChatMessage("/multiplier");
			}
		};

		timer.scheduleAtFixedRate(checkTask, 1000, 5 * 1000 * 60);
		timer.scheduleAtFixedRate(new ItemChangeEvent(), 1000, 10);
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

	public static boolean getVisible() {
		try {
			return visibleField.getBoolean(Minecraft.getMinecraft().ingameGUI.getTabList());
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Get Visible faild");
		}
	}
}
