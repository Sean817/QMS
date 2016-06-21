

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;

/**
 * =============================================================
 * QMS开发：利用JFreeChart开发实时曲线
 * =============================================================
 *时间：2016.6.1  开发者：桑鸿庆
 * -------------------------------------------------------------
 */

// 导入java2d包
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;









public class QmsWindows extends JFrame implements Runnable, ActionListener 
{
	/**
	 * 主界面
	 */
	private static final long serialVersionUID = 1L;
	// 时序图数据集
	public static  Label[] numbers = new Label[8];
	public Label[] msIndex = new Label[8];
	public static Label[] rowIndex= new Label[8];
	public static Label[] ynIndex = new Label[8];
	public static Label[] ynIndex1 = new Label[9];
	public static JTextField[] textPaneIndex = new JTextField[15];
	
	Label a = new Label("正在检查项目",Label.CENTER);
	Label b = new Label("值",Label.CENTER);
	Label c = new Label("基准",Label.CENTER);
    JPanel ms = new JPanel();
    JPanel colorPane = new JPanel();
	Label[] colorPaneIndex = new Label[8];
    Button startBtn = new Button("测试");
    Button stopBtn = new Button("停止");
    Button setBtn = new Button("配置");
	JComboBox<String> historyList = new JComboBox<String>();

	public Double sqlD = (double) 0;
	public Double sqlW = (double) 0;
	public Double lumD = (double) 0;
	public Double lumW = (double) 0;
	public Double tempD = (double) 0;
	public Double tempW = (double) 0;
	public Double colorD = (double) 0;
	public Double colorW = (double) 0;
	XYSeries spl = new XYSeries("");
	XYSeries stander_spl = new XYSeries("");
	XYSeries lum = new XYSeries("");
	XYSeries stander_lum = new XYSeries("");
	XYSeries colorX = new XYSeries("");
	XYSeries colorY = new XYSeries("");
	XYSeries stander_colorX = new XYSeries("");
	XYSeries stander_colorY = new XYSeries("");
	Button cutButton = new Button("细节");


	XYSeries temperature = new XYSeries("");
	XYSeries stander_temperature = new XYSeries("");

