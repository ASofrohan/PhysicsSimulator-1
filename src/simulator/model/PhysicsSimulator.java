package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private ForceLaws forceLaw;
	private double dt;
	private List<Body> bs;
	private double currentT;
	
	public PhysicsSimulator(ForceLaws forceLaw, double dt) throws IllegalArgumentException {
		if(forceLaw == null) throw new IllegalArgumentException();
		if(dt <= 0) throw new IllegalArgumentException();
		this.forceLaw = forceLaw;
		this.dt = dt;
		currentT = 0.0;
		bs = new ArrayList<Body>();		//excepcion pdf
	}

	public void advance() {
		for(int i = 0; i < bs.size(); i++) {
			bs.get(i).resetForce();
		}
		forceLaw.apply(bs);
		for(int i = 0; i < bs.size(); i++) {
			bs.get(i).move(dt);
		}
		currentT += dt;
	}
	
	public void addBody(Body b) throws IllegalArgumentException {
		if(bs.contains(b)) throw new IllegalArgumentException();
		bs.add(b);
	}
	
	public JSONObject getState() {
		JSONObject state = new JSONObject();
		JSONArray bodies = new JSONArray();
		state.put("time", this.currentT);
		for(int i = 0; i < bs.size(); i++) {
			bodies.put(bs.get(i).getState());
		}
		state.put("bodies", bodies);
		return state;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public ForceLaws getForceLaw() {
		return forceLaw;
	}
	public void setForceLaw(ForceLaws forceLaw) {
		this.forceLaw = forceLaw;
	}
	public double getDt() {
		return dt;
	}
	public void setDt(double dt) {
		this.dt = dt;
	}
	public List<Body> getBs() {
		return bs;
	}
	public void setBs(List<Body> bs) {
		this.bs = bs;
	}
	public double getCurrentT() {
		return currentT;
	}
	public void setCurrentT(double currentT) {
		this.currentT = currentT;
	}
}
