package management_app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;

	    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_management";
	    private static final String DB_USER = "postgres";
	    private static final String DB_PASSWORD = "Tanmay@2001";

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        int requestId = Integer.parseInt(request.getParameter("requestId"));
	        String action = request.getParameter("action"); // "Approve" or "Reject"

	        String status = action.equals("Approve") ? "Approved" : "Rejected";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            // Update the request status in the database
	            String sql = "UPDATE requests SET status = ? WHERE id = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, status);
	            stmt.setInt(2, requestId);
	            stmt.executeUpdate();

	            // Redirect back to the pending requests page with a success message
	            response.sendRedirect("pendingRequests.jsp?success=1");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("pendingRequests.jsp?error=1");
	        }
	    }
	

}
