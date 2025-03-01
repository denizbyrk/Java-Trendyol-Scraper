package com.denizbyrk.TrendyolScraper;

public class Comment {

	private String author;
	private String text;
	private String date;
	private String rating;
	
	public Comment() { }

	public Comment(String author, String text, String date, String rating) {

		this.author = author;
		this.text = text;
		this.date = date;
		this.rating = rating;
	}

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
	
	@Override
	public String toString() {
		
		return "Comment{" +
	                "Author: " + author +
	                ", Date: " + date + 
	                ", Text: " + text +
	                ", Rating: " + rating +
	                "}";
	}
}