	// Value坐标轴初始值
	private static  int server_playTime=0;
	private javax.swing.JPanel jPanel1;
	public void run() {
		int historyIndex = 0;
		AllVarible.curIndex=0;
		while (AllVarible.testControl) {
//    	  	try {
//    	  		AllVarible.upDataList.add(getAllData());
//				System.out.println(getAllData());
//    	  		} catch (IOException | InterruptedException e1) 
//    	  	{
////				i+=1;
//				e1.printStackTrace();
//			}
	
		
			try {
				AllVarible.upDataContainer[AllVarible.curIndex]=getAllData();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
			ThreadDb threadDb=new ThreadDb();
			threadDb.start();
			System.out.println(AllVarible.curIndex);
//			System.out.println(AllVarible.curIndex );

//			System.out.println(AllVarible.upDataContainer[AllVarible.curIndex]);
			

			String[] str = AllVarible.upDataContainer[AllVarible.curIndex].split(",");
			String[] str1 = AllVarible.historyDataList.get(historyIndex).split(",");
			System.out.println(AllVarible.upDataContainer[AllVarible.curIndex].split(",")[3]);
			System.out.println(str1[1]);


			String[] record =str;
			String[] stander_record=str1;
			
			double a = Double.parseDouble(record[3]);
			int playTime = Integer.parseInt(record[0]);
			spl.add(playTime, a);
			stander_spl.add(Integer.parseInt(str1[0]), Double.parseDouble(stander_record[1]));
			double c = Double.parseDouble(record[4]);
			lum.add(playTime, c);
			stander_lum.add(playTime,Double.parseDouble(stander_record[2]));
			//色彩曲线数据
			double e = Double.parseDouble(record[5]);
			double f = Double.parseDouble(record[6]);
			colorX.add(playTime, e);
			stander_colorX.add(playTime,Double.parseDouble(stander_record[3]));
			colorY.add(playTime, f);
			stander_colorY.add(playTime,Double.parseDouble(stander_record[4]));
			//温度曲线数据
			double g = Double.parseDouble(record[7]);
			temperature.add(playTime, g);
			stander_temperature.add(playTime,Double.parseDouble(stander_record[5]));
//				System.out.println("step = "+playTime+",lastvalue = "+a);
//			spl.add(playTime, a);
//			stander_spl.add(playTime, 80);
			if(playTime%5==0){
				Double sumSql = (double) 0;
				Double sumLum = (double) 0;
				Double sumColorX = (double) 0;
				Double sumColorY = (double) 0;
				Double sumTempreature = (double) 0;
				Double sumSqlS = (double) 0;
				Double sumLumS = (double) 0;
				Double sumColorXS = (double) 0;
				Double sumColorYS = (double) 0;
				Double sumTempreatureS = (double) 0;
				for(int i=0;i<5;i++)
				{
					int sumIndex =AllVarible.curIndex-4;
//					String[] sumRecord = AllVarible.upDataContainer[sumIndex+i].split(",");
//					String[] sumRecordS = AllVarible.historyDataList.get(sumIndex+i).split(",");
					
					String[] sumRecord = AllVarible.upDataContainer[sumIndex+i].split(",");
					String[] sumRecordS = AllVarible.historyDataList.get(sumIndex+i).split(",");
//					System.out.println("#####"+sumRecordS[1]+sumRecordS[2]+sumRecordS[3]);

//					double recordSql = Double.parseDouble(sumRecord[3]);
					sumSql = (Double) (sumSql+Double.parseDouble(sumRecord[3]));
					sumLum = (Double) (sumLum+Double.parseDouble(sumRecord[4]));
//					System.out.println("！！！！~~~~~"+Double.parseDouble(sumRecord[4]));

					sumColorX = (Double) (sumColorX+Double.parseDouble(sumRecord[5]));
					sumColorY = (Double) (sumColorY+Double.parseDouble(sumRecord[6]));
					sumTempreature = (Double) (sumTempreature+Double.parseDouble(sumRecord[7]));
					sumSqlS = (Double) (sumSqlS+Double.parseDouble(sumRecordS[1]));
					sumLumS = (Double) (sumLumS+Double.parseDouble(sumRecordS[2]));
					sumColorXS = (Double) (sumColorXS+Double.parseDouble(sumRecordS[3]));
					sumColorYS = (Double) (sumColorYS+Double.parseDouble(sumRecordS[4]));
					sumTempreatureS = (Double) (sumTempreatureS+Double.parseDouble(sumRecordS[5]));
//					System.out.println("~~~~~"+sumLumS);
					System.out.println(sumLum-sumLumS);
					if(i==4){
					if((Math.abs(sumLum/5-sumLumS/5)>lumW) && (Math.abs(sumLum/5-sumLumS)<lumD))
					{
						ynIndex[0].setText("警");
			        	ynIndex[0].setForeground(Color.YELLOW);
					}
					if (Math.abs(sumColorX-sumColorXS)>colorW&&Math.abs(sumColorX-sumColorXS)<colorD) {
						ynIndex[AllVarible.mIndex+1].setText("警");
			        	ynIndex[AllVarible.mIndex+1].setForeground(Color.YELLOW);

					}
					if (Math.abs(sumTempreatureS-sumTempreature)>tempW&&Math.abs(sumTempreatureS-sumTempreature)<tempD) {
						ynIndex1[0].setText("警");
			        	ynIndex1[0].setForeground(Color.YELLOW);
					}
					if (Math.abs(sumSqlS-sumSql)>sqlW&&Math.abs(sumSqlS-sumSql)<sqlD) 
					{
						ynIndex1[AllVarible.mIndex+1].setText("警");
			        	ynIndex1[AllVarible.mIndex+1].setForeground(Color.YELLOW);
					}
					if(Math.abs(sumLum-sumLumS)>lumD)
					{
						ynIndex[0].setText("危");
			        	ynIndex[0].setForeground(Color.RED);

					}
					if (Math.abs(sumColorX-sumColorXS)>colorD) 
					{
						ynIndex[AllVarible.mIndex+1].setText("危");
			        	ynIndex[AllVarible.mIndex+1].setForeground(Color.RED);
					}
					if (Math.abs(sumTempreatureS-sumTempreature)>tempD) 
					{
//						System.out.println("*******"+Math.abs(sumTempreatureS-sumTempreature)+"@@@"+tempD);
						
						ynIndex1[0].setText("危");
			        	ynIndex1[0].setForeground(Color.RED);
					}
					if (Math.abs(sumSqlS-sumSql)>sqlD) {
						ynIndex1[AllVarible.mIndex+1].setText("危");
			        	ynIndex1[AllVarible.mIndex+1].setForeground(Color.RED);
					}
				}
				}
				AllVarible.mIndex +=1;
				if(AllVarible.mIndex>7)
				{
					AllVarible.mIndex =0;
				}
				QmsWindows.numbers[0].setText("            "+String.valueOf(String.format("%.2f",sumLum/5))
				+"                       "+String.valueOf(String.format("%.2f",sumLumS/5)));
				
				
				QmsWindows.numbers[AllVarible.mIndex].setText("        "
							  +String.valueOf(String.format("%.2f",sumColorX/5)) 
						+"   "+String.valueOf( String.format("%.2f",sumColorY/5))+"                " 
		  +String.valueOf(String.format("%.2f",sumColorXS/5))
						+"   "+String.valueOf( String.format("%.2f",sumColorYS/5)));
				QmsWindows.rowIndex[0].setText("            "+String.valueOf(String.format("%.2f",sumTempreature/5))
				+"                       "+String.valueOf(String.format("%.2f",sumTempreatureS/5)));
				QmsWindows.rowIndex[AllVarible.mIndex].setText("            "+String.valueOf(String.format("%.2f",sumSql/5))
				+"                       "+String.valueOf(String.format("%.2f",sumSqlS/5)));
			}
			
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			
			AllVarible.curIndex +=1;
			historyIndex++;
			if(AllVarible.curIndex>10000)
			{
				AllVarible.curIndex=0;
			}
		}
	
	}
	
	private JPanel chartPanel(int chartW,int chartH) {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Qms");
        setLocationByPlatform(true);
        setResizable(true);
//
//        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("panel标题"));
//        jPanel1.setToolTipText("串口设置");

//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 350, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGrouph
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 180, Short.MAX_VALUE)
//        );
//        jPanel1.add( comp );
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(new FlowLayout());
	    JPanel chartPanel=new JPanel();
	    chartPanel.setLayout(new BorderLayout());
	    JPanel p1=new JPanel();
	    p1.setLayout(new BorderLayout());
	    JPanel p2=new JPanel();
	    p2.setLayout(new BorderLayout());
       
	    //添加四个曲线
	    JPanel chart_spl = drawChart(spl,stander_spl,"声音（dB）",0,100);
	    JPanel chart_lum = drawChart(lum,stander_lum,"亮度（fL）",1,999);
	    JPanel chart_color =drawChart4(colorX,stander_colorX,colorY,stander_colorY,"色度x|y",0,1);
	    JPanel chart_temperature =drawChart(temperature,stander_temperature,"温度（℃）",10,50);
	    //折线大小
	    int w =chartW;
	    int h = chartH/4;
	    chart_spl.setPreferredSize(new Dimension(w,h));
	    chart_lum.setPreferredSize(new Dimension(w,h));
	    chart_color.setPreferredSize(new Dimension(w,h));
	    chart_temperature.setPreferredSize(new Dimension(w,h));
	    p1.add(chart_spl,"North");
	    p1.add(chart_lum);
	    p2.add(chart_color,"North");
	    p2.add(chart_temperature);
	    
	    chartPanel.add(p1,"North");
	    chartPanel.add(p2);
	    return chartPanel;






//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(0, 1, 100)
//                .addComponent(comp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addContainerGap(0, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(0,1,1)
//                .addComponent(comp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addContainerGap(0, Short.MAX_VALUE))
//        );

//        jPanel1.getAccessibleContext().setAccessibleName("饭店");
//        pack();
    }
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("EXIT")) {
			AllVarible.threadChar.interrupt();
			System.exit(0);
		}
	}

	public QmsWindows() 
	{
		AllVarible.threadChar = new Thread(this);
		// 创建时序图对象
//		chartPanel();
        getContentPane().setLayout(null);
        getContentPane().setPreferredSize(new Dimension(1200,700));


        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BorderLayout());
        rightPane.add(cutPanel(400, 585));
		
        
		JPanel leftPane = new JPanel();
	
        leftPane.setLayout(new BorderLayout());
        leftPane.setPreferredSize(new Dimension(750, 600));
        cmdPanel();
        leftPane.add(labelPane(),"North");
        leftPane.add(testListPane(200,300),"Center");
        leftPane.add(setPanel(30),"South");


        getContentPane().add(leftPane,"North");    
        getContentPane().add(rightPane);

