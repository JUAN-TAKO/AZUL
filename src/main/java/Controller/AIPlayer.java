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
			if(state.factoryIsEmpty(i))
				continue;

			for(int j = 1; j <= 5; j++){
				if(!state.factoryContainsColor(i, j))
					continue;

				for(int k = 0; k < 5; k++){
					if(!state.getPlayerBoard(this.num).canLineBeColor(k, j) || state.getPlayerBoard(this.num).isLineFull(k))
						continue;
					moves.add(new Move(i, j, k));
				}
				moves.add(new Move(i, j, 5));
			}
		}
		for(int j = 1; j <= 5; j++){
			if(!state.centerContainsColor(j))
				continue;

			for(int k = 0; k < 5; k++){
				if(!state.getPlayerBoard(this.num).canLineBeColor(k, j) || state.getPlayerBoard(this.num).isLineFull(k))
					continue;
				moves.add(new Move(state.getNFactories(), j, k));
			}
			moves.add(new Move(state.getNFactories() , j, 5));
		}
		return moves;
	}

	public void setNum(int n){
		num = n;
		playerBoard = globalBoard.getPlayerBoards()[num];
	}

}