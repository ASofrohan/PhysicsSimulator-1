package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
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
	private JButton play;
	private JButton exit;
	private JButton stop;
	private JSpinner steps;
	private TextField deltaTime;
	private ChangeForceClassDialog dialogoF;

	
	ControlPanel(Controller ctrl) {
		chooser = new JFileChooser(System.getProperty("user.dir") + "/resources/examples");
		_ctrl = ctrl;
		_stopped = true;
		dialogoF = new ChangeForceClassDialog(_ctrl);
		initGUI();
		this.add(toolBar, BorderLayout.PAGE_START);		
	    _ctrl.addObserver(this);
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
        
        toolBar.addSeparator();
		
		this.physics = new JButton();
		toolBar.add(physics);
		physics.setIcon(new ImageIcon("resources\\icons\\physics.png"));
		physics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	MostrarCustomForce();
            }
        });
		physics.setToolTipText("Force law selector");
		
        toolBar.addSeparator();

		this.play = new JButton();
		toolBar.add(play);
		play.setIcon(new ImageIcon("resources\\icons\\run.png"));
		play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _stopped = false;
                enableToolBar(false);
                _ctrl.setDeltaTime((Integer.parseInt(deltaTime.getText())));
                run_sim((int)steps.getValue());
                
                
            }
        });
		play.setToolTipText("Start simulation");
		
		this.stop = new JButton();
		toolBar.add(stop);
		stop.setIcon(new ImageIcon("resources\\icons\\stop.png"));
		stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	_stopped = true;
                enableToolBar(true);
            }
        });
		stop.setToolTipText("End simulation");
		
		
		toolBar.add(new JLabel("Steps:"));
		SpinnerNumberModel sm= new SpinnerNumberModel(1,1,99999,100);
		steps = new JSpinner(sm);
		toolBar.add(steps);
		
		toolBar.add(new JLabel("Delta-time:"));
		deltaTime = new TextField("10");
		toolBar.add(deltaTime);
		
		toolBar.add(Box.createGlue());

		this.exit = new JButton();

		toolBar.add(exit);
		exit.setIcon(new ImageIcon("resources\\icons\\exit.png"));
		exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING",
            	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            	    System.exit(0);
            	}
            }
            });
		exit.setToolTipText("Exit simulator");
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 50));
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
				JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR",
				        JOptionPane.ERROR_MESSAGE);
				enableToolBar(true);
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
			enableToolBar(true);
			// TODO enable all buttons
		}
	}
	
	private void enableToolBar(boolean enable) {
		this.load.setEnabled(enable);
		this.physics.setEnabled(enable);
		this.play.setEnabled(enable);
		this.steps.setEnabled(enable);
		this.deltaTime.setEnabled(enable);
		
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
	
	private void MostrarCustomForce() {
		dialogoF.setLocationRelativeTo(null);
		dialogoF.Mostrar();
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