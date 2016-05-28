package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.Window.Type;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Label;


public class CS_GUI_findPatient extends JFrame {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5798215983453009657L;
	
	private JPanel contentPane;
	private JButton btnCancel;
	private JButton findPatient;
	private JButton btnCrtPt;
	private JLabel lblwarningMessage = null;
	private JTextField InsertPatientId;
	
	/**
	 * Create the frame.
	 */
	public CS_GUI_findPatient() {
		setResizable(false);
		setTitle("G-Health");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorGUI.class.getResource("/images/logo2.PNG")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("New label");
		label.setBounds(0, 0, 0, 0);
		contentPane.add(label);
		
		JLabel lblLogo = new JLabel("Welcome CS");
		lblLogo.setIcon(new ImageIcon(DoctorGUI.class.getResource("/images/logo2.png")));
		lblLogo.setBounds(0, 0, 794, 79);
		contentPane.add(lblLogo);
		
		findPatient = new JButton("Search Patient");
		findPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		findPatient.setBounds(202, 157, 140, 23);
		contentPane.add(findPatient);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(352, 157, 140, 23);
		contentPane.add(btnCancel);
		
		InsertPatientId = new JTextField();
		InsertPatientId.setText("insert patient id here");
		InsertPatientId.setBounds(270, 126, 137, 20);
		contentPane.add(InsertPatientId);
		InsertPatientId.setColumns(10);
		
		Label label_1 = new Label("Patient ID");
		label_1.setBounds(202, 126, 62, 22);
		contentPane.add(label_1);
		
		
		setLocationRelativeTo(null);
		
		setVisible(true);
	
	}
	
	public void setWarningMessageVisibleTrue() {
		lblwarningMessage.setVisible(true);	
	}
	
	public void setWarningMessageVisibleTrue(String st) {
		lblwarningMessage.setText(st);
		lblwarningMessage.setForeground(Color.RED);
		lblwarningMessage.setBounds(10, 165, 245, 30);
		lblwarningMessage.setVisible(true);	
		
	}
	
	
	
	public void undisplayWarningMessage() {
		lblwarningMessage.setVisible(false);
		
	}
	
	
	public void findPatientActionListener(ActionListener e)
	{
		findPatient.addActionListener(e);
	}
	
	public void addCancelActionListener(ActionListener e)
	{
		btnCancel.addActionListener(e);
	}
	
	public void createNewPtActionListener(ActionListener e)
	{
		btnCrtPt.addActionListener(e);
	}

	
	public String getPtID() {
		return InsertPatientId.getText();
	}
	
	public void addPatientOpt() {
		btnCrtPt = new JButton("CREATE NEW PATIENT?");
		btnCrtPt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCrtPt.setBounds(142, 399, 172, 68);
		contentPane.add(btnCrtPt);
		
	}


}