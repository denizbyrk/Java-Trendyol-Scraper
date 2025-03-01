package com.denizbyrk.TrendyolScraper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
    private JPanel commentsPanel;
    private Font font;
    private Font dataFont;
    
    private int currentPage = 0;
    private int commentsPerPage = 4;
    private List<Comment> commentsList = new ArrayList<Comment>();
    private JButton prevButton, nextButton;

    public Main() {
    	
        this.setTitle("Web Scraper");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Main.WIDTH, Main.HEIGHT);
        this.setLocationRelativeTo(null);

        this.font = new Font("Arial", Font.BOLD, 18);
        this.dataFont = new Font("Arial", Font.BOLD, 12);

        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("Label.font", this.dataFont);
        UIManager.put("Label.foreground", Color.BLACK);
        
        //create a panel with a null layout
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(0, 0, Main.WIDTH, 50);

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
        fetchButton.setFocusPainted(false);
        fetchButton.setBounds(Main.WIDTH - 140, titleLabel.getBounds().y + 30, 100, 25);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        fetchButton.setBorder(compound);
        contentPane.add(fetchButton);

        //URL TextField (next to URL label)
        this.urlField = new JTextField();
        this.urlField.setBounds(urlLabel.getBounds().x + 50, titleLabel.getBounds().y + 30, fetchButton.getBounds().x - urlLabel.getBounds().x - fetchButton.getBounds().width + 30, 25);
        contentPane.add(urlField);
        
        this.infoPanel = new JPanel();
        this.infoPanel.setLayout(null);
        this.infoPanel.setBounds(urlLabel.getBounds().x, urlLabel.getBounds().y + 40, fetchButton.getBounds().x + fetchButton.getBounds().width - urlLabel.getBounds().width + 30, Main.HEIGHT - 140);
        this.infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Scraped Data"));
        this.infoPanel.setVisible(false);
        contentPane.add(this.infoPanel);
        
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
    	
    	String lines = "";
    	
    	for (int i = 0; i < url.length() + 16; i++) {
    		
    		lines += "=";
    	}
    	
    	System.out.println(lines);
        System.out.println("Processing URL: " + url);
        System.out.println(lines + "\n");
        
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
        
        Product p = ws.getProduct();

        this.commentsList = p.getComments();
        
        ImageIcon imageIcon = new ImageIcon(new URL(p.getImageURL()));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(getWidth() / 3, getHeight() / 3, Image.SCALE_SMOOTH);

        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(20, 20, scaledImage.getWidth(imageLabel), scaledImage.getHeight(imageLabel));
        imageLabel.setBorder(new LineBorder(Color.BLACK, 2));
        
        JLabel titleLabel = new JLabel("<html>Title: " + p.getTitle() + "</html>");
        titleLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, 20, 300, 50); 
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        
        JLabel priceLabel = new JLabel("<html><b>Price:</b> " + p.getPrice() + "</html>");
        priceLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, titleLabel.getBounds().y + 40, 400, 25);

        JLabel rankingLabel = new JLabel("Ranking: " + p.getRanking() + " / 5 --- " + p.getRankingCount() + " Votes");
        rankingLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, priceLabel.getBounds().y + 30, 400, 25);

        JLabel reviewCountLabel = new JLabel("Comment Count: " + p.getCommentCount());
        reviewCountLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, rankingLabel.getBounds().y + 30, 400, 25);

        JLabel sellerLabel = new JLabel("Seller: " + p.getSeller());
        sellerLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, reviewCountLabel.getBounds().y + 30, 400, 25);
        
        JLabel brandLabel = new JLabel("Brand: " + p.getBrand());
        brandLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, sellerLabel.getBounds().y + 30, 400, 25);

        this.infoPanel.add(imageLabel);
        this.infoPanel.add(titleLabel);
        this.infoPanel.add(priceLabel);
        this.infoPanel.add(rankingLabel);
        this.infoPanel.add(reviewCountLabel);
        this.infoPanel.add(sellerLabel);
        this.infoPanel.add(brandLabel);
        
        this.commentsPanel = new JPanel();
        this.commentsPanel.setLayout(null);
        this.commentsPanel.setBounds(imageLabel.getBounds().x - 2, imageLabel.getBounds().y + imageLabel.getBounds().height, infoPanel.getWidth() - 36, infoPanel.getHeight() - 300);
        this.commentsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Comments"));
        this.commentsPanel.setVisible(false);
        this.infoPanel.add(this.commentsPanel);
        
        this.displayComments();

        this.infoPanel.setVisible(true);
    }

    //display comments
    private void displayComments() {

    	this.commentsPanel.removeAll();

        int startIndex = currentPage * commentsPerPage;
        int endIndex = Math.min(startIndex + commentsPerPage, commentsList.size());

        int spacing = 12;
        
        for (int i = startIndex; i < endIndex; i++) {
        	
            Comment c = commentsList.get(i);
            
            JLabel commentLabel = new JLabel("<html> Date Published: " + c.getDate()  + "<br>" + c.getAuthor() + ": " + c.getText() + "<br> Rating: " + c.getRating() + "</html>");
            commentLabel.setBounds(20, spacing, 400, 90);
            
            this.commentsPanel.add(commentLabel);
            spacing += 70;
        }

        this.addPageButtons();

        this.commentsPanel.revalidate();
        this.commentsPanel.repaint();
        this.commentsPanel.setVisible(true);
    }
    
    //add comment page change buttons
    private void addPageButtons() {
    	
        if (prevButton != null) this.commentsPanel.remove(prevButton);
        if (nextButton != null) this.commentsPanel.remove(nextButton);

        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        
        prevButton = new JButton("Previous Page");
        prevButton.setBounds(20, this.commentsPanel.getHeight() - 50, this.commentsPanel.getWidth() / 2 - 30, 40);
        prevButton.setEnabled(currentPage > 0);
        prevButton.setBorderPainted(true);
        prevButton.setBorder(compound);
        prevButton.addActionListener(e -> {
        	
            currentPage--;
            this.displayComments();
        });

        nextButton = new JButton("Next Page");
        nextButton.setBounds(prevButton.getX() + prevButton.getWidth() + 20, this.commentsPanel.getHeight() - 50, prevButton.getWidth(), 40);
        nextButton.setEnabled((currentPage + 1) * commentsPerPage < commentsList.size());
        nextButton.setBorderPainted(true);
        nextButton.setBorder(compound);
        nextButton.addActionListener(e -> {
        	
            currentPage++;
            this.displayComments();
        });

        this.commentsPanel.add(prevButton);
        this.commentsPanel.add(nextButton);
    }
    
    public static void main(String[] args) {
    	
        new Main();
    }
}