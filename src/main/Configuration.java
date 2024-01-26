package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {
	private int hauteur; //hauteur de grille
	private int largeur; //largeur de la grille 
	private int nbFeux; //Nombre des cellues brulés au début
	private static List<int[]> Positionfeu;
	private double probabilite;// Probabilité de propagation de feu aux cellules adjacentes
	
	// Constructor to initialize the Forest with parameters from a file
	public Configuration(String fichier) {
		LireParametresFichier(fichier);

	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getNbFeux() {
		return nbFeux;
	}

	public List<int[]> getPositionfeu() {
		return Positionfeu;
	}

	public double getProbabilite() {
		return probabilite;
	}
	public void LireParametresFichier(String fichier) {
		try(BufferedReader br = new BufferedReader(new FileReader(fichier))){
					
					//Sauter la première ligne
					br.readLine();
					String[] dimensions = br.readLine().split(" ");
					hauteur = Integer.parseInt(dimensions[0]);
					largeur = Integer.parseInt(dimensions[1]);
					
					//Numéro des points de feu
					br.readLine();
					nbFeux = Integer.parseInt(br.readLine()); 
					
					//Coordonnées de feu initiaux
					br.readLine();
					Positionfeu = new ArrayList<>();
					for(int i=0; i<nbFeux;i++){
						String[] coordonnees = br.readLine().split(" ");
						int x = Integer.parseInt(coordonnees[0]);
						int y = Integer.parseInt(coordonnees[1]);
						Positionfeu.add(new int[]{x,y});
					}
					//sauter la ligne de commentaire
					br.readLine();
					probabilite = Double.parseDouble(br.readLine());
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace(); // gestion des erreur I/O et les erreurs de format
		}
	}
	/*
	public static void main(String[] args) {
		Configuration config = new Configuration("src/main/config.txt");
		System.out.println("Hauteur de la grille: " + config.getHauteur());
		System.out.println("Largeur de la grille: " + config.getLargeur());
		System.out.println("Nombre des cellules brulées dans la grille: " + config.getNbFeux());
		System.out.println("Coordonées des points de feu: ");
		for(int[] point : Positionfeu) {
			System.out.println(Arrays.toString(point));
		}
		System.out.println("Probabilité de propagation de feu: " + config.getProbabilite());

	}*/
}
