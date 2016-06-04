/**
 * TODO This is the class description
 */


package models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import enums.Status;



//class for creating appt. by customer service 

public class AppointmentSettings  implements Serializable {
	
	
	private int apsID;
	private String apsPtID;
	private Date apsDate;
	private String apsTime;
	private String CreateDate;
	private String CreateTime;
	private Status apsStatus;
	private String apsDocID;
	private Doctor doctor;
	
	
	public AppointmentSettings()
	{
		super();
	}
	
	
	public AppointmentSettings(String apsPtID, Date apsDate, String apsTime, String createTimeDate, String createTime,
			Status apsStatus, String apsDocID) {
		super();
		this.apsPtID = apsPtID;
		this.apsDate = apsDate;
		this.apsTime = apsTime;
		CreateDate = createTimeDate;
		CreateTime = createTime;
		this.apsStatus = apsStatus;
		this.apsDocID = apsDocID;
	}
	
	public AppointmentSettings(int apsID, String apsPtID, Date apsDate, String apsTime, String createTimeDate,
			String createTime, Status apsStatus, String apsDocID) {
		super();
		this.apsID = apsID;
		this.apsPtID = apsPtID;
		this.apsDate = apsDate;
		this.apsTime = apsTime;
		CreateDate = createTimeDate;
		CreateTime = createTime;
		this.apsStatus = apsStatus;
		this.apsDocID = apsDocID;
	}
	
	
	public void setApsID(int apsID) {
		this.apsID = apsID;
	}
	public void setApsPtID(String apsPtID) {
		this.apsPtID = apsPtID;
	}
	public void setApsDate(Date apsDate) {
		this.apsDate = apsDate;
	}
	public void setApsTime(String apsTime) {
		this.apsTime = apsTime;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public void setApsStatus(Status apsStatus) {
		this.apsStatus = apsStatus;
	}
	public void setApsDocID(String apsDocID) {
		this.apsDocID = apsDocID;
	}

	public int getApsID() {
		return apsID;
	}

	public String getApsPtID() {
		return apsPtID;
	}

	public Date getApsDate() {
		return apsDate;
	}

	public String getApsTime() {
		return apsTime;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public Status getApsStatus() {
		return apsStatus;
	}

	public String getApsDocID() {
		return apsDocID;
	}



	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	
	@Override
	public String toString() {
		
		return "AppointmentSettings [apsPtID=" + apsPtID + ", apsDate=" + apsDate + ", apsTime=" + apsTime
				+ ", CreateDate=" + CreateDate + ", CreateTime=" + CreateTime + ", apsStatus=" + apsStatus
				+ ", apsDocID=" + apsDocID + "]";
	}


	public String toStringCancelAppoint() {
		return "("+doctor.getdSpeciality()+") Date=" + apsDate + ", Doctor "+
				doctor.getuLastName()+",Clinic="+doctor.getuClinic().getcName()+" ("+doctor.getuClinic().getcLocation()+")";
	}
	
}
