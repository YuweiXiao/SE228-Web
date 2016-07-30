package entity;


public class Role {
	private int roleID;
	private String name;
	
	
	@Override
	public String toString() {
		return "Role [roleID=" + roleID + ", name=" + name + "]";
	}
	
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
