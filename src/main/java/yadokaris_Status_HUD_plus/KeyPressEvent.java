package yadokaris_Status_HUD_plus;

import java.awt.EventQueue;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyPressEvent {

	public static KeyBinding resetKey = new KeyBinding("yadokaris_shp.key.Reset", Keyboard.KEY_I, "Status HUD Plus");
	public static KeyBinding displayKey = new KeyBinding("yadokaris_shp.key.Display", Keyboard.KEY_O, "Status HUD Plus");
	public static KeyBinding settingKey = new KeyBinding("yadokaris_shp.key.Setting", Keyboard.KEY_P, "Status HUD Plus");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event) {
		if (resetKey.isPressed()) {
			Status_HUD.killCountSword = 0;
			Status_HUD.killCountBow = 0;
			Status_HUD.deathCount = 0;
			Status_HUD.xp = 0;
			Status_HUD.nexusDamage = 0;
			Status_HUD.rate = 0f;
			Status_HUD.player.addChatMessage(new TextComponentTranslation("yadokaris_shp.render.Reset"));
			Rendering.updateAllTexts();
		}

		else if (displayKey.isPressed()) {
			Status_HUD.isRender = !Status_HUD.isRender;
			Status_HUD.player.addChatMessage(new TextComponentTranslation(Status_HUD.isRender ? "yadokaris_shp.render.Show" : "yadokaris_shp.render.Hide"));
		}

		else if (settingKey.isPressed()) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						new SettingGui().setVisible(true);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
