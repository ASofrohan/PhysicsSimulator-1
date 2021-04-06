package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {
	
	private double eps;
	
	public EpsilonEqualStates(double eps) {
		this.eps = eps;
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if(s1.getDouble("time") != s2.getDouble("time")) return false;
		JSONArray s1Array = s1.getJSONArray("bodies");
		JSONArray s2Array = s2.getJSONArray("bodies");
		Vector2D v1 = new Vector2D();
		Vector2D v2 = new Vector2D();
		if(s2Array.length() != s1Array.length()) return false;
		for(int i = 0; i < s1Array.length(); i++) {
			if(!s1Array.getJSONObject(i).getString("id").contentEquals(s2Array.getJSONObject(i).getString("id"))) return false;							//check id
			if(Math.abs(s1Array.getJSONObject(i).getDouble("m") - s2Array.getJSONObject(i).getDouble("m"))>eps) return false;							//check mass
			v1 = new Vector2D(s1Array.getJSONObject(i).getJSONArray("p").getDouble(0), s1Array.getJSONObject(i).getJSONArray("p").getDouble(1));		//check position
			v2 = new Vector2D(s2Array.getJSONObject(i).getJSONArray("p").getDouble(0), s2Array.getJSONObject(i).getJSONArray("p").getDouble(1));
			if(v1.distanceTo(v2) > eps) return false;
			v1 = new Vector2D(s1Array.getJSONObject(i).getJSONArray("v").getDouble(0), s1Array.getJSONObject(i).getJSONArray("v").getDouble(1));		//check velocity
			v2 = new Vector2D(s2Array.getJSONObject(i).getJSONArray("v").getDouble(0), s2Array.getJSONObject(i).getJSONArray("v").getDouble(1));
			if(v1.distanceTo(v2) > eps) return false;
			v1 = new Vector2D(s1Array.getJSONObject(i).getJSONArray("f").getDouble(0), s1Array.getJSONObject(i).getJSONArray("f").getDouble(1));		//check force
			v2 = new Vector2D(s2Array.getJSONObject(i).getJSONArray("f").getDouble(0), s2Array.getJSONObject(i).getJSONArray("f").getDouble(1));
			if(v1.distanceTo(v2) > eps) return false;
		}	
		return true;
	}

}