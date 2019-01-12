#include <avr/sleep.h>
#include <avr/power.h>
#include <avr/wdt.h>

int lightPin = 2;  //define a pin for Photo resistor
int humPin = 5;  //define a pin for Photo resistor

void enterSleep(void){
  set_sleep_mode(SLEEP_MODE_PWR_DOWN); 
  sleep_enable();
  sleep_mode();/* Now enter sleep mode. */ 
  /* The program will continue from here after the WDT timeout*/
  sleep_disable(); /* First thing to do is disable sleep. */
  power_all_enable();/* Re-enable the peripherals. */
} 

volatile int f_wdt=1;
ISR(WDT_vect){   
  f_wdt++;
}
void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
 /*** Setup the WDT ***/   
MCUSR &= ~(1<<WDRF);  /* clean flag of restart. */
  /* In order to change WDE or the prescaler, we need to
   * set WDCE (This will allow updates for 4 clock cycles).
   */
  WDTCSR |= (1<<WDCE) | (1<<WDE);
  /* setup new overtime of watchdog */
  WDTCSR = 1<<WDP1 | 1<<WDP2; /* 1.0 seconds */ 
  /*  setup interupt modes  */
  WDTCSR |= _BV(WDIE); 
}
void sensors(){
  //LM 335 for temp display though serial
Serial.print("Tep:");//output "Tep:"
Serial.print((125*analogRead(0))>>8);//output and display value of temp
Serial.println("C");//original output
//LDR 07 for light.
Serial.print("light:");//output "light:"
Serial.print(analogRead(lightPin)); //Write the value of the photoresistor to the serial monitor.
Serial.println("cd");//original output
//SHS-A3 for humidity
Serial.print("hum:");//output "hum:"
Serial.print(analogRead(humPin)); //Write the value of the photoresistor to the serial monitor.
Serial.println("kohm");//original output
  }
  
void loop() {
   if(f_wdt>=10){
       sensors();
       delay(500);
       f_wdt=0;     
  } 
enterSleep();

}
