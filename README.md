# pja
Navod na spustenie :

-Zapnete aplikaciu PjaApplication

-Zapnete H2 databazu 'http://localhost:8080/h2-console/'
 
do JDBC URL vlozite  'jdbc:h2:file:C:/CESTA K PRIECINKU/pja/src/main/resources'(tuto path bude treba asi zmenit aj v application.properties)
 
username : idk , password : idc 
 
napisete 'SELECT * FROM MICROSERVICE' a date run co vam ukaze vsetky data v db (na zaciatku ziadne niesu, po znovuspusteni aplikacie sa db zresetuje)

-Na 'http://localhost:8080/' sa nachadza jednoduchy FE alebo sa da aj cez swagger 'http://localhost:8080/swagger-ui/'

-Aplikaciu som aj zdockerizoval len si ho musite zbuildit ' ./mvnw package', 'docker build -t app .','docker run -p 8080:8080 app' lenze bohuzial H2 databaza nefunguje cez remote connection 