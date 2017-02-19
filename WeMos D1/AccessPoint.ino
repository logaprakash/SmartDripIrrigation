/* Create a WiFi access point and provide a web server on it. */

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>


/* Set these to your desired credentials. */
const char *ssid = "sdi";
const char *password = "";
int status = WL_IDLE_STATUS;
int input1=1,input2=2,input3=3,input4=4;

WiFiServer server(80);

void setup()
{
   motor_init();
   delay(1000);
   Serial.begin(115200);
   //Serial.println();
   //Serial.print("Configuring rover header ...");
   
   Serial.println();
   Serial.print("Configuring access point...");
   WiFi.softAP(ssid);
   IPAddress myIP = WiFi.softAPIP();
   Serial.print("AP IP address: ");
   Serial.println(myIP);

   Serial.println("\nStarting server...");
  // start the server:
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
        return (MOISTURE/PRECISION);             
    }

void loop()
{
  WiFiClient client = server.available();
  if (!client) {
    return;
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
     Serial.println("Forward for 50 ms"); 
     forward(1000);
  }
    
  else if (req.indexOf("value=2") != -1){
    Serial.println("Backward for 50 ms"); 
    backward(1000);

    }

  else if (req.indexOf("value=3") != -1){
    Serial.println("Left for 50 ms"); 
    left(1000);

    }
    
  else if (req.indexOf("value=4") != -1){
    Serial.println("Right for 50 ms"); 
    right(1000);

    }
    
 else if (req.indexOf("value=5") != -1){
  Serial.println("Fetching for 50 ms"); 
  Serial.println(get_moisture(0,1,100));
  }
    
  else {
    Serial.println("invalid request");
    client.stop();
    return;
  }
   
  client.flush();

}


