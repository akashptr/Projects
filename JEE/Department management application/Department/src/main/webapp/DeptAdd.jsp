<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Department</title>
<style type="text/css">
	#add_form{
		width: 30%;
		margin: auto;
		border: 1px solid black;
		padding: 20px;
		font-size: 20px;
	}
</style>
</head>
<body>
	<div id="add_form">
		<h1>Department Add Form</h1>
		<form action="dept_add" method="post">
			<table>
				<tr>
					<td>Department ID</td>
					<td><input name="deptid" type="text" maxlength=5
						placeholder="Max 5 characters" /></td>	<!-- deptid is varchar(5) -->
				</tr>
				<tr>
					<td>Department Name</td>
					<td><input name="deptname" type="text" maxlength=30
						placeholder="Max 30 character" /></td>	<!-- deptname is varchar(30) -->
				</tr>
				<tr>
					<td>Location</td>
					<td><input name="deptloc" type="text" maxlength=20
						placeholder="Max 20 character" /></td>	<!-- deptloc is varchar(5) -->
				</tr>
				<tr>
					<td>Phone</td>
					<td><input name="deptphone" type="text"
						pattern="[1-9]{1}[0-9]{9}" title="Exactly 10 digits"
						placeholder="Max 10 digits" /></td>	<!-- pattern signifies that user should give a 10 digit number -->
				</tr>
				<tr>
					<td></td>
					<td>
						<button type="submit">Add</button>
						<button type="reset">Reset</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>