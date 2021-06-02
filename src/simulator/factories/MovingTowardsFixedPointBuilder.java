package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private final double g = -9.81;

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "fuerza que atrae cuerpos a un punto fijo");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double f = g;
		Vector2D pos = new Vector2D();
		if(data.has("g"))
			f = data.getDouble("g");
		if(data.has("c"))
			pos = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1)); 

		return new MovingTowardsFixedPoint(pos, f);
	}

	@Override
	protected JSONObject getBuilderData() {
		
		JSONObject jo1 = new JSONObject();
		jo1.put("c", "the point towards which bodies move(a json list of 2 numbers, e.g., [100.0,50.0])");
		jo1.put("g", "the length of the acceleration vector (a number)");
		return jo1;
	}

}
