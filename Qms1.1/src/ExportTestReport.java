import java.awt.Color;
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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;   
 
 
 
public class ExportTestReport {     
 
 
 
    /**     
 
     * 创建JFreeChart LineXY Chart（折线图）     
     * @throws IOException 
 
     */     
 

    public static void exprotChartPicture() throws IOException {
    	 
        //步骤1：创建XYDataset对象（准备数据）      
 
        XYDataset dataset_spl = createXYDataset(3,1);      
        XYDataset dataset_lum = createXYDataset(4,2); 
        XYDataset dataset_temp = createXYDataset(7,5);
        XYDataset dataset_color = createXYDataset4(5,3,6,4);
        
        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置   
  
        JFreeChart freeChart_spl = createChart(dataset_spl,"音频（Db）");   
        JFreeChart freeChart_lum = createChart(dataset_lum,"亮度（fL）");   
        JFreeChart freeChart_color = createChart(dataset_color,"色度（x|y）");      
        JFreeChart freeChart_temp = createChart(dataset_temp,"温度（℃）");   
        
        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等      
        int w = 500;
        int h = 160;
        saveAsFile(freeChart_spl, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/1"+AllVarible.historyTableName+".png", w, h);      
        System.out.print("Spl Updata complete");
        saveAsFile(freeChart_lum, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/2"+AllVarible.historyTableName+".png", w, h);      
        System.out.print("Lum Updata complete");
        saveAsFile(freeChart_color, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/3"+AllVarible.historyTableName+".png", w, h);      
        System.out.print("Temp Updata complete");
        saveAsFile(freeChart_temp, "/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/4"+AllVarible.historyTableName+".png", w, h);      
        System.out.print("Temp Updata complete");
        
        exportPDF();//图片插入PDF并导出
        
	}
    // 保存为文件 
 
    public static void saveAsFile(JFreeChart chart, String outputPath,      
 
            int weight, int height)
    {      
 
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
    public static JFreeChart createChart(XYDataset dataset,String yname) {      
        // 创建JFreeChart对象：ChartFactory.createXYLineChart      
        JFreeChart jfreechart = ChartFactory.createXYLineChart("", 
        		// 标题      
                "", // categoryAxisLabel （category轴，横轴，X轴标签）      
                yname, // valueAxisLabel（value轴，纵轴，Y轴的标签）      
                dataset, // dataset      
                PlotOrientation.VERTICAL,   
                false, // legend      
                false, // tooltips      
                false); // URLs      
 
        // 使用CategoryPlot设置各种参数。以下设置可以省略。      
        XYPlot plot = (XYPlot) jfreechart.getPlot();      
        // 背景色 透明度      
        plot.setBackgroundAlpha(1f);      
        // 前景色 透明度      
        plot.setForegroundAlpha(1f);      
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
  

        
        for(int i=0;i< AllVarible.curIndex;i++)
        {
			String[] str = AllVarible.upDataContainer[i].split(",");
			String[] str1 = AllVarible.historyDataList.get(i).split(",");

			String[] record =str;
			String[] stander_record=str1;
			xyseries_spl.add(Integer.parseInt(stander_record[0]),Double.parseDouble(record[x]));      
			xyseries_standerSpl.add(Integer.parseInt(stander_record[0]),Double.parseDouble(stander_record[x1]));
        }

 
 
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();      
 
        xySeriesCollection.addSeries(xyseries_spl);      
        xySeriesCollection.addSeries(xyseries_standerSpl);      
 
        return xySeriesCollection;      
    }    
    private static XYDataset createXYDataset4(int x,int x1,int y,int y1) {      
        
    	//建立折线对象
    	XYSeries xyseries_colorX = new XYSeries("colorX");      
        XYSeries xyseries_standerColorX = new XYSeries("standerColorX");      
        XYSeries xyseries_colorY = new XYSeries("colorY");      
        XYSeries xyseries_standerColorY = new XYSeries("standerColorY");    

        //折线赋值
        for(int i=0;i< AllVarible.curIndex;i++)
        {
			String[] str = AllVarible.upDataContainer[i].split(",");
			String[] str1 = AllVarible.historyDataList.get(i).split(",");

			String[] record =str;
			String[] stander_record=str1;
			xyseries_colorX.add(Integer.parseInt(stander_record[0]),Double.parseDouble(record[x]));      
			xyseries_standerColorX.add(Integer.parseInt(stander_record[0]),Double.parseDouble(stander_record[x1]));
			xyseries_colorY.add(Integer.parseInt(record[0]),Double.parseDouble(record[y]));      
			xyseries_standerColorY.add(Integer.parseInt(stander_record[0]),Double.parseDouble(stander_record[y1]));
        }
   
   
 
 
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();  
        
        xySeriesCollection.addSeries(xyseries_colorX);      
        xySeriesCollection.addSeries(xyseries_standerColorX);  
        xySeriesCollection.addSeries(xyseries_colorY);      
        xySeriesCollection.addSeries(xyseries_standerColorY);      
    
//        xySeriesCollection.addSeries(xyseries3);      
 
        return xySeriesCollection;      
    }
    
    	public static void exportPDF() throws IOException {
    		
    		 Document doc = new Document();

    		  try {

    		   // 定义输出位置并把文档对象装入输出对象中

    		   PdfWriter.getInstance(doc, new FileOutputStream("/Users/Sean/Desktop/tt.pdf"));

    		   // 打开文档对象

    		   doc.open();
    		   
    		   // 设置中文字体
    		   
    		   BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

    		   Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);

    		   // 加入测试信息
    		   
    		   String str = "QMS 质量检测报告";
    		   String str1 = "———————————————————————————————————————————";
    		   
    		   Paragraph tt = new Paragraph(str, FontChinese);
    		   Paragraph tt1 = new Paragraph(str1, FontChinese);

    		   doc.add(tt);
    		   doc.add(tt1);


    		   // 加入图片Deepinpl.jpg
    		   
    		   for (int i=1;i<5;i++)
    		   {
    			   Image jpg = Image.getInstance("/Users/Sean/Desktop/javawork/Qms1.1/chartPicture/"+i+"t2016613170407.png");
    			   jpg.setAlignment(Image.ALIGN_CENTER);
    			   doc.add(jpg);
    		   }
    		   doc.newPage();
    		   String footer1 = "———————————————————————————————————————————";
    		   String footer2 = "                                        "
    		   		+ "正在检测项目" 
    		   		+ "                                                "
    		   		+ "值"  
    		   		+ "                              "
    		   		+ "基准"
    		   		+ "";
    		   String footer3 = "———————————————————————————————————————————";
//    		   String footer3 =QmsWindows.rowIndex[AllVarible.mIndex].getText();
;

    		   Font FontChinese1 = new Font(bfChinese, 12, Font.NORMAL);
    		   
    		   Paragraph paragraph1 = new Paragraph(footer1, FontChinese);
    		   Paragraph paragraph2 = new Paragraph(footer2, FontChinese1);
    		   Paragraph paragraph3 = new Paragraph(footer3, FontChinese);

    		   doc.add(paragraph1);
    		   doc.add(paragraph2);
    		   doc.add(paragraph3);


    		   Font chinese = new Font(bfChinese, 12, Font.NORMAL); 
    		   HeaderFooter footer=new HeaderFooter(new Phrase("-",chinese),new Phrase("-",chinese));
    			/**
    			 * 0是靠左
    			 * 1是居中
    			 * 2是居右
    			 */
//    			footer.setAlignment(2);
//    			footer.setBorderColor(Color.red);
//    			footer.setBorder(Rectangle.BOX);
//    		    doc.add(footer);
//    		    doc.close();
    		   //对其控制
    		   String space ="                                 ";
    		  //亮度行
    		   String footerLum = "                                             "+QmsWindows.ynIndex[0].getText()+
    				   			  "           "+QmsWindows.msIndex[0].getText()+
    				   			  "                                 "+QmsWindows.numbers[0].getText();
    		   Paragraph paragraphLum = new Paragraph(footerLum, FontChinese);
			   doc.add(paragraphLum);
			  //色彩7行
    		   Paragraph[] paragraphColor = new Paragraph[8];
    		   String[] footerColor = new String[8];

    		   for(int i=1;i<8;i++){
    			   footerColor[i] = "                                             "+QmsWindows.ynIndex[i].getText()
    					   		  +"           "+QmsWindows.msIndex[i].getText()
    					   		  +"                                 "+QmsWindows.numbers[i].getText();
        		   paragraphColor[i] = new Paragraph(footerColor[i], chinese);
    			   doc.add(paragraphColor[i]);
    		   }
 
 			  //空行
    		   String footerNull = "";
    		   Paragraph paragraphNull = new Paragraph(footerNull, FontChinese);
    		   doc.add(paragraphNull);
    		  
    		   //温度
    		   String footerTemp = "                                             "+QmsWindows.ynIndex1[0].getText()
    				   +"           "+"温度"
    				   +"                                 "+QmsWindows.rowIndex[0].getText();
    		   Paragraph paragraphTemp = new Paragraph(footerTemp, FontChinese);
			   doc.add(paragraphTemp);
    		  
			   //声音
			   Paragraph[] paragraphSpl = new Paragraph[8];
    		   String[] footerSpl = new String[8];

    		   for(int i=1;i<8;i++){
    			   footerSpl[i] = "                                             "+QmsWindows.ynIndex1[i].getText()
					   		  +"           "+QmsWindows.msIndex1[i].getText()
					   		  +"                                 "+QmsWindows.rowIndex[i].getText();
    					   
    			   paragraphSpl[i] = new Paragraph(footerSpl[i], chinese);
    			   doc.add(paragraphSpl[i]);
    		   }
			   // 关闭文档对象，释放资源
    		   doc.close();
    		   
    		   


    		  } catch (FileNotFoundException e) {

    		   e.printStackTrace();

    		  } catch (DocumentException e) {

    		   e.printStackTrace();

    		  }
    		  
    		  System.out.println("OK");

    		 }
    	public static  void detailPage() {
			
		}

	}
      	

 
 
 