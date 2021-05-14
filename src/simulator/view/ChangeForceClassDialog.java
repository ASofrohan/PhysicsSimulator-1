package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;


public class ChangeForceClassDialog extends JDialog {
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
			_data = new String [ForceData.length()];
			Force = jo;
			update();
		}

		public void update() {
			fireTableStructureChanged();
		}
		
		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		public void setForceData(JSONObject jo) {
			ForceData = jo.getJSONObject("data");
			_data = new String [ForceData.length()];
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
		
		public JSONObject getData() {
			JSONObject jo = new JSONObject();
			jo = Force;
			
			JSONArray arr = ForceData.names();
			
			StringBuilder s = new StringBuilder();
			s.append('{');
			for(int i = 0; i< ForceData.length(); i++) {
				s.append('"');
				s.append(arr.getString(i));

				s.append('"');
				s.append(':');
				s.append(_data[i]);
				s.append(',');
				
			}
			if (s.length() > 1)
				s.deleteCharAt(s.length() - 1);
			s.append('}');
			

			jo.put("data", new JSONObject(s.toString()));
			
			return jo;			
		}
		
		//String builder 
		
	}
	
	public  ChangeForceClassDialog(Controller controller) {
		_ctrl = controller;
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
		JLabel help = new JLabel(
				"<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");

		comForcesBob.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(-1!=comForcesBob.getSelectedIndex())
					ParamTable.setForceData(_ctrl.getForceLawsInfo().get(comForcesBob.getSelectedIndex()));
			}						
		});
		help.setAlignmentX(CENTER_ALIGNMENT);
		
		
		//Anyadir descripcion
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		setContentPane(main);
		main.add(help);
		
		main.add(Box.createRigidArea(new Dimension(0, 20)));

		
		//Anyadir tabla
		_eventsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		_eventsTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		TableColumnModel colModel = _eventsTable.getColumnModel();
        colModel.getColumn(1).setPreferredWidth(200);
		colModel.getColumn(2).setPreferredWidth(400);
		
		_eventsTable.setPreferredSize(new Dimension(500, 200));
		_eventsTable.setMaximumSize(new Dimension(500, 200));
		_eventsTable.setMinimumSize(new Dimension(500, 200));
		JScrollPane x = new JScrollPane(_eventsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		x.setPreferredSize(new Dimension(500, 200));
		x.setMaximumSize(new Dimension(500, 200));
		x.setMinimumSize(new Dimension(500, 200));

		main.add(x);
		
		main.add(Box.createRigidArea(new Dimension(0, 20)));
		
		//Anyadir zona combobox
		JPanel combopanel = new JPanel();
		combopanel.setLayout(new BoxLayout(combopanel, BoxLayout.X_AXIS));
		combopanel.setAlignmentX(CENTER_ALIGNMENT);
		combopanel.add(Fuerzas);
		combopanel.add(comForcesBob);
		combopanel.setPreferredSize(new Dimension(50, 30));
		combopanel.setMinimumSize(new Dimension(50, 30));
		combopanel.setMaximumSize(new Dimension(500, 30));
		
		main.add(combopanel);
		
		main.add(Box.createRigidArea(new Dimension(0, 20)));
		
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
		
		main.add(Box.createRigidArea(new Dimension(0, 20)));
		
		pack();
		setResizable(false);
		setVisible(false);

	}

	
	private void updateForce(){
		_ctrl.setForceLaws(ParamTable.getData());

	}
	
}
