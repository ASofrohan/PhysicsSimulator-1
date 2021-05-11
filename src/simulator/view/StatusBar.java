package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
// ...
		private Controller _ctrl;
		private double steps;
		private int bodies;
		private String law;
		private JLabel _currTime;     // for current time
		private JLabel _currLaws;     // for gravity laws
		private JLabel _numOfBodies;  // for number of bodies
		
		StatusBar(Controller ctrl) {
			_ctrl = ctrl; 
			steps=0;
			bodies = 0;
			 law = " ";
			_currTime = new JLabel("Time: "+String.valueOf(this.steps));
			_currLaws = new JLabel("Laws: "+String.valueOf(this.law));     
			_numOfBodies = new JLabel("Bodies: "+String.valueOf(this.bodies));
			initGUI();
			ctrl.addObserver(this);

		}
		
		private void initGUI() {
			this.setLayout( new FlowLayout( FlowLayout.LEFT ));
			this.setBorder( BorderFactory.createBevelBorder( 1 ));
			this.add(_currTime);
			JSeparator separador = new JSeparator(SwingConstants.VERTICAL);
			separador.setPreferredSize(new Dimension(32,18));
			this.add(separador);
			this.add(_numOfBodies);
			this.add(separador);
			this.add(_currLaws);
		}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		steps = time;	
		_currTime.setText("Time: "+String.valueOf(this.steps));
		this.bodies = bodies.size();	
		_numOfBodies.setText("Bodies: : "+String.valueOf(this.bodies));
		law = fLawsDesc;	
		_currLaws.setText("Laws: "+String.valueOf(this.law));
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		steps = time;	
		_currTime.setText("Time: "+String.valueOf(this.steps));
		this.bodies = bodies.size();	
		_numOfBodies.setText("Bodies: : "+String.valueOf(this.bodies));
		law = fLawsDesc;	
		_currLaws.setText("Laws: "+String.valueOf(this.law));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		this.bodies++;	
		_numOfBodies.setText("Bodies: : "+String.valueOf(this.bodies));

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		steps = time;	
		_currTime.setText("Time: "+String.valueOf(this.steps));

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		law = fLawsDesc;	
		_currLaws.setText("Laws: "+String.valueOf(this.law));

	}

}
