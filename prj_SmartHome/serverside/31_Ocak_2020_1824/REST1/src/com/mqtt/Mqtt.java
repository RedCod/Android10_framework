package com.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt implements MqttCallback{
	/**
	 * Mqtt komut işletim merkezi:http://www.bytesofgigabytes.com/mqtt-tutorial/
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
	public String executeCommand(String topic,String command) {
		//TODO: mqtt execute command..
		//..
		return "command output";
	}//executeCommand()
	
	
	
	/**
	 * connect Mqtt Server.
	 */
	private void connect() {
		if(mqttClient != null && mqttClient.isConnected()) {
			System.out.println("Bağlantı var!");
			return;
		}
		/*MemoryPersistence*/ persistence = new MemoryPersistence();
		try
		{
			/*MqttClient*/ mqttClient = new MqttClient(SERVER_URL, clientId, persistence);
			/*MqttConnectOptions*/ mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(true);
			
			/*//not used now
              mqttConnectOptions.setUserName(USER_NAME);
              mqttConnectOptions.setPassword(PASSWORD.toCharArray());
             */
			System.out.println("checking");
			System.out.println("Mqtt Connecting to broker: " + SERVER_URL);
			mqttClient.connect(mqttConnectOptions);
			System.out.println("Mqtt Connected");
			mqttClient.setCallback(this);
			//mqttClient.subscribe(topic);
			//System.out.println("Subscribed");
			//System.out.println("Listening");
		} catch (MqttException me) {
			System.out.println(me);
		}
	}//connect()
	
	/**
	 * Disconnect mqtt server.
	 * @throws MqttException
	 */
	public void disconnect() throws MqttException {
		if(mqttClient != null && mqttClient.isConnected()) {
			mqttClient.disconnect();
			mqttClient.close();
		}
	}//disconnect()
	

	/**
	 * subscribe to mqtt
	 * @param topic //specify topic
	 */
	public void subscribe(String topic) {
		try
		{
			mqttClient.subscribe(topic);
			System.out.println("Subscribed");
			System.out.println("Listening");
		} catch (MqttException me) {
			System.out.println(me);
		}
	}//subscribe()
	
	/**
	 * publisher message/content on mqtt
	 * @param content  //message OR content
	 */
	public void publisher(String topic,String content) {
        //content      = "Temp:20,Humi:70";
        int qos             = 0;
        /*hostname is localhost as mqtt publisher and broker are
          running on the same computer*/ 
        //String broker       = SERVER_URL;//"tcp://localhost:1883";
        //String clientId     = "JavaSample";
        try {
            System.out.println("Publishing message:"+content);
            MqttMessage mqttMessage = new MqttMessage(content.getBytes());
            mqttMessage.setQos(qos);
            mqttClient.publish(topic, mqttMessage);
            System.out.println("Message published");
            //mqttClient.disconnect();
           // mqttClient.close();
            //System.exit(0);
            } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}//publisher()
////////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////
	
}
