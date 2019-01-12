# arduino-environment-sensor-network
A power efficient environment sensor network from arduino units.

# Environment Sensor Monitoring System
Take periodic or on demand measurements off sensors controlled by arduono units.
User a raspberryPi as a communication hub to deliver sensor results to a database and a mobile android application.
The sensor frequency can be controlled and updated by the rPi. THe rPi receives data and sends when next update should be sent
Since the rPi used in the project is powerful enough, it can also be used as a cloud store and a web server and/or service provider.

# Practical project hardware:
	Arduino UNO/ATmega328
	Raspberry Pi 2 B
	WiFi Transever for Arduino
	WiFi Transever for R Pi
	Sensors: 2 Humidity (air and surface), 1 Light, 1 Temperature

# Tasks of components
## Arduino
Establish Sensor communication
Establish XBEE interface communication
Establish Wireless communication with Raspberry Pi
Develop communication protocol with Raspberry Pi
Implement Sleep Mode
Record power consumption
    Normal working conditions w/o sensors reading
    Normal working condition with sensors reading
    During data transmission - sensort turned off
    During data transmission - sensort turned on
    During sleep mode
    
## Raspberry Pi
Setup Operationg System
Establish XBEE interface communication
Establish Wireless communication with Arduino
Develop communication protocol with Arduino
Setup Database
Develop Database interface and save Arduino data persistantly
Setup Web server
Develop Webpage and report data

# Communication & Setup
To setup communication between coordinator and router we should setup the xctu values
of DH Destination Address High and DL Destination Number Low in the coordinator should be matching the respective values of  
SH Serial Number High and SL Serial Number Low in the Router\end-device configuration
img/xbee cooridinator configuration.png

Arduino Setup
xb24 zigbee route AT

zigbee rPi setup
xb24 Zigbee coordinator AT
