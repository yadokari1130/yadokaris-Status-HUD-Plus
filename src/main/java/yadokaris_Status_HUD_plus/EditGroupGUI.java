package yadokaris_Status_HUD_plus;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentTranslation;

public class EditGroupGUI extends HasColorFrame implements ActionListener{

	private JPanel contentPane;
	private Map<String, String> idMap = new HashMap<>();
	private JTextField textField;
	private DefaultListModel<String> modelGroupStatus = new DefaultListModel<>();
	private DefaultComboBoxModel<String> modelGroups = new DefaultComboBoxModel<>();
	private DefaultComboBoxModel<String> modelStatus = new DefaultComboBoxModel<>();
	private JList<String> listGroupStatus = new DnDList<>();
	private JComboBox comboBoxGroups = new JComboBox();
	private JSpinner spinnerX = new JSpinner();
	private JSpinner spinnerY = new JSpinner();
	private JCheckBox checkBoxDoShowName = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doShowGroupName").getUnformattedText());
	private StatusGroup selectedGroup;
	private String register = new TextComponentTranslation("yadokaris_shp.setting.register").getUnformattedText();
	public static String path;
	private final JCheckBox checkBoxIsRainbow = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.isRainbow").getUnformattedText());
	private final JCheckBox checkBoxDoChangeTeamColor = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doChangeTeamColor").getUnformattedText());
	private final JCheckBox checkBoxDoRender = new JCheckBox(new TextComponentTranslation("yadokaris_shp.setting.doRender").getUnformattedText());

