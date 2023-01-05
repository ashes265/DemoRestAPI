package demo.demoJWT.service;

import demo.demoJWT.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAllByID(Long id);

    List<Role> findAll();

    <S extends Role> S saveAndFlush(S entity);

    <S extends Role> S save(S entity);

    Optional<Role> findById(Long aLong);

    boolean existsById(Long aLong);

    void deleteById(Long aLong);
}
