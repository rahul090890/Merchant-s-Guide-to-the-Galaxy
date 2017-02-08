package com.galaxy.roman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CrunchingInputData{

	
	static Map<String, String> romanMapping = new HashMap<String, String>();
	static Map<String, Float> integerVal = new HashMap<String, Float>(); 
	static Map<String, String> qReply = new LinkedHashMap<String, String>(); 
	static ArrayList<String> missingVal = new ArrayList<String>(); 
	static Map<String, Float> elementValList = new HashMap<String, Float>(); 


	/**
	 * take a file name if given else take from resource
	 * @param filePath
	 * @throws IOException
	 */
	public static void readInputFile(String filePath) throws IOException {
		BufferedReader bufferedReader = null;
		if (filePath == null){
			InputStream in = CrunchingInputData.class.getResourceAsStream("/resource/input.txt");
			bufferedReader =new BufferedReader(new InputStreamReader(in));
		}
		else{
			FileReader fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);
		}
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			readFileLine(line);
		}
		bufferedReader.close();
	}

	
	/**
	 * @param line
	 */
	public static void readFileLine(String line){
		String arr[] = line.split(" ");

		if (line.endsWith("?")){
			qReply.put(line,"");
		}
		else if (arr.length == 3 && arr[1].equalsIgnoreCase("is")){
			romanMapping.put(arr[0], arr[arr.length-1]);
		}
		else if(line.toLowerCase().endsWith("credits")){
			missingVal.add(line);
		}
	}
/*	glob is I
	prok is V
	pish is X
	tegj is L
	glob glob Silver is 34 Credits
	glob prok Gold is 57800 Credits
	pish pish Iron is 3910 Credits
	how much is pish tegj glob glob ?
	how many Credits is glob prok Silver ?
	how many Credits is glob prok Gold ?
	how many Credits is glob prok Iron ?
	how much wood could a woodchuck chuck if a woodchuck could chuck= wood ?*/
	/**
	 * Maps tokens to Roman equivalent  
	 * 
	 */
	public static void MapTokentoIntegerValue(){

		Iterator it = romanMapping.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry token = (Map.Entry)it.next();
			float integerValue = new RomanToDecimalConverter().romanToDecimal(token.getValue().toString());
			integerVal.put(token.getKey().toString(), integerValue);
		}
		mapMissingEntities();
	}


	private static void mapMissingEntities(){
		for (int i = 0; i < missingVal.size(); i++) {
			deCodeMissingQuery(missingVal.get(i));
		}
	}

	/**
	 * @param query
	 */
	private static void deCodeMissingQuery(String query){
		String array[] = query.split("((?<=:)|(?=:))|( )");
		int splitIndex = 0;
		int value = 0; String element= null; String[] valueofElement = null;
		for (int i = 0; i < array.length; i++) {
			if(array[i].toLowerCase().equals("credits")){
				value = Integer.parseInt(array[i-1]);
			}
			if(array[i].toLowerCase().equals("is")){
				splitIndex = i-1;
				element = array[i-1];
			}
			valueofElement = java.util.Arrays.copyOfRange(array, 0, splitIndex);
		}

		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < valueofElement.length; j++) {
			builder.append(romanMapping.get(valueofElement[j]));
		}
		float valueInDecimal = new RomanToDecimalConverter().romanToDecimal(builder.toString());
		elementValList.put(element, value/valueInDecimal);
	}

}
