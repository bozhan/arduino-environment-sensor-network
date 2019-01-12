#include <avr/sleep.h>
#include <avr/wdt.h>
#include <avr/power.h>
#define LED 13

int lightPin = 2;  //define a pin for Photo resistor
int humPin = 5;
int sleepCounter = 1;
int x;

ISR(WDT_vect){ // watchdog interrupt
  wdt_disable();  // disable watchdog
}

void enableWatchdog(const byte interval){ 
  MCUSR = 0;                          // reset various flags
  WDTCSR |= 0b00011000;               // see docs, set WDCE, WDE
  WDTCSR =  0b01000000 | interval;    // set WDIE, and appropriate delay

  wdt_reset();
  set_sleep_mode (SLEEP_MODE_PWR_DOWN); 
  sleep_mode();            // now goes to Sleep and waits for the interrupt
}

void setup(){
  Serial.begin(9600);
  Serial.println("Watchdog ADA Method");
}  // end of setup

void loop(){
  sensors();
  readSleepValue();
  sleep(sleepCounter); //multiple of 8sec
}

void sensors(){ 
  digitalWrite (LED, HIGH);  // awake
            
  //LM 335 for temp display though serial
  Serial.print("Temp:");//output "Tep:"
  Serial.print((125*analogRead(0))>>8);//output and display value of temp
  Serial.println("C");//original output
  
  //LDR 07 for light.
  Serial.print("light:");//output "light:"
  Serial.print(analogRead(lightPin)); //Write the value of the photoresistor to the serial monitor.
  Serial.println("LX");//original output
  
  //SHS-A3 for humidity
  Serial.print("hum:");//output "hum:"
  Serial.print(analogRead(humPin)); //Write the value of the photoresistor to the serial monitor.
  Serial.println("rh");//original output
  
  delay(500);
}

void readSleepValue(){
  Serial.println("Read serial value:" + Serial.read());
}

void sleep(int c){
  Serial.println("Going to sleep in one second...");
  delay(1000);
  digitalWrite (LED, LOW);  // asleep
  for(int i=0; i<=c; i++){
    enableWatchdog(0b100001);  // 8 seconds
  }
}


