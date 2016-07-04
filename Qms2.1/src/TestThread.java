import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestThread extends Thread{

	private static  int server_playTime=0;

	private  String equipmentIp; 
	private  int port; 
	

  
	public void TestThread1(String equipmentIp,int port) 
	
	{ 
		this.equipmentIp = equipmentIp;
		this.port = port;

	} 
	
	public void run() {
		
		while(true)
		{
			try {
				getAllData(equipmentIp, port);
				} catch (IOException | InterruptedException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
		}
	}
	public static String getAllData(String equipmentIp,int port) throws UnknownHostException, IOException, InterruptedException//访问指定的亮度计，获取数据

	  {
	  		Socket s = new Socket(equipmentIp,port);
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

}
