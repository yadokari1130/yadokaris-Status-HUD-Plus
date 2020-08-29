package yadokaris_Status_HUD_plus;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DevicePressEvent {

	public static KeyBinding resetKey = new KeyBinding("yadokaris_shp.key.Reset", GLFW_KEY_I,"Status HUD Plus");
	public static KeyBinding displayKey = new KeyBinding("yadokaris_shp.key.Display", GLFW_KEY_O, "Status HUD Plus");
	public static KeyBinding settingKey = new KeyBinding("yadokaris_shp.key.Setting", GLFW_KEY_P, "Status HUD Plus");
	static List clicks = new ArrayList<Long>();

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event) {
		if (resetKey.isPressed()) {
			Status.KillCountSword.value = 0;
			Status.KillCountBow.value = 0;
			Status.AttackingKillCount.value = 0;
			Status.DefendingKillCount.value = 0;
			Status.DeathCount.value = 0;
			Status.XP.value = 0;
			Status.NexusDamage.value = 0;
			Status.RepairPoint.value = 0;
			Status.Rate.value = 0f;
			Status_HUD.player.sendMessage(new TranslationTextComponent("yadokaris_shp.render.Reset"), null);
		}

		else if (displayKey.isPressed()) {
			SHPConfig.doRender.set(!SHPConfig.doRender.get());
			Status_HUD.doCheck = SHPConfig.doRender.get();
			Status_HUD.player.sendMessage(new TranslationTextComponent(SHPConfig.doRender.get() ? "yadokaris_shp.render.Show" : "yadokaris_shp.render.Hide"), null);
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

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onMouseClicked(MouseInputEvent e) {
		if(e.getButton() == 0 && e.getAction() == GLFW_PRESS) clicks.add(System.nanoTime());
	}
}
