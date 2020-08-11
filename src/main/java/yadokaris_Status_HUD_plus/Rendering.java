package yadokaris_Status_HUD_plus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rendering {

	private static boolean isDeath;
	private static int colorRed, colorGreen;
	private static int colorBlue = 255;
	private static int plusColor;
	private static int fps, cps;
	private static String ping = "0ms";
	private static int currentTick;
	private static long deathTime = System.currentTimeMillis();
	private static final String[] TEXTS = {
		String.format(SHPConfig.text.get(), Status_HUD.playerName),
		new TranslationTextComponent("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword).getString(),
		new TranslationTextComponent("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow).getString(),
		new TranslationTextComponent("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount).getString(),
		new TranslationTextComponent("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount).getString(),
		new TranslationTextComponent("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount).getString(),
		new TranslationTextComponent("yadokaris_shp.render.Rate", Status_HUD.rate).getString(),
		new TranslationTextComponent("yadokaris_shp.render.TotalRate", Status_HUD.totalRate).getString(),
		new TranslationTextComponent("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage).getString(),
		new TranslationTextComponent("yadokaris_shp.render.RepairPoint", Status_HUD.repairPoint).getString(),
		new TranslationTextComponent("yadokaris_shp.render.XP", Status_HUD.xp).getString(),
		new TranslationTextComponent("yadokaris_shp.render.TotalXP", Status_HUD.totalXp).getString(),
		new TranslationTextComponent("yadokaris_shp.render.Rank", Status_HUD.rank).getString(),
		new TranslationTextComponent("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint).getString(),
		new TranslationTextComponent("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob).getString(),
		new TranslationTextComponent("yadokaris_shp.render.FPS", fps).getString(),
		new TranslationTextComponent("yadokaris_shp.render.CPS", cps).getString(),
		new TranslationTextComponent("yadokaris_shp.render.Ping", ping).getString(),
		new TranslationTextComponent("yadokaris_shp.render.Team", Status_HUD.team).getString()
	};

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
									if (line.contains("Average = ")) ping = line.substring(line.lastIndexOf("= ") + 2);
								}
							}
						}

						if (Status_HUD.osName.startsWith("linux") || Status_HUD.osName.startsWith("mac")) {
							String[] command = {"ping", "-c", "1", "-t", "255", target.getHostAddress()};
							Process process = new ProcessBuilder(command).start();

							try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
								String line;
								while ((line = r.readLine()) != null) {
									if (line.contains("time=")) ping = line.substring(line.lastIndexOf("=") + 1);
								}
							}
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}).start();

				updateText(Status.Ping);
			}
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

			float showy = SHPConfig.y.get().floatValue() - 10;
			for (int i = 0; i < TEXTS.length; i++) {
				if (TEXTS[i].length() >= 1 && SHPConfig.doShow[i].get()) {
					showy += 10;
					Minecraft.getInstance().fontRenderer.func_238405_a_(new MatrixStack(), TEXTS[i], SHPConfig.x.get().floatValue(), showy, Status_HUD.color);
				}
			}
		}

		if (SHPConfig.doShow[Status.FPS.ordinal()].get()) {
			updateText(Status.FPS);
		}

		if (SHPConfig.doShow[Status.CPS.ordinal()].get() && DevicePressEvent.clicks.size() > 0) {
			while (System.nanoTime() - (long)DevicePressEvent.clicks.get(0) > 1000000000l) {
				DevicePressEvent.clicks.remove(0);
				if (DevicePressEvent.clicks.size() < 1) break;
			}
			cps = DevicePressEvent.clicks.size();
			updateText(Status.CPS);
		}

		currentTick++;
	}

	@SubscribeEvent
	public void onTick(TickEvent event) {
		if (Minecraft.getInstance().currentScreen instanceof DeathScreen) {
			if (!isDeath && System.currentTimeMillis() - deathTime > 500) {
				isDeath = true;
				deathTime = System.currentTimeMillis();
				Status_HUD.deathCount += 1f;
				Status_HUD.totalDeathCount += 1f;
				Status_HUD.totalRate = Status_HUD.totalKillCount / (Status_HUD.totalDeathCount + 1f);
				Status_HUD.rate = (Status_HUD.killCountSword + Status_HUD.killCountBow) / (Status_HUD.deathCount + 1f);
				updateTexts(Status.DeathCount, Status.Rate, Status.TotalRate);

				new Thread(() -> {
					Status_HUD.prop.setProperty("deathCount", "" + Status_HUD.totalDeathCount);
					Status_HUD.writeProperty();
				}).start();
			}
		}
		else isDeath = false;
	}

	public static void updateText(Status status) {
		TranslationTextComponent[] translations = {
				new TranslationTextComponent("Dummy!"),
				new TranslationTextComponent("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TranslationTextComponent("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TranslationTextComponent("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TranslationTextComponent("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TranslationTextComponent("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TranslationTextComponent("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TranslationTextComponent("yadokaris_shp.render.RepairPoint", Status_HUD.repairPoint),
				new TranslationTextComponent("yadokaris_shp.render.XP", Status_HUD.xp),
				new TranslationTextComponent("yadokaris_shp.render.TotalXP", Status_HUD.totalXp),
				new TranslationTextComponent("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TranslationTextComponent("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TranslationTextComponent("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TranslationTextComponent("yadokaris_shp.render.FPS", Status_HUD.getFPS()),
				new TranslationTextComponent("yadokaris_shp.render.CPS", cps),
				new TranslationTextComponent("yadokaris_shp.render.Ping", ping),
				new TranslationTextComponent("yadokaris_shp.render.Team", Status_HUD.team)
		};

		if (status == Status.Text) TEXTS[0] = String.format(SHPConfig.text.get(), Status_HUD.playerName);
		else TEXTS[status.ordinal()] = translations[status.ordinal()].getString();
	}

	public static void updateTexts(Status... statuses) {

		TranslationTextComponent[] translations = {
				new TranslationTextComponent("Dummy!"),
				new TranslationTextComponent("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TranslationTextComponent("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TranslationTextComponent("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TranslationTextComponent("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TranslationTextComponent("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TranslationTextComponent("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TranslationTextComponent("yadokaris_shp.render.RepairPoint", Status_HUD.repairPoint),
				new TranslationTextComponent("yadokaris_shp.render.XP", Status_HUD.xp),
				new TranslationTextComponent("yadokaris_shp.render.TotalXP", Status_HUD.totalXp),
				new TranslationTextComponent("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TranslationTextComponent("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TranslationTextComponent("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TranslationTextComponent("yadokaris_shp.render.FPS", Status_HUD.getFPS()),
				new TranslationTextComponent("yadokaris_shp.render.CPS", cps),
				new TranslationTextComponent("yadokaris_shp.render.Ping", ping),
				new TranslationTextComponent("yadokaris_shp.render.Team", Status_HUD.team)
		};

		for (Status status : statuses) {
			if (status == Status.Text) TEXTS[0] = String.format(SHPConfig.text.get(), Status_HUD.playerName);
			else TEXTS[status.ordinal()] = translations[status.ordinal()].getString();
		}
	}

	public static void updateAllTexts() {
		TranslationTextComponent[] translations = {
				new TranslationTextComponent("Dummy!"),
				new TranslationTextComponent("yadokaris_shp.render.KillCountSword", (int)Status_HUD.killCountSword),
				new TranslationTextComponent("yadokaris_shp.render.KillCountBow", (int)Status_HUD.killCountBow),
				new TranslationTextComponent("yadokaris_shp.render.AttackingKillCount", (int)Status_HUD.attackingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DefendingKillCount", (int)Status_HUD.defendingKillCount),
				new TranslationTextComponent("yadokaris_shp.render.DeathCount", (int)Status_HUD.deathCount),
				new TranslationTextComponent("yadokaris_shp.render.Rate", Status_HUD.rate),
				new TranslationTextComponent("yadokaris_shp.render.TotalRate", Status_HUD.totalRate),
				new TranslationTextComponent("yadokaris_shp.render.NexusDamage", Status_HUD.nexusDamage),
				new TranslationTextComponent("yadokaris_shp.render.RepairPoint", Status_HUD.repairPoint),
				new TranslationTextComponent("yadokaris_shp.render.XP", Status_HUD.xp),
				new TranslationTextComponent("yadokaris_shp.render.TotalXP", Status_HUD.totalXp),
				new TranslationTextComponent("yadokaris_shp.render.Rank", Status_HUD.rank),
				new TranslationTextComponent("yadokaris_shp.render.RankPoint", Status_HUD.rankPoint),
				new TranslationTextComponent("yadokaris_shp.render.CurrentJob", Status_HUD.currentJob),
				new TranslationTextComponent("yadokaris_shp.render.FPS", Status_HUD.getFPS()),
				new TranslationTextComponent("yadokaris_shp.render.CPS", cps),
				new TranslationTextComponent("yadokaris_shp.render.Ping", ping),
				new TranslationTextComponent("yadokaris_shp.render.Team", Status_HUD.team)
		};

		TEXTS[0] = String.format(SHPConfig.text.get(), Status_HUD.playerName);
		for (int i = 1; i < TEXTS.length; i++) TEXTS[i] = translations[i].getString();
	}
}
