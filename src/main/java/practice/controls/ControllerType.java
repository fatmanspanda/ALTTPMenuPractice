package practice.controls;

import static net.java.games.input.Component.*;
import static net.java.games.input.Component.Identifier.Button.*;

import net.java.games.input.Controller;

public enum ControllerType {
	KEYBOARD (DirectionType.DPAD,
			Key.UP, Key.DOWN, Key.RIGHT, Key.LEFT,
			Key.Q, Key.W, Key.R, Key.E,
			Key.S, Key.A, Key.D, Key.F,
			"Keyboard"),
	MAYFLASH_GAMECUBE (DirectionType.DPAD,
			_12, _14, _13, _15,
			_1, _2, _0, _3,
			_5, _4, _9, _7,
			"Mayflash GameCube Controller Adapter"),
	WINDOWS_XBOX (DirectionType.HAT,
			Identifier.Axis.POV, Identifier.Axis.POV, Identifier.Axis.POV, Identifier.Axis.POV,
			_1, _0, _3, _2,
			_5, _4, _7, _6,
			"XBOX 360 For Windows (Controller)",
			"Controller (XBOX 360 For Windows)",
			"Controller (MadCatz GamePad)"),
	PS3 (DirectionType.DPAD,
			_4, _6, _5, _7,
			_13, _14, _15, _12,
			_11, _10, _3, _0,
			"PLAYSTATION(R)3 Controller"),
	N64 (DirectionType.HAT,
			Identifier.Axis.POV, Identifier.Axis.POV, Identifier.Axis.POV, Identifier.Axis.POV,
			_6, _8, _0, _3,
			_5, _4, _9, _7), // does this need support?
	IBUFFALO (DirectionType.AXIS,
			Identifier.Axis.Y, Identifier.Axis.Y, Identifier.Axis.X, Identifier.Axis.X,
			_0, _1, _2, _3,
			_5, _4, _7, _6,
			"USB,2-axis 8-button gamepad"
			),
	BASIC_PAD (DirectionType.AXIS,
			Identifier.Axis.Y, Identifier.Axis.Y, Identifier.Axis.X, Identifier.Axis.X,
			_1, _2, _0, _3,
			_6, _4, _9, _8,
			"usb gamepad"),
	SCATTER (DirectionType.AXIS,
			Identifier.Axis.Y, Identifier.Axis.Y, Identifier.Axis.X, Identifier.Axis.X,
			_1, _2, _0, _3, // A B X Y
			_5, _4, _9, _8, // R L St Sl
			"2Axes 11Keys Game Pad"),
	;

	private final String[] names;

	public final DirectionType dType;

	public final Identifier hatSwitch;

	public final Identifier defaultUp;
	public final Identifier defaultDown;
	public final Identifier defaultRight;
	public final Identifier defaultLeft;

	public final Identifier defaultA;
	public final Identifier defaultB;
	public final Identifier defaultX;
	public final Identifier defaultY;

	public final Identifier defaultR;
	public final Identifier defaultL;
	public final Identifier defaultStart;
	public final Identifier defaultSelect;

	public final Class<? extends Identifier> buttonType;
	public final Class<? extends Identifier> moveType;

	private ControllerType(
			DirectionType d,
			Identifier defaultUp, Identifier defaultDown, Identifier defaultRight, Identifier defaultLeft,
			Identifier defaultA, Identifier defaultB, Identifier defaultX, Identifier defaultY,
			Identifier defaultR, Identifier defaultL, Identifier defaultStart, Identifier defaultSelect,
			String... names) {

		dType = d;

		if (defaultUp instanceof Identifier.Axis) {
			this.hatSwitch = defaultUp;
		} else {
			this.hatSwitch = null;
		}

		this.defaultUp = defaultUp;
		this.defaultDown = defaultDown;
		this.defaultRight = defaultRight;
		this.defaultLeft = defaultLeft;

		this.defaultA = defaultA;
		this.defaultB = defaultB;
		this.defaultX = defaultX;
		this.defaultY = defaultY;

		this.defaultR = defaultR;
		this.defaultL = defaultL;

		this.defaultStart = defaultStart;
		this.defaultSelect = defaultSelect;

		this.names = names;

		buttonType = defaultStart.getClass();
		moveType = defaultUp.getClass();
	}

	private static ControllerType getTypeFromName(String n) throws ControllerException {
		ControllerType ret = null;
		typeSearch :
		for (ControllerType t : values()) {
			for (String s : t.names) {
				if (n.replaceAll("\\s","").equalsIgnoreCase(s.replaceAll("\\s",""))) {
					ret = t;
					break typeSearch;
				}
			}
		}
		if (ret == null) {
			throw new ControllerException(
					String.format("Unable to match your controller's name (%s) to a valid type.", n));
		}
		return ret;
	}

	public static ControllerType inferType(Controller c) throws ControllerException {
		if (c.getType() == Controller.Type.KEYBOARD) {
			return KEYBOARD;
		}
		return getTypeFromName(c.getName());
	}

	public Identifier getDefaultButton(SNESButton b) {
		Identifier ret = null;
		switch(b) {
			case UP :
				ret = defaultUp;
				break;
			case DOWN :
				ret = defaultDown;
				break;
			case RIGHT :
				ret = defaultRight;
				break;
			case LEFT :
				ret = defaultLeft;
				break;
			case A :
				ret = defaultA;
				break;
			case B :
				ret = defaultB;
				break;
			case X :
				ret = defaultX;
				break;
			case Y :
				ret = defaultY;
				break;
			case R :
				ret = defaultR;
				break;
			case L :
				ret = defaultL;
				break;
			case START :
				ret = defaultStart;
				break;
			case SELECT :
				ret = defaultSelect;
				break;
		}
		return ret;
	}
}