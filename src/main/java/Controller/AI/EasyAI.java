package Controller.AI;

import java.util.Random;
import Model.*;
import Controller.AIPlayer;
import java.util.*;

public class EasyAI extends AIPlayer {
	Random r;

	public EasyAI(int n, GlobalBoard g) {
		super(n, g);
		r = new Random();
	}

	@Override
        protected boolean tick() {
            
                ArrayList<Integer> fList=new ArrayList<Integer>();
                int f, c, l;
                int fSelected;
                int cSelected;
                int lSelected;
                int nextScoreSelected;
                int nextScore;
                GlobalBoard nextBoard;
                
                //----------VALEURS PAR DEFAUT: initialisation------------------(tire au hasard une factory, tire une couleur au hasard et joue en prioirié dans les premières lignes)
                fSelected=r.nextInt(globalBoard.getNFactories()+1);  //la valeur NFactories étant la valeur symbolique de la factory centrale 
                while ( (fSelected!=globalBoard.getNFactories()) && (globalBoard.factoryIsEmpty(fSelected) )
                        || ( (fSelected==globalBoard.getNFactories()) && globalBoard.centerIsEmpty() )  ){
                    fSelected=r.nextInt(globalBoard.getNFactories()+1);
                }
                cSelected=r.nextInt(5)+1;
                while (   ( (fSelected!=globalBoard.getNFactories()) && (globalBoard.factoryContainsColor(fSelected, cSelected)==false) )
                        || ((fSelected==globalBoard.getNFactories()) && (globalBoard.centerContainsColor(cSelected)==false) ) ){
                        cSelected=r.nextInt(5)+1;
                   }
		lSelected = 0;
		while ((lSelected < 5) && (playerBoard.isLineFull(lSelected) || !playerBoard.canLineBeColor(lSelected, cSelected))) {
			lSelected+=1;
		}
                //----------VALEURS PAR DEFAUT: fin-----------------------------
                
                
                //------SCORE: initialisation avec les valeurs par défaut-------
                nextBoard=new GlobalBoard(globalBoard); //clonage
                if (fSelected==globalBoard.getNFactories()){
                    nextBoard.currentPlayerDraw(-1, cSelected, lSelected);
                }
                else{
                    nextBoard.currentPlayerDraw(fSelected, cSelected, lSelected);
                }
                nextScoreSelected=nextBoard.getPlayerBoards()[num].getScore();
                //-------------SCORE: fin---------------------------------------
                
                

                //L'IA facile énumère les prochains coups possibles
                //Elle calcule le gain qu'ils lui rapportent et choisit celui qui lui rapporte le plus

                //création de la liste des factory où il est possible de piocher (y compris le centre)
                for (int i=0; i<globalBoard.getNFactories(); i++){  //la valeur NFactories étant la valeur symbolique de la factory centrale
                    if (globalBoard.factoryIsEmpty(i)==false){
                        fList.add(i);
                    }        
                }
                if (globalBoard.centerIsEmpty()==false){
                    fList.add(globalBoard.getNFactories());
                    }
                
                
                //parcours de toutes les solutions
                for (int i=0; i<fList.size(); i++){
                    f=fList.get(i);
                    
                    for (int j=1; j<6; j++){
                        c=j;

                        for (int k=0; k<6; k++){
                            l=k;
                            
                            //si la factory choisie contient bien cette couleur
                            if ( ! ( ( (f!=globalBoard.getNFactories()) && (globalBoard.factoryContainsColor(f, c)==false) )
                        || ((f==globalBoard.getNFactories()) && (globalBoard.centerContainsColor(c)==false) ) )   ){
                                
                                //si la ligne choisie est bien de cette couleur
                                if (! ((l < 5) && (playerBoard.isLineFull(l) || !playerBoard.canLineBeColor(l, c)))){
                                    
                                    //alors on duplique le plateau pour faire les tests de score
                                    nextBoard=new GlobalBoard(globalBoard); //clonage
                                    if (f==globalBoard.getNFactories()){
                                        nextBoard.currentPlayerDraw(-1, c, l);
                                    }
                                    else{
                                        nextBoard.currentPlayerDraw(f, c, l);
                                    }
                                    
                                    //*****Débuggage*****
                                    
                                    //System.out.println("nextBoard: \n");
                                    //TestGlobalBoard.display(nextBoard);
                                   
                                    //System.out.println("AI " + (num+1) + " choose the factory :" + f + " tried playing color : " + c + " line : " + l);
                                    
                                    nextScore=nextBoard.getPlayerBoards()[num].getScore();
                                    if (nextScore>nextScoreSelected){
                                        nextScoreSelected=nextScore;
                                        fSelected=f;
                                        cSelected=c;
                                        lSelected=l;
                                    }
                                    
                                }
                            }       
                        }

                    }
                }
               

                if (fSelected==globalBoard.getNFactories()){ //la valeur NFactories étant la valeur symbolique de la factory centrale
                    int r=globalBoard.currentPlayerDraw(-1, cSelected, lSelected);
                    return r==0;
                }
                else {
                    int r=globalBoard.currentPlayerDraw(fSelected, cSelected, lSelected);
                    return r==0;
                }
            
	}
        
}