//        getContentPane().add(cmdPanel());
		
		
	  
	    





        

        




//		initComponents(chartpanel_lum);

	}
	
	public Panel  labelPane() {
		Panel labelPane = new Panel();
		labelPane.setLayout(null);
		labelPane.setPreferredSize(new Dimension(100,40));
//		labelPane.setBackground(Color.red);
		Label nuLabel = new Label("");
		nuLabel.setBackground(Color.red);
		nuLabel.setBounds(0, 0, 100, 20);
		labelPane.add(nuLabel);

		Label properLabel = new Label("属性",Label.CENTER);
		properLabel.setBackground(Color.red);
		properLabel.setBounds(100, 0, 90, 20);
		labelPane.add(properLabel);

		
		Label dataLabel = new Label("日期",Label.CENTER);
		dataLabel.setBackground(Color.red);
		dataLabel.setBounds(190, 0, 160, 20);
		labelPane.add(dataLabel);
		
		Label screenLabel = new Label("屏 幕",Label.CENTER);
		screenLabel.setBackground(Color.red);
		screenLabel.setBounds(350, 0, 80, 20);
		labelPane.add(screenLabel);

		Label lumLabel = new Label("亮 度",Label.CENTER);
		lumLabel.setBackground(Color.red);
		lumLabel.setBounds(430, 0, 80, 20);
		labelPane.add(lumLabel);
		
		Label colorLabel = new Label("色 度",Label.CENTER);
		colorLabel.setBackground(Color.red);
		colorLabel.setBounds(510, 0, 80, 20);
		labelPane.add(colorLabel);
		
		Label splLabel = new Label("音 频",Label.CENTER);
		splLabel.setBackground(Color.red);
		splLabel.setBounds(590, 0, 80, 20);
		labelPane.add(splLabel);
		
		Label tempLabel = new Label("温 度",Label.CENTER);
		tempLabel.setBackground(Color.red);
		tempLabel.setBounds(670, 0, 80, 20);
		labelPane.add(tempLabel);
		
		startBtn.setBounds(0, 20, 100, 20);
		labelPane.add(startBtn);
		
		JComboBox<String> setList = new JComboBox<String>();
		setList.addItem("2D_F");
		setList.addItem("3D_F");
		setList.setBounds(100, 20, 90, 20);
		labelPane.add(setList);
		
		historyList.setBounds(190, 20, 160, 20);
		labelPane.add(historyList);

		Label screenLabel1 = new Label("1厅",Label.CENTER);
		screenLabel1.setBounds(350, 20, 80, 20);
		labelPane.add(screenLabel1);

		Label lumLabel1 = new Label("√基准",Label.CENTER);
		lumLabel1.setBounds(430, 20, 80, 20);
		labelPane.add(lumLabel1);
		
		Label colorLabel1 = new Label("√基准",Label.CENTER);
		colorLabel1.setBounds(510, 20, 80, 20);
		labelPane.add(colorLabel1);
		
		Label splLabel1 = new Label("√基准",Label.CENTER);
		splLabel1.setBounds(590, 20, 80, 20);
		labelPane.add(splLabel1);
		
		Label tempLabel1 = new Label("√基准",Label.CENTER);
		tempLabel1.setBounds(670, 20, 80, 20);
		labelPane.add(tempLabel1);
		
		return labelPane;
		
	}
	
	public Panel testListPane(int w, int h){
		
		Panel testListPane = new Panel();
		testListPane.setPreferredSize(new Dimension(w, h));
		testListPane.setBackground(Color.GRAY);
		return testListPane;
		
	}
	
	public Button setBtn(){
		
        setBtn.addActionListener(new ActionListener() {
        	   @Override
        	   public void actionPerformed(ActionEvent e) {
        	   JFrame frame = new JFrame("配置界面");
        	   
        	    frame.setLayout(new BorderLayout());
        	    frame.setSize(200, 200);
        	    frame.setLocationRelativeTo(null); 
        	    JPanel setPane = new JPanel();
                setPane.setLayout(new BorderLayout());
        	    JLabel wrongLabel = new JLabel("容许偏差");
                GridLayout gridLayout1 = new GridLayout(0, 3, 2, 2);
                JPanel textPane = new JPanel();
                textPane.setLayout(gridLayout1);


                JTextField[] textPaneIndex = new JTextField[15];
                for (int i = 0; i < 15; i++) { 
                   	if(i==0){
                        textPane.add(new Label(""));
                	}                	
                   	else if(i==1){
                        textPane.add(new Label("警告",Label.CENTER));
                	}
                   	else if(i==2){
                        textPane.add(new Label("危险",Label.CENTER));
                	}
                   	else if(i==3){
                        textPane.add(new Label("声压级",Label.RIGHT));
                	}
                   	else if(i==6){
                        textPane.add(new Label("亮度",Label.RIGHT));
                	}
                   	else if(i==9){
                        textPane.add(new Label("色度",Label.RIGHT));
                	}
                   	else if(i==12){
                        textPane.add(new Label("温度",Label.RIGHT));
                	}else  {
                    	textPaneIndex[i] = new JTextField("10");
                    	textPane.add(textPaneIndex[i]);
					}
                   	
                   	
                  	
                }
               	ExportTestReport.exprotChartPicture();
        	    JButton saveButton = new JButton("保存");
        	    JButton deleButton = new JButton("取消");
        	    //关闭配置界面
        	    deleButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent arg0) 
                    {
                	    frame.setVisible(false);
                	    
                    }
                });
        	    saveButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent arg0) 
                    {
                	    sqlW = Double.parseDouble(textPaneIndex[4].getText());
                	    sqlD = Double.parseDouble(textPaneIndex[5].getText());
                  	    lumW = Double.parseDouble(textPaneIndex[7].getText());
                	    lumD = Double.parseDouble(textPaneIndex[8].getText());
                 	    colorW = Double.parseDouble(textPaneIndex[10].getText());
                	    colorD = Double.parseDouble(textPaneIndex[11].getText());
                 	    tempW = Double.parseDouble(textPaneIndex[13].getText());
                	    tempD = Double.parseDouble(textPaneIndex[14].getText());
                    	frame.setVisible(false);
                    }
                });
        	    setPane.add(saveButton,"East");
        	    setPane.add(deleButton,"West");
        	    frame.add(wrongLabel,"North");
        	    frame.add(setPane,"South");
        	    frame.add(textPane);

        	    frame.setVisible(true);
