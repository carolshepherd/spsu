import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class usernameCheck {
	public static String inputSentance(String entry) throws Exception {
		String [] stringArray = entry.split(" ");
		String output = "";
		for(String word : stringArray){
			if(checkLanguage(word).contains("true")){
				output = "{\"success\":\"true\",\"response\":\"true\"}";
			}
		}
		if(output == ""){
			output = "{\"success\":\"true\",\"response\":\"false\"}";
		}
		return output;
	}
	public static String checkLanguage(String entry) throws Exception {
		String url = "http://www.wdyl.com/profanity?q="+entry;
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
		return response.toString();
	}
	
	
}
