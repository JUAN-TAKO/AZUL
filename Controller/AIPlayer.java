package Controller;

import Model.GlobalBoard;

public class AIPlayer extends Player {

	protected AIPlayer(int n, GlobalBoard b) {
		super(n, b);
	}

	protected boolean tick() {
		return true;
	};
}