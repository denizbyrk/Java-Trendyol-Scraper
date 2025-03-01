package com.denizbyrk.TrendyolScraper;

import java.util.List;

public class Product {

	private String imageURL;
	private String title;
	private String price;
	private List<String> category;
	private String ranking;
	private String rankingCount;
	private String description;
	private String commentCount;
	private List<Comment> comments;
	private String brand;
	private String seller;
	private String favourites;
		
	public Product() { }
	
	public Product(String imageURL, String title, String price, List<String> category, String ranking, String rankingCount, String description, String commentCount, List<Comment> comments, String brand, String seller, String favourites) {

		this.imageURL = imageURL;
		this.title = title;
		this.price = price;
		this.category = category;
		this.ranking = ranking;
		this.rankingCount = rankingCount;
		this.description = description;
		this.commentCount = commentCount;
		this.comments = comments;
		this.brand = brand;
		this.seller = seller;
		this.favourites = favourites;
	}

	public String getImageURL() {
		
		return this.imageURL;
	}
	
	public void setImageURL(String image) {
		
		this.imageURL = image;
	}
	
	public String getTitle() {
		
		return this.title;
	}
	public void setTitle(String title) {
		
		this.title = title;
	}
	
	public String getPrice() {
		
		return this.price;
	}
	
	public void setPrice(String price) {
		
		this.price = price;
	}
	
	public List<String> getCategory() {
		
		return this.category;
	}
	
	public void setCategory(List<String> category) {
		
		this.category = category;
	}
	
	public String getRanking() {
		
		return this.ranking;
	}
	
	public void setRanking(String ranking) {
		
		this.ranking = ranking;
	}
	
	public String getRankingCount() {
		
		return this.rankingCount;
	}
	
	public void setRankingCount(String rankingCount) {
		
		this.rankingCount = rankingCount;
	}
	
	public String getDescription() {
		
		return this.description;
	}
	
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	public String getCommentCount() {
		
		return this.commentCount;
	}
	
	public void setCommentCount(String reviewCount) {
		
		this.commentCount = reviewCount;
	}
	
	public List<Comment> getComments() {
		
		return this.comments;
	}
	
	public void setComments(List<Comment> comments) {
		
		this.comments = comments;
	}
	
	public String getBrand() {
		
		return this.brand;
	}
	
	public void setBrand(String brand) {
		
		this.brand = brand;
	}
	
	public String getSeller() {
		
		return this.seller;
	}
	
	public void setSeller(String seller) {
		
		this.seller = seller;
	}
	
	public String getFavourites() {
		
		return this.favourites;
	}
	
	public void setFavourites(String favourites) {
		
		this.favourites = favourites;
	}
	
	@Override
	public String toString() {
	    return "Product Details\n" +
	           "---------------\n" +
	           "Image: " + this.getImageURL() + "\n" +
	           "Title: " + this.getTitle() + "\n" +
	           "Category: " + this.getCategory() + "\n" +
	           "Price: " + this.getPrice() + "\n" +
	           "Ranking: " + this.getRanking() + " / 5 --- " + this.getRankingCount() + " Votes\n" +
	           "Review Count: " + this.getCommentCount() + "\n" +
	           "Seller: " + this.getSeller() + "\n" +
	           "Brand: " + this.getBrand() + "\n" +
	           "Comments: " + this.getComments();
	}
}