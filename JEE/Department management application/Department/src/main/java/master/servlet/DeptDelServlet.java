package master.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import master.dao.DepartmentDao;
import master.dto.DepartmentDto;

public class DeptDelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the department id from the user, provided in DeptDel.jsp.
		resp.setContentType("text/html");
		String deptId = req.getParameter("dept_id");
		DepartmentDto dDto = new DepartmentDto();
		dDto.setDept_id(deptId);
		
		try {
			// Create a DAO object and fire the delete query using deleteData() method.
			DepartmentDao dDao=new DepartmentDao();
			dDao.deleteData(dDto);
			resp.sendRedirect("DeptReport.jsp");
		}
		catch(SQLException exp)
		{
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('Id not found');</script>");
		}
		
	}
}
