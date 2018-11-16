package com.jit.robert;

import com.jit.robert.mqtt.MqttPushClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RobertApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobertApplication.class, args);


//		//测试
//		MqttPushClient.getInstance().publish(0,false,"fetchSensor","dfdfdfdfd");
//		String pubTopic = "qqq";
//		MqttPushClient.getInstance().subscribe(pubTopic);


		String result = "result";//硬件订阅
		MqttPushClient.getInstance().subscribe(result);

		//订阅//与Android协定的topic
		String sensorTopic = "b_fetchSensor";//读传感器组
		MqttPushClient.getInstance().subscribe(sensorTopic);
		String stateTopic = "b_fetchState";//读继电器状态
		MqttPushClient.getInstance().subscribe(stateTopic);
		String switchTopic = "b_switch";//机器人操作
		MqttPushClient.getInstance().subscribe(switchTopic);
		String driveTopic = "b_drive";//巡航1
		MqttPushClient.getInstance().subscribe(driveTopic);
		String drive2Topic = "b_drive2";//巡航2
		MqttPushClient.getInstance().subscribe(drive2Topic);
		String cameraTopic = "b_camera";//摄像头
		MqttPushClient.getInstance().subscribe(cameraTopic);
	}
}
