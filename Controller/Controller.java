package Controller;

import Model.GlobalBoard;
import View.AdaptateurTemps;

import javax.swing.Timer;

import Controller.AI.*;

public class Controller {
	private static final Controller instance = new Controller();
	private GlobalBoard board;
	private Player[] players;
	private final int delay = 50;
	private int countdown;
	private boolean isFrontUpdated = false;
	private boolean AIHasPlayed = false;

	public Controller() {
		Timer chrono = new Timer( 16, new AdaptateurTemps(this));
		chrono.start();
	}
	
	public static final Controller getInstance() {
		return instance;
	}
	
	public GlobalBoard getBoard() {
		return board;
	}
	
	public int playMove(int factory, int color, int line) {
		if(isFrontUpdated()) {
			return players[board.getCurrentPlayer()].click(factory,color,line);
		} else {
			return 0;
		}
	}
	
	public void startGame(int nPlayers, String[] names, boolean[] AI) {
		this.board = new GlobalBoard(nPlayers, names);
		players = new Player[AI.length];
		for (int i = 0; i < players.length; i++)
			if (AI[i])
				players[i] = new RandomAI(i, board);
			else
				players[i] = new HumanPlayer(i, board);
	}

	public void tick() {
		if (board != null && board.isOnGoing()) {
			if (countdown == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// On v�rifie que le front est pr�t pour le prochain coup.
				if(isFrontUpdated()) {
					setAIHasPlayed(false);
					// Si un coup a été joué (IA) on change de joueur.
					if (players[board.getCurrentPlayer()].tick()) {
						setAIHasPlayed(true);
						setFrontUpdated(false);
						System.out.println("AI " + (board.getCurrentPlayer() + 1) + " move was valid.");
					} else {
						// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
						//System.out.println("On vous attend, joueur " + players[currentPlayer].num());
						countdown = delay;
					}
				} else {
					System.out.println("Waiting for front to update..");
					countdown = delay;
				}
			} else {
				countdown--;
			}
		}
	}
	
	public boolean hasAIPlayed() {
		return AIHasPlayed;
	}

	public void setAIHasPlayed(boolean aIHasPlayed) {
		AIHasPlayed = aIHasPlayed;
	}

	public boolean isFrontUpdated() {
		return isFrontUpdated;
	}

	public void setFrontUpdated(boolean isFrontUpdated) {
		this.isFrontUpdated = isFrontUpdated;
	}
}
