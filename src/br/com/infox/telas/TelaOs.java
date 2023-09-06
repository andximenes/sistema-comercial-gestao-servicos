package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; //preenche a tabela



public class TelaOs extends JInternalFrame {
	private JTextField txtOs;
	private JTextField txtData;
	private JTextField txtCliPesquisar;
	private JTextField txtCliId;
	private JTable tblClientes;
	private JTextField txtOsEquipe;
	private JTextField txtOsDef;
	private JTextField txtOSValor;
	private JTextField txtOsServ;
	private JTextField txtOsTec;
	
	//VARIÁVEIS PARA ESTABELECER A CONEXÃO
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
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
		
		conexao = ModuloConexao.conector();
		
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
		
		txtOs = new JTextField();
		txtOs.setEditable(false);
		txtOs.setBounds(10, 36, 62, 20);
		panel.add(txtOs);
		txtOs.setColumns(10);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(104, 36, 86, 20);
		panel.add(txtData);
		txtData.setColumns(10);
		
		//RADIO BUTTONS
		JRadioButton rbtOrc = new JRadioButton("Orçamento");
		rbtOrc.setBounds(10, 63, 92, 23);
		panel.add(rbtOrc);
		
		JRadioButton rbtOs = new JRadioButton("Ordem de Serviço");
		rbtOs.setBounds(104, 63, 131, 23);
		panel.add(rbtOs);
		
		//GRUPO - SELECIONANDO UM BTN RADIO POR VEZ
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rbtOs);
		grupo.add(rbtOrc);
		
		JLabel lblNewLabel_2 = new JLabel("Situação");
		lblNewLabel_2.setBounds(261, 15, 50, 14);
		getContentPane().add(lblNewLabel_2);
		
		JComboBox cboOsSit = new JComboBox();
		cboOsSit.setModel(new DefaultComboBoxModel(new String[] {"Entrega OK", "Orçamento reprovado", "aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Na bancada", "Retornou"}));
		cboOsSit.setBounds(313, 11, 184, 22);
		getContentPane().add(cboOsSit);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 132, 487, 150);
		panel_1.setLayout(null);
		panel_1.setBorder(BorderFactory.createTitledBorder("Cliente"));
		getContentPane().add(panel_1);
		
		//O EVENTO ABAIXO É DO TIPO "EQUANTO FOR DIGITANDO"
		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisar_cliente();
			}
		});
		txtCliPesquisar.setBounds(10, 34, 154, 20);
		panel_1.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/search.png")));
		lblNewLabel_3.setBounds(174, 11, 46, 58);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("*Id");
		lblNewLabel_4.setBounds(230, 37, 46, 14);
		panel_1.add(lblNewLabel_4);
		
		txtCliId = new JTextField();
		txtCliId.setEditable(false);
		txtCliId.setBounds(263, 34, 203, 20);
		panel_1.add(txtCliId);
		txtCliId.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 456, 76);
		panel_1.add(scrollPane);
		
		
		tblClientes = new JTable();
		//Sobrescrevendo a JTable
		tblClientes = new JTable() {
			//NÃO PERMITE A EDIÇÃO DIRETO NA TABELA
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
		//CHAMANDO O MÉTODO SETAR CAMPOS
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setar_campos();
			}
		});
		
		tblClientes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Id", "Nome", "Fone"
			}
		));
		scrollPane.setViewportView(tblClientes);
		
		JLabel lblNewLabel_5 = new JLabel("Equipamento");
		lblNewLabel_5.setBounds(20, 306, 76, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtOsEquipe = new JTextField();
		txtOsEquipe.setBounds(89, 303, 86, 20);
		getContentPane().add(txtOsEquipe);
		txtOsEquipe.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("*Defeito");
		lblNewLabel_6.setBounds(20, 344, 46, 14);
		getContentPane().add(lblNewLabel_6);
		
		txtOsDef = new JTextField();
		txtOsDef.setBounds(89, 341, 379, 20);
		getContentPane().add(txtOsDef);
		txtOsDef.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Valor Total");
		lblNewLabel_7.setBounds(341, 306, 51, 14);
		getContentPane().add(lblNewLabel_7);
		
		txtOSValor = new JTextField();
		txtOSValor.setBounds(402, 303, 66, 20);
		getContentPane().add(txtOSValor);
		txtOSValor.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Serviço");
		lblNewLabel_8.setBounds(20, 372, 46, 14);
		getContentPane().add(lblNewLabel_8);
		
		txtOsServ = new JTextField();
		txtOsServ.setBounds(89, 372, 379, 20);
		getContentPane().add(txtOsServ);
		txtOsServ.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Técnico");
		lblNewLabel_9.setBounds(185, 306, 46, 14);
		getContentPane().add(lblNewLabel_9);
		
		txtOsTec = new JTextField();
		txtOsTec.setBounds(230, 303, 101, 20);
		getContentPane().add(txtOsTec);
		txtOsTec.setColumns(10);
		
		JButton btnOsAdicionar = new JButton("");
		btnOsAdicionar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnOsAdicionar.setBounds(20, 428, 76, 76);
		getContentPane().add(btnOsAdicionar);
		
		JButton btnOsAlterar = new JButton("");
		btnOsAlterar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/update.png")));
		btnOsAlterar.setBounds(117, 428, 76, 76);
		getContentPane().add(btnOsAlterar);
		
		JButton btnOsRemover = new JButton("");
		btnOsRemover.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/delete.png")));
		btnOsRemover.setBounds(218, 428, 76, 76);
		getContentPane().add(btnOsRemover);
		
		JButton btnOsPesquisar = new JButton("");
		btnOsPesquisar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/read.png")));
		btnOsPesquisar.setBounds(313, 428, 76, 76);
		getContentPane().add(btnOsPesquisar);
		
		JButton btnOsImprimir = new JButton("");
		btnOsImprimir.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/print.png")));
		btnOsImprimir.setBounds(410, 428, 76, 76);
		getContentPane().add(btnOsImprimir);
		tblClientes.getTableHeader().setReorderingAllowed(false); //desativa a reordenação de colunas da jTable 
		
	}
	
	//PESQUISA CLIENTES PELO NOME COM FILTRO
	private void pesquisar_cliente() {
		String sql = "select idCli as Id, nomeCli as Nome, foneCli as Fone from clientes where nomeCli like ?";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			//Usando a biblioteca rs2xml.jar para preencher a tabela
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//SETA O CAMPO ID COM O ID DO CLIENTE CLICADO
	private void setar_campos() {
		int setar = tblClientes.getSelectedRow();
		txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
	}
	
	
}
