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
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.Font; //preenche a tabela



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
	
	private JComboBox cboOsSit = new JComboBox();
	private JRadioButton rbtOrc = new JRadioButton("Orçamento");
	private JRadioButton rbtOs = new JRadioButton("Ordem de Serviço");
	
	private JButton btnOsAdicionar = new JButton("");
	
	//VARIÁVEIS PARA ESTABELECER A CONEXÃO
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	//A VAR ABAIXO ARMAZENA UM TEXTO DE ACORDO COM O RADIO BTN SELECIONADO
	private String tipo;
	
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
				
		//AO ABRIR O FORMULÁRIO, MARCAR O RADIO BTN "ORÇAMENTO"
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				rbtOrc.setSelected(true);
				tipo = "Orçamento"; //Já seta a var com o valor "ORÇAMENTO"
			}
		});
		
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
		txtOs.setBounds(10, 36, 36, 20);
		panel.add(txtOs);
		txtOs.setColumns(10);
		
		txtData = new JTextField();
		txtData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtData.setEditable(false);
		txtData.setBounds(104, 36, 127, 20);
		panel.add(txtData);
		txtData.setColumns(10);
		
		//RADIO BUTTONS ORÇAMENTO
		rbtOrc.addActionListener(new ActionListener() {
			//Atribuindo um txt a variável (tipo) se selecionado
			public void actionPerformed(ActionEvent e) {
				tipo = "Orçamento";
			}
		});
		rbtOrc.setBounds(10, 63, 92, 23);
		panel.add(rbtOrc);
		
		//RADIO BUTTONS ORÇAMENTO ORDEM DE SERVIÇO
		rbtOs.addActionListener(new ActionListener() {
			//Atribuindo um txt a variável (tipo) se selecionado
			public void actionPerformed(ActionEvent e) {
				tipo = "Ordem de Serviço";
			}
		});
		rbtOs.setBounds(104, 63, 131, 23);
		panel.add(rbtOs);
		
		//GRUPO - SELECIONANDO UM BTN RADIO POR VEZ
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rbtOs);
		grupo.add(rbtOrc);
		
		JLabel lblNewLabel_2 = new JLabel("Situação");
		lblNewLabel_2.setBounds(261, 15, 50, 14);
		getContentPane().add(lblNewLabel_2);
		
		
		cboOsSit.setModel(new DefaultComboBoxModel(new String[] {"Na bancada", "Entrega OK", "Orçamento reprovado", "aguardando aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou"}));
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
		
		JLabel lblNewLabel_5 = new JLabel("*Equipamento");
		lblNewLabel_5.setBounds(10, 306, 76, 14);
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
		txtOSValor.setText("0");
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
		
		//CHAMA O MÉTODO EMITIR ORDEM DE SERVIÇO
		
		btnOsAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitir_os();
			}
		});
		btnOsAdicionar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnOsAdicionar.setBounds(20, 428, 76, 76);
		getContentPane().add(btnOsAdicionar);
		
		//CHAMA O MÉTODO ALTERAR ORDEM DE SERVIÇO
		JButton btnOsAlterar = new JButton("");
		btnOsAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar_os();
			}
		});
		btnOsAlterar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/update.png")));
		btnOsAlterar.setBounds(216, 428, 76, 76);
		getContentPane().add(btnOsAlterar);
		
		JButton btnOsRemover = new JButton("");
		btnOsRemover.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/delete.png")));
		btnOsRemover.setBounds(316, 428, 76, 76);
		getContentPane().add(btnOsRemover);
		
		//CHAMA O MÉTODO DE PESQUISA DE ORDEM DE SERVIÇO
		JButton btnOsPesquisar = new JButton("");
		btnOsPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisa_ordem_servico();
			}
		});
		btnOsPesquisar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/icones/read.png")));
		btnOsPesquisar.setBounds(118, 428, 76, 76);
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
	
	//CADASTRA UMA ORDEM DE SERVIÇO
	private void emitir_os(){
		String sql = "insert into ordemservico (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idCli)values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtOsEquipe.getText());
			pst.setString(4, txtOsDef.getText());
			pst.setString(5, txtOsServ.getText());
			pst.setString(6, txtOsTec.getText());
			pst.setString(7, txtOSValor.getText().replace(",", ".")); //.replace substitui a virgula pelo ponto para não dar erro caso o usuário coloque uma virgula no campo valor
			pst.setString(8, txtCliId.getText());
			
			//VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS
			if((txtCliId.getText().isEmpty() || txtOsEquipe.getText().isEmpty() || txtOsDef.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");				
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Ordem de serviço emitda com sucesso!");
					limpar();
				}
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//PESQUISA UMA ORDEM DE SERVICO
	private void pesquisa_ordem_servico() {
		//CRIA UMA CAIXA DE ENTRADA TIPO JOption Pane
		String num_os = JOptionPane.showInputDialog("Digite o número da OS "); //RECEBE O NUMERO DA OS
		
		String slq = "Select * from ordemservico where oS = " + num_os;
		
		try {
			pst = conexao.prepareStatement(slq);
			rs = pst.executeQuery(); // MOSTRA O RESULTADO NO FORMULÁRIO
			
			if(rs.next()) {
				
				txtOs.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				
				//SETANDO OS RADIO BTN
				String rbt_tipo = rs.getString(3);
				if(rbt_tipo.equals("Ordem de Serviço")) {
					rbtOs.setSelected(true);
					tipo = "Ordem de serviço";
				}else {
					rbtOrc.setSelected(true);
					tipo = "Orçamento";
				}
				
				cboOsSit.setSelectedItem(rs.getString(4)); //PEGANDO O VALOR DA CAIXA DE OPÇÕES "SITUAÇÃO"
				txtOsEquipe.setText(rs.getString(5));
				txtOsDef.setText(rs.getString(6));
				txtOsServ.setText(rs.getString(7));
				txtOsTec.setText(rs.getString(8));
				txtOSValor.setText(rs.getString(9));
				txtCliId.setText(rs.getString(10));
				
				//DESABILITA O BOTÃO ADICIONAR
				btnOsAdicionar.setEnabled(false);
				
				//DESABILITA O CAMPO PESQUISAR
				txtCliPesquisar.setEnabled(false);
				
				//DESABILITA A VISIBILIDADE DA TABELA CLIENTES
				tblClientes.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Ordem de serviço não cadastrada");
			}
				//TRATA A MENSAGEM DE ERRO CASO O USUÁRIO DIGITE UMA LETRA
		} catch (java.sql.SQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "OS inválida");
		
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
			
		}
	}
	
	//ALTERA UMA ORDEM DE SERVIÇO
	private void alterar_os(){
		String sql = "update ordemservico set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where oS = ?";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtOsEquipe.getText());
			pst.setString(4, txtOsDef.getText());
			pst.setString(5, txtOsServ.getText());
			pst.setString(6, txtOsTec.getText());
			pst.setString(7, txtOSValor.getText().replace(",", ".")); //.replace substitui a virgula pelo ponto para não dar erro caso o usuário coloque uma virgula no campo valor
			pst.setString(8, txtOs.getText());
			
			//VALIDAÇÃO DOS CAMPOS OBRIGATÓRIOS
			if((txtCliId.getText().isEmpty() || txtOsEquipe.getText().isEmpty() || txtOsDef.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");				
			}else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Ordem de serviço alterada com sucesso!");
					
					//LIMPA OS CAMPOS
					txtCliId.setText(null);
					txtData.setText(null);
					limpar();
					
					//HABILITA O BOTÃO ADICIONAR
					btnOsAdicionar.setEnabled(true);
					
					//HABILITA O CAMPO PESQUISAR
					txtCliPesquisar.setEnabled(true);
					
					//HABILITA A VISIBILIDADE DA TABELA CLIENTES
					tblClientes.setVisible(true);
				}
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	//LIMPA OS CAMPOS
	private void limpar() {
		txtCliId.setText(null);
		txtOsEquipe.setText(null);
		txtOsDef.setText(null);
		txtOsServ.setText(null);
		txtOsTec.setText(null);
		txtOSValor.setText(null);
	}
}
