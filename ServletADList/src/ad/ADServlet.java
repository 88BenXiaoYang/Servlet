package ad;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import ad.DBUtil;

/**
 * Servlet implementation class ADServlet
 */
@WebServlet(description = "adDataList", urlPatterns = { "/home/ADServlet" })
public class ADServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ADServlet() {
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
			String sqlQuery = "select * from " + DBUtil.TABLE_AD;
			String commonImgUrl = ""; //资源在服务端的存储路径
			
			result = statement.executeQuery(sqlQuery);
			while (result.next()) {
				ADItem item = new ADItem();
				item.adID = "" + result.getInt("ad_id");
				item.adTitle = result.getString("ad_title");
				item.adSummary = result.getString("ad_summary");
				item.adContent = result.getString("ad_detailcontent");
				item.adImgUrl = commonImgUrl + result.getString("ad_img");
				item.adContentUrl = result.getString("ad_url");
				
				jsonobj.put("adTitle", item.adTitle);  
			    jsonobj.put("adSummary", 	item.adSummary);  
			    jsonobj.put("adContent", item.adContent);
			    jsonobj.put("adImgUrl", item.adImgUrl);
			    jsonobj.put("adContentUrl", item.adContentUrl);
			    
			    jsonarray.add(jsonobj);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
//	    //data
//	    JSONArray jsonarray = new JSONArray();
//	    JSONObject jsonobj = new JSONObject();
//	    
//	    String[] adIDList = {"ad00", "ad01", "ad02"};
//	    String[] adContentUrlList = {"adContentUrl00", "adContentUrl01", "adContentUrl02"};
//	    String[] imgUrlList = {"themeImage_00.png", "themeImage_01.png", "themeImage_02.png"};
//	    String commonImgUrl = "";  //资源在服务端的存储路径
//	    for (int i = 0; i < adIDList.length; i++) {
//			ADItem item = new ADItem();
//			item.adID = adIDList[i];
//			item.adImgUrl = commonImgUrl + imgUrlList[i];
//			item.adContentUrl = adContentUrlList[i];
//			
//			jsonobj.put("adID", item.adID);  
//		    jsonobj.put("adImgUrl", 	item.adImgUrl);  
//		    jsonobj.put("adContentUrl", item.adContentUrl);
//		    
//		    jsonarray.add(jsonobj);
//		}
	    
		String adJSONString = jsonarray.toString();
		PrintWriter out =null ;  
	    out =response.getWriter() ;  
	    out.write(adJSONString);  
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
