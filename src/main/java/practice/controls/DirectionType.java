package practice.controls;

public enum DirectionType {
	DPAD (DPadHandler.class),
	HAT (HatSwitchHandler.class);

	public final Class<? extends ControllerHandler> handler;

	private DirectionType(Class<? extends ControllerHandler> c) {
		handler = c;
	}
}