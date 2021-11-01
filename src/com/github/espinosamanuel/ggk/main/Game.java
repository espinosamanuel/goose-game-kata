package com.github.espinosamanuel.ggk.main;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is the implementation of The Goose Game.
 * 
 * You can find the complete requirements at
 * <a href= "https://github.com/xpeppers/goose-game-kata/blob/master/README.md"
 * target="blank"> xpeppers/goose-game-kata</a>.
 *
 * @author Manuel Espinosa
 * @version 1.0
 * @since 31/10/2021
 */
public class Game {

	private LinkedHashMap<String, Player> players;
	private boolean running;
	private Scanner scanner;

	/**
	 * Constructor, creates a new game with empty player and position maps and
	 * the running attribute set to false.
	 */
	public Game() {
		super();
		this.players = new LinkedHashMap<String, Player>();
		this.running = false;
	}

	/**
	 * This method orchestrates the phases of the game. Any errors within the
	 * game are handled here.
	 */
	public void start() {
		// Instructions for the user
		notify(Constants.MSG_WELCOME);
		notify(Constants.MSG_ADD_PLAYERS);
		notify(Constants.MSG_START_GAME);
		notify(Constants.MSG_MOVE_INSTRUCTION);
		notify(Constants.MSG_MOVE_AUTO_INSTRUCTION);
		try {
			this.scanner = new Scanner(System.in);

			do {
				// Add players

				String instruction = scanner.nextLine();

				if (isPlay(instruction)) {
					if (this.players.size() < Constants.MIN_PLAYERS_NUMBER) {
						notify(Constants.MSG_GAME_STARTING_KO_PLAYERS_NUMBER);
						continue;
					}
					running = true;
				} else {
					notify(addPlayer(instruction));
				}

			} while (!this.running);

			notify(Constants.MSG_GAME_STARTING);
			do {
				playTurn();
			} while (running);

		} catch (Exception e) {
			notify(Constants.MSG_GAME_ERROR);
		} finally {
			this.scanner.close();
			notify(Constants.MSG_GAME_END);
		}

	}

	/**
	 * This method allows players to take a turn. The turn consists of one move
	 * by each of the players.
	 */
	private void playTurn() {

		for (Entry<String, Player> entry : this.players.entrySet()) {

			tryMove(entry.getValue());

			if (!running) {
				return;
			}
		}
	}

	/**
	 * This method simulates the roll of a dice.
	 * 
	 * @return int This returns a random number between 1 and 6.
	 */
	private int rollDice() {
		return ThreadLocalRandom.current().nextInt(1, 7);
	}

	/**
	 * This method allows the user to move the Player by validating the
	 * instruction. The move instruction can be with automatic or manual dice
	 * roll. In the first case, the move command and the player's name must be
	 * specified, in the second case, in addition to the move command and the
	 * name, valid values must be entered for the two dice. Case is not
	 * important, blanks will be ignored.
	 * 
	 * @param player
	 *            is the player to move
	 */
	private void tryMove(Player player) {

		String checkAuto = Constants.MOVE_INSTRUCTION.concat(player.getName());

		boolean valid = false;
		int diceA = 0, diceB = 0;
		do {

			String instruction = this.scanner.nextLine();

			if (instruction == null || instruction.isEmpty()) {
				notify(Constants.MSG_KO_INVALID_INS);
			}

			instruction = instruction.replaceAll("\\s+", "");

			if (checkAuto.equalsIgnoreCase(instruction)) {
				valid = true;
				diceA = rollDice();
				diceB = rollDice();
			} else {
				try {
					instruction = instruction.toLowerCase().replace(checkAuto.toLowerCase(), Constants.EMPTY);
					String[] diceInput = instruction.split(Constants.COMMA);
					if (diceInput != null && diceInput.length == 2) {
						diceA = Integer.valueOf((diceInput[0]));
						diceB = Integer.valueOf((diceInput[1]));

						if (diceA >= 1 && diceA <= 6 && diceB >= 1 && diceB <= 6) {
							valid = true;
						}
					}
				} catch (Exception e) {
					valid = false;
				}
			}

			if (!valid)
				notify(Constants.MSG_KO_INVALID_INS);

		} while (!valid);

		int source = player.getPosition();
		int target = source + diceA + diceB;
		notify(resolveMarkers(Constants.MSG_MOVE_OK.concat(move(player, diceA, diceB)), player.getName(), source,
				target, diceA, diceB));
	}

