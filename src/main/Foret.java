package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Foret {
    private final int[][] grille; // Grille de simulation
    private final List<int[] []> grilles = new ArrayList<>();
	
    // Constructeur pour initialiser la foret avec les paramètres du fichier
	public Foret(String fichier) {
		Configuration config = new Configuration(fichier);
		int h = config.getHauteur();
		int l = config.getLargeur();
		grille = new int[l][h];
		
		//Initialiser la grille
		for(int i = 0; i<l;i++) {
			for(int j=0; j<h;j++) {
				grille[i][j] = 1;
			}
		}
		//Affichage de la matrice avant le feu
		System.out.println("Etat initial avant le feu: ");
		AffichageGrille(grille);
			
		//Remplacer les points de feu par 0
		for(int[] position : config.getPositionfeu()) {
			grille[position[0]][position[1]] = 0;
		}
		
		//Affichage de la matrice avant le feu
		System.out.println("Matrice avec les zeros: ");
		
		AffichageGrille(grille);
		//Simulation de propagation de feu
		Simulation.simulation(this, config);
		
	}
	public int[][] getGrille(){
		return grille;
	}
	
	//Méthode d'affichage
	public static void AffichageGrille(int[][] grille) {
		for(int[] ligne : grille) {
			for(int colone : ligne) {
				System.out.print(String.format("%3d", colone) + "   ");
			}
			System.out.println();
		}
	}
	
	public List<int[][]> getGrilles() {
		return grilles;
	}
	
	public void addGrille(int [][] grille) {
		int[][] copy = new int[grille.length][];
		for(int i = 0; i< grille.length; i++) {
			copy[i] = Arrays.copyOf(grille[i], grille[i].length);
		}
		grilles.add(copy);
	}
	//Méthode principale
	public static void main(String[] args) {
		//Création de l'objet Configuration pour lire les paramètres du fichier
	    Configuration config = new Configuration("src/main/config.txt");
	 // Affichage des informations des paramètres de configuration
	    System.out.println("Hauteur de la grille: " + config.getHauteur());
	    System.out.println("Largeur de la grille: " + config.getLargeur());
	    System.out.println("Nombre des cellules brulées dans la grille: " + config.getNbFeux());
	    System.out.println("Coordonnées des points de feu: ");
	    for (int[] point : config.getPositionfeu()) {
	        System.out.println(Arrays.toString(point));
	    }
	    System.out.println("Probabilité de propagation de feu: " + config.getProbabilite());
	    
	    // Création de l'objet Foret avec les paramètres de configuration
	    Foret foret = new Foret("src/main/config.txt");
	    
	    // Simulation de la propagation du feu dans la forêt
	    Simulation.simulation(foret,config);
		
	}
	      
}


       
