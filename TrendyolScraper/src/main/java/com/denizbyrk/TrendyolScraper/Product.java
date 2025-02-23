package com.denizbyrk.TrendyolScraper;

public class Product {

	private String title;
	private String price;
	private String ranking;
	private String rankingCount;
	private String description;
	private String commentCount;
	private String brand;
	private String seller;
	private String favourites;
	
	public void displayData() {
		
		System.out.println("Product Details");
		System.out.println("---------------");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Price: " + this.getPrice());
		System.out.println("Ranking: " + this.getRanking() + " / 5 --- " + this.getRankingCount() + " Votes");
		System.out.println("Review Count: " + this.getCommentCount());
		System.out.println("Seller: " + this.getSeller());
		System.out.println("Brand: " + this.getBrand());
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
}