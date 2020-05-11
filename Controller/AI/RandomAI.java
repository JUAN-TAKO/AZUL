package Controller.AI;
import java.util.Random;

import Controller.AIPlayer;
import Model.*;

public class RandomAI extends AIPlayer {
	Random r;

	public RandomAI(int n, GlobalBoard g) {
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

                c=r.nextInt(4)+1;
                while (   ( (f!=globalBoard.getNFactories()) && (globalBoard.factoryContainsColor(f, c)==false) )
                        || ((f==globalBoard.getNFactories()) && (globalBoard.centerContainsColor(c)==false) ) ){
                        c=r.nextInt(5)+1;
                   }


		l = r.nextInt(5);
		while (playerBoard.isLineFull(l) && playerBoard.canLineBeColor(l, c)) {
			l = r.nextInt(5);
		}
		System.out.println("AI " + (num+1) + " tried playing color : " + c + " line : " + l);
        if (f==globalBoard.getNFactories()){   //la valeur NFactories étant la valeur symbolique de la factory centrale
        	int r = globalBoard.currentPlayerDraw(-1, c, l);
            return r == 0;
        } else{
        	int r = globalBoard.currentPlayerDraw(f, c, l);
        	return r == 0;
        }
	}
}