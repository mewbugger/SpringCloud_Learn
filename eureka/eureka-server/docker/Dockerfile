FROM openjdk:8
ENV workdir=/root/opt/environment/eureka-serer
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8761
CMD ["java","-jar","eureka-server-1.0.jar"]