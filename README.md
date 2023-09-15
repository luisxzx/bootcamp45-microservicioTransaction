cambiar lo que hay en application.properties por lo siguiente para que funcione en local :


spring.application.name=microservicios-transaccion
spring.config.import=configserver:http://localhost:8888
server.port=8089
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
spring.cloud.config.enabled=true





