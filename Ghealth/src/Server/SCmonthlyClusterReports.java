

package Server;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import enums.Status;
import enums.task;
import models.Envelope;


public class SCmonthlyClusterReports {

	
	
	private static SCmonthlyClusterReports instance = null;
	private List<Object> ReportToEnv;
	
	
	
	private SCmonthlyClusterReports(){	
		// Exists only to defeat instantiation.
		System.out.println("in MonthlyReports constructor");
	}
	
	
	 public static SCmonthlyClusterReports getInstance() {
	      if(instance == null) {
	         instance = new SCmonthlyClusterReports();
	      }
	      return instance;
	   }
	
	 

	
	
	private void createReport(String cID ,String from_date_str, String to_date_str){
		
		
		this.ReportToEnv =  new ArrayList<Object>();
		
		ResultSet resultClinicName = null;
		Statement stmt1;
		
		int clinicID = Integer.parseInt(cID);
		String querystr = "SELECT C.cName FROM clinic C " + " WHERE C.cID = " + clinicID ; // clinic name
		String clinicName = null;
		
		try {
			mysqlConnection ms = new mysqlConnection();
			stmt1 = ms.conn.createStatement();

			resultClinicName = stmt1.executeQuery(querystr);
			
			/*-- create weekly report for every clinic --*/
			resultClinicName.next();
			
			clinicName = resultClinicName.getString(1);
					
			
			ms.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		ResultSet result = null;
		Statement stmt;
		

		System.out.println(from_date_str);
		System.out.println(to_date_str);

		String query1 = ""
				+ "Create or replace view TrypA as "
				+ "(SELECT * "
				+ "  from appointmentsettings A "
				+ "  where A.apsstatus = 'ARRIVED');";


				String query2 = ""
				+ "Create or replace view TrypB as "
				+ "(SELECT idweeks AS weekNumar, "
				+ "        Count(DISTINCT A.apsptid) AS NumOfPatients, "
				+ "		AVG(DATEDIFF(A.apsDate,A.apsCreateDate)) as AvgProcessTime, "
				+ "		AVG(timediff(A.apsStartTime, A.apsTime)/60) as AvgWaitingTime "
				+ "FROM   TrypA A RIGHT OUTER JOIN Weeks ON WEEK(A.apsdate) = weeks.idweeks "
				+ "WHERE  weeks.idweeks>= week('" + from_date_str
				+ "') AND "
				+ "       weeks.idweeks<=week('" + to_date_str
				+ "') AND "
				+ "	   A.apsstatus IS NULL OR (A.apsstatus = 'ARRIVED' AND "
				+ "       A.apsdate >= '" + from_date_str
				+ "' AND "
				+ "       A.apsdate <= '" + to_date_str
				+ "') and "
				+ "       A.apsDocID in (SELECT doc.uID FROM user doc where doc.ucID=" + clinicID
				+ ") "
				+ "       GROUP BY idweeks "
				+ "       order by idweeks);";


				String query3 = ""
				+ "Create or replace view TryLEAVEa as "
				+ "(SELECT * "
				+ "	FROM patient P "
				+ "    where P.ptIsRegistered = 'NOT_REG' and P.ptLeaveDate IS NOT NULL);";


				String query4 = ""
				+ "Create or replace view TryLEAVEb as "
				+ "(SELECT idweeks AS weekNuml, "
				+ "   Count(Pa.ptLeaveDate) AS LeaveClients "
				+ "   FROM   TryLEAVEa Pa  RIGHT OUTER JOIN Weeks ON WEEK(Pa.ptLeaveDate) = weeks.idweeks "
				+ "   WHERE  weeks.idweeks>= week('" + from_date_str
				+ "') AND "
				+ "       weeks.idweeks<=week('" + to_date_str
				+ "') and Pa.ptLeaveDate is NULL "
				+ "   OR (Pa.ptIsRegistered = 'NOT_REG' AND "
				+ "      Pa.ptLeaveDate >= '" + from_date_str
				+ "' AND "
				+ "      Pa.ptLeaveDate <= '" + to_date_str
				+ "') "
				+ "      group by idweeks "
				+ "      order by idweeks "
				+ ");";


				String query5 = ""
				+ "Create or replace view TryNOSHOWa as "
				+ "(SELECT * "
				+ "from appointmentsettings A "
				+ "where A.apsStatus ='NOSHOW' "
				+ ");";


				String query6 = ""
				+ "Create or replace view TryNOSHOWb as "
				+ "(SELECT idweeks AS weekNumns, "
				+ "       Count(DISTINCT A.apsptid) AS NumOfNoshows "
				+ "FROM   TryNOSHOWa A RIGHT OUTER JOIN Weeks ON WEEK(A.apsdate) = weeks.idweeks "
				+ "WHERE  weeks.idweeks>= week('" + from_date_str
				+ "') AND "
				+ "       weeks.idweeks<=week('" + to_date_str
				+ "') AND "
				+ "	   A.apsstatus IS NULL OR "
				+ "       (A.apsstatus = 'NOSHOW' AND "
				+ "       A.apsdate >= '" + from_date_str
				+ "' AND "
				+ "       A.apsdate <= '" + to_date_str
				+ "' and "
				+ "       A.apsDocID in (SELECT doc.uID FROM user doc where doc.ucID=" + clinicID
				+ ")) "
				+ "GROUP BY idweeks);";


				String query7 = ""
				+ "Create or replace view NMonthlyView as "
				+ "(SELECT * "
				+ "FROM   TrypB AR,TryNOSHOWb NS, TryLEAVEb L "
				+ "WHERE  NS.weekNumns = L.weekNuml AND L.weekNuml = AR.weekNumar AND NS.weekNumns = AR.weekNumar "
				+ " "
				+ ");";


				String query8 = ""
				+ "SELECT * FROM ghealth.NMonthlyView;";
		
		try {
			mysqlConnection ms = new mysqlConnection();
			stmt = ms.conn.createStatement();
			
			stmt.executeUpdate(query1);
			stmt.executeUpdate(query2);
			stmt.executeUpdate(query3);
			stmt.executeUpdate(query4);
			stmt.executeUpdate(query5);
			stmt.executeUpdate(query6);
			stmt.executeUpdate(query7);
			result = stmt.executeQuery(query8);
			
			
			while(result.next())
			{
				Calendar tempDay = Calendar.getInstance();
				tempDay.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(result.getString(1)));
				
				int week =(Integer.parseInt(result.getString(1))%4);
				int month = (Integer.parseInt(result.getString(1))/4);
				
				
				this.ReportToEnv.add(new String[]{clinicName,this.getMonthName(month), String.valueOf(week), result.getString(2), result.getString(3), result.getString(4), result.getString(6), result.getString(8) });	
				
			}
			
			
			
			ms.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}//end of createReport function 
	
	
	
	public Envelope getClinicMonthlyClusterReport(List<Object> objList){
		
		
		String from_date = (String)objList.get(0);
		 String to_date =  (String)objList.get(1);
		String cID =  (String)objList.get(2);
        this.createReport(cID , from_date,  to_date);
    	
		Envelope en = new Envelope();    
		try {
			en.setobjList(this.ReportToEnv);
			en.setStatus(Status.EXIST);		
			en.setType(task.GET_CLINIC_MONTHLY_REPORT);
		
			
		} catch (ArrayIndexOutOfBoundsException e) {
			
			e.printStackTrace();
		    en.setStatus(Status.FAILED_EXCEPTION);
		}
		
		return en;
             
	}//end of getClinicMonthlyReport
	
	
	
	private int getWeekNum(int weekOfYear){
		int weekNum =0;
		
		switch(weekOfYear%4){
			
		case 0:
			weekNum = 1;
		case 1:
			weekNum = 2;
		case 2:
			weekNum = 3;
		case 3:
			weekNum = 4; 
		
		}
		return weekNum;
		
	}
	private String getMonthName(int month_num){
		
		switch(month_num){
		
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6: 
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9: 
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default: 
			return "no_month";
		
		}
		
		
	}
	
	private int getMonthNumber(String month){
		
		
		if(month.equals("January")){
			return 1;
		}else if(month.equals("February")){
			return 2;
		}else if(month.equals("March")){
			return 3;
		}else if(month.equals("April")){
			return 4;
		}else if(month.equals("May")){
			return 5;
		}else if(month.equals("June")){
			return 6;
		}else if(month.equals("July")){
			return 7;
		}else if(month.equals("August")){
			return 8;
		}else if(month.equals("September")){
			return 9;
		}else if(month.equals("October")){
			return 10;
		}else if(month.equals("November")){
			return 11;
		}else if(month.equals("December")){
			return 12;
		}else{ return 0; }
	}
	
}



