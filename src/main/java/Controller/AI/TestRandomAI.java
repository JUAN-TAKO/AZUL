
package Controller.AI;
import Model.*;

public class TestRandomAI {
    
    public static void main (String[] args){
        
        
        GlobalBoard gb=new GlobalBoard(2, new String[] {"Joueur 1", "Joueur 2"});
        
        //TEST0  une IA joue indépendemment (début de partie) OK
        RandomAI pb0=new RandomAI(0, gb);

        //TestGlobalBoard.display(gb);  //affichage
        pb0.tick();
        //TestGlobalBoard.display(gb);  //affichage
        
        
        
        //TEST1  une IA joue indépendemment (après qq tours)   OK
        //TestGlobalBoard.display(gb);  //affichage
        pb0.tick(); pb0.tick(); pb0.tick(); pb0.tick();
        //TestGlobalBoard.display(gb);  //affichage
        
        
        
        //TEST2 une IA joue indépendemment (milieu de partie)   OK
        //TestGlobalBoard.display(gb);  //affichage
        pb0.tick(); pb0.tick(); pb0.tick(); pb0.tick();
        //TestGlobalBoard.display(gb);  //affichage
        
        
        
        //TEST3 une IA joue indépendemment (fin de partie: limite) OK
        TestGlobalBoard.display(gb);  //affichage
        pb0.tick(); pb0.tick(); pb0.tick(); pb0.tick();
        TestGlobalBoard.display(gb);  //affichage
    
    }
    
}
