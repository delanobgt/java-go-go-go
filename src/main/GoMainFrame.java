package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GoMainFrame extends JFrame {

    // global constants
    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 600;
    public static final Color COLOR_1 = new Color(255, 255, 255);
    public static final Color COLOR_2 = new Color(1, 175, 184);
    public static final Color COLOR_3 = new Color(51, 60, 69);
    public static final Color COLOR_4 = new Color(24, 34, 44);
    public static final int ROOM_INFO_SERVER_PORT = 9898;
    public static final int GAME_SERVER_PORT = 6666;
    
    // main container
    JPanel mainPanel = new JPanel();
    CardLayout cardLayout = new CardLayout();
    
    // different types of cards (Scenes)
    GoMainMenu goMainMenu;
    
    // map of name -> components
    HashMap<String, Component> componentMap = new HashMap<>();
    
    public GoMainFrame() {
        mainPanel.setLayout(cardLayout);
        mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        mainPanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        goMainMenu = new GoMainMenu(this);
        
        addComponent("mainMenu", goMainMenu);
        
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Go Mania!");
        this.pack();
        this.setLocationRelativeTo(null);
        
        changeSceneTo("mainMenu");
    }

    public void changeSceneTo(String sceneName) {
        cardLayout.show(mainPanel, sceneName);
    }
    
    public void addComponent(String name, Component component) {
        removeComponent(name);
        mainPanel.add(name, component);
        componentMap.put(name, component);
    }
    public boolean removeComponent(String name) {
        if (componentMap.containsKey(name)) {
            mainPanel.remove(componentMap.get(name));
            componentMap.remove(name);
            return true;
        }
        return false;
    }
    
    // program's main entry 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GoMainFrame().setVisible(true);
        });
    }
    
}
