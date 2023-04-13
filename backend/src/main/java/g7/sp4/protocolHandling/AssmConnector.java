package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AssmConnector implements AssmConnectionService {

	private String brokerUrl;
	private String clientId;
	private String[] topics;
	private MqttClient client;

	public AssmConnector() {
		brokerUrl = "tcp://localhost:1883";
		clientId = "mqtt-client";
		topics = new String[]{
				"emulator/operation",
				"emulator/status",
				"emulator/checkhealth"
		};
		String payload = "{\"ProcessID\": 9999}";
		MqttMessage msg = new MqttMessage(payload.getBytes());
		msg.setQos(2);

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
			client.subscribe(getTopics()[1], JSONtoString);

			//JSONtoString.getStatusProperties();

			return new AssmStatus(
					JSONtoString.getCurrentOperation(),
					JSONtoString.getJsonString(),
					JSONtoString.getState()
			);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Flag build() {
		AtomicBoolean flagState = new AtomicBoolean(false);
		//Flag flag = new Flag();
		return null;
	}

	public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}

	public static void main(String[] args) {

		AssmConnector mqtt = new AssmConnector();
		mqtt.getStatus();
	}
}