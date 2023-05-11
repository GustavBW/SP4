package g7.sp4.util;

import g7.sp4.common.models.AssmState;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
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
	private AssmState state;
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

	public AssmState getState() {
		return state;
	}

	public void setState(AssmState state) {
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
			this.timeStamp = new Date(1970, 1, 1);
		}
	}

	public void convertJSONtoString(JSONObject json) {
		setJsonString(json.toString());
		getStatusProperties();
	}

	public void getStatusProperties() {
		try {
			setLastOperation(Integer.toString(getJson().getInt("LastOperation")));
			setCurrentOperation(Integer.toString(getJson().getInt("CurrentOperation")));
			setState(AssmState.valueOf(getJson().getInt("State")));
			setTimeStamp(getJson().getString("TimeStamp"));
		} catch (JSONException e) {
			setLastOperation("UNKNOWN");
			setCurrentOperation("UNKNOWN");
			setState(AssmState.ERROR_UNKNOWN);
			setTimeStamp(new Date(1970, 1, 1).toString());
		}
	}

	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) {
		setJson(new JSONObject(new String(mqttMessage.getPayload())));

		convertJSONtoString(getJson());
	}
}
