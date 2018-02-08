package yadokaris_Status_HUD_plus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Rendering{

	static boolean isRender, isRainbow, isChangeTeamColor;
	static EntityPlayer player;
	private static boolean isDeath;
	private static int colorRed, colorGreen;
	private static int colorBlue = 255;
	private static int plusColor;
	private static int fps;
	private static long lastDeathTime;
	static float totalRate, rate;
	static int currentTick;

	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent e) {

		if (currentTick == 20) currentTick = 0;

		if (Minecraft.getMinecraft().currentScreen == null && isRender) {
			if (isRainbow && currentTick % 5 == 0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						//Rainbow
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
					}
				}).start();
			}

			String[] showTexts = {String.format(Status_HUD.text, player.getName()), new TextComponentTranslation("yadokaris_shp.render.KillCountSword", (int)ChatEvent.killCountSword).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.KillCountBow", (int)ChatEvent.killCountBow).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.DeathCount", (int)ChatEvent.deathCount).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.Rate", rate).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.TotalRate", totalRate).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.NexusDamage", ChatEvent.nexusDamage).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.XP", ChatEvent.xp).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.TotalXP", (int)ChatEvent.totalXp).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.Rank", ChatEvent.rank).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.RankPoint", ChatEvent.rankPoint).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.CurrentJob", ChatEvent.currentJob).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.FPS", fps).getFormattedText(), new TextComponentTranslation("yadokaris_shp.render.Team", ChatEvent.team).getFormattedText()};
			int showy = Status_HUD.y - 10;
			for (int i = 0; i < showTexts.length; i++) {
				if (showTexts[i].length() >= 1 && Status_HUD.isShow[i]) {
					showy += 10;
					Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(showTexts[i] , Status_HUD.x, showy, Status_HUD.color);
				}
			}
		}

		if (Minecraft.getMinecraft().currentScreen instanceof GuiGameOver) {
			if (!isDeath && System.currentTimeMillis() - lastDeathTime > 500) {
				new Thread(new Runnable() {
					@Override
					public void run() {

						isDeath = true;

						ChatEvent.deathCount += 1f;
						Status_HUD.totalDeathCount += 1f;
						Rendering.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
						Rendering.rate = (ChatEvent.killCountSword + ChatEvent.killCountBow) / (ChatEvent.deathCount + 1f);
						Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);

						OutputStream writer = null;
						try {
							writer = new FileOutputStream(Status_HUD.cfgFile);
							Status_HUD.prop.storeToXML(writer, "Comment");
							writer.flush();
						}
						catch (IOException ioe) {ioe.printStackTrace();}
						finally {
							if (writer != null) {
								try {
									writer.close();
								}
								catch (IOException ioe) {
									ioe.printStackTrace();
								}
							}
						}
						lastDeathTime = System.currentTimeMillis();
					}
				}).start();
			}
		}

		else isDeath = false;

		if (Status_HUD.isShow[11]) fps = Minecraft.getDebugFPS();
		currentTick++;
	}
}

