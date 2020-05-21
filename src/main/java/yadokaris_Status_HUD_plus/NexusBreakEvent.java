package yadokaris_Status_HUD_plus;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.world.BossInfo;

public class NexusBreakEvent {
	static Set<String> bossNames = new HashSet<>();

	public boolean isNexusBreak() {

		try {
			bossNames = getBossNames(Minecraft.getMinecraft());
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}

		for (String bossName : bossNames) {
			if (bossName.contains(Status_HUD.player.getName())) return true;
		}

		return false;
	}

	public static Set<String> getBossNames(Minecraft minecraft) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		GuiBossOverlay bossOverlay = minecraft.ingameGUI.getBossOverlay();

		String mapName = null;

		for (Field field : GuiBossOverlay.class.getDeclaredFields()) {
			if (field.getType().getName().equals("java.util.Map")) {
				mapName = field.getName();
				break;
			}
		}

		Set<String> names = new HashSet<String>();

		if (mapName != null) {
			Field bossField = GuiBossOverlay.class.getDeclaredField(mapName);
			bossField.setAccessible(true);

			Map<UUID, BossInfo> bossInfos = (Map<UUID, BossInfo>) bossField.get(bossOverlay);

			for (BossInfo info : bossInfos.values()) names.add(info.getName().getFormattedText());
		}

		return names;
	}
}
