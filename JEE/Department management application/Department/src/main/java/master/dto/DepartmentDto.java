package master.dto;

// DTO class for creating transferable objects.

public class DepartmentDto {
	private String dept_id;
	private String dept_name;
	private String dept_loc;
	private String dept_phone;
	
	public DepartmentDto() {
		this.dept_id = null;
		this.dept_name = null;
		this.dept_loc = null;
		this.dept_phone = null;
	}
	
	public DepartmentDto(String dept_id, String dept_name, String dept_loc, String dept_phone) {
		this.dept_id = dept_id;
		this.dept_name = dept_name;
		this.dept_loc = dept_loc;
		this.dept_phone = dept_phone;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_loc() {
		return dept_loc;
	}
	public void setDept_loc(String dept_loc) {
		this.dept_loc = dept_loc;
	}
	public String getDept_phone() {
		return dept_phone;
	}
	public void setDept_phone(String dept_phone) {
		this.dept_phone = dept_phone;
	}
}
