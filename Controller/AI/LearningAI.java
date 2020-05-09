package Controller.AI;

import java.util.Random;
import Model.*;

class LearningAI extends AIPlayer {
    Random r;

    LearningAI(int n, GlobalBoard g) {
        super(n, g);
        r = new Random();
    }

    public static float estimateBoard(int num, GlobalBoard g){

    }
    @Override
    boolean tick() {
        // Pour cette IA, on selectionne aléatoirement une factory, puis un groupe de couleur, puis une ligne libre
        int f;
        int c;
        int l;

        f=r.nextInt(globalBoard.getNFabriques());
        while (globalBoard.fabriqueIsEmpty(f)){
            f=r.nextInt(globalBoard.getNFabriques());
        }

        c=r.nextInt(5);
        while (globalBoard.fabriqueContainsColor(f, c)==false){
            c=r.nextInt(5);
        }


        l = r.nextInt((playerBoard.getLinesNb()).length);
        while (!playerBoard.isLineFull(l)) {
            l = r.nextInt((playerBoard.getLinesNb()).length);
        }
        globalBoard.playerDrawFromFabrique(num, f, c, l);
        return true;
    }
}