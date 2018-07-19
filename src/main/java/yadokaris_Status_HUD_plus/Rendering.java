package yadokaris_Status_HUD_plus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Rendering {

	private static boolean isDeath;
	private static int colorRed, colorGreen;
	private static int colorBlue = 255;
	private static int plusColor;
	private static int fps;
	private static int currentTick;
	private static final String[] TEXTS = {
		String.format(Status_HUD.text, Status_HUD.playerName),
		new TextComponentTranslation("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.Rate", Status_HUD.rate).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.TotalRate", Status_HUD.totalRate).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.XP", Status_HUD.xp).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.TotalXP", (int)Status_HUD.totalXp).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.Rank", Status_HUD.rank).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.FPS", fps).getFormattedText(),
		new TextComponentTranslation("yadokaris_shp.render.Team", Status_HUD.team).getFormattedText()
	};

	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent event) {

		if (currentTick == 20) currentTick = 0;

		if (Minecraft.getMinecraft().currentScreen == null && Status_HUD.doRender) {
			if (Status_HUD.isRainbow && currentTick % 5 == 0) {
				new Thread(() -> {
					// Rainbow
					switch (plusColor) {
					case 0:
						colorRed++;
						colorBlue--;
						if (colorRed == 255) plusColor = 1;
						break;
					case 1:
						colorGreen++;
						colorRed--;
						if (colorGreen == 255) plusColor = 2;
						break;
					case 2:
						colorBlue++;
						colorGreen--;
						if (colorBlue == 255) plusColor = 0;
						break;
					}

					String red = Integer.toHexString(colorRed);
					String green = Integer.toHexString(colorGreen);
					String blue = Integer.toHexString(colorBlue);
					if (red.length() < 2) red = "0" + red;
					if (green.length() < 2) green = "0" + green;
					if (blue.length() < 2) blue = "0" + blue;

					Status_HUD.color = Integer.parseInt(red + green + blue, 16);
				}).start();
			}
			
			int showy = Status_HUD.y - 10;
			for (int i = 0; i < TEXTS.length; i++) {
				if (TEXTS[i].length() >= 1 && Status_HUD.doShow[i]) {
					showy += 10;
					Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(TEXTS[i], Status_HUD.x, showy, Status_HUD.color);
				}
			}
		}

		if (Minecraft.getMinecraft().currentScreen instanceof GuiGameOver) {
			if (!isDeath) {
				isDeath = true;
				Status_HUD.deathCount += 1f;
				Status_HUD.totalDeathCount += 1f;
				Status_HUD.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Status_HUD.rate = (Status_HUD.killCountSword + Status_HUD.killCountBow) / (Status_HUD.deathCount + 1f);
				updateTexts(5, 6, 7);

				new Thread(() -> {
					Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);
					Status_HUD.writeProperty();
				}).start();
			}
		}
		else isDeath = false;

		if (Status_HUD.doShow[14]) {
			fps = Minecraft.getDebugFPS();
			updateText(14);
		}
		
		currentTick++;
	}

	public static void updateText(int number) {
		if (number > TEXTS.length - 1) return;
		
		TextComponentTranslation[] translations = {
				new TextComponentTranslation("Dummy!"),
				new TextComponentTranslation("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TextComponentTranslation("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TextComponentTranslation("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TextComponentTranslation("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TextComponentTranslation("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TextComponentTranslation("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TextComponentTranslation("yadokaris_shp.render.XP", Status_HUD.xp),
				new TextComponentTranslation("yadokaris_shp.render.TotalXP", (int)Status_HUD.totalXp),
				new TextComponentTranslation("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TextComponentTranslation("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TextComponentTranslation("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TextComponentTranslation("yadokaris_shp.render.FPS", fps),
				new TextComponentTranslation("yadokaris_shp.render.Team", Status_HUD.team)
		};
		
		if (number == 0) TEXTS[0] = String.format(Status_HUD.text, Status_HUD.playerName);
		else TEXTS[number] = translations[number].getFormattedText();
	}
	
	public static void updateTexts(int... numbers) {
		
		TextComponentTranslation[] translations = {
				new TextComponentTranslation("Dummy!"),
				new TextComponentTranslation("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TextComponentTranslation("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TextComponentTranslation("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TextComponentTranslation("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TextComponentTranslation("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TextComponentTranslation("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TextComponentTranslation("yadokaris_shp.render.XP", Status_HUD.xp),
				new TextComponentTranslation("yadokaris_shp.render.TotalXP", (int)Status_HUD.totalXp),
				new TextComponentTranslation("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TextComponentTranslation("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TextComponentTranslation("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TextComponentTranslation("yadokaris_shp.render.FPS", fps),
				new TextComponentTranslation("yadokaris_shp.render.Team", Status_HUD.team)
			};
		
		for (int number : numbers) {
			if (number > TEXTS.length - 1) continue;
			
			if (number == 0) TEXTS[0] = String.format(Status_HUD.text, Status_HUD.playerName);
			else TEXTS[number] = translations[number].getFormattedText();
		}
	}
	
	public static void updateAllTexts() {
		TextComponentTranslation[] translations = {
				new TextComponentTranslation("Dummy!"),
				new TextComponentTranslation("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TextComponentTranslation("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TextComponentTranslation("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TextComponentTranslation("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TextComponentTranslation("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TextComponentTranslation("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TextComponentTranslation("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TextComponentTranslation("yadokaris_shp.render.XP", Status_HUD.xp),
				new TextComponentTranslation("yadokaris_shp.render.TotalXP", (int)Status_HUD.totalXp),
				new TextComponentTranslation("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TextComponentTranslation("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TextComponentTranslation("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TextComponentTranslation("yadokaris_shp.render.FPS", fps),
				new TextComponentTranslation("yadokaris_shp.render.Team", Status_HUD.team)
		};
		
		TEXTS[0] = String.format(Status_HUD.text, Status_HUD.playerName);
		for (int i = 1; i < TEXTS.length; i++) TEXTS[i] = translations[i].getFormattedText();
	}
}
