package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


public class TelaOs extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOs frame = new TelaOs();
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
	public TelaOs() {
		setTitle("OS");
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setBounds(100, 100, 530, 607);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 7, 241, 105);
		panel.setLayout(null); //Permite que JLabel seja movimentada dentro do Panel
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Nº OS");
		lblNewLabel.setBounds(10, 11, 62, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setBounds(104, 11, 86, 14);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 36, 62, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(104, 36, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		//RADIO BUTTONS
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Orçamento");
		rdbtnNewRadioButton.setBounds(10, 63, 92, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Ordem de Serviço");
		rdbtnNewRadioButton_1.setBounds(104, 63, 131, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		//GRUPO - SELECIONANDO UM BTN RADIO POR VEZ
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rdbtnNewRadioButton_1);
		grupo.add(rdbtnNewRadioButton);
		
		JLabel lblNewLabel_2 = new JLabel("Situação");
		lblNewLabel_2.setBounds(261, 15, 50, 14);
		getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Entrega OK", "Orçamento reprovado", "aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Na bancada", "Retornou"}));
		comboBox.setBounds(313, 11, 184, 22);
		getContentPane().add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 132, 476, 150);
		panel_1.setLayout(null);
		panel_1.setBorder(BorderFactory.createTitledBorder("Cliente"));
		getContentPane().add(panel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 34, 154, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/search.png")));
		lblNewLabel_3.setBounds(174, 11, 46, 58);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("*Id");
		lblNewLabel_4.setBounds(230, 37, 46, 14);
		panel_1.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(263, 34, 189, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 442, 76);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Id", "Nome", "Fone"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_5 = new JLabel("Equipamento");
		lblNewLabel_5.setBounds(20, 293, 76, 14);
		getContentPane().add(lblNewLabel_5);
		
		textField_4 = new JTextField();
		textField_4.setBounds(89, 290, 86, 20);
		getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("*Defeito");
		lblNewLabel_6.setBounds(20, 331, 46, 14);
		getContentPane().add(lblNewLabel_6);
		
		textField_5 = new JTextField();
		textField_5.setBounds(89, 328, 379, 20);
		getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Valor Total");
		lblNewLabel_7.setBounds(341, 293, 51, 14);
		getContentPane().add(lblNewLabel_7);
		
		textField_6 = new JTextField();
		textField_6.setBounds(402, 290, 66, 20);
		getContentPane().add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Serviço");
		lblNewLabel_8.setBounds(20, 359, 46, 14);
		getContentPane().add(lblNewLabel_8);
		
		textField_7 = new JTextField();
		textField_7.setBounds(89, 359, 379, 20);
		getContentPane().add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Técnico");
		lblNewLabel_9.setBounds(185, 293, 46, 14);
		getContentPane().add(lblNewLabel_9);
		
		textField_8 = new JTextField();
		textField_8.setBounds(230, 290, 101, 20);
		getContentPane().add(textField_8);
		textField_8.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnNewButton.setBounds(20, 384, 76, 76);
		getContentPane().add(btnNewButton);
		table.getTableHeader().setReorderingAllowed(false); //desativa a reordenação de colunas da jTable 
		
		
		

	}
}
