import java.io.Serializable;


public class LeaderBoard_Entry implements Serializable {

private String Name;
private String Score;

public LeaderBoard_Entry(String n, String s){
setName(n);
setScore(s);
}
public LeaderBoard_Entry(){}

public void setName(String name) {
this.Name = name;
}

public void setScore(String score) {
this.Score = score;
}

public String getName() {
return Name;
}

public String getScore() {
return Score;
}

public String toString(){
	return Name + ": " + Score;
}


}