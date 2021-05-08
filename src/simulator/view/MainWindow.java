package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ...
	Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	private void initGUI() {
		//Panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		//Status y control
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		//Panel central
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		mainPanel.add(middlePanel,BorderLayout.CENTER);
		//Panel de la tabla
		JPanel BodiesTable =
		createViewPanel(new BodiesTable(_ctrl),"Bodies");
		BodiesTable.setPreferredSize(new Dimension(500, 200));
		middlePanel.add(BodiesTable);
		//Panel del visor
		JPanel Viewer =
		createViewPanel(new Viewer(_ctrl), "Viewer");
		Viewer.setPreferredSize(new Dimension(500, 200));
		middlePanel.add(Viewer);
		//
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);



	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		p.setPreferredSize(new Dimension(500, 400));
		p.setBorder(BorderFactory.createTitledBorder(b, title)); 
		p.add(new JScrollPane(c));
		return p;
		}
	
}