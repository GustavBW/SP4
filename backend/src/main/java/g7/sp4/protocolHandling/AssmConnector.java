package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmStatus;
import g7.sp4.util.JSONWrapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class AssmConnector implements AssmConnectionService {

	String broker = "mqtt://localhost:1883/";
	String clientId = "subscribe_client";

	MqttClient client;

	{
		try {
			client = new MqttClient(broker, clientId, new MemoryPersistence());
			client.connect();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public AssmStatus getStatus() {
		String bodyAsString;
		int code = 200;

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

	}

	@Override
	public Flag build() {
		return null;
	}
}
