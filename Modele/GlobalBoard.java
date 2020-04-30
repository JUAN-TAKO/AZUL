package Modele;

import java.util.*;

public class GlobalBoard {

	private int nPlayers;
	private PlayerBoard[] PB;
	private int[][] fabrique;

	private int iCenter;
	private int[] center;

	private Random rnd;
	private int iBag;
	private int nBag;
	private int[] bag;

	private int iLid;
	private int[] lid;

	private int futureFirstPlayer;

	public GlobalBoard(int np){
		//np : number of players [2 - 4]
		this.nPlayers = np;
		PB = new PlayerBoard[nPlayers];
		for(int i = 0; i < nPlayers; i++) PB[i] = new PlayerBoard(this);

		fabrique = new int[getNFabriques()][4];

		iCenter = 0;
		center = new int[100];

		rnd = new Random();
		bag = new int[100];
		initBag();

		iLid = 0;
		lid = new int[100];

		futureFirstPlayer = 0;
		initFabriques();
	}

	public int getNPlayers() {return nPlayers;}
	public int getNFabriques() {return 2*nPlayers + 1;}
	public int[][] getFabriques() {return fabrique;}
	public PlayerBoard[] getPlayerBoards() {return PB;}
	public int getICenter() {return iCenter;}
	public int[] getCenter() {return center;}
	public int getFutureFirstPlayer() {return futureFirstPlayer;}

	public void initBag(){
		iBag = 0;
		nBag = 100;
		for(int i = 0; i < nBag; i++) bag[i] = 1 + i/20;
		shuffleBag();
	}	

	private void shuffleBag(){
		for(int i = 0; i < nBag; i++){
			int j = rnd.nextInt(nBag);
			int tmp = bag[i];
			bag[i] = bag[j];
			bag[j] = tmp;
		}
	}

	private boolean endOfRound(){
		int i;
		for(i = 0; i < getNFabriques(); i++)
			if(fabrique[i][0] != 0) return false;

		for(i = 0; i < iCenter; i++)
			if(center[i] != 0) return false;
		return true;
	}

	private boolean endOfGame(){
		for(int i = 0; i < nPlayers; i++)
			if(PB[i].endOfGame()) return true;
		return false;
	}

	public void nextRound(){
	}		

	private int drawFromBag(){
		if(iBag >= nBag)
			lidToBag();
		return bag[iBag++];
	}

	private void lidToBag(){
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

	private void initFabriques(){
		for(int i = 0; i < getNFabriques(); i++)
			for(int j = 0; j < 4; j++)
				fabrique[i][j] = drawFromBag();
	}

	public int playerDrawFromFabrique(int plyr, int fab, int color, int line){
		//Player 'plyr' draw all 'color' tiles from fabrique 'fab' to his line 'line'.
		//All others tiles from same fabrique are discarded to center.
		//plyr : [0 - (nPlayers-1)]
		//fabrique : [0 - (nFabrique-1)]
		//color : [1 - 5]
		//line : [0 - 4]

		if(!fabriqueContainsColor(fab, color)) return -2;//missing color
		if(PB[plyr].isLineFull(line)) return -3;//player line full
		if(!PB[plyr].canLineBeColor(line, color)) return -4;//this color can't go on this line

		for(int i = 0; i < 4; i++){
			if(fabrique[fab][i] == color)
				PB[plyr].addTileToLine(line, color);
			else addTileToCenter(color); 
			fabrique[fab][i] = 0;
		}
		return 0;
	}

	private void addTileToCenter(int color){
		center[iCenter++] = color;
	}

	private boolean fabriqueContainsColor(int fab, int color){
		for(int i = 0; i < 4; i++)
			if(fabrique[fab][i] == color) return true;
		return false;
	}

	public boolean centerContainsColor(int color){
		for(int i = 0; i < iCenter; i++)
			if(center[i] == color) return true;
		return false;
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

}	
