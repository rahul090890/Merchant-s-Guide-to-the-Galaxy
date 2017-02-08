package com.galaxy.roman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessOutPut extends CrunchingInputData{

	
	/**
	 * 
	 */
	public static void processReplyForQuestion(){
		Map<String, String> map = qReply;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			processReply(entry.getKey());
		}
	}

	private static void processReply(String query){
		if (query.toLowerCase().startsWith("how much")){
			findRomanVal(query);
		}
		else if (query.toLowerCase().startsWith("how many")){
			findElementVal(query);
		}

	}

	public static void findRomanVal(String query){
		if (checkValidInput(query)== true){
			ArrayList<String> tokenValueToRoman = new ArrayList<String>();
			ArrayList<String> tokenValue = splitQuery(query);
			for (int i = 0; i < tokenValue.size(); i++) {
				tokenValueToRoman.add(romanMapping.get(tokenValue.get(i)));
			}
			float value = new RomanToDecimalConverter().romanToDecimal(tokenValueToRoman.toString());
			tokenValue.add("is");tokenValue.add(Float.toString(value));
			System.out.println(outputFormatter(tokenValue));
		}
		else{
			System.err.println("I have no idea what you are talking about");
		}
	}


	private static void findElementVal(String query){
		if (checkValidInput(query) == true){
			ArrayList<String> tokenValue = splitQuery(query);
			ArrayList<String> tokenValueToRoman = new ArrayList<String>();
			String element = null;
			for (int i = 0; i < tokenValue.size(); i++) {
				if(romanMapping.get(tokenValue.get(i)) != null){
					tokenValueToRoman.add(romanMapping.get(tokenValue.get(i)));
				}
				else if (elementValList.get(tokenValue.get(i)) != null){
					element = tokenValue.get(i);
				}
				else{
					System.err.println("I have no idea what you are talking about");
				}
			}
			float elementValue = (new RomanToDecimalConverter().romanToDecimal(tokenValueToRoman.toString()) * elementValList.get(element));
			tokenValue.add("is");tokenValue.add(Float.toString(elementValue));tokenValue.add("Credits");
			System.out.println(outputFormatter(tokenValue));
		}
		else{
			System.err.println("I have no idea what you are talking about");
		}
	}

	
	private static String outputFormatter(ArrayList<String> output){
		return output.toString().replace(",", "").replace("[", "").replace("]", "");
	}

	private static boolean checkValidInput(String query){
		Pattern regex = Pattern.compile("[$&+,:;=@#|]");
		Matcher matcher = regex.matcher(query);
		if (matcher.find()){
			return false;
		}
		else{
			return true;
		}

	}
	private static ArrayList<String> splitQuery(String query){
		ArrayList<String> queryArray = new ArrayList<String>(Arrays.asList(query.split("((?<=:)|(?=:))|( )")));
		int startIndex = 0, endIndex = 0;
		for (int i = 0; i < queryArray.size(); i++) {
			if(queryArray.get(i).toLowerCase().equals("is")){
				startIndex = i+1;
			}
			else if(queryArray.get(i).toLowerCase().equals("?")){
				endIndex = i;

			}
		}
		String[] array = queryArray.toArray(new String[queryArray.size()]);
		return new ArrayList<String>(Arrays.asList(java.util.Arrays.copyOfRange(array, startIndex, endIndex)));

	}

}

