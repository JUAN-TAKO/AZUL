package Modele;

public class GlobalBoard {

	int nPlayers;
	PlayerBoard[] PB;
	int[][] fabrique;
	int nCenter;
	int[] center;

	public GlobalBoard(int np){
		this.nPlayers = np;
		PB = new PlayerBoard[nPlayers];
		for(i = 0; i < nPlayers; i++) PB[i] = new PlayerBoard(this);

		fabrique = new int[nFabrique()][4];
		for(i = 0; i < nFabrique(); i++)
			for(int j = 0; j < 4; j++)
				fabrique[i][j] = 0;

		nCenter = 0;
		center = new int[100];
		for(i = 0; i < 100; i++) center[i] = 0;
	}

	public int nFabrique() {return 2*nPlayers + 1;}

	public int playerDrawFromFabrique(int plyr, int fab, int color, int line){
		//Player 'plyr' draw all 'color' tiles from fabrique 'fab' to his line 'line'.
		//All others tiles from same fabrique are discarded to center.
		//plyr : [0 - (nPlayers-1)]
		//fabrique : [0 - (nFabrique-1)]
		//color : [1 - 5]
		//line : [0 - 4]

		if(fabrique[fab][0] == 0)//emtpy fabrique
			return -1;

		if(fabrique[fab][0] != color &&
			fabrique[fab][1] != color &&
			fabrique[fab][2] != color &&
			fabrique[fab][3] != color)//missing color
			return -2;

		for(int i = 0; i < 4; i++){
			if(fabrique[fab][i] == color)
				PB[plyr].addTileToLine(line, color);
			else addTileToCenter(color); 
		}


	}	
