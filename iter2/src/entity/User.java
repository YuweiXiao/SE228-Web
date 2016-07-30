package entity;

public class User {
	private int userID;
	private String username;
	private String password;
	private String name;
	private int gender;
	private String e_mail;
	private Role role;
	private String role_name;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}




	public User(int userID, String username, String password, String name,
			int gender, String e_mail, Role role, String role_name) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.e_mail = e_mail;
		this.role = role;
		this.role_name = role_name;
	}


	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", password=" + password + ", name=" + name + ", gender="
				+ gender + ", e_mail=" + e_mail + ", role=" + role
				+ ", role_name=" + role_name + "]";
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
		role_name = role.getName();
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
}
