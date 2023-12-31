package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.awt.Dimension;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	public static JMenu menRel = new JMenu("Relatório");
	public static JMenuItem menCadUsu = new JMenuItem("Usuários");
	public static JLabel lblUsuario = new JLabel("Usuário");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		
		setResizable(false);
		setTitle("X - Sistema para controle de serviços");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 715, 690);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menu = new JMenuBar();
		menu.setBounds(0, 0, 709, 22);
		contentPane.add(menu);
		
		JDesktopPane desktop = new JDesktopPane();
		desktop.setBounds(10, 33, 530, 607);
		contentPane.add(desktop);
		
		JMenu menCad = new JMenu("Cadastro");
		menu.add(menCad);
		
		//CHAMANDO A TELA CLIENTE
		JMenuItem menCadCli = new JMenuItem("Cliente");
		menCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente telaCliente = new TelaCliente();
				telaCliente.setVisible(true);
				desktop.add(telaCliente);
				Dimension desktopSize = desktop.getSize ();
				Dimension jInternalFrameSize = telaCliente.getSize ();
				telaCliente.setLocation ((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
			}
		});
		menCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadCli);
		
		//CHAMANDO TELA DE ORDEM DE SERVIÇO
		JMenuItem menCadOs = new JMenuItem("Ordem de Serviço");
		menCadOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOs telaOs = new TelaOs();
				telaOs.setVisible(true);
				desktop.add(telaOs);
				Dimension desktopSize = desktop.getSize ();
				Dimension jInternalFrameSize = telaOs.getSize ();
				telaOs.setLocation ((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
			}
		});
		menCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadOs);

		//CHAMANDO A TELA DE USUÁRIO
		menCadUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaUsuario telaUsuario = new TelaUsuario();
				telaUsuario.setVisible(true);
				desktop.add(telaUsuario);
				//ara centralizar o JInternalFrame na tela,
				Dimension desktopSize = desktop.getSize ();
				Dimension jInternalFrameSize = telaUsuario.getSize ();
				telaUsuario.setLocation ((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
				
			}
		});
		//MENU USUÁRIO PARAMENTROS
		menCadUsu.setEnabled(false);
		menCadUsu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		menCad.add(menCadUsu);
		
		// MENU RELATÓRIO PARAMENTROS
		menRel.setEnabled(false);
		menu.add(menRel);
		
		JMenuItem menRelSer = new JMenuItem("Serviços");
		menRelSer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		menRel.add(menRelSer);
		
		JMenu menuiAju = new JMenu("Ajuda");
		menu.add(menuiAju);
		
		JMenu menOpc = new JMenu("Opções");
		menu.add(menOpc);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/icones/x.png")));
		lblNewLabel.setBounds(533, 324, 156, 199);
		contentPane.add(lblNewLabel);
		
		// PARÂMETROS DA VAR USUÁRIO
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(533, 54, 156, 37);
		contentPane.add(lblUsuario);
		
		JLabel lblData = new JLabel("Data");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblData.setBounds(533, 102, 156, 39);
		contentPane.add(lblData);
		
		//DATA E HORA ATUAL DO SISTEMA
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				/*Substitui a lblData pela data atual do sistema*/
				
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
				
				lblData.setText(formatador.format(data));
			}
		});
	}
	
	
	
}
