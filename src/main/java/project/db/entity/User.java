package project.db.entity;

/**
 *
 * Entity of user
 *
 * @author V.Etenko
 */

public class User extends Entity{

    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private int roleId;
    private String locale;

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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

	@Override
	public String toString(){
		return "User [name=" + name + 
		", surname=" + surname +
		", login=" + login + 
		", password=" + password + 
		", email=" + email + 
		", roleId=" + roleId +
        ", local=" + locale +
        "];";
	}

    public static class Builder implements BuilderInterface{
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder withName(String name){
            user.name = name;
            return this;
        }

        public Builder withSurname(String surname){
            user.surname = surname;
            return this;
        }

        public Builder withLogin(String login){
            user.login = login;
            return this;
        }

        public Builder withPassword(String password){
            user.password = password;
            return this;
        }

        public Builder withEmail(String email){
            user.email = email;
            return this;
        }

        public Builder withRoleId(int roleId) {
            user.roleId = roleId;
            return this;
        }

        public Builder withLocale(String locale){
            user.locale = locale;
            return this;
        }

        public Builder withId(Long id) {
            user.setId(id);
            return this;
        }

        public User build(){
            return user;
        }

    }
}

