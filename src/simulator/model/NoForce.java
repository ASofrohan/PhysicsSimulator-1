package simulator.model;

import java.util.List;

public class NoForce implements ForceLaws {

	@Override
	public void apply(List<Body> bs) {
		// empty
	}
	
	public String toString() {
		return "No force applied";
	}

}
