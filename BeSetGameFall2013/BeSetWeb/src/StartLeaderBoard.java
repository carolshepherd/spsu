

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Application Lifecycle Listener implementation class StartLeaderBoard
 *
 */
public class StartLeaderBoard implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public StartLeaderBoard() {
        // TODO Auto-generated constructor stub
    }
    public static Leaderboard_List list;
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	
    	list = new Leaderboard_List();
    	list.loadList("LeaderBoard2.ser");
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	list.saveList("LeaderBoard2.ser");
    }
	
}
