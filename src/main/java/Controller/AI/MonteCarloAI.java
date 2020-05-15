package Controller.AI;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import Controller.AIPlayer;
import Model.*;
import Utils.Move;

class MonteCarloAI extends AIPlayer {
    Random random;
    int maxMovesAhead = 2;
    int randomSampleSize = 100;

    MonteCarloAI(int n, GlobalBoard g) {
        super(n, g);
        random = new Random();
    }
    public int randomSimulation(GlobalBoard g){
        RandomAI agent = new RandomAI(-1, g);
        ArrayList<Integer> scores = new ArrayList<>();
        for(int i=0; i < g.getNPlayers();i++){
            g.getPlayerBoard(i).getScore();
        }
        while (g.isRoundActive()) {
            super.tick();
        }
        int max = 0;
        int maxVal = 0;
        for(int i=0; i < g.getNPlayers();i++){
            int v = g.getPlayerBoard(i).getScore() - scores.get(i);
            if(v > maxVal){
                maxVal = v;
                max = i;
            }
        }
        return max;
    }
    public float estimateBoard(int pnum, GlobalBoard g, int movesAhead){
        if(movesAhead >= maxMovesAhead){
            int nb_wins = 0;
            for(int i=0; i < randomSampleSize; i++){
                GlobalBoard sim = new GlobalBoard(g);
                while(g.isOnGoing()) {
                    if (randomSimulation(sim) == pnum) {
                        nb_wins++;
                    }
                }
            }
            return (float)nb_wins/(float)randomSampleSize;
        }
        else{
            ArrayList<Move> moves = this.getAllMoves(g);
            float sum = 0.0f;
            for(int i=0; i < moves.size(); i++){
                GlobalBoard sim = new GlobalBoard(g);
                sum += estimateBoard(pnum, sim, movesAhead+1);
            }
            return sum / moves.size();
        }
    }
    @Override
    protected boolean tick() {
        ArrayList<Move> moves = this.getAllMoves(globalBoard);
        NavigableMap<Float, Move> map = new TreeMap<Float, Move>();
        float sum = 0.0f;
        for(int i=0; i < moves.size(); i++){
            GlobalBoard sim = new GlobalBoard(globalBoard);
            sum += estimateBoard(num, sim, 1);
            map.put(sum, moves.get(i));
        }
        float r = random.nextFloat() * sum;
        Move move = map.higherEntry(r).getValue();
        globalBoard.currentPlayerDraw(move.factory, move.color, move.line);
        return true;
    }
}