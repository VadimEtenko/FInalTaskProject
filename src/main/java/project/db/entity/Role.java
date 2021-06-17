package project.db.entity;

/**
 *  Enum entity of types of roles
 *  of registered users
 *
 * @author V. Etenko
 *
 */

public enum Role {
    CLIENT,
    MANAGER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
