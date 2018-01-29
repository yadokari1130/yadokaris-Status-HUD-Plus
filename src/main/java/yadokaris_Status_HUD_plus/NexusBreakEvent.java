package yadokaris_Status_HUD_plus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.world.BossInfoLerping;

public class NexusBreakEvent {
	static List<String> bossNames = new ArrayList<String>();

	public boolean isNexusBreak() {

		try {
			bossNames = getBossBarNames(Minecraft.getMinecraft());
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}

		for (String bossName : bossNames) {
			if (bossName.contains(Rendering.player.getName())) return true;
		}

		return false;
	}


	public static List<String> getBossBarNames(Minecraft minecraft) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		GuiBossOverlay bossOverlay = minecraft.ingameGUI.getBossOverlay();

		String nameAfter = null;

		for (Field s : GuiBossOverlay.class.getDeclaredFields()) {
			if (s.getType().getName().equals("java.util.Map")) {
				nameAfter = s.getName();
				break;
			}
		}

		List<String> names = new ArrayList<String>();

		if (nameAfter != null) {
			Field bossField = GuiBossOverlay.class.getDeclaredField(nameAfter);
			bossField.setAccessible(true);

			Map<UUID, BossInfoLerping> mapBossInfos = (Map<UUID, BossInfoLerping>) bossField.get(bossOverlay);

			for (BossInfoLerping bIL : mapBossInfos.values())
				names.add(bIL.getName().getUnformattedText());
		}

		return names;
	}
}
