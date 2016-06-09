package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import GUI.*;
import enums.*;
import models.*;

public class AppointmentControl {


	private CS_GUI_findPatient csGUI_findPt;
	private CS_GUI_Appoint csGUI_Appoint;
	private CS_GUI_newAppoint csGUI_CreateNewAppoint;
	private CS_GUI_cancelAppoint csGUI_cancelAppoint;
	private Patient pt;
	private AppointmentSettings as;
	private PatientControl ptCtrl;
	private List<Object> objList_str;
	
	public AppointmentControl(CS_GUI_newAppoint cs,Patient pt)
	{
		this.pt=pt;
		as = new AppointmentSettings();
		as.setApsPtID(pt.getpID());
		as.setApsStatus(Status.SCHEDUELD);
		csGUI_CreateNewAppoint = cs;
		csGUI_CreateNewAppoint.SetPatient(pt);
		csGUI_CreateNewAppoint.SelectDocTypeActionListener(new SelectDoctorTypeListener());
		csGUI_CreateNewAppoint.DoctorBoxActionListener(new SelectClnicAndDoctor());
		csGUI_CreateNewAppoint.DoctorHoursBoxActionListener(new SelectHour());
		csGUI_CreateNewAppoint.getChoosenDateOK().addActionListener(new SelectDateListener());
		csGUI_CreateNewAppoint.getbtnCrtAppoint().addActionListener(new InsertNewAppointToDBListener());
		
		
	}
	
	/**
	 * 
	 * constractor for the find patient screen GUI
	 * @param
	 * @param
	 * 
	 */
	public AppointmentControl(CS_GUI_Appoint cs,Patient pt)
	{
		this.pt = pt;
		
		csGUI_Appoint = cs;
		csGUI_Appoint.SetPatient(pt);
		csGUI_Appoint.createAppointActionListener(new createNewAppointListener());
		csGUI_Appoint.SearchPatientActionListener(new SearchPatientListener());
		csGUI_Appoint.cancelAppointActionListener(new CancelAppointListener());
		csGUI_Appoint.UncreatePatientActionListener(new UncreatePatientListener());
	}
	
	
	

	public List<String> GET_OPEN_APPOINTMENTS(String ptID)
	{
		
		Envelope en = Controller.Control(new Patient(ptID),task.GET_OPEN_APPOINTMENTS);
		List<String> strList = new ArrayList<String>();
		objList_str = en.getobjList();
		
		if(en.getStatus() == Status.NOT_EXIST)
		{
			System.out.println("There is no open appointments to cancel!");
			return null;
		}
		for (Object obj : en.getobjList())
		{
			strList.add(((AppointmentSettings)obj).toStringCancelAppoint());
			System.out.println((AppointmentSettings)obj);
		}
				
		return strList;
	}
	
	public List<String> GET_DOCTOR_CLINIC(String ptID,DoctorSpeciallity ds)
	{
		/* GET_DOCTORS_IN_CLINIC_BY_TYPE */
		
		System.out.println("INSIDE GET_DOCTOR_CLINIC FUNC");
		List<Object> objList = new ArrayList<Object>();
		objList_str = new ArrayList<Object>();
		
		objList.add(ptID);
		objList.add(ds);
		Envelope en = Controller.Control(objList,task.GET_DOCTORS_IN_CLINIC_BY_TYPE);
		if(en.getStatus() == Status.SCHEDUELD)
		{
			System.out.println("There is SCHEDUELD appointment to "+ds.toString()+" for "+ptID);
			return null;
		}
		else System.out.println("else");
		List<String> strList = new ArrayList<String>();
		for (Object obj : en.getobjList())
		{
			objList_str.add(((User)obj).getuID());
			strList.add(((User)obj).toStringClinicList());
			System.out.println(((User)obj).toStringClinicList());
		}
		
		return strList;
	}
	
	public List<String> GET_DOCTOR_HOURS(String ptID,String date)
	{
		/* GET_AVAILIBLE_DOCTOR_HOURS */
		Envelope en;
		List<Object> objList = new ArrayList<Object>();
		objList.add(date);
		objList.add(ptID);
		en = Controller.Control(objList,task.GET_AVAILIBLE_DOCTOR_HOURS);
		List<String> strList = new ArrayList<String>();
		for (Object obj : en.getobjList())
		{
			System.out.print("Hour - ");
			System.out.print(obj.toString()+" | ");
			strList.add(obj.toString());
		}
		
		return strList;
	}
	
	
	class createNewAppointListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			System.out.println("trying to create appoint ");
			
