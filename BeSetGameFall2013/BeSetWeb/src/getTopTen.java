

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class getTopTen
 */
public class getTopTen extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getTopTen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    String output = "";
		//ObjectMapper mapper = new ObjectMapper();
		try {
			//StartLeaderBoard.list.loadList("LeaderBoardList.ser");
			output = StartLeaderBoard.list.toStringTopTen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//list.loadList("LeaderBoardList.ser");
		
		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    String output = "";
		//ObjectMapper mapper = new ObjectMapper();
		try {
			output = StartLeaderBoard.list.toStringTopTen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(output);
	}

}
