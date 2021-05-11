package simulator.control;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator sim;
	private Factory<Body> factoryB;
	private Factory<ForceLaws> factoryF;
	
	public Controller(PhysicsSimulator sim, Factory<Body> factoryB,  Factory<ForceLaws> factoryF) {
		this.sim = sim;
		this.factoryB = factoryB;
		this.factoryF = factoryF;

	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws CmpException{
		int i = 1;
		JSONObject jsonExpOut = null;
		JSONArray jaExpOut = null;
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		if(expOut != null) {
			jsonExpOut = new JSONObject(new JSONTokener(expOut));
			jaExpOut = jsonExpOut.getJSONArray("states");
		}
		p.println(sim.toString());
		try { 
		if(expOut != null) {
			if(!cmp.equal(sim.getState(), jaExpOut.getJSONObject(0))) {
			throw new CmpException("Different state: " + 0 + "\nStep: " + 0);	
			}
		}
		
		while(i <= n) {
			sim.advance();
			p.println("," + sim.toString());
			if(expOut != null) {
				if(!cmp.equal(sim.getState(), jaExpOut.getJSONObject(i))) {
				throw new CmpException("Different state: " + i + "\nStep: " + i);	
				}
			}
			i++;
		}
		}catch(CmpException ex) {
			throw new CmpException(ex.getMessage());
		}
		finally {
		p.println("\n]");
		p.println("}");
		}
	}
	
	public void run(int n) throws CmpException{
		int i = 1;		
		while(i <= n) {
			sim.advance();
			
			i++;
		}
		
	}
	
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray ja = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < ja.length(); i++) {
			sim.addBody(factoryB.createInstance(ja.getJSONObject(i)));
		}
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		sim.addObserver(o);
	}
	
	public List<JSONObject>getForceLawsInfo(){
		return factoryF.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		
		sim.setForceLaw(factoryF.createInstance(info));
	}
}
