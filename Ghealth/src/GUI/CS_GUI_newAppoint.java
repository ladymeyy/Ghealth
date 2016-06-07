package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import client.LoginControl;

import javax.swing.JTextField;

import java.awt.Window.Type;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Label;
import javax.swing.JComboBox;

import enums.*;
import models.*;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JTree;
import java.awt.Panel;

public class CS_GUI_newAppoint extends LoggingOut {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1645191120165568000L;
	
	private JPanel contentPane;
	private JButton btnCancelAppoint;
	private JButton btnCrtAppoint;
	private JLabel lblwarningMessage = null;
	private JComboBox<?> DoctorTypeBox;
	private JComboBox<?> Doctor_and_ClinicBox;
	private JComboBox<?> DoctorHoursBox;
	private JLabel lblDoctorHours;
	private JLabel lblDoctor_and_Clinic;
	private JLabel lblDoctorType;
	private JDatePickerImpl datePicker;
	private Panel cal;
	private JButton btnChooseDate;
	private JPanel patientDetails;
	
	/**
	 * Create the frame.
	 */
	public CS_GUI_newAppoint() {
		setResizable(false);
		setTitle("G-Health");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DoctorGUI.class.getResource("/images/logo2.PNG")));
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("New label");
		label.setBounds(0, 0, 0, 0);
		contentPane.add(label);


		JLabel lblLogo;
		if(LoginControl.getUser_full_name() == null)
			lblLogo = new JLabel("Welcome CS!");
		else lblLogo = new JLabel("Hi "+LoginControl.getUser_full_name()+"!");
		
		lblLogo.setIcon(new ImageIcon(DoctorGUI.class.getResource("/images/logo2.png")));
		lblLogo.setBounds(0, 0, 794, 109);
		contentPane.add(lblLogo);
		
		btnCrtAppoint = new JButton("CREATE APPOINTMENT");
		btnCrtAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCrtAppoint.setBounds(413, 470, 172, 68);
		contentPane.add(btnCrtAppoint);
		
		btnCancelAppoint = new JButton("CANCEL APPOINTMENT");
		btnCancelAppoint.setBounds(202, 470, 172, 68);
		contentPane.add(btnCancelAppoint);
		btnCancelAppoint.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		
		DoctorTypeBox = new JComboBox<Object>(DoctorSpeciallity.values());
		DoctorTypeBox.setBounds(38, 157, 106, 20);
		contentPane.add(DoctorTypeBox);
		
		lblDoctorType = new JLabel("Choose Doctor Type:");
		lblDoctorType.setBounds(38, 138, 183, 14);
		contentPane.add(lblDoctorType);
		
		Doctor_and_ClinicBox = new JComboBox<Object>();
		Doctor_and_ClinicBox.setBounds(38, 217, 328, 20);
		Doctor_and_ClinicBox.setVisible(false);
		contentPane.add(Doctor_and_ClinicBox);
		
		lblDoctor_and_Clinic = new JLabel("Choose Clinic & Doctor:");
		lblDoctor_and_Clinic.setBounds(38, 192, 183, 14);
		lblDoctor_and_Clinic.setVisible(false);
		contentPane.add(lblDoctor_and_Clinic);
		
		DoctorHoursBox = new JComboBox<Object>();
		DoctorHoursBox.setBounds(38, 305, 207, 20);
		DoctorHoursBox.setVisible(false);
		contentPane.add(DoctorHoursBox);
		
		lblDoctorHours = new JLabel("Doctor Hours");
		lblDoctorHours.setBounds(38, 282, 143, 14);
		lblDoctorHours.setVisible(false);
		contentPane.add(lblDoctorHours);
		
		UtilDateModel model = new UtilDateModel();
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH); 
		int year = calendar.get(Calendar.YEAR); 
		model.setDate(year, month, day);
		model.setSelected(true);
		Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setVisible(true);
		cal = new Panel();
		cal.add(datePicker);
		cal.setBounds(26, 243, 278, 33);
		cal.setVisible(false);
		contentPane.add(cal,BorderLayout.WEST);
		
		btnChooseDate = new JButton("OK");
		cal.add(btnChooseDate);
		
		
		
		
		
		
		
		
		
		
		
		
		
		setLocationRelativeTo(null);
		
		setVisible(true);
	
	}
	
	
	
	public void SetPatient(Patient pt) {
		
		
		JLabel lblPatientDetails = new JLabel("Patient Details:");
		lblPatientDetails.setBounds(541, 135, 107, 20);
		contentPane.add(lblPatientDetails);
		patientDetails = new JPanel();
		patientDetails.setBounds(497, 170, 273, 232);
		contentPane.add(patientDetails);
		patientDetails.setLayout(null);
		
		
		Label label_1 = new Label("Patient ID");
		label_1.setBounds(5, 5, 62, 22);
		patientDetails.add(label_1);
		
		Label label_2 = new Label("First Name");
		label_2.setBounds(5, 35, 62, 22);
		patientDetails.add(label_2);
		
		Label label_3 = new Label("Last Name");
		label_3.setBounds(5, 65, 62, 22);
		patientDetails.add(label_3);
		
		Label label_4 = new Label("Email");
		label_4.setBounds(5, 95, 62, 22);
		patientDetails.add(label_4);
		
		Label label_5 = new Label("Phone");
		label_5.setBounds(5, 125, 62, 22);
		patientDetails.add(label_5);
		
		Label label_6 = new Label("Private Clinic");
		label_6.setBounds(5, 155, 82, 22);
		patientDetails.add(label_6);
		
		Label label_7 = new Label("Doctor ID");
		label_7.setBounds(5, 185, 62, 22);
		patientDetails.add(label_7);
		
		JLabel PationID = new JLabel(pt.getpID());
		PationID.setBounds(95,5,300, 22);
		patientDetails.add(PationID);
		

		JLabel fName = new JLabel(pt.getpFirstName());
		fName.setBounds(95, 35, 300, 22);
		patientDetails.add(fName);	
		

		JLabel lName = new JLabel(pt.getpLastName());
		lName.setBounds(95, 65, 300, 22);
		patientDetails.add(lName);

		JLabel eMail = new JLabel(pt.getPtEmail());
		eMail.setBounds(95, 95, 300, 22);
		patientDetails.add(eMail);
		
		JLabel phone = new JLabel(pt.getPtPhone());
		phone.setBounds(95, 125, 300, 22);
		patientDetails.add(phone);
		
		JLabel pClinic = new JLabel(pt.getPtPrivateClinic());
		pClinic.setBounds(95, 155, 90, 22);
		patientDetails.add(pClinic);
		

		JLabel doctorID = new JLabel(pt.getPd());
		doctorID.setBounds(95, 185, 90, 22);
		patientDetails.add(doctorID);
		
		
		
		patientDetails.setVisible(true);
		
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
	
	
	public void createNewAppointActionListener(ActionListener e)
	{
		btnCrtAppoint.addActionListener(e);
	}
	
	public void cancelNewAppointActionListener(ActionListener e)
	{
		btnCancelAppoint.addActionListener(e);
	}
	
	public void SelectDocTypeActionListener(ActionListener e){
		
		DoctorTypeBox.addActionListener(e);
		
	}
	
	public void DoctorBoxActionListener(ActionListener e){
		
		Doctor_and_ClinicBox.addActionListener(e);
	}
	
public void DoctorHoursBoxActionListener(ActionListener e){
		
		DoctorHoursBox.addActionListener(e);
	}
	
/*
	public void setfName(String ffName) {

		
		JLabel displayData = new JLabel(ffName);
		displayData.setBounds(247, 120, 122, 14);
		contentPane.add(displayData);	

	}*/

	public void setPtID(String ptIDstr) {
		
		JLabel ptID = new JLabel(ptIDstr);
		ptID.setBounds(372, 120, 100, 14);
		contentPane.add(ptID);
	}

	public JComboBox<?> getDoctorTypeBox() {
		return DoctorTypeBox;
	}

	public JComboBox<?> getDoctor_and_ClinicBox() {
		return Doctor_and_ClinicBox;
	}
	
	public JComboBox<?> getDoctorHoursBox() {
		return DoctorHoursBox;
	}

	public JLabel getLblDoctorHours() {
		return lblDoctorHours;
	}

	public JLabel getLblDoctor_and_Clinic() {
		return lblDoctor_and_Clinic;
	}

	public JLabel getLblDoctorType() {
		return lblDoctorType;
	}

	public Panel getCal() {
		return cal;
	}	
	
	public JDatePickerImpl getDatePicker() {
		return datePicker;
	}	
	
	public JButton getChoosenDateOK() {
		return btnChooseDate;
	}
	
	public JButton getbtnCrtAppoint() {
		return btnCrtAppoint;
	}
}

