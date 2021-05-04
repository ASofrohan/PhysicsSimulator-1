package simulator.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;


public class ChangeForceClassDialog extends JDialog  implements SimulatorObserver {
	private JComboBox<String> idsc;
	private Controller _ctrl;


	public  ChangeForceClassDialog(Controller controller) {
		_ctrl = controller;
		_ctrl.addObserver(this);
		this.initGUI();
	}
	public void Mostrar() {
		this.setVisible(true);
		idsc.removeAllItems();
		List<JSONObject> l = _ctrl.getForceLawsInfo();
		for(JSONObject jo : l) {
			idsc.addItem(jo.getString("desc"));
			}
		
		
	}
	
	private void initGUI(){
		
	}


	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
