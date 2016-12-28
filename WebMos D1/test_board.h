void led_glow(){
  pinMode(LED_BUILTIN,OUTPUT);
  digitalWrite(LED_BUILTIN,LOW);
}

void led_blink(int DELAY){
  pinMode(LED_BUILTIN,OUTPUT);
  digitalWrite(LED_BUILTIN,LOW);
  delay(DELAY);
  digitalWrite(LED_BUILTIN,HIGH);
  delay(DELAY);
}
