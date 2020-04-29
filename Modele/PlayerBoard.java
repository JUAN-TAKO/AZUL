package Modele;

public class PlayerBoard {

	private GlobalBoard GB;

	private int score;
	private int[] linesColor;
	private int[] linesNb;
	private boolean[][] wall;
	private int nfloor;
	private int[] floor;
	
	public PlayerBoard(GlobalBoard gb){
		this.GB = gb;

		this.score = 0;
		
		this.linesColor = new int[5];
		this.linesNb = new int[5];
		this.wall = new boolean[5][5];
		for(int i = 0; i < 5; i++){
			this.linesColor[i] = 0;
			this.linesNb[i] = 0;
			for(int j = 0; j < 5; j++)
				this.wall[i][j] = false;
		}
		
		this.nfloor = 0;
		this.floor = new int[7];
	}

	public getScore(){return score;}

	public boolean isLineFull(int line){ return (linesNb[line] >= line + 1); }
	//return true if line 'line' can't contain more tiles

	public boolean isLineColor(int line, int color){ return (linesColor[line] == color); }
	//return true if line 'line' accept 'color' tiles
	
	public boolean canLineBeColor(int line, int color){ return linesColor[line] == color ||
		(linesColor[line] == 0 && !wall[line][ (linesColor[line] + line) % 5 ] ); }

	public int addTileToLine(int line, int color){
		//line : line where to add new tiles 0-4
		//color : color of the new tiles 1-5
		
		if(linesColor[line] == 0){//empty line
			linesColor[line] = color;
			linesNb[line] = 0;
		}

		linesNb[line] ++;
		
		if(linesNb[line] > line + 1){//excess tiles sent to floor
			addTileToFloor(int color);
			linesNb[line] = line + 1;
		}

		return 0;
	}

	public void addTileToFloor(int color){
		if(nfloor < 7) floor[nfloor++] = color;
		else if(color != 6) GB.addTileToLid(int color);//excess tiles sent back to lid
	}

	public void decoration(){
		int i, col;
		for(i = 0; i < 5; i++)
			if(linesNb[i] == i+1){
				col = (linesColor[i] + i) % 5;
				wall[i][col] = true;
				updatePoints(i, col);
				GB.addTilesToLid(linesColor[i], i);
			}
		for(i = 0; i < nfloor; i++){
			//score -= 1 + (i>=2?1:0) + (i>=5?1:0);
			score -= (i+4)/3;
			if(floor[i] != 6) GB.addTileToLid(floor[i]);	
		}
	}

	private void updatePoints(int x, int y){
		int horizLine = neighbors(x-1, y, -1, 0) + neighbors(x+1, y, 1, 0);	
		if(horizLine > 0) horizLine++;
		int vertLine = neighbors(x, y-1, 0, -1) + neighbors(x, y+1, 0, 1);
		if(vertLine > 0) vertLine++;
		score += max(1, horizLine + vertLine);
	}

	private int neighbors(int x, int y, int dx, int dy){
		if(x < 0 || x > 4 || y < 0 || y > 4 || !wall[x][y]) return 0;
		return 1 + neighbors(x + dx, y + dy, dx, dy);
	}
}
