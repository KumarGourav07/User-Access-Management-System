<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Access</title>
</head>
<body>
    <h2>Request Software Access</h2>

    <!-- Form for requesting software access -->
    <form action="RequestServlet" method="post">
        <label for="software">Select Software:</label><br>
        <select id="software" name="software" required>
            <%-- Dynamically populate the software list --%>
            <%
                java.sql.Connection conn = null;
                java.sql.PreparedStatement stmt = null;
                java.sql.ResultSet rs = null;
                try {
                    Class.forName("org.postgresql.Driver");
                    conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_management", "postgres", "yourpassword");
                    String sql = "SELECT id, name FROM software";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int softwareId = rs.getInt("id");
                        String softwareName = rs.getString("name");
            %>
                        <option value="<%= softwareId %>"><%= softwareName %></option>
            <%
                    }
                } catch (Exception e) {
                    out.println("<option disabled>Error fetching software list</option>");
                } finally {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                }
            %>
        </select><br><br>

        <label for="accessType">Access Type:</label><br>
        <select id="accessType" name="accessType" required>
            <option value="Read">Read</option>
            <option value="Write">Write</option>
            <option value="Admin">Admin</option>
        </select><br><br>

        <label for="reason">Reason for Request:</label><br>
        <textarea id="reason" name="reason" rows="4" cols="50" required></textarea><br><br>

        <button type="submit">Submit Request</button>
    </form>

    <!-- Link to log out -->
    <br>
    <a href="logout.jsp">Log Out</a>
</body>
</html>
    