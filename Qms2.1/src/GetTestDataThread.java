import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;

public class GetTestDataThread extends Thread{

	private static java.sql.Statement stmt;
	private  String equipmentIp ; 
	private  int port; 
	private  int index; 
	private  String tableName="";
	private static  Socket s ;
	private static double playTime[] = new double [200];

//    static int dataIndex=0;
   

	public  GetTestDataThread(int index,String equipmentIp,int port) 
	
	{ 
		this.equipmentIp = equipmentIp;
		this.port = port;
		this.index=index;

	} 
	
	public void run() {
		
		System.out.println("Thread"+index+"Success");
		try {
			qureyHistoryDb(index);
		} catch (IOException | InterruptedException | SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		while(true)
		{
			
			try {
				getAllData(index,equipmentIp,port);
				Thread.sleep(500);
				if(playTime[index]==200)
					{
						break; 
					}
				} catch (IOException | InterruptedException | SQLException e) 
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			try {
				QmsWindows.draw(AllVarible.dramNumber);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		try {
		    Date now = new Date();
		    DateFormat d2 = DateFormat.getDateTimeInstance(); 
		    String testDate = d2.format(now).replace("-","").replace(":","").replace(" ", "");//获取测试时
//		    AllVarible.tableName = "t"+testDate+"s"+AllVarible.standerTableName;
		    String standerName = AllVarible.standerTableName[index].substring(AllVarible.standerTableName[index].indexOf("s")+1);
		    tableName = "t"+testDate+"s"+standerName;
			creatTable(tableName);
		} catch (SQLException | IOException | InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(int i=1;i<400;i++){

			String[] record = new String[8]; 
//			System.out.println("~~"+index+"~~");
			record =AllVarible.testDataContainer[index][i].split(",");
			Double insert_playTime=Double.parseDouble(record[0]);
			String insert_HostName = record[1];
			String insert_getIp = record[2];
			String insert_soundPressyreLevel = record[3];
			String server_luminance = record[4];
			String server_colorTempreatureX = record[5];
			String server_colorTempreatureY = record[6];
			String server_tempreature = record[7];
			stmt = null;
		    try {
				stmt = connectMysql().createStatement();
			} catch (SQLException | IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				stmt.addBatch("insert into "
				        +tableName
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
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("No."+index+"Updata Complete!");
		}
	public static void getAllData(int index,String equipmentIp,int port) throws UnknownHostException, IOException, InterruptedException//访问指定的亮度计，获取数据
, SQLException

	  {
	  		//历史数据下载
//			qureyHistoryDb(connectMysql());

			s = new Socket(equipmentIp,port);
	  		BufferedWriter bufOut = 
	  				new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	  		BufferedReader bufIn = 
	  				new BufferedReader(new InputStreamReader(s. getInputStream()));
	  			  
	  		//获取设备名称
//	  		String cmd_theaterNo = "GetTheaterNo"+"\r";
//	  		String cmd_theater = "Lss100.sys.theater_name"+"\r";
	        String cmd_hostname = "GetHostName"+"\r";
	        bufOut.write(cmd_hostname);
	        bufOut.flush();
	        String server_HostName = bufIn.readLine().replace(" ", "");
//	         			 System.out.println(server_HostName);

	        //获取设备Ip
	        String cmd_getIp = "Getip"+"\r";
	        bufOut.write(cmd_getIp);
	        bufOut.flush();
	        String server_getIp = bufIn.readLine();
//	              System.out.println(server_getIp);
	              
	          	  //声音
	        String soundPressyreLevel = "GetS"+"\r";
	        bufOut.write(soundPressyreLevel);
	        bufOut.flush();
	        String server_soundPressyreLevel = bufIn.readLine();
//	              System.out.println(server_soundPressyreLevel);
//	         
	              //光照
	        String luminance = "GetF"+"\r";
	        bufOut.write(luminance);
	        bufOut.flush();
	        String server_luminance = bufIn.readLine();
	              
	              //色x
	        String colorTempreature = "GetX"+"\r";
	        bufOut.write(colorTempreature);
	        bufOut.flush();
	        String server_colorTempreatureX = bufIn.readLine();
//	              System.out.println(server_colorTempreatureX);
	              
		              //色y
		    String colorTempreatureY = "GetY"+"\r";
		    bufOut.write(colorTempreatureY);
		    bufOut.flush();
		    String server_colorTempreatureY = bufIn.readLine();
//	              System.out.println(server_colorTempreatureY);
	              
		    String tempreature = "Lss100.sys.temperature"+"\r";
		    bufOut.write(tempreature);
		    bufOut.flush();
		    String server_tempreature = bufIn.readLine().substring(0,4);
		   
		    
		  	String data = playTime[index]+","
	            		  	+server_HostName+","
	              			+server_getIp+","
	              			+server_soundPressyreLevel+","
	              			+server_luminance+","
	              			+server_colorTempreatureX+","
	              			+server_colorTempreatureY+","
	              			+server_tempreature;
		  	playTime[index]+=0.5;
		  	
		  	AllVarible.testDataContainer[index][AllVarible.vec[index]] = data;
		  	System.out.println(AllVarible.testDataContainer[index][AllVarible.vec[index]]+"!!!"+index);
//		  	AllVarible.curIndex+=1;
		  	AllVarible.vec[index]+=1;

//		  	s.close(); 
	  }
	static Connection connectMysql() throws UnknownHostException, IOException, InterruptedException, SQLException
	{

		
		Properties prop = new Properties();
		prop.load(new FileInputStream("/Users/Sean/Desktop/javawork/Qms2.1/src/DBconfig.properties"));
        
		String dbDriver=prop.getProperty("dbDriver"); 
		String dbUrl=prop.getProperty("dbUrl");//根据实际情况变化
		String dbUser=prop.getProperty("dbUser");
		String dbPass=prop.getProperty("dbPass");

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


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void qureyHistoryDb(int index) throws UnknownHostException, IOException, InterruptedException, SQLException
	{
		PreparedStatement pstmt;
	    Connection conn = connectMysql();
		String sql = "select * from "+AllVarible.standerTableName[index];//查询语句

		try {
	 
	       pstmt = (PreparedStatement)conn.prepareStatement(sql);
	       ResultSet rs = pstmt.executeQuery();
		   System.out.println(AllVarible.standerTableName[index]);

//		   String sql = "select * from t201662141845";//查询语句
//		   String sql = "select * from t201662141845 where playTime=(select max(playTime) from t201662141845)";//查询语句

		   //得到结果集
		   String a = new String();
		   int i = 1;
		   while (rs.next()) {//如果有数据，取第一列添加如list
			   a = rs.getString(1)+","
			  +rs.getString(4)+","
			  +rs.getString(5)+","
			  +rs.getString(6)+","
			  +rs.getString(7)+","
			  +rs.getString(8);
//			  System.out.println(a+"null");
			  AllVarible.standerDataContainer[index][200] = new String();
			  AllVarible.standerDataContainer[index][i]= a ;
			  i+=1;
		   }
			  System.out.println("No."+index+"Downlaod Complete!");

	  } catch (Exception e) 
	 		{
		  // TODO Auto-generated catch block   
		  		e.printStackTrace();
	 		}
	}

	public static void creatTable(String tableName) throws UnknownHostException, SQLException, IOException, InterruptedException//创建数据库表格
	{		
		
		stmt = null;
	    stmt = connectMysql().createStatement();
		String sql = "CREATE TABLE "+tableName+" "
				+ "( PlayTime double not null,"
				+ "HostName varchar(20) not null,"
				+ "Ip varchar(20) not null, "
				+ "Spl varchar(20) not null,"
				+ "Luminance varchar(20) not null, "
				+ "ChromaX varchar(20) not null, "
				+ "ChromaY varchar(20) not null, "
				+ "Temperature varchar(20) not null,primary key (PlayTime));";
		PreparedStatement pstmt = connectMysql().prepareStatement(sql);
	    pstmt.executeUpdate();
//	    System.out.println(AllVarible.tableName);
//	    AllVarible.tableName=AllVarible.tableName.substring(AllVarible.tableName.indexOf("s")+1,AllVarible.tableName.length()-"".length());
//	    AllVarible.tableName=AllVarible.tableName.substring(AllVarible.tableName.indexOf("s")+1,AllVarible.tableName.length()-"".length());

//	    System.out.println(AllVarible.tableName);
	}
	
	public static void getTableNameByCon() throws UnknownHostException, IOException, InterruptedException, SQLException//将所有的测试名称放入列表
	{
	       Connection con = connectMysql();
		   try {
		   DatabaseMetaData meta = (DatabaseMetaData) con.getMetaData();
		   ResultSet rs = meta.getTables(null, null, null,
		   new String[] { "TABLE" });
		
		   while (rs.next()) {
			   
			 AllVarible.tableList.add(rs.getString(3));
		 
		   }
		   con.close();
		   } catch (Exception e) {
		   try {
		     con.close();
		   } catch (SQLException e1) {
		     e1.printStackTrace();
		   }
		   e.printStackTrace();
		   }
		}

//	public static void main() {
//		
//		GetTestDataThread aTestThread  = new GetTestDataThread("192.168.50.160",10001);
//		aTestThread.start();
//	}
	
}
