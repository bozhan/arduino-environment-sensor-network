import java.util.ArrayDeque;
import com.pi4j.util.Console;

public class DateiBehandeln {
	private ArrayDeque<String> addr=new ArrayDeque<String>(20);
	private ArrayDeque<Double> tem=new ArrayDeque<Double>(20);
	private ArrayDeque<Double> feu=new ArrayDeque<Double>(20);
	private ArrayDeque<Double> leu=new ArrayDeque<Double>(20);
	private String buf,netaddr;
	private Console con;
	private int zahl=0;
	
	public DateiBehandeln(Console c) {
		buf="";
		con=c;
		netaddr="";
	}
	
	synchronized public void writeBuff(String str1,String str2){
		buf=buf+str1;
		if(buf.indexOf("rh")>=0 && str2.length()>12){
			Asc2Dou(buf,str2);
			buf="";
		} 	
	}
	
	synchronized public void Asc2Dou(String str,String asc){
		double d1,d2,d3;
		String temp,addr;
		d1=0.0;
		d2=0.0;
		d3=0.0;
		
		int i=str.indexOf(":");
		int j=str.indexOf("C");
		if(i>=0 && j>i)
		{
			addr=asc.substring(0, 11);
			temp=str.substring(i+1, j);
		    d1=Double.parseDouble(temp);   
		}else return;
		
		i=str.indexOf(":", j);
		j=str.indexOf("LX");
		if(i>=0 && j>i)
		{
			temp=str.substring(i+1, j);
	    	d2=Double.parseDouble(temp);
		}else return;
		
		i=str.indexOf(":", j);
		j=str.indexOf("rh");
		if(i>=0 && j>i)
		{
			temp=str.substring(i+1, j);
	    	d3=Double.parseDouble(temp);
		}else return;
		
		set(addr,d1,d2,d3);
	}
	synchronized public void set(String add,double te,double le,double fe){
		addr.addFirst(add);
		tem.addFirst(te);
		leu.addFirst(le);
		feu.addFirst(fe);
		zahl++;
		printData();
	} 
//	synchronized public ArrayDeque<Double> gettem(){
//		return tem;
//	}
//	synchronized public ArrayDeque<Double> getfeu(){
//		return tem;
//	}	

	public void SetNetAddr(String str){
		netaddr=str;
	}
	public String toString2(){
		return "Num: "+zahl+"\nAdress:"+addr.getFirst()+"\nTemperatur:"+tem.getFirst()+"C\nLicht:"+feu.getFirst()+"LX\nLeuchtigkeit:"+leu.getFirst()+"rh";
	}
	
	public String toFileLog(){
		return "Num: "+zahl+ " Temperatur:"+tem.getFirst()+" Licht:"+feu.getFirst()+" Leuchtigkeit:"+leu.getFirst();
	}
	
	public String toFileLogWithAddress(){
		return "Num: "+zahl+" Adress:"+addr.getFirst()+" Temperatur:"+tem.getFirst()+" Licht:"+feu.getFirst()+" Leuchtigkeit:"+leu.getFirst();
	}
	
	public String toPrintLog(){
		return "Num: "+zahl+" Temperatur:"+tem.getFirst()+" Licht:"+feu.getFirst()+" Leuchtigkeit:"+leu.getFirst();
	}
	
	public void printData(){
		con.box(6,"Num: "+zahl,"Adress:"+addr.getFirst(),"Temperatur:"+tem.getFirst()+"C","Licht:"+feu.getFirst()+"LX","Leuchtigkeit:"+leu.getFirst()+"rh","Verbinden mit :"+netaddr);
	}
}
