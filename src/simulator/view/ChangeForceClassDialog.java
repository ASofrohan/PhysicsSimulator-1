package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;


public class ChangeForceClassDialog extends JDialog  implements SimulatorObserver {
	private JComboBox<String> idsc;
	private Controller _ctrl;

	private class JsonParamTable extends AbstractTableModel {

		private static final long serialVersionUID = 1L;		
		private String[] _header = { "Key", "Value", "description" };
		private JSONObject ForceData;
		private String[] _data;


		public JsonParamTable(JSONObject jo) {
			ForceData = jo;
			_data = new String [jo.length()];
		}

		public void update() {
			fireTableDataChanged();
		}
		
		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		public void setForceData(JSONObject jo) {
			ForceData = jo;
			update();
		}
		
		
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column ==1)
				return true;
			
			return false;
		}
		
		
		public int getColumnCount() {
			return _header.length;
		}
		public int getRowCount() {
			return ForceData == null ? 0 : ForceData.length();
		}
		
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object s = null;
			JSONArray arr = ForceData.names();
			switch (columnIndex) {
			case 0:
				s =  arr.get(rowIndex);
				break;
			case 1:
				s = _data[rowIndex];
				break;
			case 2:
				s = ForceData.getString(arr.getString(rowIndex));
				break;
			}
			return s;
		}
		
		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex] = o.toString();
		}
	
		
		
	
	}
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
		this.setLayout(new BorderLayout(15, 20));
		JLabel descripcion   = new JLabel("Select a force law and provide values for the parameters "
			+ "in the Value column (default values are used for parameters with no value) ");
		this.add(descripcion, BorderLayout.NORTH);
		
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.X_AXIS));
		
		
		
		this.add(centro, BorderLayout.CENTER);
				
		JPanel botones = new JPanel();
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}						
		});
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				

			}			
		});
		
		JLabel Fuerzas   = new JLabel("Forcelaw:");
		centro.add(Fuerzas);
		idsc = new JComboBox<String>();
		idsc.setMaximumSize(new Dimension(500,20));
		botones.add(idsc);
		
		botones.add(cancel);
		botones.add(ok);
		this.add(botones, BorderLayout.SOUTH);
		
		
		this.setPreferredSize(new Dimension(700, 200));
		this.pack();

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
