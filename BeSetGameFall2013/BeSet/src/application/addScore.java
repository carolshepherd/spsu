package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class addScore {
	public static boolean checkName(String name) throws Exception {
		if(checkLanguage(name).contains("false")){
			return true;
		}
		return false;
	}
	public static String checkLanguage(String entry) throws Exception {
		String url = "http://besetgame.com/checkProfanity?username="+entry;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("\n\ncheck language call " + inputLine);
		return response.toString();
	}
	public static void addScoreAndName (String name, String score)throws Exception {
		String url = "http://besetgame.com/addLeaderBoard?name="+name+"&score="+score;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//return response.toString();
	}
	
	
}
