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
	
	//window dimensions
	private final static int WIDTH = 600;
	private final static int HEIGHT = 800;
	
    private JTextField urlField;
    private JButton fetchButton;
    private JButton clearButton;
    private JPanel infoPanel;
    private JPanel commentsPanel;
    private Font font;
    private Font dataFont;
    
    private int currentPage = 0; //current comment page
    private int commentsPerPage = 4; //comments displayed per page
    private List<Comment> commentsList = new ArrayList<Comment>();
    private JButton prevButton;
    private JButton nextButton;

    public Main() {
    	
        this.setTitle("Web Scraper");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Main.WIDTH, Main.HEIGHT);
        this.setLocationRelativeTo(null); //sets the window position to the center of the screen

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
        titleLabel.setBounds(Main.WIDTH / 2 - 105, 20, titleLabel.getText().length() * this.font.getSize(), 25);
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
        
        //create info panel
        this.infoPanel = new JPanel();
        this.infoPanel.setLayout(null);
        this.infoPanel.setBounds(urlLabel.getBounds().x, urlLabel.getBounds().y + 40, fetchButton.getBounds().x + fetchButton.getBounds().width - urlLabel.getBounds().width + 30, Main.HEIGHT - 140);
        this.infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Scraped Data"));
        this.infoPanel.setVisible(false);
        contentPane.add(this.infoPanel);
        
        //add mouse listener to change color on hover
        this.fetchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	
            	if (fetchButton.isEnabled()) {
            		
            		fetchButton.setBackground(Color.GRAY);
            	}
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	fetchButton.setBackground(Color.WHITE);
            }
        });
        
        //button Action Listener
        this.fetchButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
            	//get url
                String url = urlField.getText();
                
                if (!url.isEmpty()) {
                	
                    try {
                    	//process url
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
        
        //check if the url is a valid trendyol link
        if (url.startsWith("https://www.trendyol.com/")) {
        	
        	//display info panel
        	this.displayInfoPanel(url);
        } else {
        	
        	JOptionPane.showMessageDialog(null, "Please enter a Trendyol link.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    //display scraped data
    private void displayInfoPanel(String url) throws IOException {
    	
    	//read data
        WebScraper ws = new WebScraper();
        ws.Read(url);
    	
        this.infoPanel.removeAll();
        this.fetchButton.setEnabled(false);
        
        //get product
        Product p = ws.getProduct();

        //get comments
        this.commentsList = p.getComments();
 
        //create image
        ImageIcon imageIcon = new ImageIcon(new URL(p.getImageURL()));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(getWidth() / 3, getHeight() / 3, Image.SCALE_SMOOTH);

        //create image label
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(20, 20, scaledImage.getWidth(imageLabel), scaledImage.getHeight(imageLabel));
        imageLabel.setBorder(new LineBorder(Color.BLACK, 2));
        
        //create title label
        JLabel titleLabel = new JLabel("<html><div style='width: 240px;'>Title: " + p.getTitle() + "</div></html>");
        titleLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, 20, 300, 50); 
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        titleLabel.setSize(titleLabel.getPreferredSize());
        
        //get categories
        String categories = "";
        p.getCategory().remove(0);
        for (String s : p.getCategory()) {
        	
        	//check if the category is the last one
        	if (s.equals(p.getCategory().get(p.getCategory().size() - 1))) {
        		
        		categories += s;
        		 
        	} else {
        		
        		categories += s + " -> ";
        	}
        }
        
        //create categories label
        JLabel categoryLabel = new JLabel("<html><div style='width: 240px;'>Category: " + categories + "</div></html>");
        categoryLabel.setBounds(titleLabel.getX(), titleLabel.getY() + titleLabel.getHeight() + 10, 300, 50);
        categoryLabel.setVerticalAlignment(SwingConstants.TOP);
        categoryLabel.setSize(categoryLabel.getPreferredSize());
        
        //create price label
        JLabel priceLabel = new JLabel("Price: " + p.getPrice());
        priceLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, categoryLabel.getBounds().y + categoryLabel.getHeight() + 10, 400, 25);

        //create ranking label
        JLabel rankingLabel = new JLabel("Ranking: " + p.getRanking() + " / 5 --- " + p.getRankingCount() + " Votes");
        rankingLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, priceLabel.getBounds().y + 30, 400, 25);

        //create seller label
        JLabel sellerLabel = new JLabel("Seller: " + p.getSeller());
        sellerLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, rankingLabel.getBounds().y + 30, 400, 25);
        
        //create brand label
        JLabel brandLabel = new JLabel("Brand: " + p.getBrand());
        brandLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, sellerLabel.getBounds().y + 30, 400, 25);

        //create review count label
        JLabel reviewCountLabel = new JLabel("Comment Count: " + p.getCommentCount());
        reviewCountLabel.setBounds(imageLabel.getBounds().x + imageLabel.getBounds().width + 8, brandLabel.getBounds().y + 30, 400, 25);
        
        //create clear button
        clearButton = new JButton("Clear");
        clearButton.setFont(this.font);
        clearButton.setFocusPainted(false);
        clearButton.setBounds(Main.WIDTH - 184, titleLabel.getBounds().y + 240, 100, 25);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        clearButton.setBorder(compound);
        this.infoPanel.add(clearButton);
        
        //add mouse listener to clear button to change color on hover
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	clearButton.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	clearButton.setBackground(Color.WHITE);
            }
        });
        
        //Action Listener for clear button
        clearButton.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
                infoPanel.setVisible(false); //make info panel invisible
                fetchButton.setEnabled(true); //enable fetch button
                urlField.setText(""); //clear url field
                currentPage = 0; //set current comment page to 0
            }
        });
        
        //add components to info panel
        this.infoPanel.add(imageLabel);
        this.infoPanel.add(titleLabel);
        this.infoPanel.add(categoryLabel);
        this.infoPanel.add(priceLabel);
        this.infoPanel.add(rankingLabel);
        this.infoPanel.add(sellerLabel);
        this.infoPanel.add(brandLabel);
        this.infoPanel.add(reviewCountLabel);
        
        //create comments panel
        this.commentsPanel = new JPanel();
        this.commentsPanel.setLayout(null);
        this.commentsPanel.setBounds(imageLabel.getBounds().x - 2, imageLabel.getBounds().y + imageLabel.getBounds().height, infoPanel.getWidth() - 36, infoPanel.getHeight() - 300);
        this.commentsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Comments"));
        this.commentsPanel.setVisible(false);
        this.infoPanel.add(this.commentsPanel);
        
        //display comments
        this.displayComments();

        this.infoPanel.setVisible(true);
    }

    //display comments
    private void displayComments() {

    	//clear comments panel
    	this.commentsPanel.removeAll();

    	//calculate starting index
        int startIndex = currentPage * commentsPerPage;
        
        //calculate ending index
        int endIndex = Math.min(startIndex + commentsPerPage, commentsList.size());

        //spacing between comments
        int spacing = 12;
        
        for (int i = startIndex; i < endIndex; i++) {
        	
            Comment c = commentsList.get(i);
            
            //label for comment
            JLabel commentLabel = new JLabel("<html> Date Published: " + c.getDate()  + "<br>" + c.getAuthor() + ": " + c.getText() + "<br> Rating: " + c.getRating() + "</html>");
            commentLabel.setBounds(20, spacing, 460, 90);
            
            this.commentsPanel.add(commentLabel);
            spacing += 70; //increase spacing
        }

        //add the buttons
        this.addPageButtons();

        this.commentsPanel.revalidate();
        this.commentsPanel.repaint();
        this.commentsPanel.setVisible(true);
    }
    
    //add comment page change buttons
    private void addPageButtons() {
    	
    	//remove old button state
        if (prevButton != null) this.commentsPanel.remove(prevButton);
        if (nextButton != null) this.commentsPanel.remove(nextButton);

        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        
        //create prev button
        prevButton = new JButton("Previous Page");
        prevButton.setBounds(20, this.commentsPanel.getHeight() - 50, this.commentsPanel.getWidth() / 2 - 30, 40);
        prevButton.setEnabled(currentPage > 0);
        prevButton.setBorderPainted(true);
        prevButton.setBorder(compound);
        prevButton.addActionListener(e -> {
        	
            currentPage--; //go to prev page
            this.displayComments(); //display comments
        });

        //create next button
        nextButton = new JButton("Next Page");
        nextButton.setBounds(prevButton.getX() + prevButton.getWidth() + 20, this.commentsPanel.getHeight() - 50, prevButton.getWidth(), 40);
        nextButton.setEnabled((currentPage + 1) * commentsPerPage < commentsList.size());
        nextButton.setBorderPainted(true);
        nextButton.setBorder(compound);
        nextButton.addActionListener(e -> {
        	
            currentPage++; //go to next page
            this.displayComments(); //display comments
        });
        
        //add mouse listener to prev button to change color on hover
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	
            	if (prevButton.isEnabled()) {
            		
            		prevButton.setBackground(Color.GRAY);
            	}
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	prevButton.setBackground(Color.WHITE);
            }
        });
        
        //add mouse listener to next button to change color on hover
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	
            	if (nextButton.isEnabled()) {
            		
            		nextButton.setBackground(Color.GRAY);
            	}
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	nextButton.setBackground(Color.WHITE);
            }
        });

        //add button to comments panel
        this.commentsPanel.add(prevButton);
        this.commentsPanel.add(nextButton);
    }
    
    //main method
    public static void main(String[] args) {
    	
        new Main();
    }
}