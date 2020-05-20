package Controller;

import Model.GlobalBoard;
import View.AdaptateurTemps;

import java.util.ArrayList;

import javax.swing.Timer;

import Controller.AI.*;

public class Controller {
	private static final Controller instance = new Controller();
	private ArrayList<GlobalBoard> boards = new ArrayList<GlobalBoard>();
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
	
	public GlobalBoard getCurrentBoard() {
		return boards.isEmpty() ? null : boards.get(0);
	}
	
	public int playMove(int factory, int color, int line) {
		if(isFrontUpdated()) {
			boards.add(1, new GlobalBoard(getCurrentBoard()));
			int r = players[getCurrentBoard().getCurrentPlayer()].click(factory,color,line); // Play the move
			if(r != 0) {
				// The move wasn't successful, remove the board which just got added
				boards.remove(1); 
			}
			return r;
		} else {
			return 0;
		}
	}
	
	public void startGame(int nPlayers, String[] names, int[] AI) {
		this.boards.clear();
		this.boards.add(new GlobalBoard(nPlayers, names));
		
		players = new Player[AI.length];
		for (int i = 0; i < players.length; i++)
			if (AI[i] == 0) {
				players[i] = new HumanPlayer(i, getCurrentBoard());
			} else {
				if(AI[i] == 1)
					players[i] = new RandomAI(i, getCurrentBoard());
				else if(AI[i] == 2)
					players[i] = new EasyAI(i, getCurrentBoard());
//				else if(AI[i] == 3)
//					players[i] = new Mo
			}
	}

	public void tick() {
		if (getCurrentBoard() != null && getCurrentBoard().isOnGoing()) {
			if (countdown == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// On verifie que le front est pret pour le prochain coup.
				if(isFrontUpdated()) {
					setAIHasPlayed(false);
					// Si un coup a été joué (IA) on change de joueur.
					if (players[getCurrentBoard().getCurrentPlayer()].tick()) {
						setAIHasPlayed(true);
						setFrontUpdated(false);
						System.out.println("AI " + (((((getCurrentBoard().getCurrentPlayer() - 1) % players.length) + players.length) % players.length)+1) + " move was valid.");
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
	
	// Return true if successful
	public boolean goPrevious() {
		if(this.boards.size() > 1) {
			boards.remove(0);
			for(Player player : players) {
				player.setBoard(getCurrentBoard());
			}
			return true;
		} else {
			return false;
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
