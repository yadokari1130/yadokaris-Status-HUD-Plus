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
			ChatEvent.killCountSword = 0;
			ChatEvent.killCountBow = 0;
			ChatEvent.deathCount = 0;
			ChatEvent.xp = 0;
			Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
			Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
			Rendering.player.addChatMessage(new TextComponentTranslation("yadokaris_shp.render.Reset"));
		}

		else if (displayKey.isPressed()) {
			if (Rendering.isRender) {
				Rendering.isRender = false;
				Rendering.player.addChatMessage(new TextComponentTranslation("yadokaris_shp.render.Hide"));
			}
			else {
				Rendering.isRender = true;
				Rendering.player.addChatMessage(new TextComponentTranslation("yadokaris_shp.render.Show"));
			}
		}

		else if (settingKey.isPressed()) {

			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						new GuiSetting().setVisible(true);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