	/**
	 * This method updates the attribute position of the Player and the
	 * positionCache attribute of the Game.
	 * 
	 * @param player
	 *            is the player to move
	 * @param target
	 *            is the player position after the move
	 * @param source
	 *            is the player position before the move
	 * @return String Returns a message if there is a Prank Scenario or a Win
	 *         Scenario. Otherwise an empty string.
	 */
	private String updatePlayerPosition(Player player, int target, int source) {
		// Check for Prank scenario
		String prankMessage = prank(player, target);

		// Update player position
		player.setPosition(target);

		// Check for a winner
		if (target == Constants.WIN_POSITION) {
			this.running = false;
			return resolveMarkers(Constants.MSG_WIN, player.getName(), null, null, null, null);
		}

		// If there was a prank return the prankMessage
		if (prankMessage != null && !prankMessage.isEmpty()) {
			return prankMessage;
		}

		return Constants.EMPTY;
	}

	/**
	 * This method handles the prank scenario. It searches for another player on
	 * the target position and if so, updates the position of the found player
	 * with the starting position of the moving player.
	 * 
	 * @param player
	 *            is the player to move
	 * @param target
	 *            is the target position for the player
	 * @return Returns a prank message if there's a prank, otherwise an empty
	 *         string.
	 */
	private String prank(Player player, int target) {
		Player otherPlayer = null;
		for (Entry<String, Player> entry : this.players.entrySet()) {
			otherPlayer = entry.getValue();
			if (!player.getName().equals(otherPlayer.getName()) && target == otherPlayer.getPosition()) {
				// If there's another player on the target he goes to the source
				otherPlayer.setPosition(player.getPosition());
				return resolveMarkers(Constants.MSG_PRANK, otherPlayer.getName(), target, player.getPosition(), null,
						null);
			}
		}
		return Constants.EMPTY;
	}

	/**
	 * This method manages the movement of a player by also covering Win,
	 * Bridge, Goose, and Bounce scenarios.
	 * 
	 * @param player
	 *            is the player to move
	 * @param diceA
	 *            is the result of a dice roll
	 * @param diceB
	 *            is the result of a dice roll
	 * @return String Returns a custom message for each scenario.
	 */
	public String move(Player player, int diceA, int diceB) {
		int source = player.getPosition();
		int target = source + diceA + diceB;
		String moveMsg = Constants.EMPTY;
		if (target == Constants.BRIDGE_POSITION) {
			// The Bridge The target becomes the bridge target
			moveMsg = updatePlayerPosition(player, Constants.BRIDGE_TARGET_POSITION, source);
			return resolveMarkers(Constants.MSG_THE_BRIDGE, player.getName(), null, null, null, null).concat(moveMsg);
		} else if (Arrays.binarySearch(Constants.GOOSE_POSITIONS, target) >= 0) {
			// The Goose Update player position and move again recursively
			moveMsg = updatePlayerPosition(player, target, source);
			return resolveMarkers(Constants.MSG_THE_GOOSE, player.getName(), null, target + diceA + diceB, null, null)
					.concat(move(player, diceA, diceB)).concat(moveMsg);
		} else if (target > Constants.WIN_POSITION) {
			// The Bounce The target becomes the win position minus the excess
			target = (Constants.WIN_POSITION - (target - Constants.WIN_POSITION));
			moveMsg = updatePlayerPosition(player, target, source);
			return resolveMarkers(Constants.MSG_BOUNCE, player.getName(), null, target, null, null).concat(moveMsg);
		} else {
			// Base case
			moveMsg = updatePlayerPosition(player, target, source);
			return moveMsg;
		}
	}

