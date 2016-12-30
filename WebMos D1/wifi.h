#include <ESP8266WiFi.h>
WiFiClient client;
WiFiServer server(80);

void initWifi(char* ssid, char* password) {
   Serial.begin(115200);
   Serial.print("Connecting to ");
   Serial.print(ssid);
   WiFi.begin(ssid, password);
   

   while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
   }
  Serial.print("\nWiFi connected, IP address: ");
  Serial.println(WiFi.localIP());
  delay(500);
}


void client_available(){
  client = server.available();
  while(!client){
  }
}

void client_data_available(){
  while(!client.available()){
    delay(1);
  }
}

String client_request(){
 return client.readStringUntil('\r'); 
}

void client_flush(){
  client.flush();
}

void client_print(String msg){
  client.print(msg);
}
void client_println(String msg){
  client.println(msg);
}

