package management_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	 private static final long serialVersionUID = 1L;

	    // Database connection details
	    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_management";
	    private static final String DB_USER = "postgres";
	    private static final String DB_PASSWORD = "Tanmay@2001";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // Retrieve username and password from the login form
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            // Query to validate user credentials
	            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                // Get user role from the result set
	                String role = rs.getString("role");

	                // Start a session and set user attributes
	                HttpSession session = request.getSession();
	                session.setAttribute("username", username);
	                session.setAttribute("role", role);

	                // Redirect user based on their role
	                switch (role) {
	                    case "Employee":
	                        response.sendRedirect("requestAccess.jsp");
	                        break;
	                    case "Manager":
	                        response.sendRedirect("pendingRequests.jsp");
	                        break;
	                    case "Admin":
	                        response.sendRedirect("createSoftware.jsp");
	                        break;
	                    default:
	                        response.sendRedirect("login.jsp?error=Invalid role");
	                        break;
	                }
	            } else {
	                // Invalid credentials
	                response.sendRedirect("login.jsp?error=Invalid username or password");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("login.jsp?error=An error occurred. Please try again.");
	        }
	    }
	

}
