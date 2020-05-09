package Controller;

import Model.GlobalBoard;
import View.AdaptateurTemps;

import javax.swing.Timer;

import Controller.AI.*;

public class Controller {
	private static final Controller instance = new Controller();
	private GlobalBoard board;
	private boolean onGoing = false;
	private Player[] players;
	private int currentPlayer;
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
			int r = players[currentPlayer].click(factory,color,line);
			if(r == 0)
				changeJoueur();
			return r;
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
		this.setOnGoing(true);
		this.board.initRound();
		currentPlayer = 0;
	}

	void changeJoueur() {
		currentPlayer = board.getCurrentPlayer();
		countdown = delay;
	}

	public void tick() {
		if (isOnGoing()) {
			if (countdown == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// On v�rifie que le front est pr�t pour le prochain coup.
				if(isFrontUpdated()) {
					setAIHasPlayed(false);
					// Si un coup a été joué (IA) on change de joueur.
					if (players[currentPlayer].tick()) {
						setAIHasPlayed(true);
						setFrontUpdated(false);
						System.out.println("AI " + (currentPlayer + 1) + " move was valid.");
						changeJoueur();
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

	public boolean isOnGoing() {
		return onGoing;
	}

	public void setOnGoing(boolean onGoing) {
		this.onGoing = onGoing;
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
