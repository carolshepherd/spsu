package application.core;

import application.logic.GameMode;

public final class Settings {
	private static GameMode _gameMode = GameMode.None;
	
	public static void setGameMode(GameMode mode) {
		_gameMode = mode;
	}
	public static GameMode getGameMode() {
		return _gameMode;
	}
}
