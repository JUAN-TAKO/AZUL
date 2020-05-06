package Controller.AI;

import java.util.Random;
import Model.*;
import Controller.AIPlayer;

class EasyAI extends AIPlayer {
	Random r;

	EasyAI(int n, GlobalBoard g) {
		super(n, g);
		r = new Random();
	}

	@Override
    protected boolean tick() {
            
                int fSelected=0, cSelected=0, lSelected=0;
                int f, c, l;
                int nextScoreSelected=-15;
                int nextScore;

                //L'IA facile énumère les prochains coups possibles
                //Elle calcule le gain qu'ils lui rapportent et choisit celui qui lui rapporte le plus

                for (int i=0; i<globalBoard.getNFactories()+1; i++){
                    f=i;

                    for (int j=0; j<5; j++){
                        c=j;

                        for (int k=0; k<5; k++){
                            l=k;
                            GlobalBoard nextBoard=globalBoard.globalBoardClone(); //méthode à implémenter dans GlobalBoard
                            nextBoard.playerDrawFromFactory(num, f, c, l);
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
                if (fSelected==globalBoard.getNFactories()){
                    globalBoard.playerDrawFromCenter(num, cSelected, lSelected);
                    return true;
                }
                globalBoard.playerDrawFromFactory(num, fSelected, cSelected, lSelected);
                return true;
            
	}
        
}
