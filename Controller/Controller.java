package Controller;

import Model.GlobalBoard;
import View.EventCollector;

public class Controller {
	GlobalBoard board;
	Player[] players;
	int currentPlayer;
	final int delay = 50;
	int countdown;

	public Controller(GlobalBoard b, boolean[] AI) {
		board = b;
		players = new Player[AI.length];
		for (int i = 0; i < players.length; i++)
			if (AI[i])
				players[i] = new AIPlayer(i, board);
			else
				players[i] = new HumanPlayer(i, board);
	}

	void changeJoueur() {
		currentPlayer = (currentPlayer + 1) % players.length;
		countdown = delay;
	}

	public void tick() {
//		if (board.enCours()) {
//			if (countdown == 0) {
//				// Lorsque le temps est écoulé on le transmet au joueur courant.
//				// Si un coup a été joué (IA) on change de joueur.
//				if (players[currentPlayer].tick()) {
//					changeJoueur();
//				} else {
//				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
//					System.out.println("On vous attend, joueur " + players[currentPlayer].num());
//					countdown = delay;
//				}
//			} else {
//				countdown--;
//			}
//		}
	}
}
