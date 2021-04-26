package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {
		super("ng", "no hay fuerza");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		return new  NoForce();
	}

	@Override
	protected JSONObject getBuilderData() {
		JSONObject jo = new JSONObject();
		jo.put("type", this._type);
		JSONObject jo1 = new JSONObject();
		jo.put("data", jo1);
		jo.put("desc", this._desc);
		return jo;
	}

}
