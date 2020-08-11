package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

public class SettingGui extends JFrame implements ActionListener {

	private final JPanel contentPane;
	private final JCheckBox checkBoxIsEnable = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doEnable").getString());
	private final JCheckBox checkBoxDoShowText = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowText").getString());
	private final JCheckBox checkBoxDoShowSword = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowSword").getString());
	private final JCheckBox checkBoxDoShowBow = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowBow").getString());
	private final JCheckBox checkBoxDoShowAttacking = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowAttackingKill").getString());
	private final JCheckBox checkBoxDoShowDefending = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowDefendingKill").getString());
	private final JCheckBox checkBoxDoShowDeath = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowDeath").getString());
	private final JCheckBox checkBoxDoShowRate = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowRate").getString());
	private final JCheckBox checkBoxDoShowTotalRate = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowTotalRate").getString());
	private final JCheckBox checkBoxDoShowNexusDamage = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowNexusDamage").getString());
	private final JCheckBox checkBoxDoShowRepairPoint = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowRepairPoint").getString());
	private final JCheckBox checkBoxDoShowGetxp = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowGetxp").getString());
	private final JCheckBox checkBoxDoShowHavexp = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowHavexp").getString());
	private final JCheckBox checkBoxDoShowRank = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowRank").getString());
	private final JCheckBox checkBoxDoShowRankPoint = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowRankPoint").getString());
	private final JCheckBox checkBoxDoShowCurrentJob = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowCurrentJob").getString());
	private final JCheckBox checkBoxDoShowFPS = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowFPS").getString());
	private final JCheckBox checkBoxDoShowCPS = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowCPS").getString());
	private final JCheckBox checkBoxDoShowPing = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowPing").getString());
	private final JCheckBox checkBoxDoShowTeam = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doShowTeam").getString());
	private final JCheckBox checkBoxDoChangeTeamColor = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.doChangeTeamColor").getString());
	private final JSpinner spinner_x = new JSpinner();
	private final JSpinner spinner_y = new JSpinner();
	private final JSpinner spinner_size = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TranslationTextComponent("yadokaris_shp.setting.isRainbow").getString());

	public SettingGui() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 520);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_color = new JButton(new TranslationTextComponent("yadokaris_shp.setting.colorButton").getString());
		button_color.setBounds(315, 309, 144, 27);
		button_color.addActionListener(new ColorSetting());
		contentPane.add(button_color);

		checkBoxIsEnable.setBackground(Color.WHITE);
		checkBoxIsEnable.setSelected(SHPConfig.doRenderWhenStart.get());
		checkBoxIsEnable.setBounds(22, 6, 254, 21);
		contentPane.add(checkBoxIsEnable);

		checkBoxDoShowText.setBackground(Color.WHITE);
		checkBoxDoShowText.setSelected(SHPConfig.doShow[Status.Text.ordinal()].get());
		checkBoxDoShowText.setBounds(22, 29, 151, 21);
		contentPane.add(checkBoxDoShowText);

		checkBoxDoShowSword.setBackground(Color.WHITE);
		checkBoxDoShowSword.setSelected(SHPConfig.doShow[Status.KillCountSword.ordinal()].get());
		checkBoxDoShowSword.setBounds(22, 52, 151, 21);
		contentPane.add(checkBoxDoShowSword);

		checkBoxDoShowBow.setBackground(Color.WHITE);
		checkBoxDoShowBow.setSelected(SHPConfig.doShow[Status.KillCountBow.ordinal()].get());
		checkBoxDoShowBow.setBounds(22, 75, 151, 21);
		contentPane.add(checkBoxDoShowBow);

		checkBoxDoShowAttacking.setSelected(SHPConfig.doShow[Status.AttackingKillCount.ordinal()].get());
		checkBoxDoShowAttacking.setBackground(Color.WHITE);
		checkBoxDoShowAttacking.setBounds(22, 98, 151, 21);
		contentPane.add(checkBoxDoShowAttacking);

		checkBoxDoShowDefending.setSelected(SHPConfig.doShow[Status.DefendingKillCount.ordinal()].get());
		checkBoxDoShowDefending.setBackground(Color.WHITE);
		checkBoxDoShowDefending.setBounds(22, 121, 151, 21);
		contentPane.add(checkBoxDoShowDefending);

		checkBoxDoShowDeath.setBackground(Color.WHITE);
		checkBoxDoShowDeath.setSelected(SHPConfig.doShow[Status.DeathCount.ordinal()].get());
		checkBoxDoShowDeath.setBounds(22, 144, 151, 21);
		contentPane.add(checkBoxDoShowDeath);

		checkBoxDoShowRate.setBackground(Color.WHITE);
		checkBoxDoShowRate.setSelected(SHPConfig.doShow[Status.Rate.ordinal()].get());
		checkBoxDoShowRate.setBounds(22, 167, 151, 21);
		contentPane.add(checkBoxDoShowRate);

		checkBoxDoShowTotalRate.setBackground(Color.WHITE);
		checkBoxDoShowTotalRate.setSelected(SHPConfig.doShow[Status.TotalRate.ordinal()].get());
		checkBoxDoShowTotalRate.setBounds(22, 190, 151, 21);
		contentPane.add(checkBoxDoShowTotalRate);

		checkBoxDoShowNexusDamage.setSelected(SHPConfig.doShow[Status.NexusDamage.ordinal()].get());
		checkBoxDoShowNexusDamage.setBackground(Color.WHITE);
		checkBoxDoShowNexusDamage.setBounds(22, 213, 213, 21);
		contentPane.add(checkBoxDoShowNexusDamage);

		checkBoxDoShowRepairPoint.setSelected(SHPConfig.doShow[Status.RepairPoint.ordinal()].get());
		checkBoxDoShowRepairPoint.setBackground(Color.WHITE);
		checkBoxDoShowRepairPoint.setBounds(22, 236, 254, 21);
		contentPane.add(checkBoxDoShowRepairPoint);

		checkBoxDoShowGetxp.setBackground(Color.WHITE);
		checkBoxDoShowGetxp.setSelected(SHPConfig.doShow[Status.XP.ordinal()].get());
		checkBoxDoShowGetxp.setBounds(22, 259, 151, 21);
		contentPane.add(checkBoxDoShowGetxp);

		checkBoxDoShowHavexp.setBackground(Color.WHITE);
		checkBoxDoShowHavexp.setSelected(SHPConfig.doShow[Status.TotalXP.ordinal()].get());
		checkBoxDoShowHavexp.setBounds(22, 282, 151, 21);
		contentPane.add(checkBoxDoShowHavexp);

		checkBoxDoShowRank.setSelected(SHPConfig.doShow[Status.Rank.ordinal()].get());
		checkBoxDoShowRank.setBackground(Color.WHITE);
		checkBoxDoShowRank.setBounds(22, 305, 151, 21);
		contentPane.add(checkBoxDoShowRank);

		checkBoxDoShowRankPoint.setSelected(SHPConfig.doShow[Status.RankPoint.ordinal()].get());
		checkBoxDoShowRankPoint.setBackground(Color.WHITE);
		checkBoxDoShowRankPoint.setBounds(22, 328, 213, 21);
		contentPane.add(checkBoxDoShowRankPoint);

		checkBoxDoShowCurrentJob.setBackground(Color.WHITE);
		checkBoxDoShowCurrentJob.setSelected(SHPConfig.doShow[Status.CurrentJob.ordinal()].get());
		checkBoxDoShowCurrentJob.setBounds(22, 351, 151, 21);
		contentPane.add(checkBoxDoShowCurrentJob);

		checkBoxDoShowFPS.setSelected(SHPConfig.doShow[Status.FPS.ordinal()].get());
		checkBoxDoShowFPS.setBackground(Color.WHITE);
		checkBoxDoShowFPS.setBounds(22, 374, 151, 21);
		contentPane.add(checkBoxDoShowFPS);

		checkBoxDoShowCPS.setSelected(SHPConfig.doShow[Status.CPS.ordinal()].get());
		checkBoxDoShowCPS.setBackground(Color.WHITE);
		checkBoxDoShowCPS.setBounds(22, 397, 151, 21);
		contentPane.add(checkBoxDoShowCPS);

		checkBoxDoShowPing.setSelected(SHPConfig.doShow[Status.Ping.ordinal()].get());
		checkBoxDoShowPing.setBackground(Color.WHITE);
		checkBoxDoShowPing.setBounds(22, 420, 151, 21);
		contentPane.add(checkBoxDoShowPing);

		checkBoxDoShowTeam.setSelected(SHPConfig.doShow[Status.Team.ordinal()].get());
		checkBoxDoShowTeam.setBackground(Color.WHITE);
		checkBoxDoShowTeam.setBounds(22, 444, 151, 21);
		contentPane.add(checkBoxDoShowTeam);

		spinner_x.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinner_x.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_x.setValue(SHPConfig.x.get());
		spinner_x.setBounds(302, 138, 157, 27);
		contentPane.add(spinner_x);

		spinner_y.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinner_y.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_y.setValue(SHPConfig.y.get());
		spinner_y.setBounds(302, 179, 157, 27);
		contentPane.add(spinner_y);

		spinner_size.setModel(new SpinnerNumberModel(1, 0, 100, 0.01));
		spinner_size.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_size.setValue(SHPConfig.fontSize.get());
		spinner_size.setBounds(302, 254, 157, 27);
		contentPane.add(spinner_size);

		textField.setText(SHPConfig.text.get());
		textField.setBounds(205, 61, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setSelected(SHPConfig.isRainbow.get());
		checkBoxIsRainbow.setBounds(239, 351, 151, 21);
		contentPane.add(checkBoxIsRainbow);

		checkBoxDoChangeTeamColor.setSelected(SHPConfig.doChangeTeamColor.get());
		checkBoxDoChangeTeamColor.setBackground(Color.WHITE);
		checkBoxDoChangeTeamColor.setBounds(239, 374, 220, 21);
		contentPane.add(checkBoxDoChangeTeamColor);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TranslationTextComponent("yadokaris_shp.setting.settingText").getString());
		textPane.setBounds(205, 37, 106, 21);
		contentPane.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TranslationTextComponent("yadokaris_shp.setting.settingPoint").getString());
		textPane_1.setBounds(302, 110, 123, 21);
		contentPane.add(textPane_1);

		JTextPane txtpnX = new JTextPane();
		txtpnX.setText("X");
		txtpnX.setBounds(277, 144, 13, 21);
		contentPane.add(txtpnX);

		JTextPane txtpnY = new JTextPane();
		txtpnY.setText("Y");
		txtpnY.setBounds(277, 185, 13, 21);
		contentPane.add(txtpnY);

		JButton button_set = new JButton(new TranslationTextComponent("yadokaris_shp.setting.settingSave").getString());
		button_set.setBounds(354, 444, 106, 27);
		button_set.addActionListener(this);
		contentPane.add(button_set);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText(new TranslationTextComponent("yadokaris_shp.setting.settingFontSize").getString());
		textPane_2.setBounds(302, 223, 123, 21);
		contentPane.add(textPane_2);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		SHPConfig.doShow[Status.Text.ordinal()].set(checkBoxDoShowText.isSelected());
		SHPConfig.doShow[Status.KillCountSword.ordinal()].set(checkBoxDoShowSword.isSelected());
		SHPConfig.doShow[Status.KillCountBow.ordinal()].set(checkBoxDoShowBow.isSelected());
		SHPConfig.doShow[Status.AttackingKillCount.ordinal()].set(checkBoxDoShowAttacking.isSelected());
		SHPConfig.doShow[Status.DefendingKillCount.ordinal()].set(checkBoxDoShowDefending.isSelected());
		SHPConfig.doShow[Status.DeathCount.ordinal()].set(checkBoxDoShowDeath.isSelected());
		SHPConfig.doShow[Status.Rate.ordinal()].set(checkBoxDoShowRate.isSelected());
		SHPConfig.doShow[Status.TotalRate.ordinal()].set(checkBoxDoShowTotalRate.isSelected());
		SHPConfig.doShow[Status.NexusDamage.ordinal()].set(checkBoxDoShowNexusDamage.isSelected());
		SHPConfig.doShow[Status.RepairPoint.ordinal()].set(checkBoxDoShowRepairPoint.isSelected());
		SHPConfig.doShow[Status.XP.ordinal()].set(checkBoxDoShowGetxp.isSelected());
		SHPConfig.doShow[Status.TotalXP.ordinal()].set(checkBoxDoShowHavexp.isSelected());
		SHPConfig.doShow[Status.Rank.ordinal()].set(checkBoxDoShowRank.isSelected());
		SHPConfig.doShow[Status.RankPoint.ordinal()].set(checkBoxDoShowRankPoint.isSelected());
		SHPConfig.doShow[Status.CurrentJob.ordinal()].set(checkBoxDoShowCurrentJob.isSelected());
		SHPConfig.doShow[Status.FPS.ordinal()].set(checkBoxDoShowFPS.isSelected());
		SHPConfig.doShow[Status.CPS.ordinal()].set(checkBoxDoShowCPS.isSelected());
		SHPConfig.doShow[Status.Ping.ordinal()].set(checkBoxDoShowPing.isSelected());
		SHPConfig.doShow[Status.Team.ordinal()].set(checkBoxDoShowTeam.isSelected());
		SHPConfig.text.set(textField.getText());
		SHPConfig.x.set((double)spinner_x.getValue());
		SHPConfig.y.set((double)spinner_y.getValue());;
		SHPConfig.fontSize.set((double) spinner_size.getValue());
		SHPConfig.isRainbow.set(checkBoxIsRainbow.isSelected());
		SHPConfig.doChangeTeamColor.set(checkBoxDoChangeTeamColor.isSelected());
		if (SHPConfig.doChangeTeamColor.get() && ChatEvent.TEAMS.containsKey(Status_HUD.team)) Status_HUD.color = ChatEvent.TEAMS.get(Status_HUD.team);
		else Status_HUD.color = Status_HUD.colorCash;
		SHPConfig.doRenderWhenStart.set(checkBoxIsEnable.isSelected());
		SHPConfig.colors.set("0x" + Integer.toHexString(Status_HUD.colorCash));

		Config.saveConfig(FMLPaths.CONFIGDIR.get().resolve("yadokaris_shp.toml").toString());
		Rendering.updateAllTexts();
		if (SHPConfig.doShow[Status.RankPoint.ordinal()].get() && Status_HUD.player != null) Status_HUD.player.sendChatMessage("/multiplier");

		JOptionPane.showMessageDialog(new SettingGui(), new TranslationTextComponent("yadokaris_shp.setting.save").getString());
	}
}
