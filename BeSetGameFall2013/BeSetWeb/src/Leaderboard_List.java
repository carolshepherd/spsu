import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;





public class Leaderboard_List {
	ArrayList<LeaderBoard_Entry> collectionScore;
	Serializer ser; // Serializer class
	int numberOfLeaders;

	public Leaderboard_List(){
	collectionScore = new ArrayList<LeaderBoard_Entry>();
	ser = new Serializer();
	}

	public void setCollection(ArrayList<LeaderBoard_Entry> c) {
	this.collectionScore = c;
	}

	public ArrayList<LeaderBoard_Entry> getCollection() {
	return collectionScore;
	}

	public String toString(){
		String output = "{\"success\":\"true\", \"list\":[";
		for(LeaderBoard_Entry entry : collectionScore){
			output = output + "{\"name\": \"" + entry.getName() + "\",\"score\":\"" + entry.getScore() + "\"},";
		}
		if(output.substring(output.length()-1) == ","){
			output = output.substring(0, output.length()-1);
		}
		return output + "]}";
	}
	
	public String toStringTopTen(){
		String output = "{\"success\":\"true\", \"list\":[";
		if(collectionScore.size() > 10){
			for(int i = 0; i < 10; i++){
				output = output + "{\"name\": \"" + collectionScore.get(i).getName() + "\",\"score\":\"" + collectionScore.get(i).getScore() + "\"},";
			}
		}
		else
		{
			for(LeaderBoard_Entry entry : collectionScore){
				output = output + "{\"name\": \"" + entry.getName() + "\",\"score\":\"" + entry.getScore() + "\"},";
			}
		}
		if(!(output.substring(output.length()-1) == ",")){
			output = output.substring(0, output.length()-1);
		}
		return output + "]}";
	}

	public void add(LeaderBoard_Entry entry) {
		
		if(!containEntry(entry)){
			collectionScore.add(entry);
		}
		Collections.sort(collectionScore, new Comparator<LeaderBoard_Entry>() {
		    public int compare(LeaderBoard_Entry a, LeaderBoard_Entry b) {
		        return Integer.parseInt(b.getScore()) - Integer.parseInt(a.getScore());
		    }
		});
		
		
	}
	
	public boolean containEntry(LeaderBoard_Entry entry) {
		boolean found = false;
		for (int i = 0; i < collectionScore.size(); ++i) {
			if (entry.equals(collectionScore.get(i))) {
				found = true;
			}
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	public void loadList(String fileName) {
		// Load and append the current jokeList
		boolean duplicate = false;

		ArrayList<LeaderBoard_Entry> holder = new ArrayList<LeaderBoard_Entry>();
		try {
			holder = ((ArrayList<LeaderBoard_Entry>) ser.ReadToClass(fileName));
		} catch (Exception e) {
			// log error
			// LOGGER.warning("Initialization failed " + fileName +
			// " caught from serializer ReadToClass method \n\n\n" +
			// System.getProperties());

		}
		for (int i = 0; i < holder.size(); ++i) {
			// check for duplicate joke
			for (int j = 0; j < collectionScore.size(); ++j) {
				if (holder.get(i).equals(collectionScore.get(j))) {
					duplicate = true;
				}
			}
			if (!duplicate) {
				collectionScore.add(holder.get(i));
			} else {
				duplicate = false;
			}
		}
		//numberOfJokes = jokeList.size();
	}

	public boolean saveList(String fileName) {
		try {
			if (ser.DumpToFile(fileName, collectionScore)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			
		}
		return false;
		
	}
}
