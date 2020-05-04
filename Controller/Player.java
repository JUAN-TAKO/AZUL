package Controller;


import Model.*;


abstract class Player {
        GlobalBoard globalBoard;
	PlayerBoard playerBoard;
	int num;

	// Le joueur connait son numéro, cela lui permet d'inspecter le plateau en
	// sachant
	// repérer ses pions et évaluer où il en est
	Player(int n, GlobalBoard g, PlayerBoard p) {
		num = n;
		globalBoard = g;
                playerBoard= p;
	}

	int num() {
		return num;
	}

	// Méthode appelée pour tous les joueurs une fois le temps écoulé
	// Si un joueur n'est pas concerné, il lui suffit de l'ignorer
	boolean timeElapsed() {
		return false;
	}
        
        int play(int factory, int color, int line){
            return -1;
        }
}