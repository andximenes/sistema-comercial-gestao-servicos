package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;

//FRAMEWORKS PARA REALIZAR A CONEXÃO
import java.sql.*;
//MÓDUILO DE CONEXAO
import br.com.infox.dal.ModuloConexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TelaUsuario extends JInternalFrame {
	
	private JTextField txtUsuId;
	private JTextField txtUsuNome;
	private JTextField txtUsuLogin;
	private JTextField txtUsuSenha;
	private JTextField txtUsuFone;
	private JComboBox cboUsuPerfil = new JComboBox();
	
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
					TelaUsuario frame = new TelaUsuario();
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
	public TelaUsuario() {

		conexao = ModuloConexao.conector();
		
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setPreferredSize(new Dimension(530, 607));
		setTitle("Usuários");
		setBounds(100, 100, 540, 580);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("* Id");
		lblNewLabel.setBounds(27, 42, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("* Nome");
		lblNewLabel_1.setBounds(27, 89, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("* Login");
		lblNewLabel_2.setBounds(247, 143, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("* Senha");
		lblNewLabel_3.setBounds(27, 198, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("* Perfil");
		lblNewLabel_4.setBounds(247, 198, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtUsuId = new JTextField();
		txtUsuId.setBounds(66, 39, 144, 20);
		getContentPane().add(txtUsuId);
		txtUsuId.setColumns(10);
		
		txtUsuNome = new JTextField();
		txtUsuNome.setBounds(66, 86, 373, 20);
		getContentPane().add(txtUsuNome);
		txtUsuNome.setColumns(10);
		
		txtUsuLogin = new JTextField();
		txtUsuLogin.setBounds(288, 140, 151, 20);
		getContentPane().add(txtUsuLogin);
		txtUsuLogin.setColumns(10);
		
		txtUsuSenha = new JTextField();
		txtUsuSenha.setBounds(66, 191, 144, 20);
		getContentPane().add(txtUsuSenha);
		txtUsuSenha.setColumns(10);
		
		
		cboUsuPerfil.setModel(new DefaultComboBoxModel(new String[] {"admin", "user"}));
		cboUsuPerfil.setBounds(288, 194, 151, 22);
		getContentPane().add(cboUsuPerfil);
		
		JLabel lblNewLabel_5 = new JLabel("Fone");
		lblNewLabel_5.setBounds(27, 140, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtUsuFone = new JTextField();
		txtUsuFone.setBounds(66, 137, 144, 20);
		getContentPane().add(txtUsuFone);
		txtUsuFone.setColumns(10);
		
		//CHAMANDO O MÉTODO ADCIONAR
		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		
		btnUsuCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuCreate.setToolTipText("Adcionar");
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/icones/create.png")));
		btnUsuCreate.setPreferredSize(new Dimension(80, 80));
		btnUsuCreate.setBounds(27, 314, 89, 85);
		getContentPane().add(btnUsuCreate);
		
		//CHAMANDO O MÉTODO CONSULTAR
		JButton btnUsuRead = new JButton("");
		btnUsuRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultar();
			}
		});
		
		btnUsuRead.setToolTipText("Pesquisar");
		btnUsuRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuRead.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/icones/read.png")));
		btnUsuRead.setPreferredSize(new Dimension(80, 80));
		btnUsuRead.setBounds(148, 314, 89, 85);
		getContentPane().add(btnUsuRead);
		
		//CHAMANDO O MÉTODO ALTERAR
		JButton betUsuUpdate = new JButton("");
		betUsuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		betUsuUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		betUsuUpdate.setToolTipText("Alterar");
		betUsuUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/icones/update.png")));
		betUsuUpdate.setPreferredSize(new Dimension(80, 80));
		betUsuUpdate.setBounds(269, 314, 89, 85);
		getContentPane().add(betUsuUpdate);
		
		//CHAMANDO O MÉTODO REMOVER
		JButton btnUsuDelete = new JButton("");
		btnUsuDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnUsuDelete.setToolTipText("Remover");
		btnUsuDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/icones/delete.png")));
		btnUsuDelete.setPreferredSize(new Dimension(80, 80));
		btnUsuDelete.setBounds(393, 314, 89, 85);
		getContentPane().add(btnUsuDelete);
		
		JLabel lblNewLabel_6 = new JLabel("* Campos obrigatórios");
		lblNewLabel_6.setBounds(332, 42, 107, 14);
		getContentPane().add(lblNewLabel_6);
		
		
	}
		
	//METODO CONSULTAR USUÁRIO
	private void consultar() {
		String sql = "select * from usuarios where iduser = ?";
		//TESTA A CONEXAO 
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuId.getText());
			rs = pst.executeQuery();
			
			if (rs.next()) {
				txtUsuNome.setText(rs.getString(2));
				txtUsuFone.setText(rs.getString(3));
				txtUsuLogin.setText(rs.getString(4));
				txtUsuSenha.setText(rs.getString(5));
				//Referente ao combobox
				cboUsuPerfil.setSelectedItem(rs.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
				//Limpam os campos
				txtUsuNome.setText(null);
				txtUsuFone.setText(null);
				txtUsuLogin.setText(null);
				txtUsuSenha.setText(null);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//MÉTODO ADCIONAR USUÁRIOS
	private void adicionar() {
		String sql = "insert into usuarios (iduser, usuario, fone, login, senha, perfil) values (?, ?, ?, ?, ?, ?)";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuId.getText());
			pst.setString(2, txtUsuNome.getText());
			pst.setString(3, txtUsuFone.getText());
			pst.setString(4, txtUsuLogin.getText());
			pst.setString(5, txtUsuSenha.getText());
			pst.setString(6, cboUsuPerfil.getSelectedItem().toString()); //converte para string
			//validação dos campos obrigatórios
			if((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios (*)");
			} else {
				//Atualiza a tabela usuário com os dados do formulário
				//A estrutura abaixo confirma a inserção dos dados na tabela
				int adicionado = pst.executeUpdate();
				//A linha abaixo serve de entendimento da lógica
				//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
					//Limpam os campos
					limpar();
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//MÉTODO ALTERAR USUÁRIOS
	private void alterar() {
		String sql = "update usuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
		
		try {
			pst = conexao.prepareStatement(sql);
			
			pst.setString(1, txtUsuNome.getText());
			pst.setString(2, txtUsuFone.getText());
			pst.setString(3, txtUsuLogin.getText());
			pst.setString(4, txtUsuSenha.getText());
			pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
			pst.setString(6, txtUsuId.getText());
			
			if((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios (*)");
			} else {
				//Atualiza a tabela usuário com os dados do formulário
				//A estrutura abaixo altera os dados na tabela
				int adicionado = pst.executeUpdate();
				//A linha abaixo serve de entendimento da lógica
				//System.out.println(adicionado);
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do Usuário alterados com sucesso");
					//Limpam os campos
					limpar();
				}
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//MÉTODO REMOVER USUÁRIO
	private void remover() {
		//confiar a remoção do usuário
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário", "Atenção", JOptionPane.YES_NO_OPTION);
		
		if(confirma == JOptionPane.YES_OPTION ) {
			String sql = "delete from usuarios where iduser=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtUsuId.getText());
				int apagado = pst.executeUpdate();
				if(apagado > 0 ) {
					JOptionPane.showMessageDialog(null, "Usuário removido com sucesso.");
					limpar();
				}
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	//LIMPA OS CAMPOS DO FORMULÁRIO
	private void limpar() {
		txtUsuId.setText(null);
		txtUsuNome.setText(null);
		txtUsuFone.setText(null);
		txtUsuLogin.setText(null);
		txtUsuSenha.setText(null);
	}
}
