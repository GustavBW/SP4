package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.JSONWrapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class AssmConnector implements AssmConnectionService {

	public static void main(String[] args) {
		String topic = "emulator/operation";
		String content = "{\"ProcessID\": 9999}";
		int qos = 2;
		String broker = "mqtt://localhost:1883/";
		String clientId = "mqtt-explorer-5b9eb246";
		MemoryPersistence persistence = new MemoryPersistence();
		MqttClient client;
/*
	{
		try {
			client = new MqttClient(broker, clientId, new MemoryPersistence());
			client.connect();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}
*/
		{
			try {
				client = new MqttClient(broker, clientId, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				connOpts.setCleanSession(true);
				System.out.println("Connecting to broker: " + broker);
				client.connect(connOpts);
				System.out.println("Connected");
				System.out.println("Publishing message: " + content);
				MqttMessage message = new MqttMessage(content.getBytes());
				message.setQos(qos);
				client.publish(topic, message);
				System.out.println("Message published");
				client.disconnect();
				System.out.println("Disconnected");
				System.exit(0);
			} catch(MqttException me) {
				System.out.println("reason " + me.getReasonCode());
				System.out.println("msg " + me.getMessage());
				System.out.println("loc " + me.getLocalizedMessage());
				System.out.println("cause " + me.getCause());
				System.out.println("excep " + me);
				me.printStackTrace();
			}
		}
	}



	@Override
	public AssmStatus getStatus() {
/*
		String msg;

		try {

			String status = client.subscribeWithResponse("emulator/status").toString();
			JSONWrapper wrapped = new JSONWrapper(status);
			String processID = wrapped.get("CurrentOperation");
			int state = Integer.parseInt(wrapped.get("State"));

			if (state == 0) {
				msg = "Idle";
			} else if (state == 1) {
				msg = "Executing";
			} else msg = "Error";

			return new AssmStatus(
					processID,
					msg,
					state
			);
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
*/
		return null;
	}

	@Override
	public Flag build() {
		return null;
	}
}
