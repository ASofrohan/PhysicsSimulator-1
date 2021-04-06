package simulator.model;
import org.json.JSONObject;

import simulator.misc.*;

public class Body {
	protected String id;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	public Body(String id, Vector2D p, Vector2D v, double m){
		this.id = id;
		this.velocity = v;
		this.force = new Vector2D(0.0,0.0);
		this.position = p;
		this.mass = m;
	}

	void addForce(Vector2D f) {
		this.force = this.force.plus(f);
	}
	
	void resetForce() {
		this.force = new Vector2D(0.0,0.0);
	}
	
	void move(double t) {
		Vector2D a = new Vector2D();		//acceleration
		if(mass != 0) a = new Vector2D(force.scale(1/mass));
		this.position = position.plus(velocity.scale(t));
		this.position = position.plus(a.scale(Math.pow(t, 2)/2));
		this.velocity = velocity.plus(a.scale(t));
	}
	
	public boolean equals(Object obj) {
		Body body2 = (Body) obj;
		if (this.id.equals(body2.id)) return true;
		else return false;
	}
	
	public JSONObject getState() {
		JSONObject joBody = new JSONObject();
		joBody.put("id", this.id);
		joBody.put("m", this.mass);
		joBody.put("p", this.position.asJSONArray());
		joBody.put("v", this.velocity.asJSONArray());
		joBody.put("f", this.force.asJSONArray());
		return joBody;
	}
	
	public String toString() {
		return this.getState().toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Vector2D getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}
	public Vector2D getForce() {
		return force;
	}
	public void setForce(Vector2D force) {
		this.force = force;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
}
