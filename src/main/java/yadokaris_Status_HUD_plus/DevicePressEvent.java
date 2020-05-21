package yadokaris_Status_HUD_plus;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DevicePressEvent {

	public static KeyBinding resetKey = new KeyBinding("yadokaris_shp.key.Reset", Keyboard.KEY_I, "Status HUD Plus");
	public static KeyBinding displayKey = new KeyBinding("yadokaris_shp.key.Display", Keyboard.KEY_O, "Status HUD Plus");
	public static KeyBinding settingKey = new KeyBinding("yadokaris_shp.key.Setting", Keyboard.KEY_P, "Status HUD Plus");
	static List clicks = new ArrayList<Long>();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event) {
		if (resetKey.isPressed()) {
			Status_HUD.killCountSword = 0;
			Status_HUD.killCountBow = 0;
			Status_HUD.attackingKillCount = 0;
			Status_HUD.defendingKillCount = 0;
			Status_HUD.deathCount = 0;
			Status_HUD.xp = 0;
			Status_HUD.nexusDamage = 0;
			Status_HUD.repairPoint = 0;
			Status_HUD.rate = 0f;
			Status_HUD.player.sendMessage(new TextComponentTranslation("yadokaris_shp.render.Reset"));
			Rendering.updateAllTexts();
		}

		else if (displayKey.isPressed()) {
			Status_HUD.doRender = !Status_HUD.doRender;
			Status_HUD.player.sendMessage(new TextComponentTranslation(Status_HUD.doRender ? "yadokaris_shp.render.Show" : "yadokaris_shp.render.Hide"));
		}

		else if (settingKey.isPressed()) {
			EventQueue.invokeLater(() -> {
				try {
					new SettingGui().setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseClicked(MouseEvent e) {
		if (!e.isButtonstate() || e.getButton() != 0) return;
		clicks.add(System.nanoTime());
	}
}
