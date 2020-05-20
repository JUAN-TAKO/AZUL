package Controller.AI;

import java.lang.reflect.Array;
import java.util.*;

import Controller.AIPlayer;
import Model.*;
import Utils.Move;
import Utils.Pair;

public class MonteCarloAI extends AIPlayer {
    Random random;
    int maxMovesAhead = 3;
    int randomSampleSize = 50;
    double growth = 4;

    public MonteCarloAI(int n, GlobalBoard g) {
        super(n, g);
        random = new Random();
    }
    public ArrayList<Float> randomSimulation(GlobalBoard g){
        float avg = 0.0f;
        RandomAI agent = new RandomAI(0, g);
        ArrayList<Float> scores = new ArrayList<>();
        for(int i=0; i < g.getNPlayers();i++){
            scores.add((float)g.getPlayerBoard(i).getScore());
        }
        do {
            agent.setNum(g.getCurrentPlayer());
            agent.tick();
        } while (g.isRoundActive());
        for(int i=0; i < g.getNPlayers();i++){
            float v = g.getPlayerBoard(i).getScore() - scores.get(i);
            avg += v/g.getNPlayers();
            scores.set(i, v);
        }
        for(int i=0; i < g.getNPlayers();i++){
            scores.set(i, scores.get(i) - avg);
        }
        return scores;
    }

    public float valueFunction(float x){
        return (float)(Math.pow(growth, x)/growth);
    }
    public float weightScores(GlobalBoard g, ArrayList<Float> scores, ArrayList<Float> sum){
        float w = valueFunction(scores.get(g.getCurrentPlayer()));
        for(int i=0; i < g.getNPlayers(); i++){
            sum.set(i, sum.get(i) + scores.get(i)*w);
        }
        return w;
    }
    public ArrayList<Float> estimateBoard(GlobalBoard old, GlobalBoard g, int movesAhead){
        ArrayList<Float> sum = new ArrayList<>();
        for(int j=0; j<g.getNPlayers();j++){
            sum.add(0.0f);
        }
        float total = 0.0f;
        if(!g.isRoundActive()){
            ArrayList<Float> scores = new ArrayList<>();
            for(int i=0; i<g.getNPlayers();i++){
                scores.add((float)g.getPlayerBoard(i).getScore() - (float)old.getPlayerBoard(i).getScore());
            }
            total += weightScores(g, scores, sum);
        }
        else if(movesAhead+1 >= maxMovesAhead){
            for(int i=0; i < randomSampleSize; i++){
                GlobalBoard sim = new GlobalBoard(g);
                total += weightScores(g, randomSimulation(sim), sum);
            }
        }
        else{
            ArrayList<Move> moves = this.getAllMoves(g);
            for(int i=0; i < moves.size(); i++){
                GlobalBoard sim = new GlobalBoard(g);
                Move move = moves.get(i);
                sim.currentPlayerDraw(move.factory, move.color, move.line);
                total += weightScores(g, estimateBoard(g, sim, movesAhead+1), sum);
            }

        }
        for(int i=0; i < g.getNPlayers(); i++){
            sum.set(i, sum.get(i) / total);
        }
        return sum;
    }
    @Override
    protected boolean tick() {
        ArrayList<Move> moves = this.getAllMoves(globalBoard);
        NavigableMap<Float, Move> map = new TreeMap<Float, Move>();
        ArrayList<Float> scores;
        for(int i=0; i < moves.size(); i++){
            GlobalBoard sim = new GlobalBoard(globalBoard);
            Move move = moves.get(i);
            sim.currentPlayerDraw(move.factory, move.color, move.line);
            scores = estimateBoard(globalBoard, sim, 0);
            float v = valueFunction(scores.get(globalBoard.getCurrentPlayer()));
            map.put(v, moves.get(i));
        }

        Move move = map.lastEntry().getValue();
        System.out.println("chosen: " + move.color + " from " + move.factory + " to " + move.line);
        System.out.println(globalBoard.currentPlayerDraw(move.factory, move.color, move.line));
        System.out.println("score IA:" + globalBoard.getPlayerBoards()[1].getScore());
        return true;
    }
}