	/**
	 * This method allows you to insert a player by entering the add instruction
	 * from the keyboard. It does not allow to add users already present in the
	 * game and performs validation on the instruction received.
	 * 
	 * @param addInstruction
	 *            is the string received from the keyboard
	 * @return String Return a message containing a validation error or the
	 *         notification of correct addition of a player
	 */
	public String addPlayer(String addInstruction) {

		if (addInstruction == null || addInstruction.isEmpty()) {
			return Constants.MSG_ADD_PLAYER_KO_INVALID;
		}

		if (!addInstruction.contains(Constants.ADD_INSTRUCTION)) {
			return Constants.MSG_KO_INVALID_INS;
		}

		String name = addInstruction.replaceAll(Constants.ADD_INSTRUCTION, Constants.EMPTY).trim();
		if (name.isEmpty()) {
			return Constants.MSG_ADD_PLAYER_KO_INVALID;
		}

		if (name.length() > Constants.NAME_MAX_LENGTH) {
			return name.concat(Constants.MSG_ADD_PLAYER_KO_TOO_LONG);
		}

		if (!name.matches(Constants.ALPHANUMERIC_REGEX)) {
			return Constants.MSG_ADD_PLAYER_KO_INVALID;
		}

		if (players.containsKey(name)) {
			return name.concat(Constants.MSG_ADD_PLAYER_KO_EXISTING);
		}

		for (String player : players.keySet()) {
			if (player.equalsIgnoreCase(name)) {
				return name.concat(resolveMarkers(Constants.MSG_ADD_PLAYER_KO_SIMILAR, player, null, null, null, null));
			}
		}

		this.players.put(name, new Player(name));
		return this.playersToString();
	}

	/**
	 * 
	 * @param instruction
	 *            Instruction taken from keyboard
	 * @return Returns true if instruction equals to
	 *         {@value com.github.espinosamanuel.ggk.main.Constants#PLAY_INSTRUCTION}
	 *         , otherwise false. Case is not important.
	 */
	private boolean isPlay(String instruction) {

		if (Constants.PLAY_INSTRUCTION.equalsIgnoreCase(instruction.trim()))
			return true;

		return false;
	}

	/**
	 * This method renders the messages that the game uses to interact with
	 * players.
	 * 
	 * @param message
	 *            is the string to print
	 */
	private void notify(String message) {
		if (message == null)
			return;

		System.out.println(message);
	}

	/**
	 * @return This method returns the comma separated list of players.
	 */
	public String playersToString() {
		return Constants.PLAYERS_LIST_PREFIX.concat(String.join(", ", this.players.keySet()));
	}

	/**
	 * This method replaces in the input string the value of the markers passed
	 * as parameter.
	 * 
	 * @param message
	 *            is the string in which the markers will have to be replaced
	 * @param player
	 *            is the player name
	 * @param source
	 *            is the value for the marker
	 *            {@value com.github.espinosamanuel.ggk.main.Constants#SOURCE_MARKER}
	 * @param target
	 *            is the value for the marker
	 *            {@value com.github.espinosamanuel.ggk.main.Constants#TARGET_MARKER}
	 * @param diceA
	 *            is the value for the marker
	 *            {@value com.github.espinosamanuel.ggk.main.Constants#DICE_A_MARKER}
	 * @param diceB
	 *            is the value for the marker
	 *            {@value com.github.espinosamanuel.ggk.main.Constants#DICE_B_MARKER}
	 * 
	 * @return String returns the parameter after replacements have been made.
	 */
	public String resolveMarkers(String message, String player, Integer source, Integer target, Integer diceA,
			Integer diceB) {
		if (source != null)
			message = message.replaceAll(Constants.SOURCE_MARKER, decodePosition(source));
		if (target != null)
			message = message.replaceAll(Constants.TARGET_MARKER, decodePosition(target));
		if (diceA != null)
			message = message.replaceAll(Constants.DICE_A_MARKER, diceA.toString());
		if (diceB != null)
			message = message.replaceAll(Constants.DICE_B_MARKER, diceB.toString());
		if (player != null)
			message = message.replaceAll(Constants.PLAYER_NAME_MARKER, player);

		return message;
	}

	/**
	 * This method replaces the number of a position with its name in case of
	 * special positions.
	 * 
	 * @param position
	 *            is one of the 63 positions
	 * @return returns the string representation of the position parameter or
	 *         its name in case it is the bridge, start or win position.
	 */
	private String decodePosition(Integer position) {
		if (position == Constants.BRIDGE_POSITION) {
			return Constants.BRIDGE_NAME;
		}

		if (position == Constants.START_POSITION) {
			return Constants.START;
		}

		if (position > Constants.WIN_POSITION) {
			return Integer.toString(Constants.WIN_POSITION);
		}

		return position.toString();
	}

	public LinkedHashMap<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedHashMap<String, Player> players) {
		this.players = players;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
