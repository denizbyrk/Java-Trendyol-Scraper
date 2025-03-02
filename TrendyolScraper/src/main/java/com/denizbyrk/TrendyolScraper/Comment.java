package com.denizbyrk.TrendyolScraper;

public class Comment {

	//comment attributes
	private String author;
	private String text;
	private String date;
	private String rating;
	
	public Comment() { }

	//constructor
	public Comment(String author, String text, String date, String rating) {

		this.author = author;
		this.text = text;
		this.date = date;
		this.rating = rating;
	}

	//getters and setters
	public String getAuthor() {
		
		return author;
	}

	public void setAuthor(String author) {
		
		this.author = author;
	}

	public String getText() {
		
		return text;
	}

	public void setText(String text) {
		
		this.text = text;
	}

	public String getDate() {
		
		return date;
	}

	public void setDate(String date) {
		
		this.date = date;
	}

	public String getRating() {
		
		return rating;
	}

	public void setRating(String rating) {
		
		this.rating = rating;
	}
	
	//to string method
	@Override
	public String toString() {
		
		return "Comment{" +
	                "Author: " + this.getAuthor() +
	                ", Date: " + this.getDate() + 
	                ", Text: " + this.getText() +
	                ", Rating: " + this.getRating() +
	                "}";
	}
}