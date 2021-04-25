/*
 Basic ESP8266 MQTT example
 This sketch demonstrates the capabilities of the pubsub library in combination
 with the ESP8266 board/library.
 It connects to an MQTT server then:
  - publishes "hello world" to the topic "outTopic" every two seconds
  - subscribes to the topic "inTopic", printing out any messages
    it receives. NB - it assumes the received payloads are strings not binary
  - If the first character of the topic "inTopic" is an 1, switch ON the ESP Led,
    else switch it off
 It will reconnect to the server if the connection is lost using a blocking
 reconnect function. See the 'mqtt_reconnect_nonblocking' example for how to
 achieve the same result without blocking the main loop.
 To install the ESP8266 board, (using Arduino 1.6.4+):
  - Add the following 3rd party board manager under "File -> Preferences -> Additional Boards Manager URLs":
       http://arduino.esp8266.com/stable/package_esp8266com_index.json
  - Open the "Tools -> Board -> Board Manager" and click install for the ESP8266"
  - Select your ESP8266 in "Tools -> Board"
*/

#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BME280.h>

#define SEALEVELPRESSURE_HPA (1013.25)

Adafruit_BME280 bme; // I2C

// Update these with values suitable for your network.
const char* ssid = "AndroidM";
const char* password = "C++jesmrt";

// Update these with values suitable for your network.
const char* mqtt_server = "80.211.215.27"; 
const char* mqtt_user = "sandra";
const char* mqtt_pass= "LAIP3lath*meey!peak";

WiFiClient espClient;
PubSubClient client(espClient);
long lastMsg = 0;
char msg[256];
int value = 0;

const byte interruptPin = 13; //D7
int val = 0;
volatile boolean checkInterrupt = false;
boolean rain = false;
int numberOfRain = 0;
int numberOfNoRain = 0;
int timer = 300000; //5 minutes
unsigned long debounceTime = 60000; //1000
unsigned long lastDebounce = 0;
unsigned long lastTime = 0;
boolean firstMeasure = true;

const int AirValue = 330;   //you need to replace this value with Value_1
const int WaterValue = 600;  //you need to replace this value with Value_2

void ICACHE_RAM_ATTR handleInterrupt(){
  Serial.print("som tu");
  checkInterrupt = true;
//  snprintf(msg, sizeof(msg), "1");
  }

//  void ICACHE_RAM_ATTR handleInterruptRising(){
//  Serial.print("som tu 5 ");
//  checkInterruptRising = true;
////  snprintf(msg, sizeof(msg), "1");
//  }

void setup_wifi() {
  // Connecting to a WiFi network
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void reconnect() {
  // Loop until we're reconnected
  Serial.println("In reconnect...");
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP_Garden1", mqtt_user, mqtt_pass)) {
      Serial.println("connected");
     client.subscribe("commands");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(9600);
  
   // default settings
  // (you can also pass in a Wire library object like &Wire2)
  //status = bme.begin();  
  if (!bme.begin(0x76)) {
    Serial.println("Could not find a valid BME280 sensor, check wiring!");
    while (1);
  }
  
  // rain sensor
  pinMode(interruptPin, INPUT_PULLUP);
//  attachInterrupt(digitalPinToInterrupt(interruptPin), handleInterrupt, CHANGE); //start raining

  setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void measureTemp(){
    float temp =  bme.readTemperature();

    Serial.print("Temperature message: ");
    Serial.println( temp);
    snprintf(msg, sizeof(msg), "%.02f", temp);
    client.publish("temp1", msg);
  }
  
void measurePressure(){
   float pressure = bme.readPressure() / 100.0F;

    Serial.print("Pressure message: ");
    Serial.println(pressure);
    snprintf(msg, sizeof(msg), "%.02f", pressure);
    client.publish("pressure1", msg);
  }

  
void measureHumidity(){
  float humidity = bme.readHumidity();
  
    Serial.print("Humidity message: ");
    Serial.println( humidity);
    snprintf(msg, sizeof(msg), "%.02f", humidity);
    client.publish("humidity1", msg);
}


void measureSoilMoisture(){
  int soilMoistureValue = analogRead(A0);  //put Sensor insert into soil
  int soilmoisturepercent = map(soilMoistureValue, AirValue, WaterValue, 0, 100);
  if(soilmoisturepercent > 100)
  {
    Serial.println("100 %");
    snprintf(msg, sizeof(msg), "%d", 100);
    }
    else if(soilmoisturepercent < 0)
  {
    Serial.println("0 %");
    snprintf(msg, sizeof(msg), "%d", 0);
  }
  else{
  Serial.print(soilmoisturepercent);
  Serial.println(" %");
  snprintf(msg, sizeof(msg), "%d", soilmoisturepercent);
  }
  client.publish("soil1", msg);
}


void callback(char* topic, byte* payload, unsigned int length){
      Serial.print("Prijate spravy: ");
      Serial.println(topic);
      String message="";
      String timeStr="";
      for (int i=0; i<length; i++){
//        Serial.print((char) payload[i]);
        if( ((char) payload[0] == 'M') && (i>0)) {
            timeStr = timeStr + (char) payload[i];
          }
          else {
            message = message + (char) payload[i];
          }
        } 

        if(timeStr.length() > 0) {
           timer = timeStr.toInt();
        }
//        if(message=="measure") {
//          measureTemp();
//          measureHumidity();
//          measurePressure();
//        }
  }

void loop() {
  if (!client.connected()) {
    reconnect();
  }
    client.loop();
    
    long now = millis();
    
    if(firstMeasure == true){
       lastMsg = now;
    measureHumidity();
    measureTemp();
    measurePressure();
    measureSoilMoisture();
    firstMeasure = false;
      }
// client.println(1.8 * bme.readTemperature() + 32); FAHRENHEIT
// client.println(bme.readAltitude(SEALEVELPRESSURE_HPA));

  if (now - lastMsg > timer) { //every 5 minutes
    lastMsg = now;
    measureHumidity();
    measureTemp();
    measurePressure();
    measureSoilMoisture();
  }

if( (now - lastTime)  > debounceTime ) {
  lastTime = now;
      Serial.print("MERANIE: ");
      
  if(digitalRead(interruptPin) == 0) {
     Serial.print("0 ");
    numberOfRain++;
    if(numberOfRain == 4)  {
      numberOfNoRain = 0;
      numberOfRain = 0;
      if(rain == false){
        rain = true;
        client.publish("rain1", "1");
        }
    }
    }
  else{
         Serial.print("1 ");
    numberOfNoRain++;
    if(numberOfNoRain == 4)  {
      numberOfNoRain = 0;
      numberOfRain = 0;
      if(rain == true){
        rain = false;
        client.publish("rain1", "0");
        }
    }
    }
  }
}
