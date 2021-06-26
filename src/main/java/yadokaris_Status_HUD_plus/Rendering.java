package yadokaris_Status_HUD_plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Rendering {

	private static boolean isDeath;
	private static int colorRed, colorGreen;
	private static int colorBlue = 255;
	private static int plusColor;
	private static int currentTick;
	private static long deathTime = System.currentTimeMillis();
	public static Map<String, StatusGroup> groups = new HashMap<>();
	int rainbow = 0;
	private static boolean isRenderedDebug = false;

	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent event) {
		GlStateManager.scale(Status_HUD.fontSize, Status_HUD.fontSize, Status_HUD.fontSize);

		if (currentTick == 240) {
			currentTick = 0;

			ServerData server = Minecraft.getMinecraft().getCurrentServerData();
			if (server != null) {
				new Thread(() -> {
					try {
						String address = server.serverIP.contains(":") ? server.serverIP.substring(0, server.serverIP.indexOf(":")) : server.serverIP;
						InetAddress target = InetAddress.getByName(address);

						if (Status_HUD.osName.startsWith("windows")) {
							String command[] = {"cmd", "/c", "chcp", "437", "&&", "ping", "-n", "1", "-w", "1000", target.getHostAddress()};
							Process process = new ProcessBuilder(command).start();

							try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
								String line;
								while ((line = r.readLine()) != null) {
									if (line.contains("Average = ")) Status.Ping.value = line.substring(line.lastIndexOf("= ") + 2);
								}
							}
						}

						if (Status_HUD.osName.startsWith("linux") || Status_HUD.osName.startsWith("mac")) {
							String[] command = {"ping", "-c", "1", "-t", "255", target.getHostAddress()};
							Process process = new ProcessBuilder(command).start();

							try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
								String line;
								while ((line = r.readLine()) != null) {
									if (line.contains("time=")) Status.Ping.value = line.substring(line.lastIndexOf("=") + 1);
								}
							}
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
			}
			else Status.Ping.value = "0ms";
		}

		if (Minecraft.getMinecraft().currentScreen == null && Status_HUD.doRender && !isRenderedDebug) {
			if (currentTick % 5 == 0) {
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

					rainbow = Integer.parseInt(red + green + blue, 16);
				}).start();
			}

			FontRenderer font = Minecraft.getMinecraft().fontRenderer;

			if (groups.isEmpty()) {
				font.drawStringWithShadow("yadokari's Status HUD Plus v" + Status_HUD.version, 2f, 2f, Status_HUD.color);
				font.drawStringWithShadow("Please set the display settings", 2f, 12f, Status_HUD.color);
			}

			for (StatusGroup group : groups.values()) {
				if (!group.doRender) continue;
				float showy = group.y - 10f;
				int color = 0;
				if (group.isRainbow) color = rainbow;
				else if (group.doChangeTeamColor && ChatEvent.TEAMS.containsKey(Status.Team.value)) color = ChatEvent.TEAMS.get(Status.Team.value);
				else if (group.color == -1) {
					if (Status_HUD.isRainbow) color = rainbow;
					else if (Status_HUD.doChangeTeamColor && ChatEvent.TEAMS.containsKey(Status.Team.value)) color = ChatEvent.TEAMS.get(Status.Team.value);
					else color = Status_HUD.color;
				}
				else color = group.color;

				if (group.doShowName) {
					for (String s : group.name.split("\\$n")) {
						showy += 10f;
						font.drawStringWithShadow(s, group.x, showy, color);
					}
				}
				for (String id : group.statusIDs) {
					for (String s : Status.getStatus(id).getString().split("\\$n")) {
						showy += 10f;
						font.drawStringWithShadow(s, group.x, showy, color);
					}
				}
			}

			int color = 0;
			if (Status_HUD.isRainbow) color = rainbow;
			else if (Status_HUD.doChangeTeamColor && ChatEvent.TEAMS.containsKey(Status.Team.value)) color = ChatEvent.TEAMS.get(Status.Team.value);
			else color = Status_HUD.color;
			if (Status_HUD.doRenderEnchantment && ItemChangeEvent.l > 0) {
				ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
				float showy = sr.getScaledHeight() - 61;
				if (!Minecraft.getMinecraft().playerController.shouldDrawHUD()) showy += 14;
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				for (String s : Status.Enchant.getString().split("\\$n")) {
					showy -= 10f;
					float showx = (sr.getScaledWidth() - font.getStringWidth(s)) / 2;
					font.drawStringWithShadow(s, showx, showy, color + (ItemChangeEvent.l << 24));
				}
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
			}
		}

		Status.FPS.value = Minecraft.getDebugFPS();

		if (DevicePressEvent.clicks.size() > 0) {
			while (System.nanoTime() - (long)DevicePressEvent.clicks.get(0) > 1000000000l) {
				DevicePressEvent.clicks.remove(0);
				if (DevicePressEvent.clicks.size() < 1) break;
			}
			Status.CPS.value = (float)DevicePressEvent.clicks.size();
		}

		if (Status_HUD.player != null) {
			Status.Coordinate.value = String.format("%.3f, %.3f, %.3f", Status_HUD.player.posX, Status_HUD.player.posY, Status_HUD.player.posZ);
			Status.Angle.value = (Status_HUD.player.rotationYawHead % 360f + 360f) % 360f;
		}

		isRenderedDebug = Minecraft.getMinecraft().gameSettings.showDebugInfo || Status_HUD.getVisible();

		currentTick++;
	}

	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiGameOver) {
			if (!isDeath && System.currentTimeMillis() - deathTime > 500) {
				isDeath = true;
				deathTime = System.currentTimeMillis();
				Status.DeathCount.increment();
				Status_HUD.totalDeathCount += 1f;
				Status.TotalRate.value = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Status.Rate.value = ((float)(Status.KillCountSword.value) + (float)Status.KillCountBow.value) / ((float)Status.DeathCount.value + 1f);

				new Thread(() -> {
					Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);
					Status_HUD.writeProperty();
				}).start();
			}
		}
		else isDeath = false;
	}
}
