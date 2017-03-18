
const char* host = "cyberknights.in";
String getBlob(String container,String segment){

  Serial.print("connecting to ");
  Serial.println(host);

  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return "Failed";
  }

  // We now create a URI for the request
  String url = "/api/sdi/getBlobContent.php?name="+container+"&segment="+segment;

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
      return "Failed";
    }
  }
  int count = 0;
  String result;
  while(client.available()) {
    String line = client.readStringUntil('\r');
    count++;
    if(count>10)
      result = line;
  } 
  result.trim();
  Serial.println("Response: "+ result);

  

  Serial.println();
  Serial.println("closing connection");
  return result;
  
  }
