<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Software</title>
</head>
<body>
    <h2>Create New Software</h2>

    <!-- Form for creating software -->
    <form action="SoftwareServlet" method="post">
        <label for="name">Software Name:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="4" cols="50" required></textarea><br><br>

        <label for="access_levels">Access Levels:</label><br>
        <input type="checkbox" id="read" name="access_levels" value="Read">
        <label for="read">Read</label><br>

        <input type="checkbox" id="write" name="access_levels" value="Write">
        <label for="write">Write</label><br>

        <input type="checkbox" id="admin" name="access_levels" value="Admin">
        <label for="admin">Admin</label><br><br>

        <button type="submit">Create Software</button>
    </form>

    <!-- Link to go back or log out -->
    <br>
    <a href="logout.jsp">Log Out</a>
</body>
</html>
  