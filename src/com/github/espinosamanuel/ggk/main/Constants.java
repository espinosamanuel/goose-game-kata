package com.github.espinosamanuel.ggk.main;

/**
 * Utility class that contains some constants useful for the game and all the
 * messages to be shown to the player.
 *
 * @author Manuel Espinosa
 * @version 1.0
 * @since 31/10/2021
 */
public class Constants {

	// Utils
	public static final String EMPTY = "";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String TO = " to ";
	public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";
	public static final int NAME_MAX_LENGTH = 30;
	public static final String PLAYERS_LIST_PREFIX = "players: ";
	public static final String RESTART = "restart";
	public static final String END = "end";
	public static final int MIN_PLAYERS_NUMBER = 2;

	// Markers
	public static final String PLAYER_NAME_MARKER = "###playername###";
	public static final String DICE_A_MARKER = "###diceA###";
	public static final String DICE_B_MARKER = "###diceB###";
	public static final String TARGET_MARKER = "###target###";
	public static final String SOURCE_MARKER = "###source###";

	// Positions
	public static final int WIN_POSITION = 63;
	public static final int[] GOOSE_POSITIONS = { 5, 9, 14, 18, 23, 27 };
	public static final int BRIDGE_POSITION = 6;
	public static final int BRIDGE_TARGET_POSITION = 12;
	public static final int START_POSITION = 0;
	public static final String BRIDGE_NAME = "The Bridge";
	public static final String START = "Start";

	// Instructions
	public static final String ADD_INSTRUCTION = "add player";
	public static final String MOVE_INSTRUCTION = "move";
	public static final String PLAY_INSTRUCTION = "play";

	// Messages
	public static final String MSG_WELCOME = "***** Welcome to The Goose Game! *****";
	public static final String MSG_ADD_PLAYERS = "Please add at least 2 players (\"" + ADD_INSTRUCTION
			+ " PLAYER_NAME\")";
	public static final String MSG_START_GAME = "When all the players are ready type \"" + PLAY_INSTRUCTION
			+ "\" to start the game";
	public static final String MSG_MOVE_INSTRUCTION = "To move a player use the instruction \"move PLAYER_NAME FIRST_DICE, SECOND_DICE\" (The case and white spaces are not important)";
	public static final String MSG_MOVE_AUTO_INSTRUCTION = "If you want the game to roll the dice use the instruction \"move PLAYER_NAME\" (The case and white spaces are not important)";
	public static final String MSG_ADD_PLAYER_KO_INVALID = "Ivalid name. The name can't be empty and must be alphanumeric";
	public static final String MSG_ADD_PLAYER_KO_TOO_LONG = ": too long, max length is " + NAME_MAX_LENGTH + " chars";
	public static final String MSG_ADD_PLAYER_KO_EXISTING = ": already existing player";
	public static final String MSG_ADD_PLAYER_KO_SIMILAR = ": already existing player has a similar name ("
			+ PLAYER_NAME_MARKER + ")";
	public static final String MSG_KO_INVALID_INS = "Please enter a valid instruction";
	public static final String MSG_GAME_STARTING_KO_PLAYERS_NUMBER = "Please add at least " + MIN_PLAYERS_NUMBER
			+ " players before start the game";
	public static final String MSG_GAME_STARTING = "The game has started, please move the first player";
	public static final String MSG_GAME_END = "***** The Goose Game is over *****";
	public static final String MSG_MOVE_OK = PLAYER_NAME_MARKER + " rolls " + DICE_A_MARKER + ", " + DICE_B_MARKER
			+ ". " + PLAYER_NAME_MARKER + " moves from " + SOURCE_MARKER + " to " + TARGET_MARKER;
	public static final String MSG_GAME_ERROR = "Ouch! the game has been stopped after an error";
	public static final String MSG_WIN = ". " + PLAYER_NAME_MARKER + " Wins!!";
	public static final String MSG_THE_BRIDGE = ". " + PLAYER_NAME_MARKER + " jumps to " + BRIDGE_TARGET_POSITION;
	public static final String MSG_THE_GOOSE = ", The Goose. " + PLAYER_NAME_MARKER + " moves again and goes to "
			+ TARGET_MARKER;
	public static final String MSG_BOUNCE = ". " + PLAYER_NAME_MARKER + " bounces! " + PLAYER_NAME_MARKER
			+ " returns to " + TARGET_MARKER;
	public static final String MSG_PRANK = ". On " + SOURCE_MARKER + " there is " + PLAYER_NAME_MARKER
			+ ", who returns to " + TARGET_MARKER;
}
