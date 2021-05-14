package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String _type;
	protected String _desc;


	Builder(String type, String desc) {
		if (type == null)
			throw new IllegalArgumentException("Invalid type: " + type);
		else
			_type = type;
			_desc = desc;
	}

	public T createInstance(JSONObject info) {

		T b = null;


		if (_type != null && _type.equals(info.getString("type"))) {
			b = createTheInstance(info.has("data") ? info.getJSONObject("data") : null);
		}

		return b;
	}

	protected abstract T createTheInstance(JSONObject data);
	
	public  JSONObject getBuilderInfo() {
		JSONObject j = new JSONObject();
		j.put("type", _type);
		j.put("desc", _desc);
		j.put("data", getBuilderData());
		return j;
	}
	
	
	protected abstract JSONObject getBuilderData();
	
}
