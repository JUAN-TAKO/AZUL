package Controller.AI;
import java.util.Random;

import Controller.AIPlayer;
import Model.*;

class RandomAI extends AIPlayer {
	Random r;

	RandomAI(int n, GlobalBoard g) {
		super(n, g);
		r = new Random();
	}

	@Override
	protected boolean tick() {
		// Pour cette IA, on selectionne aléatoirement une factory, puis un groupe de couleur, puis une ligne libre
                int f;
                int c;
				int l;

                f=r.nextInt(globalBoard.getNFactories());
                while (globalBoard.factoryIsEmpty(f)){
                    f=r.nextInt(globalBoard.getNFactories());
                }
                
                c=r.nextInt(5);
                while (globalBoard.factoryContainsColor(f, c)==false){
                        c=r.nextInt(5);
                   }
               
                        
		l = r.nextInt((playerBoard.getLinesNb()).length);
		while (!playerBoard.isLineFull(l)) {
			l = r.nextInt((playerBoard.getLinesNb()).length);
		}
		globalBoard.playerDrawFromFactory(num, f, c, l);
		return true;
	}
}