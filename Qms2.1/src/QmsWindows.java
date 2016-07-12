import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.PrivateCredentialPermission;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
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

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public class QmsWindows extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Button cutBtn = new Button("细节");
	public static  Label[] numbers = new Label[8];
	public static JLabel[] msIndex = new JLabel[8];
	static JLabel[] msIndex1 = new JLabel[8];
	public static Label[] rowIndex= new Label[8];
	public static Label[] ynIndex = new Label[8];
	public static Label[] ynIndex1 = new Label[9];
	public static JPanel[] testRecordPane = new JPanel[201];
    Button startBtn[] = new Button[201];

	public static JTextField[] textPaneIndex = new JTextField[15];
    static JPanel ms = new JPanel();
    Button stopBtn = new Button("停止");
    Button setBtn = new Button("配置");
    Button sDchartBtn = new Button("均方差");
    
	static JPanel cutPanel = new JPanel(new BorderLayout());
	static JPanel detailPane = new JPanel();

    
	JComboBox[] setList = new JComboBox[201];//2D 3D 设置
	JComboBox historyList[] = new JComboBox[201];//历史测试列表
	JComboBox sDList = new JComboBox<Object>();

	public static JPanel chart_spl = new JPanel();
	public static JPanel chart_lum = new JPanel();
	public static JPanel chart_color = new JPanel();
	public static JPanel chart_temperature = new JPanel();
	public static JPanel sD_lumChart = new JPanel();
	public static JPanel sD_splChart = new JPanel();
	public static JPanel sD_colorChart = new JPanel();
	public static JPanel sD_tempChart = new JPanel();
    
	static JPanel  rightPane = new JPanel();

	JPanel labelPane = new JPanel();//名称标签
	JPanel testListPane = new JPanel();//测试列表面板
	static XYSeries spl = new XYSeries("");
	static XYSeries stander_spl = new XYSeries("");
	static XYSeries lum = new XYSeries("");
	static XYSeries stander_lum = new XYSeries("");
	static XYSeries colorX = new XYSeries("");
	static XYSeries colorY = new XYSeries("");
	static XYSeries stander_colorX = new XYSeries("");
	static XYSeries stander_colorY = new XYSeries("");
	static XYSeries temperature = new XYSeries("");
	static XYSeries stander_temperature = new XYSeries("");
	
	
	static XYSeries sD_spl = new XYSeries("");
	static XYSeries sD_colorX = new XYSeries("");
	static XYSeries sD_colorY = new XYSeries("");
	static XYSeries sD_temp = new XYSeries("");	
	static XYSeries sD_lum = new XYSeries("");
	static XYSeries sD1 = new XYSeries("");

	
	
	public static Double sqlD = (double) 0;
	public static Double sqlW = (double) 0;
	public static Double lumD = (double) 0;
	public static Double lumW = (double) 0;
	public static Double tempD = (double) 0;
	public static Double tempW = (double) 0;
	public static Double colorD = (double) 0;
	public static Double colorW = (double) 0;
	private javax.swing.JPanel jPanel1;
	
	
	public QmsWindows() throws UnknownHostException, IOException, InterruptedException, SQLException {
        jPanel1 = new javax.swing.JPanel();
        
        GetTestDataThread.getTableNameByCon();//更新测试历史列表
        getContentPane().setLayout(new FlowLayout());
        getContentPane().setPreferredSize(new Dimension(1200,700));
        rightPane.setPreferredSize(new Dimension(400, 700));
//        rightPane.setLayout(new BorderLayout());
        detailPane();
        rightPane.add(chartPanel(400, 580));
        cutPanel();
		JPanel leftPane = new JPanel();
		leftPane.setPreferredSize(new Dimension(750, 700));
//		leftPane.setLayout(new BorderLayout());
	    leftPane.add(testListLabel(750,20));
	    leftPane.add(testListPanel(750,650));
	    leftPane.add(setPanel(750,30));

//	    leftPane.add(testListScrollPanel(20));
	    



        getContentPane().add(leftPane,"North");    
        getContentPane().add(rightPane);
        
        
	}

	public JPanel setPanel(int w,int h)//配置界面区域（以后需要添加配置文件选择）
	{
		JPanel setPanel = new  JPanel();
		setPanel.setPreferredSize(new Dimension(w, h));
		setPanel.setBackground(Color.lightGray);
		setPanel.setLayout(null);
		setBtnCmd();
		sDchartBtnCmd();
		setBtn.setBounds(0, 0, 60, 20);
		sDchartBtn.setBounds(100,0,60,20);
		cutBtn.setBounds(200,0, 60, 20);
		setPanel.add(setBtn);//设置按钮
		setPanel.add(cutBtn);//设置按钮
		setPanel.add(sDchartBtn);//均方差曲线显示按钮
			return setPanel;
			
		}
    
	public Button setBtnCmd()//配节界面按钮事件
	{
		
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

	public Button sDchartBtnCmd()//配节界面按钮事件
	{
		
		sDchartBtn.addActionListener(new ActionListener() {
        	   @Override
        	   public void actionPerformed(ActionEvent e) {
        	   JFrame frame = new JFrame("均方差曲线");
        	   
        	    frame.setLayout(new BorderLayout());
        	    frame.setSize(800, 400);
        	    frame.setLocationRelativeTo(null); 

        		Properties prop = new Properties();
        		try {
        			prop.load(new FileInputStream("/Users/Sean/Desktop/javawork/Qms2.1/src/Ipconfig.properties"));
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
                sDList = new JComboBox<Object>();

            	int testCountNumbers = Integer.parseInt(prop.getProperty("TestCountNumbers"));
        		for(int i =1;i<testCountNumbers+1;i++){
        			sDList.addItem(prop.getProperty("Ip_No."+i));
        		}
        		
        		//建立均方差
              sD_lumChart = drawChartPanel(sD_spl, sD1,"亮度", 0, 2);
              sD_splChart = drawChartPanel(sD_lum, sD1,"声音", 0, 2);
              sD_colorChart = drawChartPanel(sD_colorX, sD_colorY,"色彩", 0, 2);
              sD_tempChart = drawChartPanel(sD_temp, sD1,"温度", 0, 2);
              int w = 400;
              int h = 175;
              sD_lumChart.setPreferredSize(new Dimension(w,h));
              sD_splChart.setPreferredSize(new Dimension(w,h));
              sD_colorChart.setPreferredSize(new Dimension(w,h));
              sD_tempChart.setPreferredSize(new Dimension(w,h));
    	    
        	    //关闭配置界面
              JPanel cmdPane = new JPanel();
              cmdPane.setLayout(new BorderLayout());
              cmdPane.add(sDList,"West");
              Button sDDrawBtn = new Button("绘制");
              cmdPane.add(sDDrawBtn,"East");
              
              JPanel sDChratPane = new JPanel();
              sDChratPane.setLayout(new BorderLayout());
              sDChratPane.add(sD_splChart,"West");
              sDChratPane.add(sD_lumChart,"East");
              
              JPanel sDChratPane1 = new JPanel();
              sDChratPane1.setLayout(new BorderLayout());
              sDChratPane1.add(sD_colorChart,"West");
              sDChratPane1.add(sD_tempChart,"East");
              
              JPanel Pane = new JPanel();
              Pane.setLayout(new BorderLayout());
              Pane.add(sDChratPane,"North");
              Pane.add(sDChratPane1,"South");

              
              frame.add(Pane);
              frame.add(cmdPane,"North");
              frame.setVisible(true);
        	   }
        	  });
		return sDchartBtn;
	}
	
	private static void cutPanel(){
//		CardLayout card = new CardLayout();
//		JPanel pane = new JPanel(card);
		cutBtn.addActionListener(new ActionListener()
		 { 
			// 下一步的按钮动作
	            public void actionPerformed(ActionEvent e)
	            {
	                if (cutBtn.getLabel().toString()=="细节"){
	                	cutBtn.setLabel("曲线图");
	                	rightPane.remove(chartPanel(400,580));
	            		rightPane.add(detailPane);

	                }else {
	                	cutBtn.setLabel("细节");
	                	rightPane.remove(detailPane);
	                	rightPane.add(chartPanel(400,580));
					}

//	                card.next(pane);
	            }
	        });
//		JPanel cutBtnPane = new JPanel();
//		cutBtnPane.setLayout(null);
//		cutBtnPane.setBackground(Color.lightGray);
//		cutBtnPane.setPreferredSize(new Dimension(100, 20));
////		cutBtn.setBounds(320, 0, 80, 20);
//////		cutBtnPane.add(cutBtn);
//		cutPanel.setLayout(new BorderLayout());
//		cutPanel.add(cutBtnPane,"South");
////		cutPanel.add(pane);
//    	return cutPanel;
    }
  
	private static JPanel detailPane()
    {
//    	detailPane.setBackground(Color.CYAN);
    	detailPane.setPreferredSize(new Dimension(200,400));
    	
    	JPanel p1 = new JPanel();
//		p1.setLayout(new BorderLayout());
		p1.setLayout(null);
		Label a = new Label("正在检查项目",Label.CENTER);
		Label b = new Label("值",Label.CENTER);
		Label c = new Label("基准",Label.CENTER);
		
    	a.setBounds(20, 0, 130, 20);
    	b.setBounds(150, 0, 130, 20);
    	c.setBounds(280, 0, 140, 20);
    	
    	p1.add(a);
    	p1.add(b);
    	p1.add(c);
//    	
    	
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
                ImageIcon icon = new ImageIcon("/Users/Sean/Desktop/javawork/Qms2.1/icon/"+i+".png");
            	msIndex[i] = new JLabel("亮度  ",icon,JLabel.CENTER);
            	msIndex[i].setFont(new java.awt.Font("Song", 0, 16));
        	}
        	else {
                ImageIcon icon = new ImageIcon("/Users/Sean/Desktop/javawork/Qms2.1/icon/"+i+".jpeg");
            	msIndex[i] = new JLabel("模式"+i,icon,JLabel.CENTER);
            	msIndex[i].setFont(new java.awt.Font("Song", 0, 16));
			}

            ms.add(msIndex[i]);
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
        ms.setBounds(20, 20, 90, 200);
        row2.setBounds(120,20,400,200);

        detailPane.add(ms);
        detailPane.add(row2);
        detailPane.add(yn);
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

        JPanel ms1 = new JPanel();
        ms1.setLayout(gridLayout2);
        for (int i = 0; i < 8; i++) 
        {	
        	if(i==0)
        	{  
        		ImageIcon icon = new ImageIcon("/Users/Sean/Desktop/javawork/Qms2.1/icon/"+i+"d.png");
            	msIndex1[i] = new JLabel("温度",icon,JLabel.CENTER);
            	msIndex1[i].setFont(new java.awt.Font("Song", 0, 16));

        	}
        	else {
        		ImageIcon icon = new ImageIcon("/Users/Sean/Desktop/javawork/Qms2.1/icon/d1.png");
            	msIndex1[i] = new JLabel("模式"+i,icon,JLabel.CENTER);
            	msIndex1[i].setFont(new java.awt.Font("Song", 0, 16));

			}


            ms1.add(msIndex1[i]);
        }
        JPanel row = new JPanel();


    	row.setLayout(gridLayout1);
        for (int i = 0; i < 8; i++) {
        	rowIndex[i] = new Label("               ");
            row.add(rowIndex[i]);
        }
        yn1.setBounds(0, 240, 20, 200);
        ms1.setBounds(20, 240, 90, 200);
        row.setBounds(110, 240, 400, 200);
        detailPane.add(yn1);
        detailPane.add(ms1);
        detailPane.add(row);


    	return detailPane;
    }

	private static JPanel chartPanel(int chartW,int chartH) {


       
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
	    JPanel chartPanel=new JPanel();
	    chartPanel.setLayout(new BorderLayout());
	    JPanel p1=new JPanel();
	    p1.setLayout(new BorderLayout());
	    JPanel p2=new JPanel();
	    p2.setLayout(new BorderLayout());
       
	    //添加四个曲线
	    chart_spl = drawChartPanel(spl,stander_spl,"声音（dB）",0,100);
	    chart_lum = drawChartPanel(lum,stander_lum,"亮度（fL）",1,999);
	    chart_color =drawChart4Panel(colorX,stander_colorX,colorY,stander_colorY,"色度x|y",0,1);
	    chart_temperature =drawChartPanel(temperature,stander_temperature,"温度（℃）",10,50);
	    //折线大小
	    int w = chartW;
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
	public static	JPanel drawChartPanel(XYSeries measure,XYSeries stander,String Yname,int Ymin,int Ymax)
	{
		XYSeriesCollection dataset = new XYSeriesCollection(measure);
        dataset.addSeries(stander);

        JFreeChart jfreechart = ChartFactory.createXYLineChart("",
				"", Yname, dataset, PlotOrientation.VERTICAL, false, false, true);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设定
		
		ValueAxis valueaxis = xyplot.getDomainAxis();
		valueaxis.setRange( 1, 100D );
		valueaxis.setAutoRange(true);
		valueaxis.setFixedAutoRange(100D);
//
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

    public static	JPanel drawChart4Panel(XYSeries measureX,XYSeries standerX,XYSeries measureY,XYSeries standerY,String Yname,int Ymin,int Ymax)
	{
		XYSeriesCollection dataset = new XYSeriesCollection(measureX);
        dataset.addSeries(measureX);
        dataset.addSeries(standerY);
        dataset.addSeries(measureY);
        dataset.addSeries(standerX);
        JFreeChart jfreechart = ChartFactory.createXYLineChart("",
				"", Yname, dataset, PlotOrientation.VERTICAL, false, false, true);
		XYPlot xyplot = jfreechart.getXYPlot();
		// 纵坐标设定
		
		ValueAxis valueaxis = xyplot.getDomainAxis();
		valueaxis.setRange( 1, 100D );
		valueaxis.setAutoRange(true);
		valueaxis.setFixedAutoRange(100D);

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

    public JPanel testListLabel(int w ,int h) {
    	
    	labelPane.setLayout(null);
		labelPane.setPreferredSize(new Dimension(w,h));
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
		
		return labelPane;
		
		
	}
    
    @SuppressWarnings("unchecked")
	public JPanel testListUnit(int testNumber) //测试操作列表
    {
		
    	testRecordPane[testNumber] = new JPanel();
	    startBtn[testNumber]= new Button("测试");
		startBtn[testNumber].setPreferredSize(new Dimension(100, 20));
		testRecordPane[testNumber].add(startBtn[testNumber]);
		startBtn[testNumber].addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent arg0) 
	            {
//	            	AllVarible.testControl = true;
//	            	AllVarible.threadChar.start();
//	            	if (AllVarible.standerTableName == null) {
//		            	AllVarible.standerTableName = historyList.getSelectedItem().toString();
//					}
//	            	ThreadDownlodHistory downloadHistory = new ThreadDownlodHistory();
//	            	downloadHistory.start();
	            	
	            	//没有设置基准则默认列表选中记录为基准
	            	if(AllVarible.standerTableName[testNumber]==null)
	            	{
		            	AllVarible.standerTableName[testNumber] = historyList[testNumber].getSelectedItem().toString();
	            	}
	            	//读取ip和port的配置文件

	            	Properties prop = new Properties();
	        		try {
						prop.load(new FileInputStream("/Users/Sean/Desktop/javawork/Qms2.1/src/Ipconfig.properties"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	            	System.out.println("开始测试"+testNumber);
	            	String ip = prop.getProperty("Ip_No."+testNumber);
	            	int port = Integer.parseInt(prop.getProperty("Prot_No."+testNumber));
	            	//建立数据线程
	            	GetTestDataThread aTestThread = new GetTestDataThread(testNumber,ip,port);
	            	aTestThread.start();
	            }
	        });
		setList[testNumber] = new JComboBox<Object>();
		setList[testNumber].addItem("2D_F");
		setList[testNumber].addItem("3D_F");
		setList[testNumber].setPreferredSize(new Dimension(90, 20));
		testRecordPane[testNumber].add(setList[testNumber]);
		
		historyList[testNumber] = new JComboBox<>();
		for(int i=0;i<AllVarible.tableList.size();i++)
	    	{
				historyList[testNumber].addItem(AllVarible.tableList.get(i));
	    	}
		historyList[testNumber].setPreferredSize(new Dimension(160, 20));
		testRecordPane[testNumber].add(historyList[testNumber]);
		//影厅号显示标签
		Label screenLabel1 = new Label(testNumber+"厅",Label.CENTER);
		screenLabel1.setPreferredSize(new Dimension(40, 20));

			JMenuItem item3  = new JMenuItem("Default");
			item3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					testListPane.setBackground(Color.GRAY);
				}
			});

		//设置右键弹出菜单，设置基准，导出报告
		screenLabel1.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e) 
				{
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						getPopup(testNumber).show(e.getComponent(),
		                           e.getX(), e.getY());
					}
				}
			});
		screenLabel1.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					System.out.println(testNumber+"Start Drawing");
					AllVarible.dramNumber=testNumber;
