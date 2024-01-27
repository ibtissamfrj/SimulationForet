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
    private JButton prevStepButton;
    private JLabel stepLabel;
    private List<int[][]> grilles; // Liste pour stocker toutes les grilles à chaque étape
    private int currentStep = -1;
    private Foret foret;
    private Configuration config;

    public ForetGUI() {
        super("Simulation de propagation de feu");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // Charger le fichier de configuration et initialiser la forêt
        try {
            config = new Configuration("src/main/config.txt");
            foret = new Foret("src/main/config.txt");
            grilles = foret.getGrilles(); // Initialiser la liste des grille
            // Initialiser les composants après avoir ajouté la grille initiale
            initComponents();
            // Mettre à jour le panneau de grille pour refléter la grille initiale
            nextStep();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier de configuration.", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Quitter l'application en cas d'erreur
        }
    }

    private void initComponents() {
        // Initialiser le panneau de grille
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(config.getLargeur(), config.getHauteur()));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        // Initialiser le label d'étape
        stepLabel = new JLabel("Step: " + currentStep);

        // Initialiser le bouton de l'étape suivante
        nextStepButton = new JButton("Next Step");
        prevStepButton = new JButton("Previous Step");
        prevStepButton.setEnabled(false);

        nextStepButton.addActionListener(e -> nextStep());
        prevStepButton.addActionListener(e -> prevStep());


        // Ajouter les composants au contenu de la fenêtre
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gridPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(prevStepButton);
        buttonPanel.add(nextStepButton);
        contentPane.add(stepLabel, BorderLayout.NORTH);
    }
    private void nextStep() {
        currentStep++;//  le panneau de grille pour refléter la grille récupérée
        updateGridPanel();

        // Mettre à jour le label d'étape avec le numéro de l'étape actuelle
        stepLabel.setText("Step: " + currentStep);

    }
    private void prevStep() {
        currentStep--; // Incrémenter le numéro de l'étape

        // Mettre à jour le panneau de grille pour refléter la grille récupérée
        updateGridPanel();

        // Mettre à jour le label d'étape avec le numéro de l'étape actuelle
        stepLabel.setText("Step: " + currentStep);

    }

    private void updateGridPanel() {
        if (currentStep > 0 && currentStep < grilles.size() - 1) {
            nextStepButton.setEnabled(true);
            prevStepButton.setEnabled(true);
        }
        if (currentStep == 0) {
            prevStepButton.setEnabled(false);
        }
        if (currentStep == grilles.size() - 1) {
            nextStepButton.setEnabled(false);
        }

        int [][] grille = grilles.get(currentStep);
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

        gridPanel.revalidate(); // Rafraîchir le panneau de grille
        gridPanel.repaint();

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