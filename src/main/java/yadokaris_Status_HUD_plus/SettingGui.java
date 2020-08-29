package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.EventQueue;
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

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentTranslation;

public class SettingGUI extends JFrame implements ActionListener {

	private final JPanel contentPane;
	private final JCheckBox checkBoxIsEnable = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doEnable").getUnformattedComponentText());
	private final JCheckBox checkBoxDoChangeTeamColor = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doChangeTeamColor").getUnformattedComponentText());
	private final JSpinner spinnerSize = new JSpinner();
	private final JTextField textField = new JTextField();
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedComponentText());

	public SettingGUI() {
		setTitle("Status HUD Plus Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton buttonColor = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedComponentText());
		buttonColor.setBounds(328, 264, 144, 27);
		buttonColor.addActionListener(new ColorSetting());
		contentPane.add(buttonColor);

		checkBoxIsEnable.setBackground(Color.WHITE);
		checkBoxIsEnable.setSelected(Status_HUD.doRenderWhenStart);
		checkBoxIsEnable.setBounds(22, 18, 254, 21);
		contentPane.add(checkBoxIsEnable);

		spinnerSize.setModel(new SpinnerNumberModel(1, 0, 100, 0.01));
		spinnerSize.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinnerSize.setValue(Status_HUD.fontSize);
		spinnerSize.setBounds(22, 175, 157, 27);
		contentPane.add(spinnerSize);

		textField.setText(Status_HUD.text);
		textField.setBounds(22, 83, 254, 27);
		textField.setColumns(10);
		contentPane.add(textField);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setSelected(Status_HUD.isRainbow);
		checkBoxIsRainbow.setBounds(22, 244, 279, 21);
		contentPane.add(checkBoxIsRainbow);

		checkBoxDoChangeTeamColor.setSelected(Status_HUD.doChangeTeamColor);
		checkBoxDoChangeTeamColor.setBackground(Color.WHITE);
		checkBoxDoChangeTeamColor.setBounds(22, 267, 279, 21);
		contentPane.add(checkBoxDoChangeTeamColor);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingText").getUnformattedComponentText());
		textPane.setBounds(22, 52, 450, 21);
		contentPane.add(textPane);

		JButton buttonSave = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedComponentText());
		buttonSave.setBounds(366, 424, 106, 27);
		buttonSave.addActionListener(this);
		contentPane.add(buttonSave);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText(new TextComponentTranslation("yadokaris_shp.setting.settingFontSize").getUnformattedComponentText());
		textPane_2.setBounds(22, 144, 123, 21);
		contentPane.add(textPane_2);

		JButton buttonEdit = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingEdit").getUnformattedComponentText());
		buttonEdit.setBounds(22, 371, 106, 27);
		buttonEdit.addActionListener(l -> {
			EventQueue.invokeLater(() -> {
				try {
					EditGroupGUI frame = new EditGroupGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		contentPane.add(buttonEdit);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingGroup").getUnformattedComponentText());
		textPane_1.setBounds(22, 340, 450, 21);
		contentPane.add(textPane_1);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Status_HUD.text = textField.getText();
		Status_HUD.fontSize = (double) spinnerSize.getValue();
		Status_HUD.isRainbow = checkBoxIsRainbow.isSelected();
		Status_HUD.doChangeTeamColor = checkBoxDoChangeTeamColor.isSelected();
		if (Status_HUD.doChangeTeamColor && ChatEvent.TEAMS.containsKey(Status.Team.value)) Status_HUD.color = ChatEvent.TEAMS.get(Status.Team.value);
		else Status_HUD.color = Status_HUD.colorCash;

		Status_HUD.conf.get("render", "fontSize", 1, "ステータスの文字サイズを設定します。", 0, 100).set(Status_HUD.fontSize);
		Status_HUD.conf.get("render", "isRainbow", false, "ステータスの文字を虹色にする(true) / しない(false)を設定します。").set(Status_HUD.isRainbow);
		Status_HUD.conf.get("render", "doChangeTeamColor", false, "テキストの色を所属チームに合わせて変える(true) / 変えない(false)を設定します。").set(Status_HUD.doChangeTeamColor);
		Status_HUD.conf.get("render", "doRenderWhenStart", true, "起動時のステータスの表示(true) / 非表示(false)を設定します。").set(checkBoxIsEnable.isSelected());
		Status_HUD.conf.get("render", "color", 0xFF0000, "文字の色を設定します。16進数で指定してください。").set("0x" + Integer.toHexString(Status_HUD.colorCash));

		Status_HUD.conf.save();
		if (Status_HUD.player != null) ((EntityPlayerSP)Status_HUD.player).sendChatMessage("/multiplier");

		JOptionPane.showMessageDialog(new SettingGUI(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());
	}
}
