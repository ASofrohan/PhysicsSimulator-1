
package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.EpsilonEqualStatesBuilder;
import simulator.factories.Factory;
import simulator.factories.MassEqualStatesBuilder;
import simulator.factories.MassLosingBodyBuilder;
import simulator.factories.MovingTowardsFixedPointBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoForceBuilder;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;
 
//-m gui -i resources/examples/ex1.2body.json -o resources/output/out.1.json -s 10000 -dt 10000 -fl nlug
public class Main {

	// default values for some parameters
	//
	private final static int _stepsDefaultValue = 150;
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";


	// some attributes to stores values corresponding to command-line parameters
	//
	private static String mode = "batch";
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _expFile = null;
	private static int _steps = 0;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;

	// factories
	private static Factory<Body> _bodyFactory = null;
	private static Factory<ForceLaws> _forceLawsFactory = null;
	private static Factory<StateComparator> _stateComparatorFactory = null;

	private static void init() {
		// TODO initialize the bodies factory
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<Builder<Body>>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);
		// TODO initialize the force laws factory
		ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<Builder<ForceLaws>>();
		forceBuilders.add(new NewtonUniversalGravitationBuilder());
		forceBuilders.add(new MovingTowardsFixedPointBuilder());
		forceBuilders.add(new NoForceBuilder());
		_forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceBuilders);
		// TODO initialize the state comparator
		ArrayList<Builder<StateComparator>> comparatorBuilders = new ArrayList<Builder<StateComparator>>();
		comparatorBuilders.add(new EpsilonEqualStatesBuilder());
		comparatorBuilders.add(new MassEqualStatesBuilder());
		_stateComparatorFactory = new BuilderBasedFactory<StateComparator>(comparatorBuilders);
	}

	private static void parseArgs(String[] args) {
		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();
		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			// TODO add support of -o, -eo, and -s (define corresponding parse methods)
			parseStepsOption(line);
			parseOutputOption(line);
			parseExpectedOutputOption(line);
			
			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		//expected-output
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").hasArg()
				.desc("The expected output file. If not provided\n" 
						+ "no comparison is applied")
				.build());
		//steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg()
				.desc("An integer representing the number of\n"
						+ "simulation steps. Default value: " + _stepsDefaultValue + ".")
				.build());
		
		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Output file, where output is written.\n"
						+ "Default value: the standard output.")
				.build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());
		
		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());
		
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
				.desc("Execution Mode. Possible values: �batch�(Batch mode), �gui� (Graphical UserInterface mode). Default value: �batch�.").build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";
		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException{
		mode = line.getOptionValue("m");
		
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && mode.equals("batch")) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}

	private static void parseExpectedOutputOption(CommandLine line) throws ParseException{
		_expFile = line.getOptionValue("eo"); 
	}
	
	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static void parseOutputOption(CommandLine line) throws ParseException{
		_outFile = line.getOptionValue("o");
	}
	
	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}
	
	private static void parseStepsOption(CommandLine line) throws ParseException{
		String steps = line.getOptionValue("steps", Integer.toString(_stepsDefaultValue));
		try {
			_steps = Integer.parseInt(steps);
			assert (_steps >= 0);
		}catch(Exception e) {
			throw new ParseException("Invalid number of steps: " + steps);
		}
	}

	private static void startBatchMode() throws Exception {
		PhysicsSimulator sim = new PhysicsSimulator(_forceLawsFactory.createInstance(_forceLawsInfo), _dtime);	
		OutputStream outStream;
		if (_outFile==null)	outStream = System.out;
		else outStream = new FileOutputStream(new File(_outFile));
		InputStream inStream = new FileInputStream(new File(_inFile));
		InputStream expStream = null;
		if(_expFile != null) expStream = new FileInputStream(new File(_expFile));
		
		StateComparator cmp = _stateComparatorFactory.createInstance(_stateComparatorInfo);
		
		Controller controller = new Controller(sim, _bodyFactory, _forceLawsFactory);
		controller.loadBodies(inStream);
		controller.run(_steps, outStream, expStream, cmp);
	}
	
	private static void startGUIMode() throws IOException {
		PhysicsSimulator sim = new PhysicsSimulator(_forceLawsFactory.createInstance(_forceLawsInfo), _dtime);
		Controller ctrl = new Controller(sim, _bodyFactory, _forceLawsFactory );
		if(_inFile != null) {
			InputStream in = new FileInputStream(new File(_inFile));
			ctrl.loadBodies(in);
			in.close();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			new MainWindow(ctrl);
			}
			});

		
		System.out.println("Done!");
	}
	

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(mode.equals("batch")) {
			startBatchMode();
		}
		else if(mode.equals("gui")) 
			startGUIMode();
		else
			System.err.println("Console o Gui mal escrito");
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
