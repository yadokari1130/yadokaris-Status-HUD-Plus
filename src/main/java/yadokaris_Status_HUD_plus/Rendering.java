package yadokaris_Status_HUD_plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rendering {

	private static boolean isDeath;
	private static int colorRed, colorGreen;
	private static int colorBlue = 255;
	private static int plusColor;
	private static int currentTick;
	private static long deathTime = System.currentTimeMillis();
	public static Map<String, StatusGroup> groups = new HashMap<>();

	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent event) {
		GlStateManager.scaled(SHPConfig.fontSize.get().doubleValue(), SHPConfig.fontSize.get().doubleValue(), SHPConfig.fontSize.get().doubleValue());

		if (currentTick == 240) {
			currentTick = 0;

			ServerData server = Minecraft.getInstance().getCurrentServerData();
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

		if (Minecraft.getInstance().currentScreen == null && SHPConfig.doRender.get()) {
			if (SHPConfig.isRainbow.get() && currentTick % 5 == 0) {
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

			for (StatusGroup group : groups.values()) {
				float showy = group.y - 10f;
				if (group.doShowName) {
					showy += 10f;
					Minecraft.getInstance().fontRenderer.func_238405_a_(new MatrixStack(), group.name, group.x, showy, Status_HUD.color);
				}
				for (String id : group.statusIDs) {
					showy += 10f;
					Minecraft.getInstance().fontRenderer.func_238405_a_(new MatrixStack(), Status.getStatus(id).getString(), group.x, showy, Status_HUD.color);
				}
			}
		}

		Status.FPS.value = Status_HUD.getFPS();

		if (DevicePressEvent.clicks.size() > 0) {
			while (System.nanoTime() - (long)DevicePressEvent.clicks.get(0) > 1000000000l) {
				DevicePressEvent.clicks.remove(0);
				if (DevicePressEvent.clicks.size() < 1) break;
			}
			Status.CPS.value = (float)DevicePressEvent.clicks.size();
		}

		currentTick++;
	}

	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (Minecraft.getInstance().currentScreen instanceof DeathScreen) {
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