//					try {
//						draw(AllVarible.dramNumber);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
				}
			}
		});
	
		testRecordPane[testNumber].add(screenLabel1);
	
		Label lumLabel = new Label("√基准",Label.CENTER);
		
//		lumLabel1.setBounds(430, 20, 80, 20);
		lumLabel.setPreferredSize(new Dimension(80, 20));
		testRecordPane[testNumber].add(lumLabel);
		
		Label colorLabel = new Label("√基准",Label.CENTER);
//		colorLabel1.setBounds(510, 20, 80, 20);
		colorLabel.setPreferredSize(new Dimension(80, 20));
		testRecordPane[testNumber].add(colorLabel);
		
		Label splLabel = new Label("√基准",Label.CENTER);
//		splLabel1.setBounds(590, 20, 80, 20);
		splLabel.setPreferredSize(new Dimension(80, 20));
		testRecordPane[testNumber].add(splLabel);
		
		Label tempLabel = new Label("√基准",Label.CENTER);
//		tempLabel1.setBounds(670, 20, 80, 20);
		tempLabel.setPreferredSize(new Dimension(80, 20));
		testRecordPane[testNumber].add(tempLabel);
		
		testRecordPane[testNumber].setPreferredSize(new Dimension(750, 30));
		testRecordPane[testNumber].setBackground(Color.GRAY);
