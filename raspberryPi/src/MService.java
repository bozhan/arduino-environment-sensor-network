import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MService extends Thread {
	private static final int PORT = 8080;
	private ServerSocket s;
	private Socket socket;
	private DateiBehandeln db;
	private String addr;
	private static LogWriter lw;
	
	MService(DateiBehandeln ndb) throws IOException {
		s = new ServerSocket(PORT);
		db=ndb;
		String timeStamp = new SimpleDateFormat("yyyymmdd_hhmmss").format(new Date());
		lw = new LogWriter("/root/Java/IotServer/iot_" + timeStamp + ".txt");
	}
	public void run() {
		System.out.println("Service Startet:" + s);
		while (true) {
			try {
				String tem;
				socket = s.accept();
				tem=socket.getInetAddress().toString();
				
				if(!tem.equals(addr)){
					addr=tem;
				}
				
				//System.out.println("Kontektet mit " + socket);
				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				//String str = in.readLine();
				
				db.SetNetAddr(addr);
				out.println(db.toString2()); //write to console
				lw.writeToFile(db.toFileLogWithAddress());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				addr="";
			}
		}
	}
	public void closeService() throws IOException{
		socket.close();
		s.close();
	}
}