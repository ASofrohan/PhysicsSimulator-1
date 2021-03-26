package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	protected String TypeTag;
	protected String desc;
	
	public T createInstance(JSONObject info) {
		T obj = null;
		if(info.getString("type").equals(this.TypeTag)) {
			obj = createTheInstance(info.getJSONObject("data"));
		}
		return obj;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject obj = new JSONObject();
		obj.put("type", TypeTag);
		obj.put("data", createData());
		obj.put("desc", desc);
		return obj;
	}

	protected JSONObject createData() {
		return new JSONObject();
	}
	
	protected abstract T createTheInstance(JSONObject jo);

}
