package com.denizbyrk.TrendyolScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WebScraper {

	private Product product; //create product
	private Document document; //create html document
	
	//read data
	public void Read(String url) throws IOException {

		this.product = new Product();
		
		//connect to url
		this.document = Jsoup.connect(url).get();

		//set product data
		this.product.setImageURL(this.scrapeImage());
		this.product.setTitle(this.scrapeTitle());
		this.product.setPrice(this.scrapePrice());
		this.product.setCategory(this.scrapeCategory());
		this.product.setRanking(this.scrapeRanking());
		this.product.setRankingCount(this.scrapeRankingCount());
		this.product.setCommentCount(this.scrapeCommentCount());
		this.product.setComments(this.scrapeComments());
		this.product.setBrand(this.scrapeBrand());
		this.product.setSeller(this.scrapeSeller());

		System.out.println(this.product);
	}

	//scrape image 
	private String scrapeImage() {
		
		return this.scrapeJSON("image", "contentUrl");
	}
	
	//scrape title
	private String scrapeTitle() {

		//find where the title is stored
		Elements title = this.document.select(".pr-in-cn");

		if (!title.isEmpty()) {

			return title.select("h1 > span").text();

		} else {

			return "Title not found";
		}
	}

	//scrape price
	private String scrapePrice() {

		//find where the price is stored
		Elements price = this.document.select(".product-price-container");

		if (!price.isEmpty()) {

			return price.select("div > div > span").text();

		} else {

			return "Price not found";
		}
	}
	
	//scrape category
	private List<String> scrapeCategory() {
		
		return this.scrapeJSONcategory();
	}
	
	//scrape comment count
	private String scrapeSeller() {
		
		//find where the brand is stored
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
	
	//scrape comments
	private List<Comment> scrapeComments() {
		
		return this.scrapeJSONcomments();
	}
	
	//scrape JSON data from where the object title is enough
	private String scrapeJSON(String objectTitle) {
		
		//get the script
		Element e = document.selectFirst("script[type=application/ld+json]");
		
		if (e != null) {
			
			//convert html to string
			String jsonContent = e.html();

			//parse string to json object
			JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();

			//check if the object has object title
			if (jsonObject.has(objectTitle)) {
				
				return jsonObject.get(objectTitle).getAsString();
			} else {
				
				return objectTitle + " not found.";
			}
		} else {
			
			return objectTitle + " not found.";
		}
	}
	
	//scrape JSON data from places where the title has subfields, requiring data name
	private String scrapeJSON(String objectTitle, String data) {
		
		//get the script
		Element e = document.selectFirst("script[type=application/ld+json]");
		
		if (e != null) {
			
			//convert html to string
			String jsonContent = e.html();

			//parse string to json object
			JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
			
			//get the results of the title
			JsonObject result = jsonObject.getAsJsonObject(objectTitle);

			if (result != null && result.has(data)) {
			
				//create json element from data
				JsonElement element = result.get(data);

				//check if the json element is an array
                if (element.isJsonArray()) {
                	
                	//convert element to json array
                    JsonArray contentArray = element.getAsJsonArray();
                    
                    //check if it is empty
                    if (!contentArray.isEmpty()) {
                    
                    	//get the first element
                        return contentArray.get(0).getAsString();
                        
                    } else {
                    	
                    	return data + " not found.";
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
	}
	
	//scrape category data
	private List<String> scrapeJSONcategory() {
		
		//get the script
		Element e = document.select("script[type=application/ld+json]").get(1);
		
		//create empty categories list
		List<String> categories = new ArrayList<String>();
		
		if (e != null) {
			
			//convert html to string
			String jsonContent = e.html();
			
			//parse string to json object
            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
            
            //get breadcrumb object
            JsonObject breadcrumb = jsonObject.getAsJsonObject("breadcrumb");
            
            //get the categories as json array
            JsonArray itemList = breadcrumb.getAsJsonArray("itemListElement");
            
            //iterate through elements
            for (JsonElement element : itemList) {
                
            	//convert to json object
                JsonObject listItem = element.getAsJsonObject();
                
                //get individual items
                JsonObject item = listItem.getAsJsonObject("item");

                //convert to string
                String name = item.get("name").getAsString();
                
                //add category to the list
                categories.add(name);
            }
            
            return categories;
		}
		
		return categories;
	}
	
	//scrape comment data
	private List<Comment> scrapeJSONcomments() {
		
		//get the script
		Element e = document.selectFirst("script[type=application/ld+json]");
		
		//create empty comments list
		List<Comment> comments = new ArrayList<Comment>();
		
		if (e != null) {
			
			//convert html to string
			String jsonContent = e.html();

			//parse string to json object
            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();

            //check if the json object has review field
            if (jsonObject.has("review")) {
            	
            	//convert to json array
                JsonArray commentsArray = jsonObject.getAsJsonArray("review");

                //iterate through comments array
                for (JsonElement j : commentsArray) {
                	
                	//convert to json object
                    JsonObject commentObj = j.getAsJsonObject();

                    //parse the fields to strings
                    String author = commentObj.getAsJsonObject("author").get("name").getAsString();
                    String text = commentObj.get("reviewBody").getAsString();
                    String date = commentObj.get("datePublished").getAsString();
                    String rating = commentObj.getAsJsonObject("reviewRating").get("ratingValue").getAsString();

                    //create comment object and add to list
                    Comment comment = new Comment(author, text, date, rating);
                    comments.add(comment);
                }
                
                return comments;
			}
		}
		
		return comments;
	}

	//get product
	public Product getProduct() {
		
		return this.product;
	}
}