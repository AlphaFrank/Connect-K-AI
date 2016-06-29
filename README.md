# Connect-K-AI

An AI to play Connect-K, whicn is a variation of the famous Connect-4 game.

To run the game:
Run ConnectKGUI.java in Connect-K/src/connectK.
Click File on the popout window
Click New
Manually add Connect-K/bin/MyAI.class as the game AI
Click New Game

The AI uses minimax algorithm with alpha-beta pruning to expand search space. A free-k sequence is a sequence of k consecutive pieces (it doesn't matter it is vertical, horizontal or diagnal) whose two endpoints are both free (not blocked by opposite pieces). The evaluation function for each state is defined as following (from the aspect of the AI):

1. If there is a winner, return the corresponding value (+inf for me, -inf for the opponent).

2. Else if there are two or more free-k sequences, return the corresponding value (+inf for me, -inf for the opponent).

3. Else, count the number of opposite pieces adjacent to my pieces, and return that number.

The result of this design is that this AI is extremely defensive, since it always tries to place pieces adjacent to opposite pieces if it does not find a winning or losing situation. This AI also understands that mutiple free-k sequences mean win so that it can realizes the winning or losing situation ahead.
