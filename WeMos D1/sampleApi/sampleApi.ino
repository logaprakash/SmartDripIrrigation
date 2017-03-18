#include <ESP8266WiFi.h>
#include "getBlob.h"
#include "updateBlob.h"
const char* ssid     = "hello";
const char* password = "12345678";


int input1=2,input2=0,input3=13,input4=12;
void setup() {
  motor_init();
  delay(1000);
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
        return (MOISTURE/PRECISION);             
    }

void loop() {
  String dec = getBlob("sample","switch");
  if(dec.equals("on"))
  {

  String result = getBlob("sample","path");
  for(int i =0 ; i<result.length();i++){
       Serial.println( result.charAt(i) );
       if(result.charAt(i) == '1')
       {
         Serial.println("Forward for 250 ms"); 
         forward(250);
         rest(1);
       }
       else if(result.charAt(i) == '2')
       {
         Serial.println("Backward for 250 ms"); 
         backward(250);
         rest(1);
       }
        else if(result.charAt(i) == '3')
       {
          Serial.println("Left for 250 ms"); 
          left(250);
          rest(1);
       }
        else if(result.charAt(i) == '4')
       {
          Serial.println("Right for 250 ms"); 
          right(250);
          rest(1);
       }
       else if(result.charAt(i) == '5'){
        rest(1);
        Serial.println("Fetching for 250 ms"); 
        Serial.println(get_moisture(0,1,100)); 
       }
    }
    result = updateBlob("sample","switch","off");
  }
}
