<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign-Up</title>
</head>
<body>
    <h2>Sign-Up</h2>
    <form action="<%=application.getContextPath()%>/SignUpServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <!-- Hidden field for default role -->
        <input type="hidden" name="role" value="Employee">
        
        <button type="submit">Sign Up</button>
    </form>
</body>
</html>
    