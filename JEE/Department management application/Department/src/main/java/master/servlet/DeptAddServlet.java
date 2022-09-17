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

public class DeptAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Fetch the user-provided data from the DeptAdd.jsp page.
		resp.setContentType("text/html");
		String deptId = req.getParameter("deptid");
		String deptName = req.getParameter("deptname");
		String deptLoc = req.getParameter("deptloc");
		String deptPhone = req.getParameter("deptphone");
		
		// Create a DTO object to transfer it to the DAO.
		DepartmentDto dDto=new DepartmentDto();
		dDto.setDept_id(deptId);
		dDto.setDept_name(deptName);
		dDto.setDept_loc(deptLoc);
		dDto.setDept_phone(deptPhone);
		
		
		try {
			// Create a DAO object and insert the DTO object to the DB.
			DepartmentDao dDao=new DepartmentDao();
			dDao.insertData(dDto);
			
			// Redirect to DeptReport after insertion.
			resp.sendRedirect("DeptReport.jsp");
		}
		catch(SQLException exp)
		{
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('Database constraint violated');</script>");
		}
	}
	

}
