package simulator.model;

import java.util.List;
import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private Vector2D o;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D o, double g) {
		this.g = g;
		this.o = o;
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D d = new Vector2D();
		for(int i = 0; i < bs.size(); i++) {
			d = o.minus(bs.get(i).getPosition().direction());
			bs.get(i).addForce(d.scale((-g)*bs.get(i).getMass()));	
		}
	}

	public String toString() {
		return "Moving towards" + o.toString() + "with constant acceleration" +g ;
	}
	public double getG() {
		return g;
	}
	public void setO(double g) {
		this.g = g;
	}
	public Vector2D getO() {
		return o;
	}
	public void setO(Vector2D o) {
		this.o = o;
	}
}
