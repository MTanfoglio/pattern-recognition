# pattern-recognition

L'idea alla base del progetto è il confronto tra i vari punti presenti all'interno dello spazio in modo da recuperare l'angolazione presente tra di essi.

I punti aventi ugual angolazione saranno quelli che appartengono a un segmento.

Maven-project on JDK8.

Librerie aggiuntive:
- javax.servlet => `4.0.1`
- Gson => `2.2.2`
- Guava => `30.1-jre`
- Lombok => `1.18.6`

## Installazione

- cd `pattern-recognition`
- mvn clean package
- all'interno della folder `target` sarà possibile recuperare il file `PatternRecognition-1.0.war` da deployare sotto il proprio application server

Viene inoltre fornita, all'interno della folder `docs`, la postman-collection utile ad interrogare il servizio.


## Improvements

Improvements points:
- Exceptions handling
- Logging system
