package com.jit.robert.serviceimpl;


import com.jit.robert.mqtt.MqttPushClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttTest {
    @Autowired
    private MqttPushClient mqttPushClient;
    @Test
    public void sendHello(){
        String kdTopic = "fetchSensor";
        mqttPushClient.publish(0, false, kdTopic, "");
        mqttPushClient.subscribe("result");

    }

}
