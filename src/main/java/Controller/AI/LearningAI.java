package Controller.AI;

import java.util.Random;

import Controller.AIPlayer;
import Model.*;

class LearningAI extends AIPlayer {
    Random r;

    LearningAI(int n, GlobalBoard g) {
        super(n, g);
        r = new Random();
    }

    public static float estimateBoard(int num, GlobalBoard g, int movesAhead){
        return 1.0f;
    }
    @Override
    protected boolean tick() {
        return true;
    }
}