package com.github.espinosamanuel.ggk.main;

/**
 * This class is the implementation of a Player of The Goose Game.
 *
 * @author Manuel Espinosa
 * @version 1.0
 * @since 31/10/2021
 */
public class Player {

	private String name;

	private int position;

	/**
	 * Constructor, creates a new player at position 0.
	 * 
	 * @param name
	 *            is the value for the attribute name
	 */
	public Player(String name) {
		super();
		this.name = name;
		this.position = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
