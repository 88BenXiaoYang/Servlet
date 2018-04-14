package homepage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import homepage.DBUtil;

/**
 * Servlet implementation class HomepageServlet
 */
@WebServlet(
		description = "HomepageListData", 
		urlPatterns = { "/home/HomepageServlet" }, 
		initParams = { 
				@WebInitParam(name = "pageNumber", value = "pageNum", description = "页码")
		})
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final String JSON = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomepageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");  
	    response.setContentType("text/html;charset=utf-8");  
	    response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式  
	    response.setCharacterEncoding("UTF-8");//setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题 
	    
	    //data
	    JSONArray jsonarray = new JSONArray();
	    JSONObject jsonobj = new JSONObject();
	    
	    try {
			Connection connect = DBUtil.getConnect();
			Statement statement = (Statement) connect.createStatement();
			ResultSet result;
			String sqlQuery = "select * from " + DBUtil.TABLE_PRODUCT;
			String commonImgUrl = "";
			
			result = statement.executeQuery(sqlQuery);
			while (result.next()) {
				HomePageItem item = new HomePageItem();
				item.title = result.getString("pro_title");
				item.summary = result.getString("pro_summary");
				item.detailContent = result.getString("pro_content");
				item.imgUrl = commonImgUrl + result.getString("pro_img");
				
				jsonobj.put("title", item.title);  
			    jsonobj.put("summary", 	item.summary);  
			    jsonobj.put("detailContent", item.detailContent);
			    jsonobj.put("imgUrl", item.imgUrl);
			    
			    jsonarray.add(jsonobj);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
//	    String[] titleList = {};
//	    String[] summaryList = {};
//	    String[] detailContentList = {
//	    		};
//	    String[] imgUrlList = {"themeImage_00.png", "themeImage_01.png", "themeImage_02.png"};
//	   
//	    for (int i = 0; i < titleList.length; i++) {
//			HomePageItem item = new HomePageItem();
//			item.title = titleList[i];
//			item.summary = summaryList[i];
//			item.detailContent = detailContentList[i];
//			item.imgUrl = commonImgUrl + imgUrlList[i];
//			
//			jsonobj.put("title", item.title);  
//		    jsonobj.put("summary", 	item.summary);  
//		    jsonobj.put("detailContent", item.detailContent);
//		    jsonobj.put("imgUrl", item.imgUrl);
//		    
//		    jsonarray.add(jsonobj);
//		}
	    
		String homepageJSONString = jsonarray.toString();
		PrintWriter out =null ;  
	    out =response.getWriter() ;  
	    out.write(homepageJSONString);  
//	    out.println(jsonarray);
	    out.close();
		
	    //test json data
//	    String jsonStr ="{\"id\":\"123\",\"name\":\"小黎\"}";  
//	    PrintWriter out =null ;  
//	    out =response.getWriter() ;  
//	    out.write(jsonStr);  
//	    out.close(); 
	    
	    //test log
//		response.getWriter().append("\n初始化参数，页码 pageNum = " + getInitParameter("pageNumber"));
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
