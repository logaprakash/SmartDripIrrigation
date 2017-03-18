/* Create a WiFi access point and provide a web server on it. */

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>


/* Set these to your desired credentials. */
const char *ssid = "sdi";
const char *password = "";
int status = WL_IDLE_STATUS;
int input1=2,input2=0,input3=13,input4=12;
WiFiServer server(80);
const char* host = "cyberknights.in";

void setup()
{
   motor_init();
   delay(1000);
   Serial.begin(115200);
   WiFi.begin("hello", "12345678");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
   
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

String getPath(){
  String line = "";
  delay(5000);
  Serial.print("connecting to ");
  Serial.println(host);

  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return line;
  }

  // We now create a URI for the request
  String url = "/api/sdi/getBlobContent.php?name=sample&segment=path";

  Serial.print("Requesting URL: ");
  Serial.println(url);

  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
               
  int timeout = millis() + 5000;
  while (client.available() == 0) {
    if (timeout - millis() < 0) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return line;
    }
  }
  while(client.available()) {
    line = client.readStringUntil('\r');
    Serial.print(line);
  } 


  

  Serial.println();
  Serial.println("closing connection");
  return line;
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
     Serial.println("Forward for 250 ms"); 
     forward(250);
     rest(1);
     return;
  }
    
  else if (req.indexOf("value=2") != -1){
    Serial.println("Backward for 250 ms"); 
    backward(250);
    rest(1);
    return;
    }

  else if (req.indexOf("value=3") != -1){
    Serial.println("Left for 250 ms"); 
    left(250);
    rest(1);
    return;
    }
    
  else if (req.indexOf("value=4") != -1){
    Serial.println("Right for 250 ms"); 
    right(250);
    rest(1);
    return;
    }
    
 else if (req.indexOf("value=5") != -1){
  rest(1);
  Serial.println("Fetching for 250 ms"); 
  Serial.println(get_moisture(0,1,100));
  return;
  }
  else if(req.indexOf("value=6") != -1){
while(1){
       String path = getPath();
       
    }
    }
  else {
    Serial.println("invalid request");
    client.stop();
    return;
  }
   
  client.flush();




}


