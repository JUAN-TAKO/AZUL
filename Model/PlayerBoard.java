package Model;

import java.lang.*;

public class PlayerBoard {

	private String name;
	private int score;
	private int[] linesColor;
	private int[] linesNb;
	private boolean[][] wall;
	private int nfloor;
	private int[] floor;
	GlobalBoard gb;
	
	public PlayerBoard(GlobalBoard gb, String name){
		this.gb = gb;
		this.score = 0;
		this.name = name;
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
		for(int i = 0; i < 7; i++)
			this.floor[i] = 0;
	}

	public PlayerBoard(PlayerBoard pb, GlobalBoard gb){
		this.gb = gb;
		this.score = pb.score;
		this.name = pb.name;
		this.linesColor = pb.linesColor.clone();
		this.linesNb = pb.linesNb.clone();
		this.wall = pb.wall.clone();
		this.nfloor = pb.nfloor;
		this.floor = pb.floor.clone();
	}

	public int getScore(){return score;}
	public int[] getLinesColor(){return linesColor;}
	public int[] getLinesNb(){return linesNb;}
	public boolean[][] getWall(){return wall;}
	public int[] getFloor(){return floor;}
	public String getName(){return name;}

	public boolean getWallLineColor(int line, int color){ return wall[line][ (line + color-1) % 5 ]; }
	private void setWallLineColor(int line, int color, boolean val){ wall[line][ (line + color-1) % 5 ] = val; }
	//get and set a 'wall' cell with a given line and color instead of line and column
	
	public int getWallColor(int line, int column){ return 1 + (line - column) % 5; }
	//return the color of a given cell in 'wall'

	public boolean isLineFull(int line){ return (linesNb[line] >= line + 1); }
	//return true if line 'line' can't contain more tiles

	public boolean isLineColor(int line, int color){ return (linesColor[line] == color); }
	
	public boolean canLineBeColor(int line, int color){ return linesColor[line] == color ||
		(linesColor[line] == 0 && !getWallLineColor(line, color) ); }
	//return true if line 'line' accept 'color' tiles

	void addTileToLine(int line, int color){
		//line : line where to add new tiles [0-4] 
		//color : color of the new tiles [1-5]
		
		if(linesColor[line] == 0){//empty line
			linesColor[line] = color;
			linesNb[line] = 0;
		}
		
		if(linesNb[line] >= line + 1){//full line
			addTileToFloor(color);
		}else linesNb[line]++;

	}

	void addTileToFloor(int color){
		if(nfloor < 7) floor[nfloor++] = color;
		else if(color != 6) this.gb.addTileToLid(color);//excess tiles sent back to lid
	}

	void decoration(){
		int i, col;
		for(i = 0; i < 5; i++)
			if(linesNb[i] == i+1){//completed line
				setWallLineColor(i, linesColor[i], true);
				updatePoints(i, (i + linesColor[i]-1) % 5);
				for(int j = 0; j < i; j++) gb.addTileToLid(linesColor[i]);
				linesColor[i] = 0;
				linesNb[i] = 0;
			}
		for(i = 0; i < nfloor; i++){
			score -= (i+4)/3;
			if(floor[i] != 6) gb.addTileToLid(floor[i]);	
			floor[i] = 0;
		}
		nfloor = 0;
	}

	private void updatePoints(int x, int y){
		int horizLine = neighbors(x-1, y, -1, 0) + neighbors(x+1, y, 1, 0);	
		if(horizLine > 0) horizLine++;
		int vertLine = neighbors(x, y-1, 0, -1) + neighbors(x, y+1, 0, 1);
		if(vertLine > 0) vertLine++;
		score += Math.max(1, horizLine + vertLine);
	}

	private int neighbors(int x, int y, int dx, int dy){
		if(x < 0 || x > 4 || y < 0 || y > 4 || !wall[x][y]) return 0;
		return 1 + neighbors(x + dx, y + dy, dx, dy);
	}

	public boolean isLineCompleted(int line){
		for(int i = 0; i < 5; i++)
			if(!wall[line][i]) return false;
		return true;
	}

	public boolean isColumnCompleted(int col){
		for(int i = 0; i < 5; i++)
			if(!wall[i][col]) return false;
		return true;
	}

	public boolean isColorCompleted(int color){
		for(int i = 0; i < 5; i++)
			if(!getWallLineColor(i, color)) return false;
		return true;
	}

	boolean isGameOver(){
		//return true if this player meets the end of game requirement
		for(int i = 0; i < 5; i++)
			if(isLineCompleted(i)) return true;
		return false;
	}

	void updatePointsFinal(){
		int i;
		for(i = 0; i < 5; i++){
			if(isLineCompleted(i)) score += 2;
			if(isColumnCompleted(i)) score += 7;
			if(isColorCompleted(i+1)) score += 10;
		}
	}

}
