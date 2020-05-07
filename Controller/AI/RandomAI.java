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
		// Pour cette IA, on selectionne aléatoirement une factory(y compris le centre), puis un groupe de couleur, puis une ligne libre
                int f;
                int c;
                int l;
		

                f=r.nextInt(globalBoard.getNFactories()+1);  //la valeur NFactories étant la valeur symbolique de la factory centrale 
                while ( (f!=globalBoard.getNFactories()) && (globalBoard.factoryIsEmpty(f) )
                        || ( (f==globalBoard.getNFactories()) && globalBoard.centerIsEmpty() )  ){
                    f=r.nextInt(globalBoard.getNFactories()+1);
                }

                c=r.nextInt(5);
                while (   ( (f!=globalBoard.getNFactories()) && (globalBoard.factoryContainsColor(f, c)==false) )
                        || ((f==globalBoard.getNFactories()) && (globalBoard.centerContainsColor(c)==false) )    ){
                        c=r.nextInt(5);
                   }


		l = r.nextInt(5);
		while (playerBoard.isLineFull(l)) {
			l = r.nextInt(5);
		}
                if (f==globalBoard.getNFactories()){   //la valeur NFactories étant la valeur symbolique de la factory centrale
                    globalBoard.playerDrawFromCenter(num, c, l);
                    return true;
                }
                else{
		globalBoard.playerDrawFromFactory(num, f, c, l);
		return true;
                }
	}
}