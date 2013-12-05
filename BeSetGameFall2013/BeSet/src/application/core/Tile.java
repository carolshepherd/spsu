package application.core;

import javafx.scene.image.Image;

public class Tile {
	public enum Symbol {
		UNDEFINED (0, "Undefined"),
		OVAL (1, "Oval"),
		DIAMOND (2, "Diamond"),
		SQUIGGLE (3, "Squiggle");
		
		public final int value;
		private final String string;
		
		Symbol(int value, String string) {
			this.value = value;
			this.string = string;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}
	public enum Color {
		UNDEFINED (0, "Undefined"),
		RED (1, "Red"),
		BLUE (2, "Blue"),
		YELLOW (3, "Yellow");
		
		public final int value;
		private final String string;
		
		Color(int value, String string) {
			this.value = value;
			this.string = string;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}
	public enum Number {
		UNDEFINED (0, "Undefined"),
		ONE (1, "1"),
		TWO (2, "2"),
		THREE (3, "3");
		
		public final int value;
		private final String string;
		
		Number(int value, String string) {
			this.value = value;
			this.string = string;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}
	public enum Shading {
		UNDEFINED (0, "Undefined"),
		SOLID (1, "Solid"),
		STRIPED (2, "Shaded"),
		OUTLINED (3, "Open");
		
		public final int value;
		private final String string;
		
		Shading(int value, String string) {
			this.value = value;
			this.string = string;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}

	private final Symbol _symbol;
	private final Color _color;
	private final Number _number;
	private final Shading _shading;
	
	private boolean _claimedByBastet = false;
	private Image _texture;
	private int _x = -1;
	private int _y = -1;

	public String positionID;
	
	public String getPositionID() {
		return positionID;
	}

	public void setPositionID(String positionID) {
		this.positionID = positionID;
	}
	
	public static final Tile Undefined = new Tile(Symbol.UNDEFINED, Color.UNDEFINED, Number.UNDEFINED, Shading.UNDEFINED, new Image("BeSetArt/Images/UNDEFINED.png"));
	
	public Tile(int symbol, int color, int number, int shading, Image image) {
		_symbol = toSymbol(symbol);
		_color = toColor(color);
		_number = toNumber(number);
		_shading = toShading(shading);
		_texture = image;
	}
	public Tile(Symbol symbol, Color color, Number number, Shading shading, Image image) {
		_symbol = symbol;
		_color = color;
		_number = number;
		_shading = shading;
		_texture = image;
	}
	
	public void setPosition(int x, int y) {
		_x = x;
		_y = y;
	}
	public int[] getPosition() {
		int[] coords = new int[2];
		coords[0] = _x;
		coords[1] = _y;
		return coords;
	}
	
	public int X() {
		return _x;
	}
	public int Y() {
		return _y;
	}
	
	public void claim() {
		_claimedByBastet = true;
	}
	public boolean isClaimed() {
		return _claimedByBastet;
	}
	
	public Shading shading() {
		return _shading;
	}
	public Color color() {
		return _color;
	}
	public Number number() {
		return _number;
	}
	public Symbol symbol() {
		return _symbol;
	}
	public Image getTexture() {
		return _texture;
	}
	public void setTexture(Image image) {
		_texture = image;
	}
	
	public static boolean checkSet(Tile tile1, Tile tile2, Tile tile3) {
		if (checkFeature(tile1.color().value, tile2.color().value, tile3.color().value) &&
			checkFeature(tile1.number().value, tile2.number().value, tile3.number().value) &&
			checkFeature(tile1.shading().value, tile2.shading().value, tile3.shading().value) &&
			checkFeature(tile1.symbol().value, tile2.symbol().value, tile3.symbol().value)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean checkSetDebugging(Tile tile1, Tile tile2, Tile tile3) {
		System.out.println("Checking if the set is a match...");
		StringBuilder result = new StringBuilder();
		if (checkFeature(tile1.color().value, tile2.color().value, tile3.color().value, result) &&
			checkFeature(tile1.number().value, tile2.number().value, tile3.number().value, result) &&
			checkFeature(tile1.shading().value, tile2.shading().value, tile3.shading().value, result) &&
			checkFeature(tile1.symbol().value, tile2.symbol().value, tile3.symbol().value, result)) {
			result.append("--> This is a match.");
			System.out.println(result.toString());
			return true;
		} else {
			result.append("--> This is not a match.");
			System.out.println(result.toString());
			return false;
		}
	}
	private static boolean checkFeature(int f1, int f2, int f3) {
		if (f1 == f2 && f2 == f3) {
			return true;
		}
		if (f1 != f2 && f1 != f3 && f2 != f3) {
			return true;
		}
		return false;
	}
	private static boolean checkFeature(int f1, int f2, int f3, StringBuilder result) {
		if (f1 == f2 && f2 == f3) {
			result.append("{ f1: " + f1 + ", f2: " + f2 + ", f3: " + f3 + " } ");
			return true;
		}
		if (f1 != f2 && f1 != f3 && f2 != f3) {
			result.append("{ f1: " + f1 + ", f2: " + f2 + ", f3: " + f3 + " } ");
			return true;
		}
		result.append("{ f1: " + f1 + ", f2: " + f2 + ", f3: " + f3 + " } ");
		return false;
	}
	
	public String toNumberString() {
		return Integer.toString(_number.value) + Integer.toString(_color.value) + Integer.toString(_shading.value) + Integer.toString(_symbol.value);
	}
	
	@Override
	public String toString() {
		return _number.toString() + " " + _color.toString() + " " + _shading.toString() + " " + _symbol.toString();
	}
	public static String toColorString(int color) {
		switch (color) {
		case 1: return Color.RED.toString();
		case 2: return Color.BLUE.toString();
		case 3: return Color.YELLOW.toString();
		default: return Color.RED.toString();
		}
	}
	private Color toColor(int color) {
		switch (color) {
			case 1: return Color.RED;
			case 2: return Color.BLUE;
			case 3: return Color.YELLOW;
			default: return Color.RED;
		}
	}
	public static String toShadingString(int shading) {
		switch (shading) {
		case 1: return Shading.SOLID.toString();
		case 2: return Shading.STRIPED.toString();
		case 3: return Shading.OUTLINED.toString();
		default: return Shading.SOLID.toString();
		}
	}
	private Shading toShading(int shading) {
		switch (shading) {
			case 1: return Shading.SOLID;
			case 2: return Shading.STRIPED;
			case 3: return Shading.OUTLINED;
			default: return Shading.SOLID;
		}
	}
	public static String toSymbolString(int symbol) {
		switch (symbol) {
		case 1: return Symbol.OVAL.toString();
		case 2: return Symbol.DIAMOND.toString();
		case 3: return Symbol.SQUIGGLE.toString();
		default: return Symbol.OVAL.toString();
		}
	}
	private Symbol toSymbol(int symbol) {
		switch (symbol) {
			case 1: return Symbol.OVAL;
			case 2: return Symbol.DIAMOND;
			case 3: return Symbol.SQUIGGLE;
			default: return Symbol.OVAL;
		}
	}
	public static String toNumberString(int number) {
		switch (number) {
		case 1: return Number.ONE.toString();
		case 2: return Number.TWO.toString();
		case 3: return Number.THREE.toString();
		default: return Number.ONE.toString();
		}
	}
	private Number toNumber(int number) {
		switch (number) {
			case 1: return Number.ONE;
			case 2: return Number.TWO;
			case 3: return Number.THREE;
			default: return Number.ONE;
		}
	}

}
