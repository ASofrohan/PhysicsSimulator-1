package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{

	
	String[] columnNames = {"ID", "Mass", "Position" , "Velocity", "Force"};
	private List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);
		String c = "";
		switch (columnIndex) {
		case 0: 
			c = b.getId();
			break;
		case 1:
			c = Double.toString(b.getMass());
			break;
		case 2:
			c = b.getPosition().toString();
			break;
		case 3:
			c = b.getVelocity().toString();
			break;
		case 4:
			c = b.getForce().toString();
			break;
		}
		return c;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableDataChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableDataChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableDataChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_bodies = bodies;
		fireTableDataChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

}
