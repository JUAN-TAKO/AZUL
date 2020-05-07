package Model;

import java.util.*;
import Controller.Server.*;

public class GlobalBoard {
	
	private int nPlayers;
	private PlayerBoard[] PB;
	private int[][] factories;

	private int iCenter;
	private int[] center;

	private Random rnd;
	private int iBag;
	private int nBag;
	private int[] bag;

	private int iLid;
	private int[] lid;

	private int currentPlayer;
	private int futureFirstPlayer;

	public GlobalBoard(int np, String[] names){
		this.nPlayers = np;			
		PB = new PlayerBoard[nPlayers];
		for(int i = 0; i < nPlayers; i++) {
			PB[i] = new PlayerBoard(this, names[i]);
		}
		
		factories = new int[getNFactories()][4];

		iCenter = 0;
		center = new int[100];

		rnd = new Random();
		bag = new int[100];
		initBag();

		iLid = 0;
		lid = new int[100];

		futureFirstPlayer = 0;
	}
	
	public GlobalBoard(GlobalBoard gb){
		this.nPlayers = gb.nPlayers;
		this.PB = new PlayerBoard[nPlayers];
		for(int i = 0; i < nPlayers; i++)
			this.PB[i] = new PlayerBoard(gb.PB[i], this);
		this.factories = gb.factories.clone();
		this.iCenter = gb.iCenter;
		this.center = gb.center.clone();
		this.rnd = new Random();
		this.iBag = gb.iBag;
		this.nBag = gb.nBag;
		this.bag = gb.bag.clone();
		this.iLid = gb.iLid;
		this.lid = gb.lid.clone();
		this.currentPlayer = gb.currentPlayer;
		this.futureFirstPlayer = gb.futureFirstPlayer;
	}



	public int getNPlayers() {return nPlayers;}
	public int getNFactories() {return 2*nPlayers + 1;}
	public int[][] getFactories() {return factories;}
	public PlayerBoard[] getPlayerBoards() {return PB;}
	public int getICenter() {return iCenter;}
	public int[] getCenter() {return center;}
	public int getCurrentPlayer() {return currentPlayer;}
	public int getFutureFirstPlayer() {return futureFirstPlayer;}

	public void initBag(){
		iBag = 0;
		nBag = 100;
		for(int i = 0; i < nBag; i++) bag[i] = 1 + i/20;
		shuffleBag();
	}	

	public void shuffleBag(){
		for(int i = 0; i < nBag; i++){
			int j = rnd.nextInt(nBag);
			int tmp = bag[i];
			bag[i] = bag[j];
			bag[j] = tmp;
		}
	}

	public boolean isRoundOver(){
		return factoriesAreEmpty() && centerIsEmpty();
	}

	public boolean isGameOver(){
		for(int i = 0; i < nPlayers; i++)
			if(PB[i].isGameOver()) return true;
		return false;
	}

	public void initRound(){
		currentPlayer = futureFirstPlayer;
		futureFirstPlayer = -1;
		initFactories();
		iCenter = 0;
	}

	private void endOfTurn(){
		nextPlayer();
		if(isRoundOver())
			endOfRound();
	}

	private void endOfRound(){
		for(int i = 0; i < nPlayers; i++)
			PB[i].decoration();
		if(isGameOver())
			endOfGame();
		else initRound();
	}

	private void endOfGame(){
		for(int i = 0; i < nPlayers; i++)
			PB[i].updatePointsFinal();

	}

	public void nextPlayer(){
		currentPlayer = (currentPlayer+1) % nPlayers;
	}

	public int drawFromBag(){
		if(iBag >= nBag)
			lidToBag();
		return bag[iBag++];
	}

	public void lidToBag(){
		if(iLid == 0) System.err.println("Warning : no tiles available !");
		nBag = iLid;
		for(int i = 0; i < nBag; i++) bag[i] = lid[i];
		iBag = 0;
		iLid = 0;
		shuffleBag();
	}

	public void addTileToLid(int color){
		lid[iLid++] = color;
	}

