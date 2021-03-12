package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {
	protected double lossFactor;
	protected double lossFrequency;
	protected double counter; 
	
	MassLosingBody(String id, Vector2D v, Vector2D p, double m, double lossFactor, double lossFrequency){
		super(id, v, p, m);
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
		
		if(counter >= lossFrequency) {
			mass *= (1-lossFactor);
			counter = 0.0;
		}
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
