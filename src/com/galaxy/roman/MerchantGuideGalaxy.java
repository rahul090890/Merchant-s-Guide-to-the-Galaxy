package com.galaxy.roman;


public class MerchantGuideGalaxy{

	/**
	 * If args is not provided it will pick file from resource folder
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = null;
		if (args.length != 0)
			filePath = args[0];
		try{
			CrunchingInputData.readInputFile(filePath);
			CrunchingInputData.MapTokentoIntegerValue();
			ProcessOutPut.processReplyForQuestion();
		}
		catch(Exception e){
			System.out.println("File Not Found ");
		}
	}

}
