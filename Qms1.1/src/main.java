import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

class AllVarible
{
	static Thread threadChar;
	static List<String> upDataList = new ArrayList<String>();
	static List<String> historyDataList = new ArrayList<String>();
	static List<String> tableList = new ArrayList<String>();
	static String[] upDataContainer = new String[1000];
	static int curIndex =0;
	static Boolean testControl =false ;
	static String tableName ;
	static String historyTableName;
	static int mIndex = 0;
	static int sIndex = 0;
	
	static String AveSql ;
	static String AveLum ;
	static String AveColorX;
	static String AveColorY;
	static String AveTempreature;
	static String AveSqlS ;
	static String AveLumS;
	static String AveColorXS;
	static String AveColorYS;
	static String AveTempreatureS;

}

public class main {
	static Thread thread1;
	public static void main(String[] args) throws InterruptedException {
//		ThreadDb threadDb = new ThreadDb();
//		threadDb.start();
		
		ThreadHistoryDb HistoryDb = new ThreadHistoryDb();
		HistoryDb.start();//更新历史测试列表
		
		QmsWindows TimeSeriesDemo1 = new QmsWindows();
		TimeSeriesDemo1.pack();
		RefineryUtilities.centerFrameOnScreen(TimeSeriesDemo1);
		TimeSeriesDemo1.setVisible(true);
//		AllVarible.threadChar.start();
//		Thread.sleep(1000);
//		System.out.println(Integer.parseInt(AllVarible.upDataContainer[0].split(",")[0]));
//		System.out.println(Integer.parseInt(AllVarible.upDataContainer[0].split(",")[0]));
	}
}

