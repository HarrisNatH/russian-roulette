# RUSSIAN ROULETTE

## DESCRIPTION
This small personal project is a game where a player pit against an AI in a death game where only one will get away alive. This also tests player's luck.

## Running the binary
Simply press run the program.

## Code details
The main works happens in `start()` in `Main.java`. 

It starts with a player press to start game, this proceeds to a `switch` case with a try-catch preceded. The game takes in methods mainly from `GameStart.java`.

`resetShotsFired()` will first reset the fired shots number from any of previous game if player chooses to restart. And finally `start()` is where the entire program kicks off.

in `setupGame()` this is where a player input how many chambers to play: a 6/9/12. After a number is chosen, it is made into an `ArrayList<>` set as false, with one is set as true. The chambers are shuffled

After `setupGame()` completed, enters a `while loop` basing on `playerTurn` boolean, each player has `takeTurn()` alternatively. For a player, there will be a thread delay to initialise first shoot every turn.

![image](/src/resources/player.png)

Each player and AI will have a choice to continue shoot (up to 2 times) or end the turn. Specially for AI side, uses a risk calculator method `aiDecision()` the smaller chambers left to fire, the higher risk getting a game over, prompts AI to end turn.

The game ends when either player or AI fired a real bullet. A game over ascii will be shown.

![image](/src/resources/gameOVer.png)

Then takes player back to main menu.

___
Thank you for reading and enjoy gambling your luck! May the odds be ever in your favor

## LICENSE
This project has a MIT license.
