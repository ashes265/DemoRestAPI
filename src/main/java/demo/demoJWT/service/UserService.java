package demo.demoJWT.service;

import demo.demoJWT.model.Role;
import demo.demoJWT.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();

    Optional<User> findById(Long aLong);
}
