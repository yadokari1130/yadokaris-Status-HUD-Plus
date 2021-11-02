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
			Status.KillCountSword.value = 0f;
			Status.KillCountBow.value = 0f;
			Status.AttackingKillCount.value = 0f;
			Status.DefendingKillCount.value = 0f;
			Status.DeathCount.value = 0f;
			Status.XP.value = 0f;
			Status.NexusDamage.value = 0f;
			Status.RepairPoint.value = 0f;
			Status.Rate.value = 0f;
			Status_HUD.player.sendMessage(new TextComponentTranslation("yadokaris_shp.render.Reset"));
		}

		else if (displayKey.isPressed()) {
			Status_HUD.doRender = !Status_HUD.doRender;
			Status_HUD.player.sendMessage(new TextComponentTranslation(Status_HUD.doRender ? "yadokaris_shp.render.Show" : "yadokaris_shp.render.Hide"));
		}

		else if (settingKey.isPressed()) {
			EventQueue.invokeLater(() -> {
				try {
					new SettingGUI().setVisible(true);
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
