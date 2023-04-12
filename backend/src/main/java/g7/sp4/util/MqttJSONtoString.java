package g7.sp4.util;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MqttJSONtoString implements IMqttMessageListener {

	private JSONObject json;
	private String jsonString;
	private String currentProcess;
	private int code;
	private String message;

	public MqttJSONtoString() {
	}

	public MqttJSONtoString(JSONObject json) {
		this.json = json;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(String currentProcess) {
		this.currentProcess = currentProcess;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void convertJSONtoString(JSONObject json) {
		setCurrentProcess(Integer.toString(json.getInt("CurrentOperation")));
		setCode(json.getInt("State"));
		setMessage(json.toString());
		System.out.println("Current Process: " + getCurrentProcess());
		System.out.println("Code: " + getCode());
		System.out.println("Message: " + getMessage());
	}

	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) {
		setJson(new JSONObject(new String(mqttMessage.getPayload())));

		convertJSONtoString(getJson());
	}
}
