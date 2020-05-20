package Controller;

import Model.GlobalBoard;

class HumanPlayer extends Player {
	HumanPlayer(int n, GlobalBoard p) {
		super(n, p);
	}
        
	@Override
	int click(int factory, int color, int line) {
        return globalBoard.currentPlayerDraw(factory, color, line);
	}
}