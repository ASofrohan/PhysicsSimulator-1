package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	
	public MassLosingBodyBuilder() {
		TypeTag = "mlb";			
		desc = "massLossingBody";
	}
	
	@Override
	protected MassLosingBody createTheInstance(JSONObject jo) {
		String id = jo.getString("id");
		JSONArray vector = jo.getJSONArray("p");
		Vector2D p = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		vector = jo.getJSONArray("v");
		Vector2D v = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		double m = jo.getDouble("m");
		double lossFrequency = jo.getDouble("freq");
		double lossFactor = jo.getDouble("factor");
		return new MassLosingBody(id, p, v, m, lossFrequency, lossFactor);
	}

	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();
		
		obj.put("id", "b1");
		
		ja.put(0.0e00);
		ja.put(0.0e00);		
		obj.put("p", ja);
		
		ja = new JSONArray();
		ja.put(0.05e04);
		ja.put(0.0e00);
		obj.put("v", ja);
		
		obj.put("m", 5.97e24);	
		obj.put("freq", 1e3);
		obj.put("factor", 1e-3);
		return obj;
	}
}
