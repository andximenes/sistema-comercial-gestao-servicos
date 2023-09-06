package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
//importa recurso da biblioteca externa rs2xml.jar 
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCliente extends JInternalFrame {
	private JTextField txtCliNome;
	private JTextField txtCliEndereco;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JButton btnAdicionar = new JButton("");
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTable tblClientes;
	private JTextField txtCliId;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
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
	public TelaCliente() {
		
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		
		conexao = ModuloConexao.conector();
		
		setTitle("Clientes");
		setBounds(100, 100, 530, 607);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("* Nome");
		lblNewLabel.setBounds(22, 194, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(22, 230, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("* Telefone");
		lblNewLabel_2.setBounds(22, 255, 56, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(22, 289, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtCliNome = new JTextField();
		txtCliNome.setBounds(78, 191, 366, 20);
		getContentPane().add(txtCliNome);
		txtCliNome.setColumns(10);
		
		txtCliEndereco = new JTextField();
		txtCliEndereco.setBounds(78, 222, 366, 20);
		getContentPane().add(txtCliEndereco);
		txtCliEndereco.setColumns(10);
		
		txtCliFone = new JTextField();
		txtCliFone.setBounds(78, 253, 196, 20);
		getContentPane().add(txtCliFone);
		txtCliFone.setColumns(10);
		
		txtCliEmail = new JTextField();
		txtCliEmail.setBounds(78, 286, 366, 20);
		getContentPane().add(txtCliEmail);
		txtCliEmail.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("* Campos obrigatórios");
		lblNewLabel_7.setBounds(306, 26, 138, 14);
		getContentPane().add(lblNewLabel_7);
		
		//O EVENTO ABAIXO É DO TIPO "EQUANTO FOR DIGITANDO"
		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisar_cliente();
			}
		});
		txtCliPesquisar.setBounds(22, 23, 237, 20);
		getContentPane().add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/search.png")));
		lblNewLabel_8.setBounds(269, 11, 46, 46);
		getContentPane().add(lblNewLabel_8);
		
		//CHAMA O MÉTODO ADICIONAR
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnAdicionar.setBounds(78, 348, 89, 76);
		getContentPane().add(btnAdicionar);
		
		//CHAMA O MÉTODO ALTERAR
		JButton btnAlterar = new JButton("");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnAlterar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/update.png")));
		btnAlterar.setBounds(220, 348, 89, 76);
		getContentPane().add(btnAlterar);
		
		//CHAMA O MÉTODO REMOVER
		JButton btRemover = new JButton("");
		btRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btRemover.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/delete.png")));
		btRemover.setBounds(355, 348, 89, 76);
		getContentPane().add(btRemover);
		
		//EVENTO PARA SETAR OS CAMPOS DA TABELA CLICANDO COM O MOUSE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 54, 426, 84);
		getContentPane().add(scrollPane);
		
		//TABELA DE CLIENTES
		tblClientes = new JTable();
		//Sobrescrevendo a JTable
		tblClientes = new JTable() {
			//NÃO PERMITE A EDIÇÃO DIRETO NA TABELA
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tblClientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Nome", "Endereço", "Fone", "Email"
			}
		));
		tblClientes.getTableHeader().setReorderingAllowed(false); //desativa a reordenação de colunas da jTable 

		//CHAMA O MÉTODO SETAR_CAMPOS
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setar_campos();
			}
		});
		scrollPane.setViewportView(tblClientes);
		
		JLabel lblNewLabel_4 = new JLabel("Id Cliente");
		lblNewLabel_4.setBounds(22, 167, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtCliId = new JTextField();
		txtCliId.setEnabled(false);
		txtCliId.setBounds(78, 160, 86, 20);
		getContentPane().add(txtCliId);
		txtCliId.setColumns(10);
		

	}
	
	private void adicionar() {
		String sql = "insert into clientes (nomeCli, enderecoCli, foneCli, emailCli) values (?, ?, ?, ?)";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
	
			//validação dos campos obrigatórios
			if((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios (*)");
			} else {
				//Atualiza a tabela usuário com os dados do formulário
				//A estrutura abaixo confirma a inserção dos dados na tabela
				int adicionado = pst.executeUpdate();
				//A linha abaixo serve de entendimento da lógica
				//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
					//Limpam os campos
					limpar();
					
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	//PESQUISA CLIENTES PELO NOME COM FILTRO
	private void pesquisar_cliente() {
		String sql = "select idCli as Id, nomeCli as Nome, enderecoCli as Endereço, foneCli as Fone, emailCli as Email from clientes where nomeCli like?";
		
		try {
			pst = conexao.prepareStatement(sql);
			//Pasando o contúdo da caixa de pesquisa para o interrogação
			//Atenção ao "%" que é a continuação da string sql
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			//Usando a biblioteca rs2xml.jar para preencher a tabela
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	//SETA O CAMPO DO FORMULÁRIO COM O CONTEÚDO DA TABELA
	private void setar_campos() {
		int setar = tblClientes.getSelectedRow();
		txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
		txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
		
		//DESABILITA O BTN ADICIONAR AO SETAR OS CAMPOS DA TABELA
		btnAdicionar.setEnabled(false);
	}
	
	private void alterar() {
		String sql = "update clientes set nomeCli=?, enderecoCli=?, foneCli=?, emailCli=? where idCli=?";
		
		try {
			pst = conexao.prepareStatement(sql);
			
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
			pst.setString(5, txtCliId.getText());
			
			if((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty())){
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios (*)");
			} else {
				//Atualiza a tabela usuário com os dados do formulário
				//A estrutura abaixo altera os dados na tabela
				int adicionado = pst.executeUpdate();
				//A linha abaixo serve de entendimento da lógica
				//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do Cliente alterados com sucesso");
					//Limpam os campos
					limpar();
					//REATIVA O BTN ADICIONAR
					btnAdicionar.setEnabled(true);
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void remover() {
		//confiar a remoção do usuário
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Cliente", "Atenção", JOptionPane.YES_NO_OPTION);
		
		if(confirma == JOptionPane.YES_OPTION ) {
			String sql = "delete from clientes where idCli=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtCliId.getText());
				int apagado = pst.executeUpdate();
				if(apagado > 0 ) {
					JOptionPane.showMessageDialog(null, "Usuário removido com sucesso.");
					//Limpa os campos
					limpar();
					//REATIVA O BTN ADICIONAR
					btnAdicionar.setEnabled(true);
				}
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	private void limpar() {
		//LIMPA OS CAMPOS DO FORMULÁRIO
		txtCliPesquisar.setText(null);
		txtCliId.setText(null);
		txtCliNome.setText(null);
		txtCliEndereco.setText(null);
		txtCliFone.setText(null);
		txtCliEmail.setText(null);
		//LIMPA A JTABLE
		((DefaultTableModel) tblClientes.getModel()).setRowCount(0); 
		
	}
}
