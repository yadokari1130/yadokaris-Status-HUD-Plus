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
import javax.swing.border.EmptyBorder;

import net.minecraft.util.text.TextComponentTranslation;

public class GuiSetting extends JFrame implements ActionListener{

	private final JPanel contentPane;
	private final JCheckBox checkBox_isEnable = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isEnable").getUnformattedText());
	private final JCheckBox checkBox_isShowText = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowText").getUnformattedText());
	private final JCheckBox checkBox_isShowSword = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowSword").getUnformattedText());
	private final JCheckBox checkBox_isShowBow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowBow").getUnformattedText());
	private final JCheckBox checkBox_isShowDeath = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowDeath").getUnformattedText());
	private final JCheckBox checkBox_isShowRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRate").getUnformattedText());
	private final JCheckBox checkBox_isShowTotalRate = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowTotalRate").getUnformattedText());
	private final JCheckBox checkBox_isShowGetxp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowGetxp").getUnformattedText());
	private final JCheckBox checkBox_isShowHavexp = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowHavexp").getUnformattedText());
	private final JCheckBox checkBox_isShowRank = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRank").getUnformattedText());
	private final JCheckBox checkBox_isShowRankPoint = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowRankPoint").getUnformattedText());
	private final JCheckBox checkBox_isShowCurrentJob = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowCurrentJob").getUnformattedText());
	private final JCheckBox checkBox_isShowFPS = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isShowFPS").getUnformattedText());
	private final JSpinner spinner_x = new JSpinner();
	private final JSpinner spinner_y = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBox_isRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedText());

	public GuiSetting() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button_color = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedText());
		button_color.setBounds(315, 255, 144, 27);
		button_color.addActionListener(new ColorSetting());
		contentPane.add(button_color);

		checkBox_isEnable.setBackground(Color.WHITE);
		checkBox_isEnable.setSelected(Status_HUD.cfg.getBoolean("isRenderWhenStart", "render", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。"));
		checkBox_isEnable.setBounds(22, 21, 254, 21);
		contentPane.add(checkBox_isEnable);

		checkBox_isShowText.setBackground(Color.WHITE);
		checkBox_isShowText.setSelected(Status_HUD.isShow[0]);
		checkBox_isShowText.setBounds(22, 44, 151, 21);
		contentPane.add(checkBox_isShowText);

		checkBox_isShowSword.setBackground(Color.WHITE);
		checkBox_isShowSword.setSelected(Status_HUD.isShow[1]);
		checkBox_isShowSword.setBounds(22, 67, 151, 21);
		contentPane.add(checkBox_isShowSword);

		checkBox_isShowBow.setBackground(Color.WHITE);
		checkBox_isShowBow.setSelected(Status_HUD.isShow[2]);
		checkBox_isShowBow.setBounds(22, 90, 151, 21);
		contentPane.add(checkBox_isShowBow);

		checkBox_isShowDeath.setBackground(Color.WHITE);
		checkBox_isShowDeath.setSelected(Status_HUD.isShow[3]);
		checkBox_isShowDeath.setBounds(22, 113, 151, 21);
		contentPane.add(checkBox_isShowDeath);

		checkBox_isShowRate.setBackground(Color.WHITE);
		checkBox_isShowRate.setSelected(Status_HUD.isShow[4]);
		checkBox_isShowRate.setBounds(22, 136, 151, 21);
		contentPane.add(checkBox_isShowRate);

		checkBox_isShowTotalRate.setBackground(Color.WHITE);
		checkBox_isShowTotalRate.setSelected(Status_HUD.isShow[5]);
		checkBox_isShowTotalRate.setBounds(22, 159, 151, 21);
		contentPane.add(checkBox_isShowTotalRate);

		checkBox_isShowGetxp.setBackground(Color.WHITE);
		checkBox_isShowGetxp.setSelected(Status_HUD.isShow[6]);
		checkBox_isShowGetxp.setBounds(22, 182, 151, 21);
		contentPane.add(checkBox_isShowGetxp);

		checkBox_isShowHavexp.setBackground(Color.WHITE);
		checkBox_isShowHavexp.setSelected(Status_HUD.isShow[7]);
		checkBox_isShowHavexp.setBounds(22, 205, 151, 21);
		contentPane.add(checkBox_isShowHavexp);

		checkBox_isShowRank.setSelected(Status_HUD.isShow[8]);
		checkBox_isShowRank.setBackground(Color.WHITE);
		checkBox_isShowRank.setBounds(22, 228, 151, 21);
		contentPane.add(checkBox_isShowRank);

		checkBox_isShowRankPoint.setSelected(Status_HUD.isShow[9]);
		checkBox_isShowRankPoint.setBackground(Color.WHITE);
		checkBox_isShowRankPoint.setBounds(22, 251, 213, 21);
		contentPane.add(checkBox_isShowRankPoint);

		checkBox_isShowCurrentJob.setBackground(Color.WHITE);
		checkBox_isShowCurrentJob.setSelected(Status_HUD.isShow[10]);
		checkBox_isShowCurrentJob.setBounds(22, 274, 151, 21);
		contentPane.add(checkBox_isShowCurrentJob);

		checkBox_isShowFPS.setSelected(Status_HUD.isShow[11]);
		checkBox_isShowFPS.setBackground(Color.WHITE);
		checkBox_isShowFPS.setBounds(22, 297, 151, 21);
		contentPane.add(checkBox_isShowFPS);

		spinner_x.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_x.setValue(Status_HUD.x);
		spinner_x.setBounds(302, 151, 157, 27);
		contentPane.add(spinner_x);

		spinner_y.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinner_y.setValue(Status_HUD.y);
		spinner_y.setBounds(302, 203, 157, 27);
		contentPane.add(spinner_y);

		textField.setText(Status_HUD.text);
		textField.setBounds(205, 76, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBox_isRainbow.setBackground(Color.WHITE);
		checkBox_isRainbow.setSelected(Rendering.isRainbow);
		checkBox_isRainbow.setBounds(315, 288, 151, 21);
		contentPane.add(checkBox_isRainbow);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingText").getUnformattedText());
		textPane.setBounds(205, 52, 106, 21);
		contentPane.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingPoint").getUnformattedText());
		textPane_1.setBounds(302, 123, 123, 21);
		contentPane.add(textPane_1);

		JTextPane txtpnX = new JTextPane();
		txtpnX.setText("X");
		txtpnX.setBounds(277, 157, 13, 21);
		contentPane.add(txtpnX);

		JTextPane txtpnY = new JTextPane();
		txtpnY.setText("Y");
		txtpnY.setBounds(277, 209, 13, 21);
		contentPane.add(txtpnY);

		JButton button_set = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedText());
		button_set.setBounds(365, 325, 106, 27);
		button_set.addActionListener(this);
		contentPane.add(button_set);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Status_HUD.isShow[0] = checkBox_isShowText.isSelected();
		Status_HUD.isShow[1] = checkBox_isShowSword.isSelected();
		Status_HUD.isShow[2] = checkBox_isShowBow.isSelected();
		Status_HUD.isShow[3] = checkBox_isShowDeath.isSelected();
		Status_HUD.isShow[4] = checkBox_isShowRate.isSelected();
		Status_HUD.isShow[5] = checkBox_isShowTotalRate.isSelected();
		Status_HUD.isShow[6] = checkBox_isShowGetxp.isSelected();
		Status_HUD.isShow[7] = checkBox_isShowHavexp.isSelected();
		Status_HUD.isShow[8] = checkBox_isShowRank.isSelected();
		Status_HUD.isShow[9] = checkBox_isShowRankPoint.isSelected();
		Status_HUD.isShow[10] = checkBox_isShowCurrentJob.isSelected();
		Status_HUD.isShow[11] = checkBox_isShowFPS.isSelected();
		Status_HUD.text = textField.getText();
		Status_HUD.x = (int) spinner_x.getValue();
		Status_HUD.y = (int) spinner_y.getValue();
		Rendering.isRainbow = checkBox_isRainbow.isSelected();

		Status_HUD.cfg.get("render", "isShowText", true, "ステータスの一番上に表示するテキストの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[0]);
		Status_HUD.cfg.get("render", "isShowSwordKill", true, "剣キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[1]) ;
		Status_HUD.cfg.get("render", "isShowBowKill", true, "弓キルの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[2]);
		Status_HUD.cfg.get("render", "isShowDeath", true, "デス数の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[3]);
		Status_HUD.cfg.get("render", "isShowRate", true, "K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[4]);
		Status_HUD.cfg.get("render", "isShowTotalRate", true, "総合K/Dレートの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[5]);
		Status_HUD.cfg.get("render", "isShowXP", true, "獲得xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[6]);
		Status_HUD.cfg.get("render", "isShowTotalXP", true, "所持xpの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[7]);
		Status_HUD.cfg.get("render", "isShowRank", true, "ランクの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[8]);
		Status_HUD.cfg.get("render", "isShowRankPoint", true, "ランクポイントの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[9]);
		Status_HUD.cfg.get("render", "isShowJob", true, "現在の職業の表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[10]);
		Status_HUD.cfg.get("rendering", "isShowFPS", true, "FPSの表示(true) / 非表示(false)を設定します。").set(Status_HUD.isShow[11]);
		Status_HUD.cfg.get("render", "text", "%sのステータス", "ステータスの一番上に表示するテキストを設定します。自分のプレイヤー名を使いたい場合は%sが自動的にプレイヤー名に置き換わります。").set(Status_HUD.text);
		Status_HUD.cfg.get("render", "x", 2, "ステータスの画面上のx座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.x);
		Status_HUD.cfg.get("render", "y", 2, "ステータスの画面上のy座標を設定します。", 0, Integer.MAX_VALUE).set(Status_HUD.y);
		Status_HUD.cfg.get("render", "isRainbow", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。").set(Rendering.isRainbow);
		Status_HUD.cfg.get("render", "isRenderWhenStart", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。").set(checkBox_isEnable.isSelected());
		Status_HUD.cfg.get("render", "color", 0xFF0000, "文字の色を設定します。16進数で指定してください。").set("0x" + Integer.toHexString(ColorSetting.colorcash));

		Status_HUD.color = ColorSetting.colorcash;

		Status_HUD.cfg.save();

		JOptionPane.showMessageDialog(new GuiSetting(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());
	}
}
