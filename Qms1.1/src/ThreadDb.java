import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;




class ThreadDb extends Thread{


	private Statement stmt;
	int insert_playTime =0;
	public ThreadDb() {
		
		// TODO Auto-generated constructor stub
	}

	
	public void run() 
	{
		try {
			if(AllVarible.curIndex==0)
				{
				 	String[] record = new String[8];  
					record =AllVarible.upDataContainer[AllVarible.curIndex].split(",");
					insert_playTime = Integer.parseInt(record[0]);
					String insert_HostName = record[1];
					String insert_getIp = record[2];
					String insert_soundPressyreLevel = record[3];
					String server_luminance = record[4];
					String server_colorTempreatureX = record[5];
					String server_colorTempreatureY = record[6];
					String server_tempreature = record[7];
					
					if(insert_playTime==1)
					{
						creatTable();
						System.out.println(AllVarible.tableName);
					}
					
					Statement stmt = null;
					stmt = connectMysql().createStatement();
					stmt.addBatch("insert into "
				            +AllVarible.tableName
				    		+ "(PlayTime,HostName,Ip,Spl,Luminance,ChromaX,ChromaY,Temperature) "
				    		+"values ("+"'"+insert_playTime+"',"
				    				+"'"+insert_HostName+"',"
				    				+"'"+insert_getIp+"',"
				    				+"'"+insert_soundPressyreLevel+"',"
				    				+"'"+server_luminance+"',"
				    				+"'"+server_colorTempreatureX+"',"
				    				+"'"+server_colorTempreatureY+"',"
				    				+"'"+server_tempreature+"'"
				    				+")");
					stmt.executeBatch();
					System.out.println("successful"+AllVarible.curIndex);
			
		}
		else if(AllVarible.curIndex!=0){
//			if(insert_playTime%5==0){
//			if(AllVarible.curIndex%5==0){
//				System.out.print(insert_playTime);
//				Double sumSql = (double) 0;
//				Double sumLum = (double) 0;
//				Double sumColorX = (double) 0;
//				Double sumColorY = (double) 0;
//				Double sumTempreature = (double) 0;
//
//				for(int i=0;i<5;i++)
//				{
//					int sumIndex =AllVarible.curIndex-5;
//					String[] sumRecord = AllVarible.upDataContainer[sumIndex+i].split(",");
////					double recordSql = Double.parseDouble(sumRecord[3]);
//					sumSql = (Double) (sumSql+Double.parseDouble(sumRecord[3]));
//					sumLum = (Double) (sumLum+Double.parseDouble(sumRecord[4]));
//					sumColorX = (Double) (sumColorX+Double.parseDouble(sumRecord[5]));
//					sumColorY = (Double) (sumColorY+Double.parseDouble(sumRecord[6]));
//					sumTempreature = (Double) (sumTempreature+Double.parseDouble(sumRecord[7]));
//				}
//				
//				AllVarible.mIndex +=1;
//				if(AllVarible.mIndex>7)
//				{
//					AllVarible.mIndex =0;
//				}
//				QmsWindows.numbers[0].setText("            "+String.valueOf(String.format("%.2f",sumLum/5)));
//				QmsWindows.numbers[AllVarible.mIndex].setText("        "
//							  +String.valueOf(String.format("%.2f",sumColorX/5))
//						+"   "+String.valueOf( String.format("%.2f",sumColorY/5)));
//				QmsWindows.rowIndex[0].setText("            "+String.valueOf(String.format("%.2f",sumTempreature/5)));
//				QmsWindows.rowIndex[AllVarible.mIndex].setText("            "+String.valueOf(String.format("%.2f",sumSql/5)));
//
//			}
		 		String record1 = new String();  
		 		record1 =AllVarible.upDataContainer[AllVarible.curIndex];
		 		String[] record = new String[8];
		 		record = record1.split(",");
				int insert_playTime = Integer.parseInt(record[0]);
				String insert_HostName = record[1];
				String insert_getIp = record[2];
				String insert_soundPressyreLevel = record[3];
				String server_luminance = record[4];
				String server_colorTempreatureX = record[5];
				String server_colorTempreatureY = record[6];
				String server_tempreature = record[7];
				Statement stmt = null;
				stmt = connectMysql().createStatement();
				stmt.addBatch("insert into "
			            +AllVarible.tableName
			    		+ "(PlayTime,HostName,Ip,Spl,Luminance,ChromaX,ChromaY,Temperature) "
			    		+"values ("+"'"+insert_playTime+"',"
			    				+"'"+insert_HostName+"',"
			    				+"'"+insert_getIp+"',"
			    				+"'"+insert_soundPressyreLevel+"',"
			    				+"'"+server_luminance+"',"
			    				+"'"+server_colorTempreatureX+"',"
			    				+"'"+server_colorTempreatureY+"',"
			    				+"'"+server_tempreature+"'"
			    				+")");
				stmt.executeBatch();
//				System.out.println("successful"+AllVarible.curIndex);
		}
//				for(int i=0;i>100;i++)
//				{
//					String[] record =AllVarible.upDataContainer[i].split(",");
//					System.out.println("successful");
//					int insert_playTime = Integer.parseInt(record[0]);
//					System.out.println("successful");
//
//					String insert_HostName = record[1];
//					String insert_getIp = record[2];
//					String insert_soundPressyreLevel = record[3];
//					String server_luminance = record[4];
//					String server_colorTempreatureX = record[5];
//					String server_colorTempreatureY = record[6];
//					System.out.println(record[6]);
//					String server_tempreature = record[7];
//					Statement stmt = null;
//					stmt = connectMysql().createStatement();
//					stmt.addBatch("insert into "
//				            +tableName
//				    		+ "(PlayTime,HostName,Ip,Spl,Luminance,ChromaX,ChromaY,Temperature) "
//				    		+"values ("+"'"+insert_playTime+"',"
//				    				+"'"+insert_HostName+"',"
//				    				+"'"+insert_getIp+"',"
//				    				+"'"+insert_soundPressyreLevel+"',"
//				    				+"'"+server_luminance+"',"
//				    				+"'"+server_colorTempreatureX+"',"
//				    				+"'"+server_colorTempreatureY+"',"
//				    				+"'"+server_tempreature+"'"
//				    				+")");
//					stmt.executeBatch();
//				}
//				AllVarible.upDataList.clear();
//				System.out.println("successful");
			

		} catch (SQLException | IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
       
	}

	

	Connection connectMysql() throws UnknownHostException, IOException, InterruptedException, SQLException
	{

		String dbDriver="com.mysql.jdbc.Driver"; 
		String dbUrl="jdbc:mysql://localhost:3306/name";//根据实际情况变化
		String dbUser="root";
		String dbPass="1223";

			Connection conn=null;
			try
			{
				Class.forName(dbDriver);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			try
			{
				conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);//注意是三个参数
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}


			return conn;
		}
	
	public void creatTable() throws UnknownHostException, SQLException, IOException, InterruptedException
	{		
		
		stmt = null;
	    stmt = connectMysql().createStatement();
	    Date now = new Date();
	    DateFormat d2 = DateFormat.getDateTimeInstance(); 
	    String testDate = d2.format(now).replace("-","").replace(":","").replace(" ", "");//获取测试时
	    AllVarible.tableName = "t"+testDate;
		
		String sql = "CREATE TABLE "+AllVarible.tableName+" "
				+ "( PlayTime int not null,"
				+ "HostName varchar(20) not null,"
				+ "Ip varchar(20) not null, "
				+ "Spl varchar(20) not null,"
				+ "Luminance varchar(20) not null, "
				+ "ChromaX varchar(20) not null, "
				+ "ChromaY varchar(20) not null, "
				+ "Temperature varchar(20) not null,primary key (PlayTime));";
		PreparedStatement pstmt = connectMysql().prepareStatement(sql);
	    pstmt.executeUpdate();
	}
	

}
