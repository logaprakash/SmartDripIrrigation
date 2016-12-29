
/* Four pin rover control
 * Functions - motor_init,forward,backward,left,right
 * Four pins are connected to HG7881 (L9110S) Dual Channel Motor Driver Module
 * HG7881 (L9110S) Dual Channel Motor Driver Module will further control the rover
 * Three wheels - Two motor wheels at back and one 360 degree wheel at front
 * input1 and input2 for leftMotor 
 * input3 and input4 for rightMotor
 */


int input1,input2,input3,input4;

void motor_init(int pin1,pin2,pin3,pin4){
  input1=pin1;
  input2=pin2;
  input3=pin3;
  input4=pin4;  
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
