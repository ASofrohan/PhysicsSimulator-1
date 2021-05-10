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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;


public class ChangeForceClassDialog extends JDialog  implements SimulatorObserver {
	private JComboBox<String> comForcesBob;
	private Controller _ctrl;
	private JsonParamTable ParamTable;
	private JTable _eventsTable;


	private class JsonParamTable extends AbstractTableModel {

		private static final long serialVersionUID = 1L;		
		private String[] _header = { "Key", "Value", "description" };
		private JSONObject ForceData;
		private JSONObject Force;
		private String[] _data;


		public JsonParamTable(JSONObject jo) {
			ForceData = jo.getJSONObject("data");
			_data = new String [ForceData.getJSONObject("data").length()];
			Force = jo;
			update();
		}

		public void update() {
			fireTableDataChanged();
		}
		
		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		public void setForceData(JSONObject jo) {
			ForceData = jo.getJSONObject("data");
			_data = new String [ForceData.getJSONObject("data").length()];
			Force = jo;
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
			return ForceData == null ? 0 : ForceData.getJSONObject("data").length();
		}
		
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object s = null;
			JSONArray arr = ForceData.getJSONObject("data").names();
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
		
		public JSONObject getData() {
			JSONObject jo = new JSONObject();
			jo = Force;
			JSONObject jo1 = new JSONObject();
			jo1 = ForceData;
			JSONObject nuevoParams = new JSONObject();
			JSONArray arr = ForceData.getJSONObject("data").names();
			for(int i = 0; i< ForceData.getJSONObject("data").length(); i++) {
				nuevoParams.put(arr.getString(i), _data[i]);
			}
			jo1.put("data", nuevoParams);
			jo.put("data", jo1);
			return jo;			
		}
		
	}
	
	public  ChangeForceClassDialog(Controller controller) {
		_ctrl = controller;
		_ctrl.addObserver(this);
		this.initGUI();
	}
	
	public void Mostrar() {
		comForcesBob.removeAllItems();
		List<JSONObject> l = _ctrl.getForceLawsInfo();
		for(JSONObject jo : l) {
			comForcesBob.addItem(jo.getString("desc"));
			}
		this.pack();
		this.setVisible(true);

		
	}
	
	private void initGUI(){
		//Inicializaciones
		JLabel Fuerzas = new JLabel("Forcelaw:");
		comForcesBob = new JComboBox<String>();
		for(int i =0; i< _ctrl.getForceLawsInfo().size(); i++) {
			comForcesBob.addItem(_ctrl.getForceLawsInfo().get(i).getString("type"));
			
		}
		ParamTable = new JsonParamTable(_ctrl.getForceLawsInfo().get(1));
		_eventsTable = new JTable(ParamTable);
		JLabel description = new JLabel("Select a force law and provide values for the parameters "
				+ "in the Value column (default values are used for parameters with no value) ");
		
		//Anyadir descripcion
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(description);
		
		//Anyadir tabla
		JPanel p = new JPanel();
		_eventsTable.setPreferredSize(new Dimension(100, 200));
		_eventsTable.setMaximumSize(new Dimension(100, 200));
		_eventsTable.setMinimumSize(new Dimension(100, 200));
		p.add(new JScrollPane(_eventsTable));
		

		p.setPreferredSize(new Dimension(100, 200));
		p.setMaximumSize(new Dimension(100, 200));
		p.setMinimumSize(new Dimension(100, 200));

		main.add(p);
		
		//Anyadir zona combobox
		JPanel combopanel = new JPanel();
		combopanel.setLayout(new BoxLayout(combopanel, BoxLayout.X_AXIS));
		combopanel.setAlignmentX(CENTER_ALIGNMENT);
		combopanel.add(Fuerzas);
		combopanel.add(comForcesBob);
		main.add(combopanel);
		
		//Anyadir zona botones
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
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
				updateForce();
				setVisible(false);
			}			
		});
		buttons.add(cancel);
		buttons.add(ok);
		main.add(buttons);
		
		//main.setPreferredSize(new Dimension(1000, 1000));
		
		

		setContentPane(main);
		//this.add(main);
		this.pack();



	}

	
	private void updateForce(){
		_ctrl.setForceLaws(ParamTable.getData());

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
