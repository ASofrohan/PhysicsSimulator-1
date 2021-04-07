package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator {
	public MassEqualStates() {		
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if(s1.getDouble("time") != s2.getDouble("time")) return false;
		JSONArray s1Array = s1.getJSONArray("bodies");
		JSONArray s2Array = s2.getJSONArray("bodies");
		if(s2Array.length() != s1Array.length()) return false;
		for(int i = 0; i < s1Array.length(); i++) {
			if(s1Array.getJSONObject(i).getString("id") != s2Array.getJSONObject(i).getString("id") &&
			s1Array.getJSONObject(i).getDouble("m") != s2Array.getJSONObject(i).getDouble("m")) return false;
		}
		return true;
	}

}
