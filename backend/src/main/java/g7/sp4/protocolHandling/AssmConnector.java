package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AssmConnector implements AssmConnectionService {

	public String brokerUrl;
	public String clientId;
	public String[] topics;
	public String payload;
	public MqttMessage msg;
	public MqttClient client;
	public JSONObject json;

	public AssmConnector() {
		brokerUrl = "tcp://localhost:1883";
		clientId = "mqtt-client";
		topics = new String[]{
				"emulator/operation",
				"emulator/status",
				"emulator/checkhealth"
		};
		payload = "{\"ProcessID\": 9999}";
		msg = new MqttMessage(payload.getBytes());
		msg.setQos(2);
		json = new JSONObject();

		try {
			client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("username");
			options.setPassword("password".toCharArray());
			client.connect(options);
			client.publish(topics[0], msg);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AssmStatus getStatus() {
		try {
			MqttJSONtoString JSONtoString = new MqttJSONtoString();
			client.subscribe(topics[1], JSONtoString);

			return new AssmStatus(
					JSONtoString.getCurrentProcess(),
					JSONtoString.getMessage(),
					JSONtoString.getCode()
			);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Flag build() {
		return null;
	}

	public static void main(String[] args) {

		AssmConnector mqtt = new AssmConnector();
		System.out.println(mqtt.getStatus());
	}
}