package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmState;
import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

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

		try {
			client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("username");
			options.setPassword("password".toCharArray());
			client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AssmStatus getStatus() {
		try {
			MqttJSONtoString JSONtoString = new MqttJSONtoString();
			client.subscribe(getTopics()[1], JSONtoString);

			while(JSONtoString.getJsonString() == null) {
				Thread.sleep(100);
			}
			return new AssmStatus(
					JSONtoString.getCurrentOperation(),
					JSONtoString.getJsonString(),
					JSONtoString.getState(),
					JSONtoString.getTimeStamp()
			);
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public Flag build(int processId) {

		String payload = "{\"ProcessID\": "+processId+"}";
		MqttMessage msg = new MqttMessage(payload.getBytes());
		msg.setQos(2);

		Flag flag = new Flag();

		try {
			client.publish(topics[0], msg);
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}

		AssmState state = getStatus().state();
		if(state == AssmState.ERROR) {
			flag.setState(false);
			flag.setError("ERROR - " + getStatus());
			return flag;
			//return new Flag((bool) -> getStatus().state() == AssmState.ERROR);
		} else if (state == AssmState.EXECUTING) {
			flag.setState(false);
			return flag;
		}
		flag.setState(true);
		return flag;
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
		while (true) {
			System.out.println(mqtt.build(9999).get());
			System.out.println(mqtt.getStatus());
		}
	}

}