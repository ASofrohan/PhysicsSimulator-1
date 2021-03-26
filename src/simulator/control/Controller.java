package simulator.control;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {
	
	private PhysicsSimulator sim;
	private Factory<Body> factory;
	
	public Controller(PhysicsSimulator sim, Factory<Body> factory) {
		this.sim = sim;
		this.factory = factory;
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {
		int i = 0;
		boolean stop = false;
		JSONObject jsonExpOut = null;
		JSONArray jaExpOut = null;
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		if(expOut != null) {
			jsonExpOut = new JSONObject(new JSONTokener(expOut));
			jaExpOut = jsonExpOut.getJSONArray("states");
		}
		p.println("\"s0\": [");
		p.println(sim.toString());
		p.println("]");
		
		while(i < n && stop) {
			if(expOut != null) cmp.equal(jaExpOut.getJSONObject(i), jaExpOut.getJSONObject(i));
			
			sim.advance();
			
			p.println("\"s" + i + "\": [");
			p.println(sim.toString());
			p.println("]");
			
			i++;
		}
		
		p.println("]");
		p.println("}");
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray ja = jsonInput.getJSONArray("bodies");
		for(int i = 0; i < ja.length(); i++) {
			sim.addBody(factory.createInstance(ja.getJSONObject(i)));
		}
	}
}
