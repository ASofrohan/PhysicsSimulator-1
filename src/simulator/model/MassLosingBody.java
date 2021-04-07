package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {
	protected double lossFactor;
	protected double lossFrequency;
	protected double counter; 
	
	public MassLosingBody(String id, Vector2D p, Vector2D v, double m, double lossFrequency, double lossFactor){
		super(id, p, v, m);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		counter = 0.0;
	}

	@Override
	void move(double t) {
		Vector2D a = new Vector2D();		//acceleration
		if(mass !=0) a = new Vector2D(force.scale(1/mass));
		position = new Vector2D(position.plus(velocity.scale(t)));
		position = new Vector2D(position.plus(a.scale(Math.pow(t, 2)/2)));
		this.velocity = velocity.plus(a.scale(t));
		this.counter += t;
		if(counter >= lossFrequency) {
			mass *= (1-lossFactor);
			counter = 0.0;
		}
	}
	
	public JSONObject getState() {
		JSONObject joBody = new JSONObject();
		joBody.put("id", this.id);
		joBody.put("m", this.mass);
		joBody.put("p", this.position.asJSONArray());
		joBody.put("v", this.velocity.asJSONArray());
		joBody.put("f", this.force.asJSONArray());
		joBody.put("freq", this.lossFrequency);
		joBody.put("factor", this.lossFactor);
		return joBody;
	}
	
	public double getLossFactor() {
		return lossFactor;
	}
	public void setLossFactor(double lossFactor) {
		this.lossFactor = lossFactor;
	}
	public double getLossFrequency() {
		return lossFrequency;
	}
	public void setLossFrequency(double lossFrequency) {
		this.lossFrequency = lossFrequency;
	}
	public double getCounter() {
		return counter;
	}
	public void setCounter(double counter) {
		this.counter = counter;
	}
}
