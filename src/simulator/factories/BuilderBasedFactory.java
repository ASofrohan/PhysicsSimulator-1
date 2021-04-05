package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory <T> implements Factory<T>{
	
	protected List<Builder<T>> builders;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<Builder<T>>(builders);
	}
	
	@Override
	public T createInstance(JSONObject info) {
		T build;
		for(int i = 0; i < builders.size(); i++) {
			build = builders.get(i).createInstance(info);
			if(build != null) {
				return build;
			}
		} 
		throw new IllegalArgumentException("Unknown model");
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < builders.size(); i++) {
			list.add(builders.get(i).getBuilderInfo());
		}
		return list;
	}
}
