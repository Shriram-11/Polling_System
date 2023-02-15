package Polling_System;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class LanguagePoll {

    private JFrame frame;
    private Map<String, Integer> votes = new HashMap<String, Integer>();

    private void loadVotes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("votes.ser"))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                this.votes = (Map<String, Integer>) obj;
            }
        } catch (FileNotFoundException e) {
            // Ignore if file does not exist
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveVotes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("votes.ser"))) {
            out.writeObject(this.votes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LanguagePoll window = new LanguagePoll();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LanguagePoll() {
        loadVotes();

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblSelectYourFavorite = new JLabel("Select your favorite programming language:");
        lblSelectYourFavorite.setBounds(23, 6, 311, 16);
        panel.add(lblSelectYourFavorite);

        JRadioButton rdbtnJava = new JRadioButton("Java");
        rdbtnJava.setBounds(23, 34, 141, 23);
        panel.add(rdbtnJava);

        JRadioButton rdbtnPython = new JRadioButton("Python");
        rdbtnPython.setBounds(23, 61, 141, 23);
        panel.add(rdbtnPython);

        JRadioButton rdbtnC = new JRadioButton("C");
        rdbtnC.setBounds(23, 88, 141, 23);
        panel.add(rdbtnC);

        JRadioButton rdbtnCpp = new JRadioButton("C++");
        rdbtnCpp.setBounds(23, 115, 141, 23);
        panel.add(rdbtnCpp);

        JButton btnVote = new JButton("Vote");
        btnVote.setBounds(23, 152, 117, 29);
        panel.add(btnVote);

        JButton btnDisplay = new JButton("Display Results");
        btnDisplay.setBounds(23, 193, 141, 29);
        panel.add(btnDisplay);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnJava);
        group.add(rdbtnPython);
        group.add(rdbtnC);
        group.add(rdbtnCpp);

        btnVote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedLanguage = "";
                if (rdbtnJava.isSelected()) {
                    selectedLanguage = "Java";
                } else if (rdbtnPython.isSelected()) {
                    selectedLanguage = "Python";
                } else if (rdbtnC.isSelected()) {
                    selectedLanguage = "C";
                } else if (rdbtnCpp.isSelected()) {
                    selectedLanguage = "C++";
                }

                if (!selectedLanguage.isEmpty()) {
                    int count = votes.getOrDefault(selectedLanguage, 0);
                    votes.put(selectedLanguage, count + 1);
                    saveVotes();
                }
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                for (String language : votes.keySet()) {
                    int count = votes.get(language);
                    sb.append(language + ": " + count + "\n");
                }
                JLabel label = new JLabel(sb.toString());
                JOptionPane.showMessageDialog(frame, label, "Poll Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
