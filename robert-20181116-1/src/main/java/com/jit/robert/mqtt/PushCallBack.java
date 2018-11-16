package com.jit.robert.mqtt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.*;

/**
 *Mqtt消息监听类
 */

public class PushCallBack implements MqttCallback {
    protected final Log logger = LogFactory.getLog(this.getClass());

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
        logger.info("连接断开，可以做重连");
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
        logger.info("deliveryComplete---------" + token.isComplete());

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
        logger.info("接收消息主题 ："+topic);
        logger.info("接收消息Qos ："+message.getQos());
        logger.info("接收消息内容 ："+new String(message.getPayload()));
//        String topic1 = "qqq";
//        MqttPushClient.getInstance().publish(1,true,topic1,"34343qq4");

        switch(topic){
            case "b_fetchSensor":
                MqttPushClient.getInstance().publish(message.getQos(),false,"fetchSensor",new String(message.getPayload()));
                logger.info("转发消主题 ："+"fetchSensor");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            case "b_fetchState":
                MqttPushClient.getInstance().publish(message.getQos(),false,"fetchState",new String(message.getPayload()));
                logger.info("转发消主题 ："+"fetchState");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            case "b_switch":
                MqttPushClient.getInstance().publish(message.getQos(),false,"switch",new String(message.getPayload()));
                logger.info("转发消主题 ："+"switch");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            case "b_drive":
                MqttPushClient.getInstance().publish(message.getQos(),false,"drive",new String(message.getPayload()));
                logger.info("转发消主题 ："+"drive");
                logger.info("接收消息Qos ："+message.getQos());
                logger.info("接收消息内容 ："+new String(message.getPayload()));
                break;
            case "b_drive2":
                MqttPushClient.getInstance().publish(message.getQos(),false,"drive2",new String(message.getPayload()));
                logger.info("转发消主题 ："+"drive2");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            case "b_camera":
                MqttPushClient.getInstance().publish(message.getQos(),false,"camera",new String(message.getPayload()));
                logger.info("转发消主题 ："+"fetchSensor");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            case "result"://硬件订阅结果,发布给android
                MqttPushClient.getInstance().publish(message.getQos(),false,"b_result",new String(message.getPayload()));
                logger.info("转发消主题 ："+"b_result");
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;
            default:
                MqttPushClient.getInstance().publish(message.getQos(),false,topic,new String(message.getPayload()));
                logger.info("转发消主题 ："+topic);
                logger.info("转发消息Qos ："+message.getQos());
                logger.info("转发消息内容 ："+new String(message.getPayload()));
                break;

        }
    }

}
