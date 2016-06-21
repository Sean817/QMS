
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.DatabaseMetaData;


class ThreadHistoryDb extends Thread {

	public ThreadHistoryDb() {
		
		// TODO Auto-generated constructor stub
	}


	
	public void run() 
	{
		
		try {	

				getTableNameByCon(connectMysql());
	

			} catch (IOException | InterruptedException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static Connection connectMysql() throws UnknownHostException, IOException, InterruptedException, SQLException
	{

		String dbDriver="com.mysql.jdbc.Driver"; 
		String dbUrl="jdbc:mysql://localhost:3306/name";
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
	public void getTableNameByCon(Connection con)//将所有的测试名称放入列表
	{
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

	

}

