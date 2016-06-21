
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;

class ThreadDownlodHistory extends Thread {

	public ThreadDownlodHistory() {
		
		// TODO Auto-generated constructor stub
	}


	
	public void run() 
	{
		
		try {	
				qureyHistoryDb(connectMysql());
			} catch (IOException | InterruptedException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static Connection connectMysql() throws UnknownHostException, IOException, InterruptedException, SQLException
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
	public void getTableNameByCon(Connection con) {
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

	public static void qureyHistoryDb(Connection conn){
	 try {
	 
	   
		   Statement stmt = (Statement) conn.createStatement();
		   conn.setAutoCommit(false);// 更改jdbc事务的默认提交方式 
	
		   String sql = "select * from "+AllVarible.historyTableName;//查询语句
//		   String sql = "select * from t201662141845";//查询语句
//		   String sql = "select * from t201662141845 where playTime=(select max(playTime) from t201662141845)";//查询语句
			  System.out.println(AllVarible.historyTableName);

		   ResultSet rs = stmt.executeQuery(sql);//得到结果集
		   conn.commit();//事务提交
		   conn.setAutoCommit(true);// 更改jdbc事务的默认提交方式 
		   String a = new String();
		   while (rs.next()) {//如果有数据，取第一列添加如list
			  
			  a = rs.getString(1)+","
			  +rs.getString(4)+","
			  +rs.getString(5)+","
			  +rs.getString(6)+","
			  +rs.getString(7)+","
			  +rs.getString(8);
			  System.out.println(a);
			  AllVarible.historyDataList.add(a);
//			  System.out.println(AllVarible.historyDataList);
		   }
	  } catch (Exception e) 
	 		{
		  // TODO Auto-generated catch block   
		  		e.printStackTrace();
	 		}
	}

}

