package etc;
import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) throws Exception {

		InetAddress addr = InetAddress.getByName(null);

		System.out.println("Connecting to:" + addr);
		Socket socket = new Socket("192.168.2.102", Server.PORT);
		try {
			System.out.println("socket = " + socket);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true);
			out.println("All");
			for (int i = 0; i < 5; i++) {
				String str = in.readLine();
				System.out.println(str);
				
			}
		 
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			System.out.println("closing...");
			socket.close();
		}
	}

}
