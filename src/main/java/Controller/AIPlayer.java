package Controller;

import Model.GlobalBoard;
import java.util.*;
import Utils.Move;

public class AIPlayer extends Player {

	protected AIPlayer(int n, GlobalBoard b) {
		super(n, b);
	}

	protected boolean tick() {
		return true;
	};

	protected ArrayList<Move> getAllMoves(GlobalBoard state){
		ArrayList<Move> moves = new ArrayList<>();

		for(int i=0; i < state.getNFactories(); i++){
			for(int j = 0; j < 5; j++){
				for(int k = 1; k <= 5; k++){
					
				}
			}
		}
		return moves;
	}
}