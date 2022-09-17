<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Home</title>
	<style type="text/css">
		#home_links {
			width: 25%;
			margin: auto;
			border: 1px solid black;
			padding: 20px;
			text-align: center;
		}
		#home_links a{
			font-size: 20px;
		}
	</style>
</head>
<body>
	<div id="home_links">
		<h1>Home Page</h1>
		<a href="DeptAdd.jsp">Add Department</a>	<!-- Link to add a department -->
		<br />
		<a href="DeptDel.jsp">Delete Department</a>	<!-- Link to delete a department -->
		<br />
		<a href="DeptReport.jsp">Show Data</a>	<!-- Link to show all the departments -->
	</div>
</body>
</html>