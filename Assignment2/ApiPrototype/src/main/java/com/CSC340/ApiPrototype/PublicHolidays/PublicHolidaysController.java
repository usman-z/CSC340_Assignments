package com.CSC340.ApiPrototype.PublicHolidays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PublicHolidaysController {
	
	@GetMapping(value="/publicHolidays/UnitedStates", produces="application/json")
	@ResponseBody
	public static String getPublicHolidays(@RequestParam int year) {
		
		String apiResponse = "";
		
        try {
        	String url = "https://date.nager.at/api/v2/publicholidays/"+year+"/US";
        	
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate jsonResponse = new RestTemplate();
            apiResponse = jsonResponse.getForObject(url, String.class);
            
            JsonNode root = mapper.readTree(apiResponse);

            //get information
            String holidayDate = root.findValue("date").asText();
            String holidayLocalName = root.findValue("localName").asText();
            String holidayName = root.findValue("name").asText();
            String holidayCountry = root.findValue("countryCode").asText();
            
            //print information
            System.out.println("\nHoliday Name: " + holidayName);
            System.out.println("Holiday Local Name: " + holidayLocalName);
            System.out.println("Holiday Date: " + holidayDate);
            System.out.println("Holiday Country: " + holidayCountry);

        } catch (JsonProcessingException ex) {
            System.out.println("ERR! Downstream API call failed");
        }
		
		return apiResponse;
	}
}
