package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * binlog parser
 *
 * @author linux_china
 */
@Component
public class BinlogParser {
    @Autowired
    private Source source;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws Exception {
        BinaryLogClient client = new BinaryLogClient("localhost", 3307, "root", "123456");
        client.setBinlogFilename("mysql-bin.000003");
        client.setBinlogPosition(154L);
        client.registerEventListener(event -> {
            DmlSentence dmlSentence = new DmlSentence();
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) { //update
                dmlSentence.setType("update");
                dmlSentence.setData(data);
            } else if (data instanceof WriteRowsEventData) { //insert
                dmlSentence.setType("insert");
                dmlSentence.setData(data);
            } else if (data instanceof DeleteRowsEventData) {  //delete
                dmlSentence.setType("delete");
                dmlSentence.setData(data);
            }
            if (dmlSentence.getType() != null) {
                try {
                    Message<String> message = MessageBuilder.withPayload(objectMapper.writeValueAsString(dmlSentence)).build();
                    source.output().send(message);
                } catch (Exception ignore) {

                }
            }
        });
        client.connect();
    }

}
