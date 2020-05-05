package Controller;

import Model.GlobalBoard;

class HumanPlayer extends Player {
	HumanPlayer(int n, GlobalBoard p) {
		super(n, p);
	}
        
	@Override
	int click(int factory, int color, int line) {
        while (globalBoard.playerDrawFromFactory(num, factory, color, line)!=0);
        return 0;
	}
}