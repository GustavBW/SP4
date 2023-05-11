package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmState;
import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.MqttJSONtoString;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AssmConnector implements AssmConnectionService {

	private String brokerUrl;
	private String clientId;
	private String[] topics;
	private MqttClient client;
	private static final String ANSI_RED = "\u001B[31m";


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
			System.out.println(ANSI_RED + "Failed trying to connect to MQTT");
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
		} catch (MqttException | InterruptedException e) {
			System.err.println(e.getMessage());
			return new AssmStatus(
					"UNKNOWN",
					"ERROR UNKNOWN",
					AssmState.ERROR_UNKNOWN,
					new Date(1970, 1, 1)
			);
		}
	}

	@Override
	public Flag build(int processId) {

		String payload = "{\"ProcessID\": "+processId+"}";
		MqttMessage msg = new MqttMessage(payload.getBytes());
		msg.setQos(2);

		//Automatically sets the error if any occours.
		Flag flag = new Flag(
				(previous, instance) -> {
					AssmState state = getStatus().state();
					if(state == AssmState.ERROR || state == AssmState.ERROR_UNKNOWN){
						instance.setError(
								new Error("Assembler Error","An error occurred while building the part.")
						);
					}
					return state == AssmState.IDLE;
				}
		);

		try {
			client.publish(topics[0], msg);
		} catch (MqttException e) {
			return new Flag().setError(
					new Error("Assembler Error", "An error occurred when trying to build the part.")
			);
		}

		return flag;
	}

	private MqttClient getClient() {
		return client;
	}

	public boolean isConnected() {
		boolean isConnected = client.isConnected();
		return isConnected;
	}


	private String[] getTopics() {
		return topics;
	}


}