package main;

public class Simulation {
	
	//Méthode statique pour simuler la propagation du feu dans la forêt
	public static void simulation(Foret foret, Configuration config) {
		 // Obtient la grille de la forêt
		int h = foret.getGrille().length;
        int l = foret.getGrille()[0].length;
        int[][] grille = foret.getGrille();

        // Remplacer les pixels de valeur 0 par -1 (cendre)
      	Boolean zerosRemaining = true;
      	int t =0;
      	while(zerosRemaining) {   		
      		zerosRemaining = false; // on suppose qu'il n'existe plus de zéros dans la matrice
      		
	        //Mise à -1 des pixels ayant une valeur de 0
      		for (int i = 0; i < l; i++) {
	            for (int j = 0; j < h; j++) {
	                if (grille[i][j] == 0) {
	                    grille[i][j] = -1;
	                    zerosRemaining = true; // Des zéros restent dans la matrice
	                    //break;
	                }  
	            }
      		}
      		
	         // Mettre à jour les pixels adjacents  à 0 selon le taux de probabilité
      		for(int i =0;i<l;i++) {
      			for(int j=0; j<h;j++) {
      				if(grille[i][j]==-1) {
	                    for (int k = i - 1; k <= i + 1; k++) {
	                        for (int m = j - 1; m <= j + 1; m++) {
	                            if (k >= 0 && k < l && m >= 0 && m < h && !(k == i && m == j) && (grille[k][m] != -1)) { // la condition  && grille[k][m] != -1 permet de ne pas changer les pixels qui ont déja la valeur -1
	                                if (Math.random() * 100 < config.getProbabilite()) {
	                                    grille[k][m] = 0; // Si la probabilité est supérieu au random, on remplace les adjacents par 0
	                                    zerosRemaining = true;
	                                }
	                            }
	                            
	                        }
	                    }
	                    //break;
	                }
	            }
	        }
      		// Si aucun zéro restant, sortir de la boucle
      	    if (!zerosRemaining) {
      	        break;
      	    }
      	    // Réinitialiser l'indicateur zerosRemaining pour la prochaine itération
      	    //zerosRemaining = false;
      		
      	    //t++;
	        System.out.println("Etape " + (++t) + " :");
      	// Afficher la grille
    		Foret.AffichageGrille(grille);
    	    /*
    		if(!zerosRemaining) {
    			break;
    		}*/
      	}
           
      
	}  
	
}