	public void initFactories(){
		for(int i = 0; i < getNFactories(); i++)
			for(int j = 0; j < 4; j++)
				factories[i][j] = drawFromBag();
	}

	public int currentPlayerDraw(int whereToDraw, int color, int line){
		int r;
		if((r = playerDraw(currentPlayer, whereToDraw, color, line)) == 0)
			endOfTurn();
		return r;
	}

	public int playerDraw(int plyr, int whereToDraw, int color, int line){
		if(whereToDraw >= 0 && whereToDraw < getNFactories() )
			return playerDrawFromFactory(plyr, whereToDraw, color, line);
		else return playerDrawFromCenter(plyr, color, line);
	}

	public int currentPlayerDrawFromFactory(int fab, int color, int line){
		return playerDrawFromFactory(currentPlayer, fab, color, line);
	}

	public int playerDrawFromFactory(int plyr, int fab, int color, int line){
		//Player 'plyr' draw all 'color' tiles from factory 'fab' to his line 'line'.
		//All others tiles from same factory are discarded to center.
		//plyr : [0 - (nPlayers-1)]
		//factory : [0 - (nFactory-1)]
		//color : [1 - 5]
		//line : [0 - 4]

		if(!factoryContainsColor(fab, color)) return -2;//missing color
		if(PB[plyr].isLineFull(line)) return -3;//player line full
		if(!PB[plyr].canLineBeColor(line, color)) return -4;//this color can't go on this line

		for(int i = 0; i < 4; i++){
			if(factories[fab][i] == color)
				PB[plyr].addTileToLine(line, color);
			else addTileToCenter(factories[fab][i]); 
			factories[fab][i] = 0;
		}
		return 0;
	}

	public void addTileToCenter(int color){
		center[iCenter++] = color;
	}

	public boolean factoryContainsColor(int fab, int color){
		for(int i = 0; i < 4; i++)
			if(factories[fab][i] == color) return true;
		return false;
	}
        
	public boolean factoryIsEmpty(int f){
		return factories[f][0] == 0;
	}
	
	public boolean factoriesAreEmpty(){
		for (int i = 0; i < getNFactories(); i++)
			if(!factoryIsEmpty(i)) return false;
		return true;
	}

	public boolean centerIsEmpty(){
		for(int i = 0; i < iCenter; i++)
			if(center[i] != 0) return false;
		return true;
	}
        
	public boolean centerContainsColor(int color){
		for(int i = 0; i < iCenter; i++)
			if(center[i] == color) return true;
		return false;
	}

	public int currentPlayerDrawFromCenter(int color, int line){
		return playerDrawFromCenter(currentPlayer, color, line);
	}

	public int playerDrawFromCenter(int plyr, int color, int line){
		//Player 'plyr' draw all 'color' tiles from center to his line 'line'.
		//plyr : [0 - (nPlayers-1)]
		//color : [1 - 5]
		//line : [0 - 4]

		if(!centerContainsColor(color)) return -2;
		if(PB[plyr].isLineFull(line)) return -3;
		if(!PB[plyr].canLineBeColor(line, color)) return -4;

		if(futureFirstPlayer == -1){
			futureFirstPlayer = plyr;
			PB[plyr].addTileToFloor(6);
		}

		for(int i = 0; i < iCenter; i++)
			if(center[i] == color){
				PB[plyr].addTileToLine(line, color);
				center[i] = 0;
			}
		return 0;
	}
	
	public JSONObject toJSON() {
		String json = "";
        try {
			JSONObject jsonObject = new JSONObject("{}");
			jsonObject.put("nPlayers",nPlayers);
			jsonObject.put("PB", PB);
			jsonObject.put("factories", factories);
			jsonObject.put("iCenter", iCenter);
			jsonObject.put("center", center);
			jsonObject.put("iBag", iBag);
			jsonObject.put("nBag", nBag);
			jsonObject.put("bag", bag);
			jsonObject.put("iLid", iLid);
			jsonObject.put("lid", lid);
			jsonObject.put("currentPlayer", currentPlayer);
			json = jsonObject.toString();
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
    }

}	
