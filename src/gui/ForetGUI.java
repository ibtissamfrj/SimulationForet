package gui;

import main.Foret;
import main.Simulation;
import main.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ForetGUI extends JFrame {
    private JPanel gridPanel;
    private JButton nextStepButton;
    private JLabel stepLabel;
    private List<int[][]> grilles; // Liste pour stocker toutes les grilles à chaque étape
    private int currentStep;
    private Foret foret;
    private Configuration config;

    public ForetGUI() {
        super("Simulation de propagation de feu");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
     
        // Charger le fichier de configuration et initialiser la forêt
        try {
            config = new Configuration("src/main/config.txt");
            foret = new Foret("src/main/config.txt");
            grilles = new ArrayList<>(); // Initialiser la liste des grilles
            grilles.add(foret.getGrille()); // Ajouter la grille initiale à la liste

            // Initialiser les composants après avoir ajouté la grille initiale
            initComponents();
            // Mettre à jour le panneau de grille pour refléter la grille initiale
            updateGridPanel(grilles.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier de configuration.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Quitter l'application en cas d'erreur
        }
        currentStep = 0;
        
    }

    private void initComponents() {
        // Initialiser le panneau de grille
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(config.getLargeur(), config.getHauteur()));

        // Initialiser le bouton de l'étape suivante
        nextStepButton = new JButton("Next Step");
        nextStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextStep();
            }
        });

        // Initialiser le label d'étape
        stepLabel = new JLabel("Step: " + currentStep);

        // Ajouter les composants au contenu de la fenêtre
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(nextStepButton, BorderLayout.SOUTH);
        contentPane.add(stepLabel, BorderLayout.NORTH);
    }
    private void nextStep() {
    	if (currentStep < grilles.size() - 1) {
            currentStep++; // Incrémenter le numéro de l'étape

            // Récupérer la grille correspondant à l'étape actuelle depuis la liste grilles
            int[][] grille = grilles.get(currentStep);

            // Mettre à jour le panneau de grille pour refléter la grille récupérée
            updateGridPanel(grille);

            // Mettre à jour le label d'étape avec le numéro de l'étape actuelle
            stepLabel.setText("Step: " + currentStep);

            // Mettre en pause l'exécution du thread principal pendant un certain temps (par exemple, 500 millisecondes)
            try {
                Thread.sleep(500); // Mettre en pause pendant 500 millisecondes entre chaque étape
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            nextStepButton.setEnabled(false); // Désactiver le bouton "Next Step" une fois que toutes les étapes ont été affichées
        }
    }

    private void updateGridPanel(int[][] grille) {
        gridPanel.removeAll(); // Effacer les composants précédents

        // Ajouter des JLabels avec les couleurs appropriées au panneau de grille
        for (int i = 0; i < config.getLargeur(); i++) {
            for (int j = 0; j < config.getHauteur(); j++) {
                JLabel cellLabel = new JLabel();
                cellLabel.setOpaque(true);
                if (grille[i][j] == 1) {
                    cellLabel.setBackground(Color.GREEN);
                } else if (grille[i][j] == 0) {
                    cellLabel.setBackground(Color.RED);
                } else {
                    cellLabel.setBackground(Color.GRAY);
                }
                gridPanel.add(cellLabel);
            }
        }

        revalidate(); // Rafraîchir le panneau de grille
        repaint();
    }

  

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ForetGUI visualisation = new ForetGUI();
                visualisation.setVisible(true);
            }
        });
    }
}