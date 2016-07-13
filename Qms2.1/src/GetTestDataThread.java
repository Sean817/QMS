import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.DatabaseMetaData;

public class GetTestDataThread extends Thread{

	private static java.sql.Statement stmt;
	private  String equipmentIp ; 
	private  int port; 
	private  int index; 
	private  String tableName="";
	private static  Socket s=null ;
	private static double playTime[] = new double [200];
	static OutputStream out =null;
	static InputStream in =null ;
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
		if(playTime[index]==0){
			try {
		    	s = new Socket(equipmentIp,port);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		try {
	  			 out = s.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		try {
	  			 in = s.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true)
		{
			
			try {
				getAllData(index,equipmentIp,port);
				Thread.sleep(200);
				if(playTime[index]==100.5)
					{
						break; 
					}
				} catch (IOException | InterruptedException | SQLException e) 
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			try {
				QmsWindows.draw(AllVarible.drawNumber);
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
		for(int i=1;i<201;i++){

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

	  		//获取设备名称
//	  		String cmd_theaterNo = "GetTheaterNo"+"\r";
//	  		String cmd_theater = "Lss100.sys.theater_name"+"\r";
	        String cmd_hostname = "GetHostName"+"\r";
	        out.write(cmd_hostname.getBytes());
			byte[] buf = new byte[1024];//建立缓冲区
			int len = in.read(buf);//读取数据放进缓冲
	        String server_HostName = new String(buf,0,len)+"\b".replace(" ", "");
//			System.out.print(server_HostName+"111");//打印


	        //获取设备Ip
	        String cmd_getIp = "Getip"+"\r";
	        out.write(cmd_getIp.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_getIp = new String(buf,0,len);
//			System.out.print(server_getIp);//打印

//	              
//	          	  //声音
	        String soundPressyreLevel = "GetS"+"\r";
	        out.write(soundPressyreLevel.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_soundPressyreLevel = new String(buf,0,len);
//			System.out.print(server_soundPressyreLevel);//打印

////	         
//	              //光照
	        String luminance = "GetF"+"\r";
			out.write(luminance.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_luminance = new String(buf,0,len);
//			System.out.print(server_luminance);//打印

//	       
//	              
//	              //色x
	        String colorTempreature = "GetX"+"\r";
			out.write(colorTempreature.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_colorTempreatureX =  new String(buf,0,len);
//			System.out.print(server_colorTempreatureX);//打印
//	        
//	              
//		              //色y
		    String colorTempreatureY = "GetY"+"\r";
			out.write(colorTempreatureY.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_colorTempreatureY = new String(buf,0,len);
//			System.out.print(server_colorTempreatureY);//打印

//	              
		    String tempreature = "Lss100.sys.temperature"+"\r";
			out.write(tempreature.getBytes());
			len = in.read(buf);//读取数据放进缓冲
	        String server_tempreature = new String(buf,0,len).substring(0,4);
//			System.out.print(server_tempreature);//打印


//		    
		  	String data = playTime[index]+","
	            		  	+server_HostName+","
	              			+server_getIp+","
	              			+server_soundPressyreLevel+","
	              			+server_luminance+","
	              			+server_colorTempreatureX+","
	              			+server_colorTempreatureY+","
	              			+server_tempreature;
		  	playTime[index]+=0.5;
//		  	
		  	AllVarible.testDataContainer[index][AllVarible.testDataContainerIndex[index]] = data;
		  	System.out.println(data.substring(0,4)+"!!!"+index);
//		  	AllVarible.curIndex+=1;
		  	AllVarible.testDataContainerIndex[index]+=1;

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
//		String sql = "select * from "+AllVarible.standerTableName[index]+"where Ip = 192.168.64";//查询语句


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
