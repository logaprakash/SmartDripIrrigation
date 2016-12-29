
/* Four pin rover control
 * Functions - motor_init,forward,backward,left,right
 * Four pins are connected to HG7881 (L9110S) Dual Channel Motor Driver Module
 * HG7881 (L9110S) Dual Channel Motor Driver Module will further control the rover
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
  digitalWrite(input1,HIGH);
  digitalWrite(input2,LOW);
  digitalWrite(input3,HIGH);
  digitalWrite(input4,LOW);
}

void backward(int duration){

}

void left(int duration){
  
}

void right(int duration){
  
}