//        	    frame.add(a);
        	   }
        	  });
		
		return setBtn;
		
		
	}
	
	public JPanel cmdPanel(){
	
		    for(int i=0;i<AllVarible.tableList.size();i++)
		    {
		       
		    	historyList.addItem(AllVarible.tableList.get(i));
//				System.out.println(AllVarible.tableList.get(0));
		    }
	    	


	        startBtn.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent arg0) 
	            {
	            	AllVarible.testControl = true;
	            	AllVarible.threadChar.start();
	            	AllVarible.historyTableName = historyList.getSelectedItem().toString();
	            	ThreadDownlodHistory downloadHistory = new ThreadDownlodHistory();
	            	downloadHistory.start();
	            }
	        });
	        stopBtn.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent arg0) 
	            {
	            	AllVarible.testControl = false;
	            	AllVarible.threadChar.interrupt();
	            }
	        });

		    JPanel cmdPanel=new JPanel();
	        cmdPanel.setLayout(new BorderLayout());
		    cmdPanel.add(historyList,"North");
		    cmdPanel.add(startBtn,"Center");
		    cmdPanel.add(stopBtn,"South");
		    cmdPanel.add(setBtn(),"West");
		return cmdPanel ;
		
	}
	
	public JPanel setPanel(int x){
		JPanel setPanel = new  JPanel();
		setPanel.setPreferredSize(new Dimension(100, x));
		setPanel.setBackground(Color.lightGray);
		setPanel.setLayout(null);
//		setBtn();
		setBtn.setBounds(0, 5, 60, 20);
		setPanel.add(setBtn);
		return setPanel;
		
	}
	
	public	JPanel drawChart(XYSeries measure,XYSeries stander,String Yname,int Ymin,int Ymax)
	{
		XYSeriesCollection dataset = new XYSeriesCollection(measure);
        dataset.addSeries(stander);

        JFreeChart jfreechart = ChartFactory.createXYLineChart("",
				"", Yname, dataset, PlotOrientation.VERTICAL, false, false, true);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设定
		
		ValueAxis valueaxis = xyplot.getDomainAxis();
		valueaxis.setRange( 1, 300D );
		valueaxis.setAutoRange(true);
		valueaxis.setFixedAutoRange(300D);

		valueaxis = xyplot.getRangeAxis();
		valueaxis.setRange(Ymin,Ymax);
		
		// 配置字体
    	Font xfont = new Font("宋体",Font.PLAIN,12) ;// X轴
    	Font yfont = new Font("宋体",Font.PLAIN,12) ;// Y轴
//    	Font kfont = new Font("宋体",Font.PLAIN,12) ;// 底部
    	Font titleFont = new Font("隶书", Font.BOLD , 25) ; // 图片标题
    	XYPlot plot = jfreechart.getXYPlot();// 图形的绘制结构对象
    	
    	// 图片标题
    	jfreechart.setTitle(new TextTitle(jfreechart.getTitle().getText(),titleFont));
    	
    	// 底部
//    	chart.getLegend().setItemFont(kfont);
    	
    	// X 轴
    	ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(xfont);// 轴标题
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLACK) ; // 字体颜色
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示 
        
    	// Y 轴
    	ValueAxis rangeAxis = plot.getRangeAxis();   
        rangeAxis.setLabelFont(yfont); 
        rangeAxis.setLabelPaint(Color.BLACK) ; // 字体颜色
        rangeAxis.setTickLabelFont(yfont);  
		
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(400,300));
		return chartpanel;
	}

	static Class<?> getClass(String s) 
	{
		Class<?> cls = null;
		try {
			cls = Class.forName(s);
		} catch (ClassNotFoundException cnfe) {
			throw new NoClassDefFoundError(cnfe.getMessage());
		}
		return cls;
	}
		
    public static String getAllData() throws UnknownHostException, IOException, InterruptedException
	  {
	  	Socket s = new Socket("192.168.50.160",10001);
	  	BufferedWriter bufOut = 
	              new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	          BufferedReader bufIn = 
	              new BufferedReader(new InputStreamReader(s. getInputStream()));
	  
	          	//获取设备名称
//	            String cmd_theaterNo = "GetTheaterNo"+"\r";
//	            String cmd_theater = "Lss100.sys.theater_name"+"\r";
	              String cmd_hostname = "GetHostName"+"\r";
	              bufOut.write(cmd_hostname);
	              bufOut.flush();
	              String server_HostName = bufIn.readLine().replace(" ", "");
//	              System.out.println(server_HostName);
	              
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
	              System.out.println("@@@@"+server_luminance);
	              
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
//	              System.out.println(server_tempreature);//温度
	              server_playTime+=1;
//	              System.out.println(server_playTime);//温度
//	              Thread.sleep(0);
	              s.close(); 
	              String data =server_playTime+","
	            		  	+server_HostName+","
	              			+server_getIp+","
	              			+server_soundPressyreLevel+","
	              			+server_luminance+","
	              			+server_colorTempreatureX+","
	              			+server_colorTempreatureY+","
	              			+server_tempreature;
//	              return server_HostName
//	              		+server_getIp
//	              		+server_soundPressyreLevel
//	              		+server_luminance
//	              		+server_colorTempreatureX
//	              		+server_colorTempreatureY
//	              		+server_tempreature;
	              
	              return data;
//	              "values ('"+i+"',"+"'Leonis','2','LLAS-200','192.168.64.160','100','123','22.2','333'"+")"
	          }
	  
    public	JPanel drawChart4(XYSeries measureX,XYSeries standerX,XYSeries measureY,XYSeries standerY,String Yname,int Ymin,int Ymax)
	{
		XYSeriesCollection dataset = new XYSeriesCollection(measureX);
        dataset.addSeries(standerX);
        dataset.addSeries(standerY);
        dataset.addSeries(standerY);
        dataset.addSeries(standerX);
        JFreeChart jfreechart = ChartFactory.createXYLineChart("",
				"", Yname, dataset, PlotOrientation.VERTICAL, false, false, true);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设定
		
		ValueAxis valueaxis = xyplot.getDomainAxis();
		valueaxis.setRange( 1, 300D );
		valueaxis.setAutoRange(true);
		valueaxis.setFixedAutoRange(300D);

		valueaxis = xyplot.getRangeAxis();
		valueaxis.setRange(Ymin,Ymax);
		
		// 配置字体
    	Font xfont = new Font("宋体",Font.PLAIN,12) ;// X轴
    	Font yfont = new Font("宋体",Font.PLAIN,12) ;// Y轴
//    	Font kfont = new Font("宋体",Font.PLAIN,12) ;// 底部
    	Font titleFont = new Font("隶书", Font.BOLD , 25) ; // 图片标题
    	XYPlot plot = jfreechart.getXYPlot();// 图形的绘制结构对象
    	
    	// 图片标题
    	jfreechart.setTitle(new TextTitle(jfreechart.getTitle().getText(),titleFont));
    	
    	// 底部
//    	chart.getLegend().setItemFont(kfont);
    	
    	// X 轴
    	ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(xfont);// 轴标题
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLACK) ; // 字体颜色
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示 
        
    	// Y 轴
    	ValueAxis rangeAxis = plot.getRangeAxis();   
        rangeAxis.setLabelFont(yfont); 
        rangeAxis.setLabelPaint(Color.BLACK) ; // 字体颜色
        rangeAxis.setTickLabelFont(yfont);  
		
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(400,300));
		return chartpanel;
	}
    
    
    private JPanel detailPane()
    {
    	JPanel detailPane = new JPanel();
//    	detailPane.setBackground(Color.CYAN);
    	detailPane.setPreferredSize(new Dimension(200,400));
    	
    	JPanel p1 = new JPanel();
//		p1.setLayout(new BorderLayout());
		p1.setLayout(null);

    	a.setBounds(20, 0, 130, 20);
    	b.setBounds(150, 0, 130, 20);
    	c.setBounds(280, 0, 140, 20);
    	
    	p1.add(a);
    	p1.add(b);
    	p1.add(c);
//    	
    	JPanel p2 = new JPanel();
		p2.setLayout(null);
    	Label a1 = new Label("ok",Label.CENTER);
    	Label b1 = new Label("值",Label.CENTER);
    	Label c1 = new Label("亮度",Label.CENTER);
    	Label d1 = new Label("33.3  33.3",Label.CENTER);
//    	Label e1 = new Label("33.3  33.3",Label.CENTER);
////
    	a1.setBounds(0, 0, 20, 20);
//    	a1.setBackground(Color.RED);
    	b1.setBounds(20, 0, 20, 20);
//    	b1.setBackground(Color.RED);
    	c1.setBounds(40, 0, 80, 20);
//    	c1.setBackground(Color.RED);
    	d1.setBounds(120, 0, 300, 20);
//    	d1.setBackground(Color.RED);

    	p2.add(a1);
    	p2.add(b1);
    	p2.add(c1);    	
    	p2.add(d1);

        JPanel row2 = new JPanel();
        GridLayout gridLayout1 = new GridLayout(8, 1);
    	GridLayout gridLayout2 = new GridLayout(8, 1);

        row2.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) {
            numbers[i] = new Label("                ");
            row2.add(numbers[i]);

        }


        ms.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) 
        {	
        	if(i==0)
        	{
            	msIndex[i] = new Label("亮度",Label.CENTER);

        	}
        	else {
            	msIndex[i] = new Label("模式"+i,Label.CENTER);
			}

            ms.add(msIndex[i]);
        }
        

    	colorPane.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) 
        {	
        	if(i==1)
        	{
        		colorPaneIndex[i] = new Label("",Label.CENTER);
            	colorPaneIndex[i].setBackground(Color.WHITE);
        	}
        	if(i==2)
        	{
        		colorPaneIndex[i] = new Label("",Label.CENTER);
            	colorPaneIndex[i].setBackground(Color.YELLOW);
        	}
        	if(i==3)
        	{
        		colorPaneIndex[i] = new Label("",Label.CENTER);
            	colorPaneIndex[i].setBackground(Color.CYAN);
        	}
        	if(i==4)
        	{
        		colorPaneIndex[i] = new Label("");
            	colorPaneIndex[i].setBackground(Color.PINK);
        	}
        	if(i==5)
        	{
        		colorPaneIndex[i] = new Label("");
            	colorPaneIndex[i].setBackground(Color.BLUE);
        	}
        	if(i==6)
        	{
        		colorPaneIndex[i] = new Label("");
            	colorPaneIndex[i].setBackground(Color.GREEN);
        	}
        	if(i==7)
        	{
        		colorPaneIndex[i] = new Label("");
            	colorPaneIndex[i].setBackground(Color.RED);
        	}
        	if(i==0)
        	{
        		colorPaneIndex[i] = new Label();
//            	colorPaneIndex[i].setBackground(Color.DARK_GRAY);
        	}
        	colorPane.add(colorPaneIndex[i]);
        }
        JPanel yn = new JPanel();
    	
    	yn.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) {
        	ynIndex[i] = new Label("√");
        	ynIndex[i].setForeground(Color.green);
        	yn.add(ynIndex[i]);
        }
        detailPane.setLayout(null);
        p1.setBounds(-20,0,420,20);
               yn.setBounds(0, 20, 20, 200);
        colorPane.setBounds(20,20,20,200);
               ms.setBounds(40, 20, 90, 200);
             row2.setBounds(130,20,400,200);
