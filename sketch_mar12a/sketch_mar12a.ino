const int LED_Rojo=12;
const int LED_Amarillo=9;
int entrada;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600); // Puerto serie
  pinMode(LED_Rojo, OUTPUT);
  pinMode(LED_Amarillo, OUTPUT);
  digitalWrite(LED_Rojo, LOW);
  digitalWrite(LED_Amarillo, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){ // Puerto serie disponible?
    entrada = Serial.read(); // Lo que se transmite desde Java
    if(entrada == '0'){
      digitalWrite(LED_Amarillo, LOW);
    } else if(entrada == '1'){
      digitalWrite(LED_Amarillo, HIGH);
    } else if(entrada == '2'){
      digitalWrite(LED_Rojo, LOW);
    } else if(entrada == '3'){
      digitalWrite(LED_Rojo, HIGH);
    }
  }
}
