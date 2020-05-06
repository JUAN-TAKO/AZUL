package Model;

public class TestPlayerBoard {
    
    public static void display(PlayerBoard pb){
        System.out.println("score=" + pb.getScore() + "\n");
        
        for (int i=0; i<5; i++){
                for (int j=0; j<pb.getLinesNb()[i]; j++){
                    System.out.print(pb.getLinesColor()[i]);
                }
                for (int j=pb.getLinesNb()[i]; j<=i; j++){
                    System.out.print("0");
                }
                System.out.print("\n");
        }
        System.out.print("\n");
        
        for (int k=0; k<pb.getWall().length; k++){
            for (int l=0; l<pb.getWall()[0].length; l++){
                System.out.print(pb.getWall()[k][l]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        
        System.out.print("floor=");
        for (int m=0; m<pb.getFloor().length; m++){
            System.out.print(pb.getFloor()[m]);
        }
        System.out.println("\n");
        
    }
    
    public static void main(String[] args){
        
        //PLAYERBOARD TEST0 triangle joueur vide     OK!
        GlobalBoard globalboard0= new GlobalBoard(2);
        PlayerBoard playerboard1= new PlayerBoard(globalboard0);
        
        //display(playerboard1);        //affichage du test
        
        
        
        //PLAYERBOARD TEST1 triangle joueur avec 1 couleur par ligne et une couleur sur le floor     OK!
        playerboard1.addTileToLine(0, 1);
        playerboard1.addTileToLine(1, 1);
        playerboard1.addTileToLine(2, 1);
        playerboard1.addTileToLine(3, 1);
        playerboard1.addTileToLine(4, 1);
        playerboard1.addTileToFloor(1);
        
        //display(playerboard1);       //affichage du test
        playerboard1.decoration();
        //display(playerboard1);       //affichage du test
        
        
        
        
        
        //PLAYERBOARD TEST3 test de placement de trop de dalles dans une ligne dans le triangle            OK
        //met le surplus dans le floor
        PlayerBoard playerboard3=new PlayerBoard(playerboard1, globalboard0);
        
        //display(playerboard3);          //affichage du test
        playerboard3.addTileToLine(1, 1); playerboard3.addTileToLine(1, 1); playerboard3.addTileToLine(1, 1);
        //display(playerboard3);          //affichage du test
        
        
        
        //PLAYERBOARD TEST4 test de remplissage du floor en excès                          OK
        //ne remplit le floor que jusqu'à 7 cases et ignore le reste
        playerboard1.addTileToFloor(1); playerboard1.addTileToFloor(1);
        playerboard1.addTileToFloor(1); playerboard1.addTileToFloor(1);
        playerboard1.addTileToFloor(1); playerboard1.addTileToFloor(1);
        playerboard1.addTileToFloor(1); playerboard1.addTileToFloor(1);
        
        
        //display(playerboard1);          //affichage du test
        

        
        //PLAYERBOARD TEST5 test de rejouter un 5e joueur: ne marche pas, comme attendu                        OK!
        //PlayerBoard pbexces=new PlayerBoard(globalboard0);
        
        
        GlobalBoard globalboard1=new GlobalBoard(4);
        
        //PLAYERBOARD TEST6 modification du clone pour vérifier si cela ne modifie pas l'original   OK!
        PlayerBoard pb=new PlayerBoard(globalboard1);
        PlayerBoard pbclone=new PlayerBoard(pb, globalboard1);
        pbclone.addTileToLine(0, 1);
        
        //display(pb);          //affichage du test
        //display(pbclone);          //affichage du test
        
        
        
        
        //PLAYERBOARD TEST7 test de decoration           comptage des points OK
        //décore le carré avec les cases correspondant aux lignes remplies du triangle
        //met à jour les points
        PlayerBoard pb2= new PlayerBoard(globalboard1);
        pb2.addTileToLine(3, 1); pb2.addTileToLine(3, 1); pb2.addTileToLine(3, 1); pb2.addTileToLine(3, 1);
        //display(pb2);          //affichage du test
        pb2.decoration();
        
        //display(pb2);          //affichage du test
        
        
        
        //PLAYERBOARD TEST8 retest de decoration       comptage des points OK
        pb2.addTileToLine(2, 1); pb2.addTileToLine(2, 1); pb2.addTileToLine(2, 1); pb2.addTileToLine(2, 1);
        pb2.addTileToLine(4, 1); pb2.addTileToLine(4, 1); pb2.addTileToLine(4, 1);
        pb2.addTileToLine(4, 1); pb2.addTileToLine(4, 1); pb2.addTileToLine(4, 1); 
        //display(pb2);          //affichage du test
        pb2.decoration();
        
        //display(pb2);          //affichage du test
        
        
        //PLAYERBOARD TEST9 retest avec autre grille                    comptage OK
        PlayerBoard playerboard4=new PlayerBoard(globalboard1);
        playerboard4.addTileToLine(1,3); playerboard4.addTileToLine(1,2);
        playerboard4.addTileToLine(2,3);
        playerboard4.addTileToLine(3,5); playerboard4.addTileToLine(3,5);
        playerboard4.addTileToLine(3,5); playerboard4.addTileToLine(3,5);
        playerboard4.addTileToLine(4,4); playerboard4.addTileToLine(4,4);
        playerboard4.addTileToLine(4,4); playerboard4.addTileToLine(4,4);
        
        //display(playerboard4);              //affichage du test
        playerboard4.decoration();
        //display(playerboard4);


        
       //PLAYERBOARD TEST10 wall comptage des points  (n'en compte que 37)
        //     X X X X X
        //     X X 0 0 0
        //     X 0 X 0 0         (10 pts + 7 pts + 2 pts = 19 pts bonus à rajouter au 37 à la fin)
        //     X 0 0 X 0                (tous les pts de même couleur+colonne+ligne)
        //     X 0 0 0 X
        PlayerBoard p5=new PlayerBoard(globalboard1);
        //remplissage de la diagonale
        p5.addTileToLine(0,1);
        p5.addTileToLine(1,1); p5.addTileToLine(1,1);
        p5.addTileToLine(2,1); p5.addTileToLine(2,1); p5.addTileToLine(2,1);
        p5.addTileToLine(3,1); p5.addTileToLine(3,1); p5.addTileToLine(3,1); p5.addTileToLine(3,1);
        p5.addTileToLine(4,1); p5.addTileToLine(4,1); p5.addTileToLine(4,1);
        p5.addTileToLine(4,1); p5.addTileToLine(4,1);
        p5.decoration();
        //remplissage de la colonne
        p5.addTileToLine(1,5); p5.addTileToLine(1,5);
        p5.addTileToLine(2,4); p5.addTileToLine(2,4); p5.addTileToLine(2,4);
        p5.addTileToLine(3,3); p5.addTileToLine(3,3); p5.addTileToLine(3,3); p5.addTileToLine(3,3);
        p5.addTileToLine(4,2); p5.addTileToLine(4,2); p5.addTileToLine(4,2); p5.addTileToLine(4,2); p5.addTileToLine(4,2);
        p5.decoration();
        //remplissage de la ligne
        p5.addTileToLine(0,2); p5.decoration();
        p5.addTileToLine(0,3); p5.decoration();
        p5.addTileToLine(0,4); p5.decoration();
        p5.addTileToLine(0,5); p5.decoration();
        p5.decoration();
        
        display(p5);
        
    }
    
}
