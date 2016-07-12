import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

class AllVarible
{
	static Thread threadChar;
	static List<String> upDataList = new ArrayList<String>();//上传数据列表
	static  String[][] standerDataContainer = new String[200][400];//基准数据容器列表
	static  String[][] testDataContainer = new String[200][400];
//	static  String testDataContainer[] = new String[200];

	
	static List<String> tableList = new ArrayList<String>();//历史测试列表
	static List<String> standerList = new ArrayList<String>();//设置的基准列表
	static int vec[] = new int [10];
	static int dramNumber = 1;

	static int curIndex =0;
	static Boolean testControl =false ;
	static String tableName ;
	static String[] standerTableName = new String[201];
	static int mIndex = 0;
	static int sIndex = 0;
	
	
	static String AveSql;
	static String AveLum;
	static String AveColorX;
	static String AveColorY;
	static String AveTempreature;
	static String AveSqlS;
	static String AveLumS;
	static String AveColorXS;
	static String AveColorYS;
	static String AveTempreatureS;

}
public class Main
	{
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException, SQLException {

	QmsWindows QmsUi = new QmsWindows();
	QmsUi.pack();
	RefineryUtilities.centerFrameOnScreen(QmsUi);
	QmsUi.setVisible(true);
	
//	GetTestDataThread aTestThread = new GetTestDataThread(0,"192.168.50.160",10001);
//	aTestThread.start();
//	GetTestDataThread aTestThread1 = new GetTestDataThread(1,"192.168.50.161",10001);
//	aTestThread1.start();
//////	
//	GetTestDataThread.creatTable();
	
	}
}
