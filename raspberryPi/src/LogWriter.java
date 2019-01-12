import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
String path = null;

public LogWriter (String filepath){
	this.path = filepath;
}
	
public void writeToFile(String content) {
			try {

				File file = new File(this.path);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.println(content);
				out.close();
				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
}

//public class LogWriter {
//String path = null;
//File file = null;
//
//public LogWriter (String filepath){
//	this.path = filepath;
//	this.file = new File(this.path);
//
//	// if file doesnt exists, then create it
//	if (!this.file.exists()) {
//		try {
//			this.file.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
//	
//public void writeToFile(String content) {
//	try{
//		FileWriter fw = new FileWriter(this.file.getAbsoluteFile(), true);
//	    BufferedWriter bw = new BufferedWriter(fw);
//	    bw.write(content);
////	    PrintWriter out = new PrintWriter(bw);
////	    out.println(content);
////	    out.close();
//	    bw.close();
////	    fw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//}
//	
//}
