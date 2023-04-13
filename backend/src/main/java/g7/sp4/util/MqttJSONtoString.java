package g7.sp4.util;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MqttJSONtoString implements IMqttMessageListener {

	private JSONObject json = new JSONObject();
	private String jsonString;
	private String lastOperation;
	private String currentOperation;
	private int state;
	private Date timeStamp;

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

	public String getLastOperation() {
		return lastOperation;
	}

	public void setLastOperation(String lastOperation) {
		this.lastOperation = lastOperation;
	}

	public String getCurrentOperation() {
		return currentOperation;
	}

	public void setCurrentOperation(String currentOperation) {
		this.currentOperation = currentOperation;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			this.timeStamp = dateFormat.parse(timeStamp);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void convertJSONtoString(JSONObject json) {
		setJsonString(json.toString());
		getStatusProperties();
	}

	public void getStatusProperties() {
		setLastOperation(Integer.toString(getJson().getInt("LastOperation")));
		setCurrentOperation(Integer.toString(getJson().getInt("CurrentOperation")));
		setState(getJson().getInt("State"));
		setTimeStamp(getJson().getString("TimeStamp"));
/*
		System.out.println("Last Operation: " + getLastOperation());
		System.out.println("Current Operation: " + getCurrentOperation());
		System.out.println("State: " + getState());
		System.out.println("Time Stamp: " + getTimeStamp());
		System.out.println("JSON String: " + getJsonString());
*/
	}

	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) {
		setJson(new JSONObject(new String(mqttMessage.getPayload())));

		convertJSONtoString(getJson());
	}
}
