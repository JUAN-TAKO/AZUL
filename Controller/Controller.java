package Controller;

/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Model.GlobalBoard;
import View.EventCollector;

public class Controller implements EventCollector {
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

	@Override
	public void click(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (players[currentPlayer].click(l, c))
			changeJoueur();
	}

	void changeJoueur() {
		currentPlayer = (currentPlayer + 1) % players.length;
		countdown = delay;
	}

	public void tick() {
		if (board.enCours()) {
			if (countdown == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (players[currentPlayer].tick()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + players[currentPlayer].num());
					countdown = delay;
				}
			} else {
				countdown--;
			}
		}
	}
}
