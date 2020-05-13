
package Model;


public class TestGlobalBoard {
    
    public static void display(GlobalBoard gb){
        System.out.println("nombre de joueurs: " + gb.getNPlayers());
        System.out.print("\n");
        
        for (int i=0; i<gb.getPlayerBoards().length; i++){
            TestPlayerBoard.display(gb.getPlayerBoards()[i]); //affiche les PlayerBoard
            System.out.print("\n");
        }
        
        System.out.println("factories: ");
        for (int i=0; i<gb.getNFactories(); i++){
            for (int j=0; j<gb.getFactories()[0].length; j++){
                System.out.print(gb.getFactories()[i][j] + " ");
            }
            System.out.println("\n");
        }
        System.out.print("\n");
        
        System.out.print("factory centrale");
        for (int i=0; i<gb.getCenter().length; i++){
            System.out.print(gb.getCenter()[i]);
        }
        System.out.print("\n");
        
    }
    
    public static void main(String[] args){
        
        GlobalBoard gb=new GlobalBoard(4, new String[] {"Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4"});
        
        
        
        //TEST0      structure de jeu OK
        //display(gb);     //affichage
        
        
        //TEST1      initialisation du sac et des factories OK
        display(gb);     //affichage
        //TEST2  coup d'un joueur          
        gb.playerDrawFromFactory(1,0, 2, 0);
        display(gb);     //affichage
        //System.out.println(renvoye);
        
        
        
    }
    
}
