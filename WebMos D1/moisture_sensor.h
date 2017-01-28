
 
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
float get_voltage(int VAL_PROBE,int PRECISION,int TOTAL_DELAY){
	float value = get_moisture(VAL_PROBE,PRECISION,TOTAL_DELAY);
    float voltage= value * (5.0 / 1023.0);
	return voltage;
	
}
