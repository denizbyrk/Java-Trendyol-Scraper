package com.denizbyrk.TrendyolScraper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private final static int WIDTH = 600;
	private final static int HEIGHT = 800;
	
    private JTextField urlField;
    private JButton fetchButton;
    private JPanel infoPanel;
    private Font font;
    private Font dataFont;

    public Main() {
    	
        this.setTitle("Web Scraper");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Main.WIDTH, Main.HEIGHT);
        this.setLocationRelativeTo(null);
        
        this.font = new Font("Arial", Font.BOLD, 18);
        this.dataFont = new Font("Arial", Font.BOLD, 12);

        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("Label.font", this.dataFont);
        UIManager.put("Label.foreground", Color.BLACK);
        
        //create a panel with a null layout
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        
        //title label (top)
        JLabel titleLabel = new JLabel("Trendyol Web Scraper");
        titleLabel.setFont(this.font);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(Main.WIDTH / 2 - 360 / 4, 20, titleLabel.getText().length() * this.font.getSize(), 25);
        contentPane.add(titleLabel);
        
        //URL label (top left)
        JLabel urlLabel = new JLabel("URL");
        urlLabel.setFont(this.font);
        urlLabel.setForeground(Color.BLACK);
        urlLabel.setBounds(20, titleLabel.getBounds().y + 30, urlLabel.getText().length() * this.font.getSize(), 25);
        contentPane.add(urlLabel);

        //fetch button (next to TextField)
        fetchButton = new JButton("Fetch");
        fetchButton.setFont(this.font);
        fetchButton.setForeground(Color.BLACK);
        fetchButton.setBackground(Color.WHITE);
        fetchButton.setFocusPainted(false);
        fetchButton.setBounds(Main.WIDTH - 140, titleLabel.getBounds().y + 30, 100, 25);
        
        fetchButton.setForeground(Color.BLACK);
        fetchButton.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        fetchButton.setBorder(compound);
        
        contentPane.add(fetchButton);

        //URL TextField (next to URL label)
        this.urlField = new JTextField();
        this.urlField.setBounds(urlLabel.getBounds().x + 50, titleLabel.getBounds().y + 30, fetchButton.getBounds().x - urlLabel.getBounds().x - fetchButton.getBounds().width + 30, 25);
        contentPane.add(urlField);
        
        infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(urlLabel.getBounds().x, urlLabel.getBounds().y + 40, fetchButton.getBounds().x + fetchButton.getBounds().width - urlLabel.getBounds().width + 30, Main.HEIGHT - 180);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Scraped Data"));
        infoPanel.setVisible(false);
        contentPane.add(infoPanel);
        
        //add mouse listener to change color on hover
        fetchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	fetchButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	fetchButton.setBackground(Color.WHITE);
            }
        });
        
        //button Action Listener
        fetchButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
                String url = urlField.getText();
                
                if (!url.isEmpty()) {
                	
                    try {
						processURL(url);
					} catch (IOException e1) {

						e1.printStackTrace();
					}
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a URL!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    //process URL
    private void processURL(String url) throws IOException {
    	
        System.out.println("Processing URL: " + url);
        
        if (url.startsWith("https://www.trendyol.com/")) {
        	
        	this.displayInfoPanel(url);
        } else {
        	
        	JOptionPane.showMessageDialog(null, "Please enter a Trendyol link.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //display scraped data
    private void displayInfoPanel(String url) throws IOException {
    	
        WebScraper ws = new WebScraper();
        ws.Read(url);
    	
        this.infoPanel.removeAll(); 
        
        String title = ws.getTitle();
        String price = ws.getPrice();
        String ranking = ws.getRanking();
        
        JLabel titleLabel = new JLabel("Title: " + title);
        titleLabel.setBounds(20, 20, 400, 25);
        infoPanel.add(titleLabel);

        JLabel priceLabel = new JLabel("Price: " + price);
        priceLabel.setBounds(20, 50, 400, 25);
        infoPanel.add(priceLabel);

        JLabel rankingLabel = new JLabel("Ranking: " + ranking);
        rankingLabel.setBounds(20, 80, 400, 25);
        infoPanel.add(rankingLabel);

        this.infoPanel.add(titleLabel);
        this.infoPanel.add(priceLabel);
        this.infoPanel.add(rankingLabel);

        this.infoPanel.setVisible(true);
        this.infoPanel.revalidate();
        this.infoPanel.repaint();
    }

    public static void main(String[] args) {
    	
        new Main();
    }
}