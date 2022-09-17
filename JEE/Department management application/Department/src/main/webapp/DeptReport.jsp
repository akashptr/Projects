<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="master.dao.DepartmentDao" %>
<%@ page import="master.dto.DepartmentDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Department Report</title>
<style type="text/css">
	#dept_details{
		margin: auto;
		font-size: 20px;
		border: 1px solid black;
		text-align: center;
	}
	#dept_details table{
		width: 100%;
	}
	#dept_details table thead{
		height: 70px;
		font-weight: bold;
	}
	#dept_details td{
		border: 1px solid black;
	}
	
</style>
</head>
<body>
	<div id="dept_details">
	<h1>Department Report</h1>
	<table>
		<thead>	<!-- Header of the table -->
			<tr>
				<td>Department ID</td>
				<td>Department Name</td>
				<td>Department Location</td>
				<td>Department Phone</td>
			</tr>
		</thead>
		
		<tbody>
		<%
			DepartmentDao dDao = new DepartmentDao(); // Dao object for database connection.
			ArrayList<DepartmentDto> result = dDao.getData();	// Getting all the departments as an array of dto objects
			for(DepartmentDto dDto : result)	// Fetching each dto object
			{
		%>
			<tr>	<!-- Showing the attributes of each dto object -->
				<td><%=dDto.getDept_id()%></td>
				<td><%=dDto.getDept_name()%></td>
				<td><%=dDto.getDept_loc()%></td>
				<td><%=dDto.getDept_phone()%></td>
			</tr>
		<%
			}
		%>
		</tbody>
	</table>
	<a href="index.jsp">Menu</a>	<!-- Link to return the home page -->
	</div>
</body>
</html>