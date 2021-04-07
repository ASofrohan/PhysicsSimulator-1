package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public MassLosingBodyBuilder() {
		super("mlb", "Mass Losing Body");
	}

	@Override
	protected Body createTheInstance(JSONObject jo) {
		
		if(!jo.has("id") || !jo.has("p") || !jo.has("v") || !jo.has("m") || !jo.has("freq") || !jo.has("factor")) {
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
		double lossFrequency = jo.getDouble("freq");
		if(lossFrequency <= 0) {
			throw new IllegalArgumentException("Frecuencia menor o igual que cero");
		}
		double lossFactor = jo.getDouble("factor");
		return new MassLosingBody(id, p, v, m, lossFrequency, lossFactor);	
	}

	@Override
	protected JSONObject getBuilderData() {
	
		JSONObject JASON = new JSONObject();
		JASON.put("id", "string que identifica al objeto");
		JASON.put("p", "posicion del objeto");		
		JASON.put("v", "velocidad del objeto");
		JASON.put("m", "masa del objeto");
		JASON.put("freq", "frecuencia con la que pierde masa");		
		JASON.put("factor", "cantidad en al que pierde masa");		
		
		return JASON;
	}

}
