package com.CSC340.ApiPrototype.SearchCrypto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SearchCrypto {
	
	@GetMapping(value="/searchCrypto/{ticker}", produces="application/json")
	public static String getCryptoInformation(@PathVariable String ticker) {
		
		String apiResponse = "";
		
        try {
        	String url = "https://api.kucoin.com/api/v1/market/stats?symbol="+ticker+"-USDT";
        	
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate jsonResponse = new RestTemplate();
            apiResponse = jsonResponse.getForObject(url, String.class);
            
            JsonNode root = mapper.readTree(apiResponse);

            //get information
            String coinSymbol = root.findValue("symbol").asText();
            String coinCurrentPrice = root.findValue("buy").asText();
            String coinHighPrice = root.findValue("high").asText();
            String coinLowPrice = root.findValue("low").asText();
            
            //print information
            System.out.println("\nCoin Symbol: "+coinSymbol);
            System.out.printf("Low price: %.2f\n", Double.parseDouble(coinLowPrice));
            System.out.printf("Current price: %.2f\n", Double.parseDouble(coinCurrentPrice));
            System.out.printf("High price: %.2f\n", Double.parseDouble(coinHighPrice));

        } catch (JsonProcessingException ex) {
            System.out.println("ERR!");
        }
		
		return apiResponse;
	}
}