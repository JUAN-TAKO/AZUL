package Controller;

import java.util.Random;
import Model.*;
import Controller.AIPlayer;

class EasyAI extends AIPlayer {
	Random r;

	EasyAI(int n, PlayerBoard p) {
		super(n, p);
		r = new Random();
	}

	@Override
	boolean tick() {
            
                int fSelected, cSelected, lSelected;
                int f, c, l;
                int nextScoreSelected=-15;
                int nextScore;
                
                //L'IA facile énumère les prochains coups possibles
                //Elle calcule le gain qu'ils lui rapportent et choisit celui qui lui rapporte le plus
                
                for (int i=0; i<globalBoard.getNFabriques(); i++){
                    f=i;
                    
                    for (int j=0; j<5; j++){
                        c=j;
                        
                        for (int k=0; k<5; k++){
                            l=k;
                            GlobalBoard nextBoard=globalBoard.globalBoardClone(); //méthode à implémenter dans GlobalBoard
                            nextBoard.playerDrawFromFabrique(num, f, c, l);
                            nextScore=nextBoard.getPlayerBoards()[num].getScore();
                            if (nextScore>nextScoreSelected){
                                nextScoreSelected=nextScore;
                                fSelected=f;
                                cSelected=c;
                                lSelected=l;
                            }
                            
                        }
                    }
                }
                globalBoard.playerDrawFromFabrique(num, fSelected, cSelected, lSelected);
                return true;
            
	}
        
}
