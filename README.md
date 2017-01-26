#Smart Drip Irrigation using IOT 

<h2>Objective</h2>
   <p>The objective of the project is to over come the current methods of automation in drip irrigation of farms with even more cost and energy efficient system and also more user friendly at live feeds display</p>
   
<h2>Abstract</h2>
   <p>This system is built to offer farmers, an automation in drip irrigation system. The proposed system uses a solar powered rover with sensors, cloud service, an android app to monitor the field ,control the rover manually as well as to switch the motor on or off for drip services. This system provides the user with real time data of the field. This is done by a WebMos which is attached to the rover and it also alerts the user when there is an overflow of water for a particular plant are area  by using the soil moisture sensor which sends the readings to the cloud. Obstacle detection technique is implemented in the rover which helps in sending notification to the user when the rover gets disturbed in its way. By using our system the drip irrigation can be done by the farmer (user) at any time anywhere in an efficient and effective way.</p>
  
<h2>Innovation</h2>
<li>The map for the farm is prepopulated to the rover for its automatic movement in the farm, it can also be controlled and simulated manually using the android app if there is any change in the movement for next time. 
<li>The rover is controlled by a solar powered battery in which the solar plate is just fixed at the top of the rover. 
<li>Digital image processing technique is also used to alert the user when the plants get affected by certain diseases (which changes the color of the leaf or the stem gets broken).

<h2>Technology</h2>
<h4>Software:</h4>
  <li>Android app
  <li>Azure Cloud 
  
<h4>Hardware:</h4>
  <li>WebMos D1 with ESP8266 - 2
  <li>Soil Moisture Meter Testing Module - 1
  <li>Motor driver l293d - 2
  <li>Chaos with four motors attached with wheels at each one - 1
  <li>Camera - 1
  <li>Solar Batteries -1 
  
 <h2>Block Diagram</h2>
 ![Alt text](/blockDiagram.jpg "Block Diagram")

<h2>TimeLine:</h2>
<h3>Webmos:</h3>
<b><li> 27th Dec 2016 :</b> GitHub Repo started by Logaprakash Raju
<b><li> 28th Dec 2016 :</b> Testing board header added (Tested board WebMos D1 with ESP8266 - Tested OK) by Logaprakash Raju
<b><li> 29th Dec 2016 :</b> Rover header added (Not Tested Yet) by Logaprakash Raju
<b><li> 30th Dec 2016 :</b> Wifi and Time header added (Time and WiFi functions - Tested OK) by Logaprakash Raju

<h3>Android:</h3>
<b><li> 31th Dec 2016 :</b> Android bulid created with minSDK (Kikat 4.4) by Logaprakash Raju
<b><li> 11th Jan 2017 :</b> Basic UI added by Logaprakash Raju
<b><li> 14th Jan 2017 :</b> Simulate page added by Logaprakash Raju
<b><li> 17th Jan 2017 :</b> Icons added by Logaprakash Raju
<b><li> 18th Jan 2017 :</b> Added listView and updated colors by Logaprakash Raju
<b><li> 22th Jan 2017 :</b> Add rover feature added (Unique IP and Name - CHECK) by Logaprakash Raju
<b><li> 23th Jan 2017 :</b> View all rovers feature in list view added by Logaprakash Raju
<b><li> 24th Jan 2017 :</b> Rover class with generic funtions added by Logaprakash Raju
<b><li> 25th Jan 2017 :</b> Delete particular rover and clear whole list features added by Logaprakash Raju
