import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
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

import com.lowagie.text.Image;

public class QmsWindows extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Button cutBtn = new Button("细节");
	public static  Label[] numbers = new Label[8];
	public static JLabel[] msIndex = new JLabel[8];
	static JLabel[] msIndex1 = new JLabel[8];
	public static Label[] rowIndex= new Label[8];
	public static Label[] ynIndex = new Label[8];
	public static Label[] ynIndex1 = new Label[9];
	public static JTextField[] textPaneIndex = new JTextField[15];
    JPanel ms = new JPanel();
    Button startBtn = new Button("测试");
    Button stopBtn = new Button("停止");
    Button setBtn = new Button("配置");
	Panel labelPane = new Panel();
	JPanel testListPane = new JPanel();//测试列表面板
	JComboBox<String> historyList = new JComboBox<String>();
	
	
	XYSeries spl = new XYSeries("");
	XYSeries stander_spl = new XYSeries("");
	XYSeries lum = new XYSeries("");
	XYSeries stander_lum = new XYSeries("");
	XYSeries colorX = new XYSeries("");
	XYSeries colorY = new XYSeries("");
	XYSeries stander_colorX = new XYSeries("");
	XYSeries stander_colorY = new XYSeries("");
	XYSeries temperature = new XYSeries("");
	XYSeries stander_temperature = new XYSeries("");
	
	public Double sqlD = (double) 0;
	public Double sqlW = (double) 0;
	public Double lumD = (double) 0;
	public Double lumW = (double) 0;
	public Double tempD = (double) 0;
	public Double tempW = (double) 0;
	public Double colorD = (double) 0;
	public Double colorW = (double) 0;
	private static  int server_playTime=0;

	
	private javax.swing.JPanel jPanel1;
	public QmsWindows() {
        jPanel1 = new javax.swing.JPanel();

        getContentPane().setLayout(null);
        getContentPane().setPreferredSize(new Dimension(1200,700));

        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BorderLayout());
        rightPane.add(cutPanel(400, 585));
        
		JPanel leftPane = new JPanel();
		leftPane.setPreferredSize(new Dimension(750, 600));
//	    leftPane.add(labelPane(750,20));
	    JPanel testListPane = new JPanel();
	    testListPane.setPreferredSize(new Dimension(750, 500));
	    leftPane.add(testListPane);
//		cmdPanel();
//	    leftPane.add(testListPane(750,30));

//	    leftPane.add(testListPane(750,30));
//	    leftPane.add(testListPane(750,30));

	    

//	    leftPane.add(setPanel(750,30));


        getContentPane().add(leftPane,"North");    
        getContentPane().add(rightPane);
        
        
	}


    private JPanel cutPanel(int cutPaneW,int cutPaneH){
		CardLayout card = new CardLayout();
		JPanel pane = new JPanel(card);
		JPanel p = new JPanel();
		p.add(cutBtn);
		pane.add(chartPanel(cutPaneW,cutPaneH));
		pane.add(detailPane());

		cutBtn.addActionListener(new ActionListener()
		 { // 下一步的按钮动作
	            public void actionPerformed(ActionEvent e)
	            {
	                if (cutBtn.getLabel().toString()=="细节"){
	                	cutBtn.setLabel("曲线图");
	                }else {
	                	cutBtn.setLabel("细节");
					}

	                card.next(pane);
	            }
	        });
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel cutBtnPane = new JPanel();
		cutBtnPane.setLayout(null);
		cutBtnPane.setBackground(Color.lightGray);
		cutBtnPane.setPreferredSize(new Dimension(100, 20));
		cutBtn.setBounds(320, 0, 80, 20);
		cutBtnPane.add(cutBtn);
		p1.setLayout(new BorderLayout());
		p1.add(cutBtnPane,"South");
		p1.add(pane);
    	return p1;
    }
    private JPanel detailPane()
    {
    	JPanel detailPane = new JPanel();
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

	private JPanel chartPanel(int chartW,int chartH) {


       
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

    public	JPanel drawChart4(XYSeries measureX,XYSeries standerX,XYSeries measureY,XYSeries standerY,String Yname,int Ymin,int Ymax)
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
	
		
	}


}