//        soundAndTempreture().setBounds(0, 0, 100, 200);
        detailPane.add(ms);
        detailPane.add(row2);
        detailPane.add(colorPane);
        detailPane.add(yn);
//        detailPane.add(soundAndTempreture());
//        detailPane.add(row3);
//        row2.setPreferredSize(new Dimension(1000,1));
//        detailPane.add(row2);
        detailPane.add(p1);
        
        JPanel yn1 = new JPanel();
    	yn1.setLayout(gridLayout2);
        for (int i = 0; i < 8; i++) 
        {
        	ynIndex1[i] = new Label("√");
        	ynIndex1[i].setForeground(Color.green);
        	yn1.add(ynIndex1[i]);
//        	ynIndex1[i].setBackground(Color.WHITE);
        }
        JPanel icon = new JPanel();
    	Label[] iconIndex = new Label[8];
    	icon.setLayout(gridLayout2);
        for (int i = 0; i < 8; i++) 
        {
        	iconIndex[i] = new Label("📣");
        	iconIndex[i].setForeground(Color.BLACK);
        	icon.add(iconIndex[i]);
//        	ynIndex1[i].setBackground(Color.WHITE);
        }
        JPanel ms1 = new JPanel();
    	Label[] ms1Index = new Label[8];
        ms1.setLayout(gridLayout2);
        for (int i = 0; i < 8; i++) 
        {	
        	if(i==0)
        	{
            	ms1Index[i] = new Label("温度",Label.CENTER);

        	}
        	else {
            	ms1Index[i] = new Label("模式"+i,Label.CENTER);


			}


            ms1.add(ms1Index[i]);
        }
        JPanel row = new JPanel();


    	row.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) {
        	rowIndex[i] = new Label("               ");
            row.add(rowIndex[i]);
        }
        yn1.setBounds(0, 240, 20, 200);
        icon.setBounds(20, 240, 20, 200);
        ms1.setBounds(40, 240, 90, 200);
        row.setBounds(130, 240, 400, 200);
        detailPane.add(yn1);
        detailPane.add(icon);
        detailPane.add(ms1);
        detailPane.add(row);


    	return detailPane;
    }
    
    private JPanel cutPanel(int cutPaneW,int cutPaneH){
		CardLayout card = new CardLayout();
		JPanel pane = new JPanel(card);
		JPanel p = new JPanel();
		p.add(cutButton);
		pane.add(chartPanel(cutPaneW,cutPaneH));
		pane.add(detailPane());

		 cutButton.addActionListener(new ActionListener()
		 { // 下一步的按钮动作
	            public void actionPerformed(ActionEvent e)
	            {
	                if (cutButton.getLabel().toString()=="细节"){
		                cutButton.setLabel("曲线图");
	                }else {
		                cutButton.setLabel("细节");
					}
//	                if (cutButton.getLabel().toString() == "曲线图")
//	                {
//		                cutButton.setLabel("细节");
//	                }
//	                System.out.print(cutButton.getLabel().toString() );
	            	card.next(pane);
	            }
	        });
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel cutBtnPane = new JPanel();
//		cutBtnPane.setLayout(new BorderLayout());
		cutBtnPane.setLayout(null);
		cutBtnPane.setBackground(Color.lightGray);
		cutBtnPane.setPreferredSize(new Dimension(100, 20));
		cutButton.setBounds(320, 0, 80, 20);
		cutBtnPane.add(cutButton);
		p1.setLayout(new BorderLayout());
//		cutButton.setBounds(2, 20, 5, 5);
		p1.add(cutBtnPane,"South");
		p1.add(pane);
    	return p1;
    }
}
	
	
	

