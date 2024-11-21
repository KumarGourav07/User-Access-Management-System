<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Requests</title>
</head>
<body>
    <h2>Pending Access Requests</h2>

    <!-- Table to display pending requests -->
    <table border="1" cellpadding="10">
        <thead>
            <tr>
                <th>Request ID</th>
                <th>Employee Name</th>
                <th>Software Name</th>
                <th>Access Type</th>
                <th>Reason</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% 
                java.sql.Connection conn = null;
                java.sql.PreparedStatement stmt = null;
                java.sql.ResultSet rs = null;
                try {
                    Class.forName("org.postgresql.Driver");
                    conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_management", "postgres", "yourpassword");

                    // Query to fetch pending requests with employee and software details
                    String sql = "SELECT r.id, u.username AS employee_name, s.name AS software_name, r.access_type, r.reason " +
                                 "FROM requests r " +
                                 "JOIN users u ON r.user_id = u.id " +
                                 "JOIN software s ON r.software_id = s.id " +
                                 "WHERE r.status = 'Pending'";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        int requestId = rs.getInt("id");
                        String employeeName = rs.getString("employee_name");
                        String softwareName = rs.getString("software_name");
                        String accessType = rs.getString("access_type");
                        String reason = rs.getString("reason");
            %>
                        <tr>
                            <td><%= requestId %></td>
                            <td><%= employeeName %></td>
                            <td><%= softwareName %></td>
                            <td><%= accessType %></td>
                            <td><%= reason %></td>
                            <td>
                                <!-- Approve Button -->
                                <form action="ApprovalServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="requestId" value="<%= requestId %>">
                                    <input type="hidden" name="action" value="Approve">
                                    <button type="submit">Approve</button>
                                </form>
                                <!-- Reject Button -->
                                <form action="ApprovalServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="requestId" value="<%= requestId %>">
                                    <input type="hidden" name="action" value="Reject">
                                    <button type="submit">Reject</button>
                                </form>
                            </td>
                        </tr>
            <% 
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<tr><td colspan='6'>Error fetching requests</td></tr>");
                } finally {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                }
            %>
        </tbody>
    </table>

    <!-- Link to log out -->
    <br>
    <a href="logout.jsp">Log Out</a>
</body>
</html>
    