package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.Component;

public class GUIParsing extends JFrame {

	private JPanel contentPane;
	private JTextField requestTextField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIParsing frame = new GUIParsing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIParsing() {
		setTitle("ParseKufar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel upperPanel = new JPanel();
		contentPane.add(upperPanel, BorderLayout.NORTH);
		upperPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel emptyPanelTop = new JPanel();
		emptyPanelTop.add(Box.createRigidArea(new Dimension(10, 10)));
		upperPanel.add(emptyPanelTop, BorderLayout.NORTH);
		
		JPanel infoPanel = new JPanel();
		upperPanel.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		
		JLabel goodsNameLabel = new JLabel("\u041D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435");
		goodsNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		
		requestTextField = new JTextField();
		requestTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		requestTextField.setColumns(10);
		
		JComboBox<String> categoryComboBox = new JComboBox<String>();
		categoryComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		categoryComboBox.setFocusable(false);
		categoryComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"\u041D\u0435\u0434\u0432\u0438\u0436\u0438\u043C\u043E\u0441\u0442\u044C",
				"\u0410\u0432\u0442\u043E \u0438 \u0442\u0440\u0430\u043D\u0441\u043F\u043E\u0440\u0442",
				"\u0411\u044B\u0442\u043E\u0432\u0430\u044F \u0442\u0435\u0445\u043D\u0438\u043A\u0430",
				"\u041A\u043E\u043C\u043F\u044C\u044E\u0442\u0435\u0440\u043D\u0430\u044F \u0442\u0435\u0445\u043D\u0438\u043A\u0430",
				"\u0422\u0435\u043B\u0435\u0444\u043E\u043D\u044B \u0438 \u043F\u043B\u0430\u043D\u0448\u0435\u0442\u044B",
				"\u042D\u043B\u0435\u043A\u0442\u0440\u043E\u043D\u0438\u043A\u0430",
				"\u0416\u0435\u043D\u0441\u043A\u0438\u0439 \u0433\u0430\u0440\u0434\u0435\u0440\u043E\u0431",
				"\u041C\u0443\u0436\u0441\u043A\u043E\u0439 \u0433\u0430\u0440\u0434\u0435\u0440\u043E\u0431",
				"\u041A\u0440\u0430\u0441\u043E\u0442\u0430 \u0438 \u0437\u0434\u043E\u0440\u043E\u0432\u044C\u0435",
				"\u0412\u0441\u0451 \u0434\u043B\u044F \u0434\u0435\u0442\u0435\u0439 \u0438 \u043C\u0430\u043C",
				"\u041C\u0435\u0431\u0435\u043B\u044C", "\u0412\u0441\u0435 \u0434\u043B\u044F \u0434\u043E\u043C\u0430",
				"\u0420\u0435\u043C\u043E\u043D\u0442 \u0438 \u0441\u0442\u0440\u043E\u0439\u043A\u0430",
				"\u0421\u0430\u0434 \u0438 \u043E\u0433\u043E\u0440\u043E\u0434",
				"\u0425\u043E\u0431\u0431\u0438, \u0441\u043F\u043E\u0440\u0442 \u0438 \u0442\u0443\u0440\u0438\u0437\u043C",
				"\u0421\u0432\u0430\u0434\u044C\u0431\u0430 \u0438 \u043F\u0440\u0430\u0437\u0434\u043D\u0438\u043A\u0438",
				"\u0416\u0438\u0432\u043E\u0442\u043D\u044B\u0435",
				"\u0413\u043E\u0442\u043E\u0432\u044B\u0439 \u0431\u0438\u0437\u043D\u0435\u0441 \u0438 \u043E\u0431\u043E\u0440\u0443\u0434\u043E\u0432\u0430\u043D\u0438\u0435",
				"\u0420\u0430\u0431\u043E\u0442\u0430",
				"\u0423\u0441\u043B\u0443\u0433\u0438",
				"\u041F\u0440\u043E\u0447\u0435\u0435"}));
		
		JComboBox<String> locationComboBox = new JComboBox<String>();
		locationComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		locationComboBox.setFocusable(false);
		locationComboBox.setModel(new DefaultComboBoxModel(new String[] {"\u0412\u0441\u044F \u0411\u0435\u043B\u0430\u0440\u0443\u0441\u044C", "\u0411\u0440\u0435\u0441\u0442\u043A\u0430\u044F \u043E\u0431\u043B.", "\u0412\u0438\u0442\u0435\u0431\u0441\u043A\u0430\u044F \u043E\u0431\u043B.", "\u0413\u043E\u043C\u0435\u043B\u044C\u0441\u043A\u0430\u044F \u043E\u0431\u043B.", "\u0413\u0440\u043E\u0434\u043D\u0435\u043D\u0441\u043A\u0430\u044F \u043E\u0431\u043B.", "\u041C\u043E\u0433\u0438\u043B\u0435\u0432\u0441\u043A\u0430\u044F \u043E\u0431\u043B.", "\u041C\u0438\u043D\u0441\u043A", "\u041C\u0438\u043D\u0441\u043A\u0430\u044F \u043E\u0431\u043B."}));
		
		JButton searchButton = new JButton("\u0418\u0441\u043A\u0430\u0442\u044C!");
		searchButton.setFocusable(false);
		searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		searchButton.addActionListener((e) -> {

		});
		
		infoPanel.add(Box.createRigidArea(new Dimension(70, 5)));
		infoPanel.add(goodsNameLabel);
		infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		infoPanel.add(requestTextField);
		infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		infoPanel.add(categoryComboBox);
		infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		infoPanel.add(locationComboBox);
		infoPanel.add(Box.createRigidArea(new Dimension(10, 5)));
		infoPanel.add(searchButton);
		infoPanel.add(Box.createRigidArea(new Dimension(70, 5)));
		
		JPanel emptyPanelBottom = new JPanel();
		upperPanel.add(emptyPanelBottom, BorderLayout.SOUTH);
		
		Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
		emptyPanelBottom.add(rigidArea);
		
		JPanel centralPanel = new JPanel();
		contentPane.add(centralPanel, BorderLayout.CENTER);
		centralPanel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{1000, "1", "1", "1", "1", "1"},
			},
			new String[] {
				"id", "\u0418\u043C\u044F \u0442\u043E\u0432\u0430\u0440\u0430", "\u0426\u0435\u043D\u0430", "\u041C\u0435\u0441\u0442\u043E \u043F\u0440\u043E\u0434\u0430\u0436\u0438", "\u041F\u0440\u043E\u0434\u0430\u0432\u0435\u0446", "\u0421\u0441\u044B\u043B\u043A\u0430"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMinWidth(25);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(600);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.getColumnModel().getColumn(1).setMaxWidth(800);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setMinWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(400);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setMinWidth(80);
		table.getColumnModel().getColumn(4).setMaxWidth(200);
		table.getColumnModel().getColumn(5).setMinWidth(50);
		table.getColumnModel().getColumn(5).setMaxWidth(150);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		JScrollPane scrollPane = new JScrollPane(table);
		centralPanel.add(scrollPane);
	}

}
