import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
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
public class Main {
	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException, SQLException {

	QmsWindows TimeSeriesDemo1 = new QmsWindows();
	TimeSeriesDemo1.pack();
	RefineryUtilities.centerFrameOnScreen(TimeSeriesDemo1);
	TimeSeriesDemo1.setVisible(true);
}
}
