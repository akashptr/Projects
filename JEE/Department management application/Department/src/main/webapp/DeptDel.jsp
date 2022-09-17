<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Delete Department</title>
	<style type="text/css">
		div{
			width: 25%;
			margin: auto;
			border: 1px solid black;
			padding: 20px;
		}
	</style>
</head>
<body>
	<div>
	<h1>Delete A Department</h1>
	<form action="dept_del" method="post">	<!-- dept_del is the servlet for deleting a department -->
		<table>
			<tr>
				<td>Department ID</td>
				<td><input name="dept_id" type="text" maxlength=5 /></td>
			</tr>
			<tr>
				<td></td>
				<td><button type="submit">Delete</button></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>