			CS_GUI_newAppoint newApp_gui = new CS_GUI_newAppoint();
			AppointmentControl newApp_ctrl = new AppointmentControl(newApp_gui,pt);
			

		}
		
	}
	
	class InsertNewAppointToDBListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{

			System.out.println("trying to create appoint ");
			
			if(as.getApsDocID() == null)
			{
				JOptionPane.showMessageDialog(null,"Please select doctor before you try to create appointment!!","Can't create the appointment!", JOptionPane.INFORMATION_MESSAGE);
				return;	//return to the find patient window
			}
			else if(as.getApsDate() == null)
			{
				JOptionPane.showMessageDialog(null,"Please select date before you try to create appointment!!","Can't create the appointment!", JOptionPane.INFORMATION_MESSAGE);
				return;	//return to the find patient window
			}
			if(as.getApsTime() == null)
			{
				JOptionPane.showMessageDialog(null,"Please select hour before you try to create appointment!!","Can't create the appointment!", JOptionPane.INFORMATION_MESSAGE);
				return;	//return to the find patient window
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
			String createdDate = formatter.format(new Date());
			String createdHour = hourFormat.format(new Date());
			
			
			as.setCreateDate(createdDate);
			as.setCreateTime(createdHour);
			
			System.out.println("Created Date & Time = "+as.getCreateDate()+" "+as.getCreateTime());
			
			Envelope en = Controller.Control(as,task.CREATE_NEW_APPOINTMENT);
			if(en.getStatus() == Status.CREATED)
				JOptionPane.showMessageDialog(null,"Appointment was created succesfully!"
						+ "\nAppointment Date & Time: "+as.getApsDate()+" "+as.getApsTime()
						+ "\nPress OK to close new appointment window","Appointment Created!", JOptionPane.INFORMATION_MESSAGE);

			csGUI_CreateNewAppoint.dispose();
			
		}
		
	} //InsertNewAppointToDBListener
	
	
		
	
	
	class SelectDoctorTypeListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String TypeSelected=csGUI_CreateNewAppoint.getDoctorTypeBox().getSelectedItem().toString();
			System.out.println("Trying to GET Select Doctor type: " + TypeSelected);
					
			 
			DoctorSpeciallity ds = DoctorSpeciallity.valueOf(TypeSelected);
			List<String> objList = GET_DOCTOR_CLINIC(pt.getpID(),ds);
			/* if there is SCHEDUELD appointment to this doctor type.*/
			if(objList == null)
			{
				
				/* Hide "Doctor & Clinic Box" Combo box and label */
				csGUI_CreateNewAppoint.getDoctor_and_ClinicBox().setVisible(false);
				csGUI_CreateNewAppoint.getLblDoctor_and_Clinic().setVisible(false);
				/* Hide "Doctor Hours Box" Combo box and label */
				csGUI_CreateNewAppoint.getDoctorHoursBox().setVisible(false);
				csGUI_CreateNewAppoint.getLblDoctorHours().setVisible(false);
				/* Hide Calender */
				csGUI_CreateNewAppoint.getCal().setVisible(false);
				
				System.out.println("There is SCHEDUELD appointment for "+ds.toString()+" to patient: "+pt.getpFirstName()+" "+pt.getpLastName());
				JOptionPane.showMessageDialog(null,"There is SCHEDUELD appointment to "+TypeSelected+" for patient: "+pt.getpFirstName()+" "+pt.getpLastName()
				+",\nPlease choose another type or cancel this appointment before creating new!","Can't make appointment", JOptionPane.INFORMATION_MESSAGE);
				return;	//return to the find patient window
			}
			else
			{
				csGUI_CreateNewAppoint.getDoctor_and_ClinicBox().setModel(new DefaultComboBoxModel(objList.toArray()));

				/* setVisible "Doctor & Clinic Box" Combo box and label */
				csGUI_CreateNewAppoint.getDoctor_and_ClinicBox().setVisible(true);
				csGUI_CreateNewAppoint.getLblDoctor_and_Clinic().setVisible(true);
				/* hide  Hours */
				csGUI_CreateNewAppoint.getDoctorHoursBox().setVisible(false);
				csGUI_CreateNewAppoint.getLblDoctorHours().setVisible(false);
				/* Hide Calender */
				csGUI_CreateNewAppoint.getCal().setVisible(false);
			}
		}
	}//SelectDoctorTypeListener
	
	class SelectClnicAndDoctor  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("Trying to GET Select Clinic & Doctor: " + csGUI_CreateNewAppoint.getDoctor_and_ClinicBox().getSelectedItem().toString());
			
			csGUI_CreateNewAppoint.getCal().setVisible(true);
			/* Hide "Doctor Hours Box" Combo box and label */
			csGUI_CreateNewAppoint.getDoctorHoursBox().setVisible(false);
			csGUI_CreateNewAppoint.getLblDoctorHours().setVisible(false);
			
			/* Set Selected Doctor ID to Appointment Settings. */
			int SelectedIndex = csGUI_CreateNewAppoint.getDoctor_and_ClinicBox().getSelectedIndex();
			as.setApsDocID(objList_str.get(SelectedIndex).toString());
			
			System.out.println(as.getApsDocID());
			
		}
		
	}//SelectClnicAndDoctor
	
	
	
	
	class CancelAppointListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("trying to cancel appoint ");

			List<String> objList = GET_OPEN_APPOINTMENTS(pt.getpID());
			if(objList == null)
			{
				System.out.println("There is no open appointment to cancel for "+pt.getpFirstName()+" "+pt.getpLastName()+"!!");
				JOptionPane.showMessageDialog(null,"There is no open appointment to cancel for "+pt.getpFirstName()+" "+pt.getpLastName()+"!!","No Open Appointment", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			csGUI_cancelAppoint = new CS_GUI_cancelAppoint();
			csGUI_cancelAppoint.getcomboBox().setModel(new DefaultComboBoxModel(objList.toArray()));
			csGUI_cancelAppoint.SetPatient(pt);
			csGUI_cancelAppoint.getbtnCancelAppointment().addActionListener(new cancelAppointmentFromDB());
		
		}
		
	}//CancelAppintListener
	
	
	class cancelAppointmentFromDB  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("trying to cancel appoint ");
			//csGUI_cancelAppoint.getcomboBox().getSelectedItem();
			int selectedIndex = csGUI_cancelAppoint.getcomboBox().getSelectedIndex();
			as = (AppointmentSettings) objList_str.get(selectedIndex);
			Envelope en = Controller.Control(as,task.CANCEL_APPOINTMENT_FROM_DB);
			if(en.getStatus() == Status.CANCELED)
			{
				System.out.println("Appointment is canceled!!\n");
				JOptionPane.showMessageDialog(null,"Appointment is canceled!\n"
						+ as,"Appointment Canceled!", JOptionPane.INFORMATION_MESSAGE);
				csGUI_cancelAppoint.dispose();
			}
		}
		
	}//CancelAppintListener
	
	

	/**
	 * 
	 * @author P34w
	 *
	 */
	class UncreatePatientListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("trying to UNCREATE PATIENT ");
			
			JOptionPane.showMessageDialog(null,"You about to cancel all appointments of "+pt.getpFirstName()+" "+pt.getpLastName()+"!!","Attention!", JOptionPane.INFORMATION_MESSAGE);
			
			int confirm = JOptionPane.showOptionDialog(
		             null, "You are about to UnRegister "+pt.getpFirstName()+"\nAre you sure?", 
		             "UnRegistration Confirmation", JOptionPane.YES_NO_OPTION, 
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) 
		        {
					List<String> objList = GET_OPEN_APPOINTMENTS(pt.getpID());

					
					
					Envelope en = Controller.Control(pt,task.CANCEL_PATIENT_REGISTRATION);
					
					if(en.getStatus() == Status.NOT_REG)
					{
						System.out.println("Patient is UnCreated and all his appointments CANCELLED!!\n");
						JOptionPane.showMessageDialog(null,"all Appointments are canceled!\n","Attention!", JOptionPane.INFORMATION_MESSAGE);
						
					}
					
					en = Controller.Control(pt,task.CANCEL_ALL_PATIENT_APPOINTMENTS);
					if(en.getStatus() == Status.CANCEL_ALL)
					{
						System.out.println("Patient's appointments are CANCELLED!!\n");							
					}
					
				
 		   		   	csGUI_findPt = new CS_GUI_findPatient();
 		   		   	ptCtrl = new PatientControl(csGUI_findPt);
					csGUI_Appoint.dispose();
					
		        	
		        }
			
			

		
		}
		
	}//CancelAppintListener
	
	/*
	class LogOutListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			Controller.Control(new User(LoginControl.getUserId()),task.LOG_OUT);
			csGUI_Appoint.dispose();
			LoginControl userctrl = new LoginControl(new LoginGUI());

		}
		
	}//LogOutListener
	*/
	
	class SearchPatientListener  implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			csGUI_Appoint.dispose();
	   		PatientControl pt = new PatientControl(new CS_GUI_findPatient());

		}
		
	}//SearchPatientListener
	
	
	class SelectHour implements ActionListener 
    {
    	@Override
    	public void actionPerformed(ActionEvent e)
    	{
    		as.setApsTime(csGUI_CreateNewAppoint.getDoctorHoursBox().getSelectedItem().toString());
    		System.out.println("Hour Choosen " + as.getApsTime());
    	}	
    }//SelectHour
	

	class SelectDateListener  implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			Date date = (Date)csGUI_CreateNewAppoint.getDatePicker().getModel().getValue();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String choosenDate = formatter.format(date);
			
			Date todayDate = new Date();
			if(todayDate.compareTo(date)>0)
			{
				System.out.println("The date '"+choosenDate+"' has passed, please select a proper date.");
				JOptionPane.showMessageDialog(null,"The date '"+choosenDate+"' has passed, please select a proper date.","Choose another date!", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			as.setApsDate(choosenDate);
			System.out.println("Selected Date is: "+as.getApsDate());
			
			List<String> objList = GET_DOCTOR_HOURS(pt.getpID(),choosenDate);
			csGUI_CreateNewAppoint.getDoctorHoursBox().setModel(new DefaultComboBoxModel(objList.toArray()));
			/* setVisible "Doctor Hours Box" Combo box and label */
			csGUI_CreateNewAppoint.getDoctorHoursBox().setVisible(true);
			csGUI_CreateNewAppoint.getLblDoctorHours().setVisible(true);
			
			
			
			
			
		}
		
	}
	
	
    class CancelListener implements ActionListener 
    {
    	@Override
    	public void actionPerformed(ActionEvent e)
    	{
    		csGUI_CreateNewAppoint.dispose();
    		//csGUI_Appoint.dispose();	//Closes the login window
    	}	
    }//action


}
