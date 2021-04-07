package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "Basic Body");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Body createTheInstance(JSONObject jo) {
		
		if(!jo.has("id") || !jo.has("p") || !jo.has("v") || !jo.has("m")) {
			throw new IllegalArgumentException("Faltan valores para crear los cuerpos");
		}
		String id = jo.getString("id");
		JSONArray vector = jo.getJSONArray("p");
		Vector2D p = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		vector = jo.getJSONArray("v");
		Vector2D v = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		double m = jo.getDouble("m");
		if(m <= 0) {
			throw new IllegalArgumentException("Masa menor o igual que cero");
		}
		return new Body(id, p, v, m);
	}

	
	@Override
	protected JSONObject getBuilderData() {
	
		
		JSONObject JASON = new JSONObject();
		JASON.put("id", "string que identifica al objeto");
		JASON.put("p", "posicion del objeto");		
		JASON.put("v", "velocidad del objeto");
		JASON.put("m", "masa del objeto");		
	
		
		return JASON;
	}
	
}
