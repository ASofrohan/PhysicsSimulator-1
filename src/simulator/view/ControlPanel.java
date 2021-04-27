package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private Controller _ctrl;
	private boolean _stopped;
	private JToolBar toolBar;
	private JButton load;
	private JFileChooser chooser;
	private JButton physics;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
	    _ctrl.addObserver(this);
	    chooser = new JFileChooser(System.getProperty("user.dir") + "/resources/examples");
	}
	
	private void initGUI() {
		// TODO build the tool bar by adding buttons, etc.
		this.toolBar = new JToolBar();
		
		this.load = new JButton();
		toolBar.add(load);
		load.setIcon(new ImageIcon("resources\\icons\\open.png"));
		load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                load();
            }
        });
        load.setToolTipText("Load simulation");
		
		this.physics = new JButton();
		toolBar.add(physics);
		load.setIcon(new ImageIcon("resources\\icons\\physics.png"));
		load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // hay que hacerlo
            }
        });
		physics.setToolTipText("Force law selector");
	}
	
	// other private/protected methods
	// ...
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				_stopped = true;
				return;
				}
			SwingUtilities.invokeLater( new Runnable() {
				@Override 
				public void run() {
					run_sim(n-1);
				}
			});
			
			
		} 
		else {
			_stopped = true;
			// TODO enable all buttons
		}
	}
	
	private void load() {
        int v = chooser.showOpenDialog(this.getParent());
        if (v==JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            System.out.println("loading " + file.getName());
            _ctrl.reset();
            try {
            _ctrl.loadBodies(new FileInputStream(file));
            }catch(FileNotFoundException e) {
            	e.printStackTrace();
            }
        }
        else System.out.println("Load cancelled by user");
    }
	
	// SimulatorObserver methods
		// ...
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