//		testRecordPane.add(labelPane);
    	
    	return testRecordPane[testNumber];
	}
    
    
    public JPanel testListScrollPanel(int testCountNumbers) //测试操作列表
    {
    	JPanel testListPane=new JPanel();
	    testListPane.setPreferredSize(new Dimension(750, 550));
	    JPanel testListFrame = new JPanel();
	    testListFrame.setLayout(new  GridLayout(200, 1));
		JScrollPane testListJScrollPane =new JScrollPane(testListFrame);
		testListJScrollPane.setPreferredSize(new Dimension(750, 550));
		for(int i=1;i<testCountNumbers+1;i++){
			testListJScrollPane.add(testListUnit(i));
        }
		testListPane.add(testListJScrollPane);
    	
    	return testListPane;
    	
    }

	public JPanel testListPanel(int w,int h){
		
    	JPanel testListPane=new JPanel();
	    testListPane.setPreferredSize(new Dimension(750, 550));
	    JPanel testListFrame = new JPanel();
	    testListFrame.setLayout(new  GridLayout(200, 1));
		JScrollPane testListJScrollPane =new JScrollPane(testListFrame);
		testListJScrollPane.setPreferredSize(new Dimension(w, h));
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("/Users/Sean/Desktop/javawork/Qms2.1/src/Ipconfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	int testCountNumbers = Integer.parseInt(prop.getProperty("TestCountNumbers"));
		for(int i =1;i<testCountNumbers+1;i++){
			testListFrame.add(testListUnit(i));
		}
		testListPane.add(testListJScrollPane);
		return testListPane;
		
	}
    
    private JPopupMenu getPopup(int num) //右键菜单列表
    {
    			JPopupMenu popup = null;
    			if(popup == null) {
    				popup = new JPopupMenu("");

    				JMenuItem item1  = new JMenuItem("设为基准");
    				item1.addActionListener(new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    		            	AllVarible.standerTableName[num] = historyList[num].getSelectedItem().toString();
//    		            	System.out.println(AllVarible.standerTableName[num]);
    					}
    				});
    				popup.add(item1);

    				JMenuItem item2  = new JMenuItem("导出报告");
    				item2.addActionListener(new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    						//导出PDF报告
    						try {
    							ExportTestReport.exprotChartPicture();
    						} catch (IOException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
    					}
    				});
    				popup.add(item2);

    				JMenuItem item3  = new JMenuItem("Default");
    				item3.addActionListener(new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    						testListPane.setBackground(Color.GRAY);
    					}
    				});
    				popup.add(item3);
    				popup.setInvoker(historyList[num]);
    			}
    			return popup;
    		}
    
    public static void drawPicture(String testData,String standerData) {
		
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public static void draw(int testNumber) throws InterruptedException{

		
		AllVarible.curIndex=0;
		AllVarible.mIndex =0;
        int standerIndex=1;
//	    chart_spl = drawChartPanel(spl,stander_spl,"声音（dB）",0,100);
//	    chart_lum = drawChartPanel(lum,stander_lum,"亮度（fL）",1,999);
//	    chart_color =drawChart4Panel(colorX,stander_colorX,colorY,stander_colorY,"色度x|y",0,1);
//	    chart_temperature =drawChartPanel(temperature,stander_temperature,"温度（℃）",10,50);
////	    cutPanel();
////		detailPane();

        for (String testData : AllVarible.testDataContainer[testNumber])

        {  
            if(testData == null){
            	break;
            }
//        	System.out.println(testData);  
        	String[] str = testData.split(",");
			String[] str1 = AllVarible.standerDataContainer[testNumber][standerIndex].split(",");
			String[] record =str;
			String[] stander_record=str1;
			
			double a = Double.parseDouble(record[3]);
//			Double playTime = Double.parseDouble(record[0]);
			float playTime = Float.parseFloat(record[0]);
//			System.out.println(playTime);
			spl.add(playTime, a);
			stander_spl.add(playTime, Double.parseDouble(stander_record[1]));
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
			standerIndex+=1;
//	        rightPane.removeAll();;

//			if(playTime%10==0&&playTime!=0)//测试均值显示
//				
//			{
//				Double sumSql = (double) 0;
//				Double sumLum = (double) 0;
//				Double sumColorX = (double) 0;
//				Double sumColorY = (double) 0;
//				Double sumTempreature = (double) 0;
//				Double sumSqlS = (double) 0;
//				Double sumLumS = (double) 0;
//				Double sumColorXS = (double) 0;
//				Double sumColorYS = (double) 0;
//				Double sumTempreatureS = (double) 0;
//				for(int i=1;i<21;i++)
//				{
//					int sumIndex =AllVarible.curIndex-9;
////					String[] sumRecord = AllVarible.testDataContainer[testNumber][sumIndex].split(",");
////
//					String[] sumRecord = AllVarible.testDataContainer[testNumber][sumIndex].split(",");
//					String[] sumRecordS = AllVarible.standerDataContainer[testNumber][sumIndex+i].split(",");
//
//					sumSql = (Double) (sumSql+Double.parseDouble(sumRecord[3]));
//					sumLum = (Double) (sumLum+Double.parseDouble(sumRecord[4]));
//
//					sumColorX = (Double) (sumColorX+Double.parseDouble(sumRecord[5]));
//					sumColorY = (Double) (sumColorY+Double.parseDouble(sumRecord[6]));
//					
//					sumSqlS = (Double) (sumSqlS+Double.parseDouble(sumRecordS[1]));
//					sumLumS = (Double) (sumLumS+Double.parseDouble(sumRecordS[2]));
//					
//					sumColorXS = (Double) (sumColorXS+Double.parseDouble(sumRecordS[3]));
//					sumColorYS = (Double) (sumColorYS+Double.parseDouble(sumRecordS[4]));
//					
//					sumTempreature = (Double) (sumTempreature+Double.parseDouble(sumRecord[7]));
//					sumTempreatureS = (Double) (sumTempreatureS+Double.parseDouble(sumRecordS[5]));
//
//
//					
////					System.out.println("~~~~~"+sumLumS);
//					System.out.println(sumLum-sumLumS);
//					if(i==10){
//					if((Math.abs(sumLum/20-sumLumS/20)>lumW) && (Math.abs(sumLum/20-sumLumS)<lumD))
//					{
//						ynIndex[0].setText("警");
//			        	ynIndex[0].setForeground(Color.YELLOW);
//					}
//					if (Math.abs(sumColorX-sumColorXS)>colorW&&Math.abs(sumColorX-sumColorXS)<colorD) {
//						ynIndex[AllVarible.mIndex+1].setText("警");
//			        	ynIndex[AllVarible.mIndex+1].setForeground(Color.YELLOW);
//
//					}
//					if (Math.abs(sumTempreatureS-sumTempreature)>tempW&&Math.abs(sumTempreatureS-sumTempreature)<tempD) {
//						ynIndex1[0].setText("警");
//			        	ynIndex1[0].setForeground(Color.YELLOW);
//					}
//					if (Math.abs(sumSqlS-sumSql)>sqlW&&Math.abs(sumSqlS-sumSql)<sqlD) 
//					{
//						ynIndex1[AllVarible.mIndex+1].setText("警");
//			        	ynIndex1[AllVarible.mIndex+1].setForeground(Color.YELLOW);
//					}
//					if(Math.abs(sumLum-sumLumS)>lumD)
//					{
//						ynIndex[0].setText("危");
//			        	ynIndex[0].setForeground(Color.RED);
//
//					}
//					if (Math.abs(sumColorX-sumColorXS)>colorD) 
//					{
//						ynIndex[AllVarible.mIndex+1].setText("危");
//			        	ynIndex[AllVarible.mIndex+1].setForeground(Color.RED);
//					}
//					if (Math.abs(sumTempreatureS-sumTempreature)>tempD) 
//					{
////						System.out.println("*******"+Math.abs(sumTempreatureS-sumTempreature)+"@@@"+tempD);
//						
//						ynIndex1[0].setText("危");
//			        	ynIndex1[0].setForeground(Color.RED);
//					}
//					if (Math.abs(sumSqlS-sumSql)>sqlD) {
//						ynIndex1[AllVarible.mIndex+1].setText("危");
//			        	ynIndex1[AllVarible.mIndex+1].setForeground(Color.RED);
//					}
//				}
//				}
//				AllVarible.mIndex +=1;
//				AllVarible.curIndex +=1;
////				if(AllVarible.mIndex>8)
////				{
////					AllVarible.mIndex =0;
////				}
//				//平均值
//				AllVarible.AveSql = String.valueOf(String.format("%.2f",sumSql/20));
//				AllVarible.AveLum = String.valueOf(String.format("%.2f",sumLum/20));
//				
//				AllVarible.AveSqlS = String.valueOf(String.format("%.2f",sumSqlS/20));
//				AllVarible.AveLumS = String.valueOf(String.format("%.2f",sumLumS/20));
//
//				AllVarible.AveColorX = String.valueOf(String.format("%.2f",sumColorX/20));
//				AllVarible.AveColorY = String.valueOf(String.format("%.2f",sumColorY/20));
//				
//				AllVarible.AveColorXS = String.valueOf(String.format("%.2f",sumColorXS/20));
//				AllVarible.AveColorYS = String.valueOf(String.format("%.2f",sumColorYS/20));
//				
//				AllVarible.AveTempreature = String.valueOf(String.format("%.2f",sumTempreature/20));
//				AllVarible.AveTempreatureS = String.valueOf(String.format("%.2f",sumTempreatureS/20));
//				
//				QmsWindows.numbers[0].setText("            "+String.valueOf(String.format("%.2f",sumLum/20))
//				+"                       "+String.valueOf(String.format("%.2f",sumLumS/20)));
//				
//				
//				QmsWindows.numbers[AllVarible.mIndex].setText("        "
//							  +String.valueOf(String.format("%.2f",sumColorX/20)) 
//						+"   "+String.valueOf( String.format("%.2f",sumColorY/20))+"                " 
//							  +String.valueOf(String.format("%.2f",sumColorXS/20))
//						+"   "+String.valueOf( String.format("%.2f",sumColorYS/20)));
//				QmsWindows.rowIndex[0].setText("            "+String.valueOf(String.format("%.2f",sumTempreature/20))
//				+"                       "+String.valueOf(String.format("%.2f",sumTempreatureS/20)));
//				QmsWindows.rowIndex[AllVarible.mIndex].setText("            "+String.valueOf(String.format("%.2f",sumSql/20))
//				+"                       "+String.valueOf(String.format("%.2f",sumSqlS/20)));
//				}
//			AllVarible.curIndex +=1;
			}
//        rightPane.add(chartPanel(400, 580));
        }  
//		
	}
