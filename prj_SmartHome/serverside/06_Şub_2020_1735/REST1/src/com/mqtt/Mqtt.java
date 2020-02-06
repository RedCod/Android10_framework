package com.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt /*implements MqttCallback*/{
	/**
	 * Mqtt komut işletim merkezi:http://www.bytesofgigabytes.com/mqtt-tutorial/
	 * 
	 * -Mobil app tarafında mqtt komutları direkt olarak işletilir,yani burayı kullanmaz. Ancak Server tarafında: Scenario,Automation'lar bu class'ı kullanacaktır.
	 */
	/*
	 * used by:
	 * -Automation.java
	 * -Scenario.java
	 */
	
	/** The broker url. */
	private static  final String SERVER_URL = "tcp://192.168.1.109:1883";
	private String USER_NAME = "admin";
	private String PASSWORD = "123";
	/** The client id. */
	private static final String clientId = "clientId";
	/** The topic. */
	private static final String topic = "test";
	//MQTT:
	private static MemoryPersistence persistence;
	private static MqttClient mqttClient;
	private static MqttConnectOptions mqttConnectOptions;
	
	/*//örnek kullanım
	public static void main(String[] args) {
		Mqtt mqtt = new Mqtt();
		mqtt.subscribe("test");
		
		for(int i=0;i<10;i++)
			mqtt.publisher("test", "mesaj:" + i);
		
		try {
			mqtt.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}//main
	 */
	
	public Mqtt() {
		connect();
	}

	/**
	 * Mqtt execute command.
	 * @param command	//command to execute
	 * @return			//commant execute output.
	 */
	/*public String execute(String topic,String command) {
		//TODO: mqtt execute command..
		//..
	 
		return "command output";
	 }//executeCommand()
	*/
	
	/**
	 * connect Mqtt Server.
	 */
	private void connect() {
		if(mqttClient != null && mqttClient.isConnected()) {
			System.out.println("Bağlantı var!");
			return;
		}

		try
		{
			persistence = new MemoryPersistence();
			mqttClient = new MqttClient(SERVER_URL, clientId, persistence);
			mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(true);
			/*//not used now
              mqttConnectOptions.setUserName(USER_NAME);
              mqttConnectOptions.setPassword(PASSWORD.toCharArray());
             */
			System.out.println("checking");
			System.out.println("Mqtt Connecting to broker: " + SERVER_URL);
			mqttClient.connect(mqttConnectOptions);
			System.out.println("Mqtt Connected");
			//mqttClient.setCallback(this);
			/*mqttClient.subscribe(topic);
			  System.out.println("Subscribed and Listening now");
			  */
		} catch (MqttException mex) {
			System.out.println(mex.getMessage());
		}
	}//connect()
	
	/**
	 * Disconnect mqtt server.
	 * @throws MqttException
	 */
	private void disconnect() throws MqttException {
		if(mqttClient != null && mqttClient.isConnected()) {
			mqttClient.disconnect();
			mqttClient.close();
			System.exit(0);//
		}
	}//disconnect()
	
	/**
	 * Clear mqtt. So disconnect.
	 * @throws MqttException
	 */
	public void clear() throws MqttException{
		disconnect(); 
	} 
	
	/**
	 * subscribe to mqtt
	 * @param topic //specify topic
	 */
	public void subscribe(String topic) {
		try
		{
			mqttClient.subscribe(topic);
			System.out.println("Subscribed and Listening now.Topic:" + topic);
		} catch (MqttException mex) {
			System.out.println(mex.getMessage());
		}
	}//subscribe()
	
	/**
	 * unsubscribe to mqtt.
	 * @param topic  //topic
	 */
	public void unsubscribe(String topic) {
	     try {
			mqttClient.unsubscribe(topic);
			System.out.println("Unsubscribed Topic:" + topic);
		} catch (MqttException mex) {
			System.out.println(mex.getMessage());
		}
	}//unsubscribe()
	
	/**
	 * unsubscribe to mqtt
	 * @param topics //topic array.
	 */
	public void unsubscribe(String[] topics) {
	     try {
			mqttClient.unsubscribe(topics);
			System.out.println("Unsubscribed Topics:" + topic.toString());
		} catch (MqttException mex) {
			System.out.println(mex.getMessage());
		}
	}//unsubscribe()
	
	/**
	 * publisher message/content on mqtt
	 * @param content  //message OR content
	 * @throws Exception 
	 */
	public void publisher(String topic,String content,MqttCallback mqttCallback) throws Exception {
        //content      = "Temp:20,Humi:70";
        int qos             = 0;
        try {
            System.out.println("Publishing message:"+content);
            MqttMessage mqttMessage = new MqttMessage(content.getBytes());
            mqttMessage.setQos(qos);
            mqttClient.publish(topic, mqttMessage);
            mqttCallback.messageArrived(topic, mqttMessage);//for callback
            System.out.println("Message published");
            /*mqttClient.disconnect();
              mqttClient.close();
              System.exit(0);*/
            } catch(MqttException mex) {
            System.out.println("reason "+mex.getReasonCode());
            System.out.println("msg "+mex.getMessage());
            System.out.println("loc "+mex.getLocalizedMessage());
            System.out.println("cause "+mex.getCause());
            System.out.println("excep "+mex);
            mex.printStackTrace();
        }
	}//publisher()
////////////////////////////////////////////////////////////////////////////
	/*
	@Override
	public void connectionLost(Throwable arg0) {
		 //Called when the client lost the connection to the broker
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		//Called when a outgoing publish is complete
	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
		//Message Arrived
		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " +mqttMessage.toString());
		System.out.println("-------------------------------------------------");
	}
	*/
//////////////////////////////////////////////////////////////////////////
	
}
