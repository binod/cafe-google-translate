package my.test;

import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.cafe.api.CommunicationSession;
import org.glassfish.cafe.api.Context;

public class ExampleServlet extends HttpServlet{        
    
    private static HashMap<String, String> lMap =
            new HashMap<String, String>();

    @Context CommunicationSession session;

    @Override
    protected void doGet(HttpServletRequest request,
    HttpServletResponse response) throws ServletException,
    java.io.IOException {
        
        java.io.PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        String language = request.getParameter("language");
        try {
            //Ideally this should be saved in some persistent
            //database.            
            lMap.put(user, language);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {            
            out.close();
        }        
    }

    public static String getLanguage(String user ) {
        String lang = lMap.get(user);
        return lang == null ? "en" : lang;
    }
}
