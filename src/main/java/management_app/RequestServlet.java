package management_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet{
	 private static final long serialVersionUID = 1L;

	    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_management";
	    private static final String DB_USER = "postgres";
	    private static final String DB_PASSWORD = "Tanmay@2001";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        String username = (String) session.getAttribute("username");

	        // Ensure the user is logged in
	        if (username == null) {
	            response.sendRedirect("login.jsp?error=Please log in to continue");
	            return;
	        }

	        try {
	            // Validate input parameters
	            String softwareIdParam = request.getParameter("software");
	            String accessType = request.getParameter("accessType");
	            String reason = request.getParameter("reason");

	            if (softwareIdParam == null || softwareIdParam.isEmpty()) {
	                response.sendRedirect("requestAccess.jsp?error=Software selection is required");
	                return;
	            }
	            if (accessType == null || accessType.isEmpty()) {
	                response.sendRedirect("requestAccess.jsp?error=Access type is required");
	                return;
	            }
	            if (reason == null || reason.isEmpty()) {
	                response.sendRedirect("requestAccess.jsp?error=Reason for access is required");
	                return;
	            }

	            int softwareId = Integer.parseInt(softwareIdParam);

	            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	                // Get user ID based on username
	                String getUserSql = "SELECT id FROM users WHERE username = ?";
	                PreparedStatement getUserStmt = conn.prepareStatement(getUserSql);
	                getUserStmt.setString(1, username);
	                ResultSet rs = getUserStmt.executeQuery();

	                if (rs.next()) {
	                    int userId = rs.getInt("id");

	                    // Insert the access request into the `requests` table
	                    String insertRequestSql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
	                    PreparedStatement insertStmt = conn.prepareStatement(insertRequestSql);
	                    insertStmt.setInt(1, userId);
	                    insertStmt.setInt(2, softwareId);
	                    insertStmt.setString(3, accessType);
	                    insertStmt.setString(4, reason);
	                    insertStmt.executeUpdate();

	                    // Redirect to the same page with a success message
	                    response.sendRedirect("requestAccess.jsp?success=1");
	                } else {
	                    response.sendRedirect("requestAccess.jsp?error=User not found");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("requestAccess.jsp?error=Database error: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            response.sendRedirect("requestAccess.jsp?error=Invalid software ID");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("requestAccess.jsp?error=Unexpected error occurred: " + e.getMessage());
	        }
	    }

}
