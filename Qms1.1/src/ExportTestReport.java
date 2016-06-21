import java.io.File;   
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import org.jfree.chart.ChartFactory;   
import org.jfree.chart.ChartUtilities;   
import org.jfree.chart.JFreeChart;   
import org.jfree.chart.plot.PlotOrientation;   
import org.jfree.chart.plot.XYPlot;   
import org.jfree.data.xy.XYDataset;   
import org.jfree.data.xy.XYSeries;   
import org.jfree.data.xy.XYSeriesCollection;   
 
 
 
public class ExportTestReport {     
 
 
 
    /**     
 
     * 创建JFreeChart LineXY Chart（折线图）     
 
     */     
 

    public static  void exprotChartPicture() {
    	 
        //步骤1：创建XYDataset对象（准备数据）      
 
        XYDataset dataset_spl = createXYDataset(3,1);      
        XYDataset dataset_lum = createXYDataset(4,2);      

        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置      
 
        JFreeChart freeChart_spl = createChart(dataset_spl);   
        JFreeChart freeChart_lum = createChart(dataset_lum);      

 
        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等      
 
        saveAsFile(freeChart_spl, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/spl"+AllVarible.historyTableName+".png", 600, 400);      
        System.out.print("Spl Updata complete");
        saveAsFile(freeChart_lum, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/lum"+AllVarible.historyTableName+".png", 600, 400);      
        System.out.print("Lum Updata complete");

	}
    // 保存为文件 
 
    public static void saveAsFile(JFreeChart chart, String outputPath,      
 
            int weight, int height) {      
 
        FileOutputStream out = null;      
 
        try {      
 
            File outFile = new File(outputPath);      
 
            if (!outFile.getParentFile().exists()) {      
 
                outFile.getParentFile().mkdirs();      
 
            }      
 
            out = new FileOutputStream(outputPath);      
 
            // 保存为PNG      
 
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);      
 
            // 保存为JPEG      
 
            // ChartUtilities.writeChartAsJPEG(out, chart, weight, height);      
 
            out.flush();      
 
        } catch (FileNotFoundException e) {      
 
            e.printStackTrace();      
 
        } catch (IOException e) {      
 
            e.printStackTrace();      
 
        } finally {      
 
            if (out != null) {      
 
                try {      
 
                    out.close();      
                } catch (IOException e) {      
                    // do nothing      
                }      
            }      
        }      
    }      
 
    // 根据XYDataset创建JFreeChart对象      
    public static JFreeChart createChart(XYDataset dataset) {      
        // 创建JFreeChart对象：ChartFactory.createXYLineChart      
        JFreeChart jfreechart = ChartFactory.createXYLineChart("XYLine Chart Demo", // 标题      
                "年分", // categoryAxisLabel （category轴，横轴，X轴标签）      
                "数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）      
                dataset, // dataset      
                PlotOrientation.VERTICAL,   
                true, // legend      
                false, // tooltips      
                false); // URLs      
 
        // 使用CategoryPlot设置各种参数。以下设置可以省略。      
        XYPlot plot = (XYPlot) jfreechart.getPlot();      
        // 背景色 透明度      
        plot.setBackgroundAlpha(0.5f);      
        // 前景色 透明度      
        plot.setForegroundAlpha(0.5f);      
        // 其它设置可以参考XYPlot类      
 
        return jfreechart;      
    }      
 
    /**     
     * 创建XYDataset对象     
     *      
     */     
 
    private static XYDataset createXYDataset(int x,int x1) {      
        XYSeries xyseries_spl = new XYSeries("spl");  
        XYSeries xyseries_standerSpl = new XYSeries("standerSpl");     
        XYSeries xyseries_lum = new XYSeries("lum");      
        XYSeries xyseries_standerLum = new XYSeries("standerLum");      
        XYSeries xyseries_colorX = new XYSeries("colorX");      
        XYSeries xyseries_standerColorX = new XYSeries("standerColorX");      
        XYSeries xyseries_colorY = new XYSeries("colorY");      
        XYSeries xyseries_standerColorY = new XYSeries("standerColorY");    
        XYSeries xyseries_Temp = new XYSeries("Temp");      
        XYSeries xyseries_standerTemp = new XYSeries("standerTemp");    

        
        for(int i=0;i< AllVarible.curIndex;i++)
        {
			String[] str = AllVarible.upDataContainer[i].split(",");
			String[] str1 = AllVarible.historyDataList.get(i).split(",");

			String[] record =str;
			String[] stander_record=str1;
			xyseries_spl.add(Integer.parseInt(stander_record[0]),Double.parseDouble(record[x]));      
			xyseries_standerSpl.add(Integer.parseInt(stander_record[0]),Double.parseDouble(stander_record[x1]));
//			xyseries_lum.add(Integer.parseInt(record[0]),Double.parseDouble(record[4]));      
//			xyseries_standerLum.add(Integer.parseInt(stander_record[0]),Double.parseDouble(stander_record[2]));
        }
//        xyseries1.add(1987, 50);      
//        xyseries1.add(1997, 20);      
//        xyseries1.add(2007, 30);      
// 
////        XYSeries xyseries2 = new XYSeries("Two");      
//        xyseries2.add(1987, 20);      
//        xyseries2.add(1997, 10D);      
//        xyseries2.add(2007, 40D);      
 
// 
//        XYSeries xyseries3 = new XYSeries("Three");      
//        xyseries3.add(1987, 40);      
//        xyseries3.add(1997, 30.0008);      
//        xyseries3.add(2007, 38.24);      
 
 
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();      
 
        xySeriesCollection.addSeries(xyseries_spl);      
        xySeriesCollection.addSeries(xyseries_standerSpl);      
//        xySeriesCollection.addSeries(xyseries3);      
 
        return xySeriesCollection;      
    }      
}  
 
 