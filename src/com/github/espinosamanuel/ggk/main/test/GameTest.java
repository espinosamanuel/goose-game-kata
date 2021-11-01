package com.github.espinosamanuel.ggk.main.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.espinosamanuel.ggk.main.Constants;
import com.github.espinosamanuel.ggk.main.Game;
import com.github.espinosamanuel.ggk.main.Player;

public class GameTest {

	private Game game;

	@Test
	public void addPlayer() {
		Game test = new Game();

		assertEquals(Constants.PLAYERS_LIST_PREFIX + "Pippo", test.addPlayer(Constants.ADD_INSTRUCTION + " Pippo"));
		assertEquals(Constants.PLAYERS_LIST_PREFIX + "Pippo, Pluto",
				test.addPlayer(Constants.ADD_INSTRUCTION + " Pluto"));
		assertEquals("Pippo" + Constants.MSG_ADD_PLAYER_KO_EXISTING,
				test.addPlayer(Constants.ADD_INSTRUCTION + " Pippo"));
		assertEquals("pIpPo: already existing player has a similar name (Pippo)",
				test.addPlayer(Constants.ADD_INSTRUCTION + " pIpPo"));
		assertEquals(Constants.MSG_ADD_PLAYER_KO_INVALID, test.addPlayer(Constants.ADD_INSTRUCTION + " Pippo@pluto"));
		assertEquals(Constants.MSG_KO_INVALID_INS, test.addPlayer("add"));
		assertEquals("30characterplayernamekotestexample" + Constants.MSG_ADD_PLAYER_KO_TOO_LONG,
				test.addPlayer(Constants.ADD_INSTRUCTION + " 30characterplayernamekotestexample"));
	}

	@Test
	public void prank() {
		initTestGame(15, 17);

		Player pippo = game.getPlayers().get("Pippo");
		Player pluto = game.getPlayers().get("Pluto");

		int source = pippo.getPosition();
		int diceA = 1;
		int diceB = 1;
		int target = source + diceA + diceB;

		assertEquals("Pippo rolls 1, 1. Pippo moves from 15 to 17. On 17 there is Pluto, who returns to 15",
				this.game.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, diceA, diceB)),
						pippo.getName(), source, target, diceA, diceB));
		int pippoPosition = pippo.getPosition();
		int plutoPosition = pluto.getPosition();
		assertTrue("Incorrect position Pippo: " + pippoPosition, pippoPosition == 17);
		assertTrue("Incorrect position Pluto: " + plutoPosition, plutoPosition == 15);

	}

	@Test
	public void goose() {
		initTestGame(3, 0);

		Player pippo = game.getPlayers().get("Pippo");

		int source = pippo.getPosition();
		int diceA = 1;
		int diceB = 1;
		int target = source + diceA + diceB;

		// Single goose
		assertEquals("Pippo rolls 1, 1. Pippo moves from 3 to 5, The Goose. Pippo moves again and goes to 7",
				this.game.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, diceA, diceB)),
						pippo.getName(), source, target, diceA, diceB));
		int position = pippo.getPosition();
		assertTrue("Incorrect position after single goose: " + position, position == 7);

	}

	@Test
	public void gooseMultiple() {
		initTestGame(10, 0);

		Player pippo = game.getPlayers().get("Pippo");

		int source = pippo.getPosition();
		int diceA = 2;
		int diceB = 2;
		int target = source + diceA + diceB;

		// Combo goose
		assertEquals(
				"Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22",
				this.game.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, diceA, diceB)),
						pippo.getName(), source, target, diceA, diceB));
		int position = pippo.getPosition();
		assertTrue("Incorrect position after multi goose: " + position, position == 22);

	}

	@Test
	public void bridge() {
		initTestGame(4, 0);

		Player pippo = game.getPlayers().get("Pippo");

		int source = pippo.getPosition();
		int diceA = 1;
		int diceB = 1;
		int target = source + diceA + diceB;

		assertEquals("Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12",
				this.game.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, diceA, diceB)),
						pippo.getName(), source, target, diceB, diceA));
		int position = pippo.getPosition();
		assertTrue("Incorrect position: " + position, position == 12);
	}

	@Test
	public void bounce() {
		initTestGame(60, 0);

		Player pippo = game.getPlayers().get("Pippo");

		int source = pippo.getPosition();
		int diceA = 3;
		int diceB = 2;
		int target = source + diceA + diceB;

		// Try bounce
		assertEquals("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61",
				this.game.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, diceA, diceB)),
						pippo.getName(), source, target, diceA, diceB));
		int position = pippo.getPosition();

		assertTrue("Incorrect position: " + position, position == 61);
	}

	@Test
	public void bridgePrank() {
		initTestGame(0, 0);

		Player pippo = game.getPlayers().get("Pippo");
		Player pluto = game.getPlayers().get("Pluto");

		this.game.move(pippo, 6, 6);
		this.game.move(pluto, 5, 1);
		int pippoPosition = pippo.getPosition();
		int plutoPosition = pluto.getPosition();

		assertTrue("Incorrect position Pippo: " + pippoPosition, pippoPosition == 0);
		assertTrue("Incorrect position Pluto: " + plutoPosition, plutoPosition == 12);
	}

	@Test
	public void multipleGoosePrank() {
		initTestGame(36, 0);

		Player pippo = game.getPlayers().get("Pippo");
		Player pluto = game.getPlayers().get("Pluto");

		this.game.move(pluto, 5, 4);
		int pippoPosition = pippo.getPosition();
		int plutoPosition = pluto.getPosition();

		assertTrue("Incorrect position Pippo: " + pippoPosition, pippoPosition == 27);
		assertTrue("Incorrect position Pluto: " + plutoPosition, plutoPosition == 36);
	}

	@Test
	public void win() {
		initTestGame(60, 55);

		Player pippo = game.getPlayers().get("Pippo");

		assertEquals("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!", this.game
				.resolveMarkers(Constants.MSG_MOVE_OK.concat(this.game.move(pippo, 1, 2)), "Pippo", 60, 63, 1, 2));
	}

	private void initTestGame(int pippoPosition, int plutoPosition) {
		this.game = new Game();
		this.game.addPlayer("add player Pippo");
		this.game.addPlayer("add player Pluto");
		this.game.setRunning(true);

		Player pippo = game.getPlayers().get("Pippo");
		Player pluto = game.getPlayers().get("Pluto");

		if (pippoPosition != 0)
			this.game.move(pippo, pippoPosition, 0);
		if (plutoPosition != 0)
			this.game.move(pluto, plutoPosition, 0);

	}

}
