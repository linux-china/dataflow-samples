Dataflow Samples
================
Dataflow sample with Task, stream applications.

### Setup environment

Start MySQL, Redis and Kafka
```
$ docker-compose up -d
$ terraform apply
```

### Start Spring Cloud Data Flow Local-Server

```
$ wget http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server-local/1.2.0.RELEASE/spring-cloud-dataflow-server-local-1.2.0.RELEASE.jar
$ java -jar spring-cloud-dataflow-server-local-1.2.0.RELEASE.jar

```
### Startup Data Flow Server
Run Spring Cloud Data Flow Server Boot Application, then execute dataflow shell.
```
$ jenv repo update
$ jenv install dataflow 1.2.0
$ dataflow-shell.sh
```
### Install DataFlow Shell
If you use jenv, it's easy as following:
```
$ jenv repo update
$ jenv install dataflow 1.2.0 
$ dataflow-shell.sh
```
### register application

```
  dataflow:> app register --name task-demo --type task --uri file:///Users/linux_china/cloud/dataflow/task-demo-1.0.0-SNAPSHOT.jar

  dataflow:> app register --name ticktock --type source --uri file:///Users/linux_china/cloud/dataflow/time-source-kafka-10-1.1.1.RELEASE.jar

  dataflow:> app register --name log-sink-kafka --type sink --uri file:///Users/linux_china/cloud/dataflow/log-sink-kafka-10-1.1.1.RELEASE.jar
```
### Run Stream
```
dataflow:> stream create --definition "ticktock | log-sink-kafka" --name ticktock-log

dataflow:> stream deploy ticktock-log --properties "app.ticktock.spring.cloud.stream.bindings.output.destination=ticktockTopic,app.log-sink-kafka.spring.cloud.stream.bindings.input.destination=ticktockTopic"
```

### FAQ

* import apps: please cd stream-apps-download and execute "mvn dependency:resolve" to download all artifacts, then execute import in dataflow shell.
```
dataflow:> app import --uri http://repo.spring.io/libs-release-local/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Avogadro.SR1/spring-cloud-stream-app-descriptor-Avogadro.SR1.stream-apps-kafka-10-maven
dataflow:> app import --uri http://repo.spring.io/libs-release-local/org/springframework/cloud/task/app/spring-cloud-task-app-descriptor/Addison.RELEASE/spring-cloud-task-app-descriptor-Addison.RELEASE.task-apps-maven
```

* Security protection problem. Please add following code in application.properties:
```
security.basic.enable: false
management.security.enabled: false
```

* differences between arguments and properties:
```
dataflow:> task launch mytask --arguments "--server.port=8080,--foo=bar"
dataflow:> task launch mytask --properties "app.timestamp.spring.cloud.deployer.foo1=bar1,app.timestamp.foo2=bar2"
```
arguments with '--' and properties with pair split by comma.

### Local Demo

```
$ java -jar time-source-kafka-10-1.1.1.RELEASE.jar --server.port=0 --spring.cloud.stream.bindings.output.destination=ticktockTopic
$ java -jar log-sink-kafka-10-1.1.1.RELEASE.jar --server.port=0 --spring.cloud.stream.bindings.input.destination=ticktockTopic
```

### References

* Spring Cloud DataFlow: http://cloud.spring.io/spring-cloud-dataflow/
* Spring Cloud DataFlow Document: http://docs.spring.io/spring-cloud-dataflow/docs/1.2.0.RELEASE/reference/htmlsingle/
* Spring Cloud Stream: https://cloud.spring.io/spring-cloud-stream/
* Spring Cloud Task: http://cloud.spring.io/spring-cloud-task/
