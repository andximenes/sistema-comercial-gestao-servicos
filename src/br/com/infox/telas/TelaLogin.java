package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JLabel lblStatus;
	
	//FRAMEWORKS
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
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void logar() {
	
		String sql = "select * from usuarios where login=? and senha = ?";
		
		try {
			pst = conexao.prepareStatement(sql);
			
			//recebe os dados do usuário e senha
			pst.setString(1, txtUsuario.getText());
			String captura = new String(txtSenha.getPassword());
			pst.setString(2, captura);
			
			//executa a consulta
			rs = pst.executeQuery();
			
			//se existir usuário e senha existênte
			if(rs.next()) {
				//obtem o conteúdo do campo perfil da tabela usuários
				String perfil = rs.getString(6);
				//faz o tratamento do perfil
				if(perfil.equals("admin")) {
					//exibe o conteúdo do campo da tabela
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.menRel.setEnabled(true);
					TelaPrincipal.menCadUsu.setEnabled(true);
					TelaPrincipal.lblUsuario.setText(rs.getString(2));
					TelaPrincipal.lblUsuario.setForeground(Color.red);
					this.dispose();
					conexao.close();
				}else {
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.lblUsuario.setText(rs.getString(2));
					this.dispose();
					conexao.close();
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s)");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		
		conexao = ModuloConexao.conector();
		
		setTitle("X - Sistem - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 326, 184);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuário");
		lblNewLabel.setBounds(33, 30, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(33, 62, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(89, 27, 168, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		//CHAMA O MÉTODO LOGAR
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
			
		});
		btnLogin.setBounds(119, 110, 89, 23);
		contentPane.add(btnLogin);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(89, 59, 168, 20);
		contentPane.add(txtSenha);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/icones/dberror.png")));
		
		//MOSTRA PARA O USUÁRIO SE O BD ESTÁ CONECTADO
		if(conexao != null) {
			lblStatus.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/icones/dbok.png")));
		} else {
			lblStatus.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/icones/dberror.png")));
		}
		
		lblStatus.setBounds(33, 85, 32, 32);
		contentPane.add(lblStatus);
	}
}
