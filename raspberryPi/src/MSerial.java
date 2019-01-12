import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;
import com.pi4j.util.Console;

public class MSerial {
	private static Console con;
	private static Serial serial;
	private static DateiBehandeln db;
	private static MService ms;
//	private static LogWriter lw;
	
	public static void main(String arg[]) throws InterruptedException, IOException{
		con=new Console();
		con.title("Serial Reader","Edit by Gaopeng Bai & Bozhan Ivanov");
		con.promptForExit();

		db=new DateiBehandeln(con);
		ms=new MService(db);
		ms.start();

		serial = SerialFactory.createInstance();
		//Interface Serial <- Klass [SerialImpl], implements
		
		serial.addListener(new SerialDataEventListener(){
			@Override
			public void dataReceived(SerialDataEvent event) {
				try {		
					String asc=event.getAsciiString();
					String hex=event.getHexByteString();
					db.writeBuff(asc,hex);
					con.println("ZIGBEE RECEIVED: " + asc);
		        	con.println("Sending update rate...");
                	serial.write((byte) 5);
				} catch (IOException e) {
                    e.printStackTrace();
                }	
			}
		});

		try {
			SerialConfig config = new SerialConfig();
			config.device("/dev/ttyUSB0")
            .baud(Baud._9600)
            .dataBits(DataBits._8)
            .parity(Parity.NONE)
            .stopBits(StopBits._1)
            .flowControl(FlowControl.NONE);
			
			con.box(" Connecting to: " + config.toString());
			serial.open(config);
			
			if(serial.isOpen()){con.box("Serial is Open and reading...");}
			
			while(con.isRunning()) {
                try {        
                	Thread.sleep(50);
                }
                catch(IllegalStateException ex){
                    ex.printStackTrace();
                }
                // wait 1 second before continuing
            }
		}catch(IOException ex) {
            con.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
	}
}
