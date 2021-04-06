package simulator.model;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws {
	
	private double g;
	
	public NewtonUniversalGravitation(double g) {
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		//Fi,j es la fuerza aplicada por el cuerpo Bj sobre el cuerpo Bi
		double f;
		for(int i = 0; i < bs.size(); i++) {		//body
			for(int j = i; j < bs.size(); j++) {	//send force to other bodies
				if(j != i) {
					f = g*bs.get(i).getMass()*bs.get(j).getMass()/Math.pow(Math.abs(bs.get(j).getPosition().distanceTo(bs.get(i).getPosition())), 2);
					bs.get(i).addForce(bs.get(j).getPosition().minus(bs.get(i).getPosition()).direction().scale(f));
					bs.get(j).addForce(bs.get(i).getPosition().minus(bs.get(j).getPosition()).direction().scale(f));
				}
			}
		}
	}
	
	public String toString() {
		return "Dos cuerpos Bi y Bj aplican una fuerza gravitacional uno sobre otro, i.e., se atraen mutuamente.";
	}
	public double getG() {
		return g;
	}
	public void setG(double g) {
		this.g = g;
	}
}
