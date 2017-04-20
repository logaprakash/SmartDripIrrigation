#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <Servo.h>   
#include "blob.h"

const char* ssid     = "hello";
const char* password = "12345678"; 
const char* hotspot_ssid     = "sdi";
const char* hotspot_password = ""; 
int status = WL_IDLE_STATUS;

float value;
Servo servo;  
WiFiServer server(80);

int input1=2,input2=0,input3=13,input4=12;

void setup() {
  motor_init();
  delay(1000);
  servo.attach(4);  
  delay(1000);
  servo.write(1);
  delay(500);
  Serial.begin(115200);
  delay(10);

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  
  delay(500);
  
  Serial.println();
  Serial.print("Configuring access point...");
  WiFi.softAP(hotspot_ssid);
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);

  Serial.println("\nStarting server...");
  server.begin();
  Serial.println("\nStarted server and waiting for client response...");
}
void motor_init(){
   pinMode(input1,OUTPUT);
   pinMode(input2,OUTPUT);
   pinMode(input3,OUTPUT);
   pinMode(input4,OUTPUT);  
}

void forward(int duration){
  
  //Left Motor
  digitalWrite(input1,HIGH);
  digitalWrite(input2,LOW);
  
  //Right Motor
  digitalWrite(input3,HIGH);
  digitalWrite(input4,LOW);

  delay(duration);
}

void backward(int duration){

  //Left Motor
  digitalWrite(input1,LOW);
  digitalWrite(input2,HIGH);

  //Right Motor
  digitalWrite(input3,LOW);
  digitalWrite(input4,HIGH);
  
  delay(duration);
}

void left(int duration){

  //Left Motor
  digitalWrite(input1,HIGH);
  digitalWrite(input2,LOW);

  //Right Motor
  digitalWrite(input3,LOW);
  digitalWrite(input4,LOW);
  
  delay(duration);
}

void right(int duration){
  
  //Left Motor
  digitalWrite(input1,LOW);
  digitalWrite(input2,LOW);

  //Right Motor
  digitalWrite(input3,HIGH);
  digitalWrite(input4,LOW);
  
  delay(duration);
}

void rest(int duration){
  
  //Left Motor
  digitalWrite(input1,LOW);
  digitalWrite(input2,LOW);

  //Right Motor
  digitalWrite(input3,LOW);
  digitalWrite(input4,LOW);
  
  delay(duration);
}

float get_moisture(int VAL_PROBE,int PRECISION,int TOTAL_DELAY) {
 
     float MOISTURE = 0;
       int i = 0;
       while(i<PRECISION){
        MOISTURE += analogRead(VAL_PROBE);
        delay((TOTAL_DELAY/PRECISION));
        i++;
        }
        servo.write(1);
        return (MOISTURE/PRECISION);             
    }
int moisture_count =0;
int simulate(){
  
   WiFiClient client = server.available(); 
  if (!client) {
    return 0;
  }
  
  // Wait until the client sends some data
  while(!client.available()){
    
    delay(1);
  }
  
  Serial.println(client.read());
  //Read the first line of the request
  String req = client.readStringUntil('\r');
  client.flush();
  
  // Match the request
 if (req.indexOf("value=1") != -1){
     Serial.println("Forward for 250 ms"); 
     forward(250);
     rest(1);
     return 0;
     moisture_count =0;
  }
    
  else if (req.indexOf("value=2") != -1){
    Serial.println("Backward for 250 ms"); 
    backward(250);
    rest(1);
    return 0;
    moisture_count =0;
    }

  else if (req.indexOf("value=3") != -1){
    Serial.println("Left for 250 ms"); 
    left(250);
    rest(1);
    return 0;
    moisture_count =0;
    }
    
  else if (req.indexOf("value=4") != -1){
    Serial.println("Right for 250 ms"); 
    right(250);
    rest(1);
    return 0;
    moisture_count = 0;
    }
    
 else if (req.indexOf("value=5") != -1){
  
  rest(1);
  if(moisture_count==0){
    servo.write(90);
    delay(1000);
    Serial.println("Fetching for 1000 ms");       
    value = get_moisture(0,4,3000);
    delay(1000);
    }
    else
    {
    
    }
    
           
        
  moisture_count = 1;
  return 0;
  }
  else if(req.indexOf("value=6") != -1){
    moisture_count =0;
    client.stop();
     return 1;
    }
  else {
    Serial.println("invalid request");
    client.stop();
    return 0;
  }
   
  client.flush();


  
  }

void execute(){
  int count = 1;
    String result = getBlob("sample","path");
  for(int i =0 ; i<result.length();i++){
       Serial.println( result.charAt(i) );
       if(result.charAt(i) == '1')
       {
         Serial.println("Forward for 1000 ms"); 
         forward(1000);
         rest(1);
       }
       else if(result.charAt(i) == '2')
       {
         Serial.println("Backward for 1000 ms"); 
         backward(1000);
         rest(1);
       }
        else if(result.charAt(i) == '3')
       {
          Serial.println("Left for 1000 ms"); 
          left(1000);
          rest(1);
       }
        else if(result.charAt(i) == '4')
       {
          Serial.println("Right for 1000 ms"); 
          right(1000);
          rest(1);
       }
       else if(result.charAt(i) == '5'){
        rest(1);
        Serial.println("Fetching for 1000 ms"); 
         servo.write(90);
         delay(1000);
         value = get_moisture(0,4,3000);         
         delay(1000);
       }
       else if(result.charAt(i) == '7'){
        
        updateBlob("sample","seg"+String(count),String(value));
        count++;
        }
    }
  }

void loop() {
  String MODE = getBlob("sample","path");
  if(MODE.equals("")){
  while(simulate()==0);
  }

while(1){
  
  String dec = getBlob("sample","switch");
  if(dec.equals("on"))
  {
    execute();
    updateBlob("sample","switch","off");
  }
  else if(dec.equals("automatic"))
  {
    String dec = getBlob("sample","time");
    Serial.println(dec);  
    String temp = checkTime(dec);
    if(!temp.equals("false")){
         execute();
         updateBlob("sample","time",temp);
      }
  }
  else if(dec.equals("simulate"))
  {
    while(simulate()==0);
  }
}
  
}
