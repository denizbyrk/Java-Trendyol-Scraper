package com.denizbyrk.TrendyolScraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WebScraper {

	private Product product;
	private Document document;
	
	public void Read(String url) throws IOException {

		this.product = new Product();
		this.document = Jsoup.connect(url).get();

		this.product.setImageURL(this.scrapeImage());
		this.product.setTitle(this.scrapeTitle());
		this.product.setPrice(this.scrapePrice());
		this.product.setRanking(this.scrapeRanking());
		this.product.setRankingCount(this.scrapeRankingCount());
		this.product.setCommentCount(this.scrapeCommentCount());
		this.product.setBrand(this.scrapeBrand());
		this.product.setSeller(this.scrapeSeller());

		this.product.displayData();
	}

	//scrape image 
	private String scrapeImage() {
		
		return this.scrapeJSON("image", "contentUrl");
	}
	
	//scrape title
	private String scrapeTitle() {

		Elements title = this.document.select(".pr-in-cn");

		if (!title.isEmpty()) {

			return title.select("h1 > span").text();

		} else {

			return "Title not found";
		}
	}

	//scrape price
	private String scrapePrice() {

		Elements price = this.document.select(".product-price-container");

		if (!price.isEmpty()) {

			return price.select("div > div > span").text();

		} else {

			return "Price not found";
		}
	}
	
	//scrape comment count
	private String scrapeSeller() {
		
		Element brand = this.document.select("span.product-description-market-place").first();

		if (!brand.text().isEmpty()) {
			
			return brand.text();

		} else {

			return "Brand not found";
		}
	}

	//scrape ranking
	private String scrapeRanking() {

		return this.scrapeJSON("aggregateRating", "ratingValue");
	}
	
	//scrape ranking count
	private String scrapeRankingCount() {
		
		return this.scrapeJSON("aggregateRating", "ratingCount");
	}
	
	//scrape brand
	private String scrapeBrand() {
		
		return this.scrapeJSON("manufacturer");
	}
	
	//scrape comment count
	private String scrapeCommentCount() {
		
		return this.scrapeJSON("aggregateRating", "reviewCount");
	}
	
	//scrape JSON data
	private String scrapeJSON(String objectTitle) {
		
		Element e = document.selectFirst("script[type=application/ld+json]");
		
		if (e != null) {
			
			String jsonContent = e.html();

			JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();

			if (jsonObject.has(objectTitle)) {
				
				return jsonObject.get(objectTitle).getAsString();
			} else {
				
				return objectTitle + " not found.";
			}
		} else {
			
			return objectTitle + " not found.";
		}
	}
	
	//scrape JSON data
	private String scrapeJSON(String objectTitle, String data) {
		
		Element e = document.selectFirst("script[type=application/ld+json]");
		
		if (e != null) {
			
			String jsonContent = e.html();

			JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
			JsonObject result = jsonObject.getAsJsonObject(objectTitle);

			if (result != null && result.has(data)) {
			
				JsonElement element = result.get(data);

                if (element.isJsonArray()) {
                	
                    JsonArray contentArray = element.getAsJsonArray();
                    
                    if (!contentArray.isEmpty()) {
                    
                        return contentArray.get(0).getAsString();
                    }
                    
                } else {
                	
                	return result.get(data).getAsString();
                }
			} else {
				
				return data + " not found.";
			}
		} else {
			
			return data + " not found.";
		}
		
		return data + " not found.";
	}

	//get product
	public Product getProduct() {
		
		return this.product;
	}
}