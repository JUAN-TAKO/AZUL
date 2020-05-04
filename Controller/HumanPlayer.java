package Controller;

import Model.*;

class HumanPlayer extends Player {
	HumanPlayer(int n, GlobalBoard g, PlayerBoard p) {
		super(n, g, p);
	}

        
	@Override
	int play(int factory, int color, int line) {
            while (globalBoard.playerDrawFromFabrique(num, factory, color, line)!=0);
            return 0;
	}
}