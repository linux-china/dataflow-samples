provider "kafka" {
  zookeeper = "localhost"
}

resource "kafka_topic" "testTopic" {
  name = "testTopic"
  partitions = 1
  replication_factor = 1
  cleanup_policy = "delete"
}

resource "kafka_topic" "ticktockTopic" {
  name = "ticktockTopic"
  partitions = 1
  replication_factor = 1
  cleanup_policy = "delete"
}