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


