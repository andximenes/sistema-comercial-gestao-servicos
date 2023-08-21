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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCliente extends JInternalFrame {
	private JTextField txtCliNome;
	private JTextField txtCliEndereco;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	
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
		
		conexao = ModuloConexao.conector();
		
		setTitle("Clientes");
		setPreferredSize(new Dimension(523, 490));
		setBounds(100, 100, 523, 490);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("* Nome");
		lblNewLabel.setBounds(22, 165, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(22, 206, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("* Telefone");
		lblNewLabel_2.setBounds(22, 245, 56, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(22, 284, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtCliNome = new JTextField();
		txtCliNome.setBounds(78, 162, 366, 20);
		getContentPane().add(txtCliNome);
		txtCliNome.setColumns(10);
		
		txtCliEndereco = new JTextField();
		txtCliEndereco.setBounds(78, 203, 366, 20);
		getContentPane().add(txtCliEndereco);
		txtCliEndereco.setColumns(10);
		
		txtCliFone = new JTextField();
		txtCliFone.setBounds(78, 242, 196, 20);
		getContentPane().add(txtCliFone);
		txtCliFone.setColumns(10);
		
		txtCliEmail = new JTextField();
		txtCliEmail.setBounds(78, 281, 366, 20);
		getContentPane().add(txtCliEmail);
		txtCliEmail.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("* Campos obrigatórios");
		lblNewLabel_7.setBounds(325, 26, 119, 14);
		getContentPane().add(lblNewLabel_7);
		
		txtCliPesquisar = new JTextField();
		txtCliPesquisar.setBounds(22, 23, 237, 20);
		getContentPane().add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/search.png")));
		lblNewLabel_8.setBounds(269, 11, 46, 46);
		getContentPane().add(lblNewLabel_8);
		
		tblClientes = new JTable();
		tblClientes.setBounds(27, 70, 417, 72);
		getContentPane().add(tblClientes);
		
		//CHAMA O MÉTODO ADICIONAR
		JButton btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnAdicionar.setBounds(78, 348, 89, 76);
		getContentPane().add(btnAdicionar);
		
		JButton btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/update.png")));
		btnAlterar.setBounds(220, 348, 89, 76);
		getContentPane().add(btnAlterar);
		
		JButton btRemover = new JButton("");
		btRemover.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/icones/delete.png")));
		btRemover.setBounds(355, 348, 89, 76);
		getContentPane().add(btRemover);
		

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
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);
					
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
