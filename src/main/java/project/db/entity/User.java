package project.db.entity;

public class User extends Entity{

    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private int roleId;
    private String local;

    public User(String name, String surname, String login,
                String password, String email, int roleId, String local) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.local = local;
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

	@Override
	public String toString(){
		return "User [name=" + name + 
		", surname=" + surname +
		", login=" + login + 
		", password=" + password + 
		", email=" + email + 
		", roleId=" + roleId + "];";
	}
}
