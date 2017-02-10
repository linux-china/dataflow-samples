Dataflow Samples
================
Dataflow sample with Task, stream applications.

### Setup environment

Start MySQL, Redis and Kafka
```
$ docker-compose up -d
$ terraform apply
```

### Startup Data Flow Server
Run Spring Cloud Data Flow Server Boot Application, then execute dataflow shell.
```
$ jenv repo update
$ jenv install dataflow 1.1.2
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

* import apps:
```
dataflow:> app import --uri http://repo.spring.io/libs-release-local/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Avogadro.RELEASE/spring-cloud-stream-app-descriptor-Avogadro.RELEASE.stream-apps-kafka-10-maven
```

* Security protection problem. Please add following code in application.properties:
```
security.basic.enable: false
management.security.enabled: false
```
### Local Demo

```
$ java -jar time-source-kafka-10-1.1.1.RELEASE.jar --server.port=0 --spring.cloud.stream.bindings.output.destination=ticktockTopic
$ java -jar log-sink-kafka-10-1.1.1.RELEASE.jar --server.port=0 --spring.cloud.stream.bindings.input.destination=ticktockTopic
```

### References

* Spring Cloud DataFlow: http://cloud.spring.io/spring-cloud-dataflow/
* Data Flow Streams: https://github.com/spring-cloud/spring-cloud-dataflow/blob/master/spring-cloud-dataflow-docs/src/main/asciidoc/streams.adoc
