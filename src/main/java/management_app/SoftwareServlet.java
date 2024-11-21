package management_app;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.StringJoiner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;

	    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_management";
	    private static final String DB_USER = "postgres";
	    private static final String DB_PASSWORD = "Tanmay@2001";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String name = request.getParameter("name");
	        String description = request.getParameter("description");

	        // Combine all selected access levels into a single string
	        String[] accessLevels = request.getParameterValues("access_levels");
	        StringJoiner accessLevelsJoiner = new StringJoiner(", ");
	        if (accessLevels != null) {
	            for (String accessLevel : accessLevels) {
	                accessLevelsJoiner.add(accessLevel);
	            }
	        }
	        String accessLevelsString = accessLevelsJoiner.toString();

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, name);
	            stmt.setString(2, description);
	            stmt.setString(3, accessLevelsString);
	            stmt.executeUpdate();

	            // Redirect to the same page with a success message
	            response.sendRedirect("createSoftware.jsp?success=1");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("createSoftware.jsp?error=1");
	        }
	    }
	
	

}
