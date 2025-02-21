package com.denizbyrk.TrendyolScraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebScraper {

	private String title;
	private String price;
	private String ranking;

	public void Read(String url) throws IOException {
		
		Document document = Jsoup.connect(url).get();
		
		this.title = this.processTitle(document);
		
		this.price = this.processPrice(document);
		
		this.ranking = this.processRanking(document);
			
		System.out.println("Title: " + this.title);
		System.out.println("Price: " + this.price);
		System.out.println("Ranking: " + this.ranking);
	}
	
	//scrape title
	private String processTitle(Document document) {
		
		Elements product = document.select(".pr-in-cn");
				
		if (!product.isEmpty()) {
			
		    return product.select("h1 > span").text();
		    
		} else {
			
		    return "Rating not found";
		}
	}
	
	//scrape price
	private String processPrice(Document document) {
		
		Elements product = document.select(".product-price-container");
		
		if (!product.isEmpty()) {
			
		    return product.select("div > div > span").text();
		    
		} else {
			
		    return "Rating not found";
		}
	}
	
	//scrape ranking
	private String processRanking(Document document) {
		
		Elements product = document.select(".product-rating-score .value, .tooltip-wrapper .tooltip-average-content");
		
		System.out.println(document.html());
		
		if (!product.isEmpty()) {
			
		    return product.text();
		    
		} else {
			
		    return "Rating not found";
		}
	}
	
	public String getTitle() {
		
		return this.title;
	}

	public String getRanking() {
		
		return this.ranking;
	}

	public String getPrice() {
		
		return this.price;
	}
}