
# The Goose Game

A Java implementation of the Goose Game.
You can find the complete requirements at [xpeppers/goose-game-kata](https://github.com/xpeppers/goose-game-kata/blob/master/README.md).


## Installation

**Requirements:**
- JDK 1.8+ installed
- Environment variables set correctly

Once you have downloaded the source, move to the src directory:

```bash
  cd your/path/src/
```

Compile Java sources:

```bash
  javac com/github/espinosamanuel/ggk/main/*.java
```
Run the game:

```bash
  java com.github.espinosamanuel.ggk.main.GameLauncher
```
**NOTE:**
You can also import this source as a Java project into an IDE. You will be able to compile and run the game directly from there.

## Gameplay

First add the players using the instruction:
```bash
  add player PLAYER_NAME
```
When you want to start the game use the instruction:
```bash
  play
```
**NOTE:**
You will need at least 2 players to start the game.

Use movement instruction with automatic dice throw:
```bash
  move PLAYER_NAME
```
or the movement instruction with manual dice throw:
```bash
  move PLAYER_NAME FIRST_DICE,SECOND_DICE
```
**NOTE:**
Case and blanks will be ignored to make easier movement instructions.

## Documentation

You can find the javadoc [here](https://espinosamanuel.github.io/goose-game-kata/).


## License

[MIT](https://github.com/espinosamanuel/goose-game-kata/blob/main/LICENSE)