	/**
	 * Create the frame.
	 */
	public EditGroupGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 799);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		listGroupStatus.setModel(modelGroupStatus);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(21, 178, 498, 221);
		scrollPane.add(listGroupStatus);
		contentPane.add(scrollPane);

		modelGroups.addElement(register);
		for(StatusGroup group : Rendering.groups.values()) {
			modelGroups.addElement(group.name);
		}
		comboBoxGroups.setBounds(21, 56, 423, 25);
		comboBoxGroups.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		comboBoxGroups.setModel(modelGroups);
		comboBoxGroups.addActionListener(l -> {
			if (modelGroups.getSelectedItem().equals(register)) {
				selectedGroup = null;
				textField.setText("");
				modelGroupStatus.clear();
				spinnerX.setValue(2f);
				spinnerY.setValue(2f);
				checkBoxDoShowName.setSelected(false);
				checkBoxIsRainbow.setSelected(false);
				checkBoxDoChangeTeamColor.setSelected(false);
				checkBoxDoRender.setSelected(true);
				color = -1;
			}
			else {
				selectedGroup = Rendering.groups.get(modelGroups.getSelectedItem());
				textField.setText(selectedGroup.name);
				modelGroupStatus.clear();
				for (String s : selectedGroup.statusIDs) {
					modelGroupStatus.addElement(Status.getStatus(s).getDefaultString());
				}
				spinnerX.setValue(selectedGroup.x);
				spinnerY.setValue(selectedGroup.y);
				checkBoxDoShowName.setSelected(selectedGroup.doShowName);
				checkBoxIsRainbow.setSelected(selectedGroup.isRainbow);
				checkBoxDoChangeTeamColor.setSelected(selectedGroup.doChangeTeamColor);
				checkBoxDoRender.setSelected(selectedGroup.doRender);
				color = selectedGroup.color;
			}
		});
		contentPane.add(comboBoxGroups);

		JTextPane textPane = new JTextPane();
		textPane.setText(new TextComponentTranslation("yadokaris_shp.setting.settingName").getUnformattedText());
		textPane.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(21, 21, 499, 25);
		contentPane.add(textPane);

		textField = new JTextField();
		textField.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(21, 91, 423, 25);
		contentPane.add(textField);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText(new TextComponentTranslation("yadokaris_shp.setting.settingStatus").getUnformattedText());
		textPane_1.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		textPane_1.setBackground(Color.WHITE);
		textPane_1.setBounds(21, 143, 499, 25);
		contentPane.add(textPane_1);

		for (Status s : Status.values()) {
			if (!s.doAddStatusGroup) continue;
			modelStatus.addElement(s.getDefaultString());
			idMap.put(s.getDefaultString(), s.getId());
		}
		JComboBox<String> comboBoxStatus = new JComboBox<String>();
		comboBoxStatus.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		comboBoxStatus.setBounds(21, 407, 498, 25);
		comboBoxStatus.setModel(modelStatus);
		contentPane.add(comboBoxStatus);

		JButton buttonRemove = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingRemove").getUnformattedText());
		buttonRemove.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		buttonRemove.setBounds(274, 442, 111, 25);
		buttonRemove.addActionListener(l -> {
			modelGroupStatus.removeElement(listGroupStatus.getSelectedValue());
		});
		contentPane.add(buttonRemove);

		JButton buttonAdd = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingAdd").getUnformattedText());
		buttonAdd.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		buttonAdd.setBounds(408, 442, 111, 25);
		buttonAdd.addActionListener(l -> {
			modelGroupStatus.addElement(modelStatus.getSelectedItem() + "");
		});
		contentPane.add(buttonAdd);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("X");
		textPane_2.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		textPane_2.setBackground(Color.WHITE);
		textPane_2.setBounds(21, 573, 79, 25);
		contentPane.add(textPane_2);

		spinnerX.setModel(new SpinnerNumberModel(new Float(2), null, null, new Float(1)));
		spinnerX.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinnerX.setBounds(21, 608, 92, 25);
		contentPane.add(spinnerX);

		spinnerY.setModel(new SpinnerNumberModel(new Float(2), null, null, new Float(1)));
		spinnerY.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		spinnerY.setBounds(180, 608, 92, 25);
		contentPane.add(spinnerY);

		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("Y");
		textPane_3.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		textPane_3.setBackground(Color.WHITE);
		textPane_3.setBounds(180, 573, 79, 25);
		contentPane.add(textPane_3);

		checkBoxDoShowName.setBackground(Color.WHITE);
		checkBoxDoShowName.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		checkBoxDoShowName.setBounds(21, 661, 498, 25);
		checkBoxDoRender.setSelected(true);
		contentPane.add(checkBoxDoShowName);

		JButton buttonSave = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingSave").getUnformattedText());
		buttonSave.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		buttonSave.setBounds(461, 725, 111, 25);
		buttonSave.addActionListener(this);
		contentPane.add(buttonSave);

		JButton buttonRemoveGroup = new JButton(new TextComponentTranslation("yadokaris_shp.setting.settingRemove").getUnformattedText());
		buttonRemoveGroup.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		buttonRemoveGroup.setBounds(461, 56, 111, 25);
		buttonRemoveGroup.addActionListener(l -> {
			if (modelGroups.getSelectedItem().equals(register)) return;

			Document doc = null;
			DocumentBuilder builder = null;
			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				try {
					doc = builder.parse(new File(path));
				}
				catch (FileNotFoundException e) {
					doc = builder.newDocument();
					e.printStackTrace();
				}
				catch (IOException | SAXException e) {
					e.printStackTrace();
				}
			}
			catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

			Element root = null;
			root = doc.getDocumentElement();
			if (root == null) {
				root = doc.createElement("groups");
				doc.appendChild(root);
			}

			StatusGroup group = Rendering.groups.get(modelGroups.getSelectedItem());
			Rendering.groups.remove(modelGroups.getSelectedItem());

			NodeList rootList = root.getChildNodes();
			for (int i = 0; i < rootList.getLength(); i++) {
				if (rootList.item(i).getChildNodes().item(0).getTextContent().equals(group.name)) root.removeChild(rootList.item(i));
			}

			try {
				Transformer tf = TransformerFactory.newInstance().newTransformer();
				tf.setOutputProperty("encoding", "UTF-8");
				tf.transform(new DOMSource(doc), new StreamResult(new File(path)));
			}
			catch (TransformerException e) {
				JOptionPane.showMessageDialog(new SettingGUI(), new TextComponentTranslation("yadokaris_shp.setting.error").getUnformattedText());
				e.printStackTrace();
			}

			modelGroups.setSelectedItem(register);
			modelGroups.removeElement(group.name);
		});
		contentPane.add(buttonRemoveGroup);

		JButton buttonColor = new JButton(new TextComponentTranslation("yadokaris_shp.setting.colorButton").getUnformattedText());
		buttonColor.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		buttonColor.setBounds(428, 609, 144, 27);
		buttonColor.addActionListener(new ColorSetting(this));
		contentPane.add(buttonColor);

		checkBoxIsRainbow.setBackground(Color.WHITE);
		checkBoxIsRainbow.setBounds(21, 486, 279, 21);
		checkBoxIsRainbow.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		contentPane.add(checkBoxIsRainbow);

		checkBoxDoChangeTeamColor.setBackground(Color.WHITE);
		checkBoxDoChangeTeamColor.setBounds(21, 509, 279, 21);
		checkBoxDoChangeTeamColor.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		contentPane.add(checkBoxDoChangeTeamColor);

		checkBoxDoRender.setBackground(Color.WHITE);
		checkBoxDoRender.setBounds(21, 532, 279, 21);
		checkBoxDoRender.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		contentPane.add(checkBoxDoRender);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Document doc = null;
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			try {
				doc = builder.parse(new File(path));
			}
			catch (FileNotFoundException e) {
				doc = builder.newDocument();
				e.printStackTrace();
			}
			catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element root = null;
		root = doc.getDocumentElement();
		if (root == null) {
			root = doc.createElement("groups");
			doc.appendChild(root);
		}

		StatusGroup group = null;
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < modelGroupStatus.getSize(); i++) ids.add(idMap.get(modelGroupStatus.get(i)));

		if (modelGroups.getSelectedItem().equals(register)) {
			group = new StatusGroup(textField.getText(), (float)spinnerX.getValue(), (float)spinnerY.getValue(), ids, checkBoxDoShowName.isSelected(), checkBoxIsRainbow.isSelected(), checkBoxDoChangeTeamColor.isSelected(), color, checkBoxDoRender.isSelected());
			selectedGroup = group;

			Rendering.groups.put(group.name, group);
			modelGroups.addElement(group.name);
			modelGroups.setSelectedItem(group.name);
		}
		else {
			group = Rendering.groups.get(modelGroups.getSelectedItem());
			Rendering.groups.remove(modelGroups.getSelectedItem());

			group.statusIDs = ids;
			group.x = (float)spinnerX.getValue();
			group.y = (float)spinnerY.getValue();
			group.doShowName = checkBoxDoShowName.isSelected();
			group.isRainbow = checkBoxIsRainbow.isSelected();
			group.doChangeTeamColor = checkBoxDoChangeTeamColor.isSelected();
			group.color = color;
			group.doRender = checkBoxDoRender.isSelected();
			String oldName = group.name;

			NodeList rootList = root.getChildNodes();
			for (int i = 0; i < rootList.getLength(); i++) {
				if (rootList.item(i).getChildNodes().item(0).getTextContent().equals(group.name)) {
					group.name = textField.getText();
					Rendering.groups.put(group.name, group);
					root.removeChild(rootList.item(i));
				}
			}

			selectedGroup = group;
			if (!oldName.equals(group.name)) {
				modelGroups.addElement(group.name);
				modelGroups.setSelectedItem(group.name);
				modelGroups.removeElement(oldName);
			}
		}

		Element elementGroup = doc.createElement("group");
		root.appendChild(elementGroup);

		Element elementName = doc.createElement("name");
		elementName.appendChild(doc.createTextNode(group.name));
		elementGroup.appendChild(elementName);

		Element elementX = doc.createElement("x");
		elementX.appendChild(doc.createTextNode(group.x + ""));
		elementGroup.appendChild(elementX);

		Element elementY = doc.createElement("y");
		elementY.appendChild(doc.createTextNode(group.y + ""));
		elementGroup.appendChild(elementY);

		Element elementDoShowName = doc.createElement("doShowName");
		elementDoShowName.appendChild(doc.createTextNode(group.doShowName + ""));
		elementGroup.appendChild(elementDoShowName);

		Element elementIDs = doc.createElement("ids");
		for (String id : ids) {
			Element elementID = doc.createElement("id");
			elementID.appendChild(doc.createTextNode(id));
			elementIDs.appendChild(elementID);
		}
		elementGroup.appendChild(elementIDs);

		Element elementIsRainbow = doc.createElement("isRainbow");
		elementIsRainbow.appendChild(doc.createTextNode(group.isRainbow + ""));
		elementGroup.appendChild(elementIsRainbow);

		Element elementDoChangeTeamColor = doc.createElement("doChangeTeamColor");
		elementDoChangeTeamColor.appendChild(doc.createTextNode(group.doChangeTeamColor + ""));
		elementGroup.appendChild(elementDoChangeTeamColor);

		Element elementColor = doc.createElement("color");
		elementColor.appendChild(doc.createTextNode(group.color + ""));
		elementGroup.appendChild(elementColor);

		Element elementDoRender = doc.createElement("doRender");
		elementDoRender.appendChild(doc.createTextNode(group.doRender + ""));
		elementGroup.appendChild(elementDoRender);

		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty("encoding", "UTF-8");
			tf.transform(new DOMSource(doc), new StreamResult(new File(path)));
		}
		catch (TransformerException e) {
			JOptionPane.showMessageDialog(new SettingGUI(), new TextComponentTranslation("yadokaris_shp.setting.error").getUnformattedText());
			e.printStackTrace();
		}
		((EntityPlayerSP)Status_HUD.player).sendChatMessage("/multiplier");

		JOptionPane.showMessageDialog(new SettingGUI(), new TextComponentTranslation("yadokaris_shp.setting.save").getUnformattedText());

	}
}
