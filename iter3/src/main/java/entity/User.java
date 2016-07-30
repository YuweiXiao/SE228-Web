package entity;

public class User {
	private int userID;
	private String username;
	private String password;
	private String name;
	private Role role;
	private String profileID;
	
	private String gender;
	private String e_mail;
	private String birthday;
	private String job;
	private String income;

	private String role_name;
	private String imageID;
	
	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", password=" + password + ", name=" + name + ", role="
				+ role + ", profileID=" + profileID + ", gender=" + gender
				+ ", e_mail=" + e_mail + ", role_name=" + role_name
				+ ", birthday=" + birthday + ", job=" + job + ", income="
				+ income + "]";
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

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getProfileID() {
		return profileID;
	}

	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	
}
