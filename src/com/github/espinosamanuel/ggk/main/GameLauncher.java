package com.github.espinosamanuel.ggk.main;

/**
 * This class launches The Goose Game
 *
 * @author Manuel Espinosa
 * @version 1.0
 * @since 31/10/2021
 */
public class GameLauncher {

	public static void main(String[] args) {
		launch();
	}
	
	/**
	 * This method creates a new Game and start it.
	 */
	public static void launch() {
		Game gooseGame = new Game();
		gooseGame.start();
	}

}
