package demo.demoJWT.service.impl;

import demo.demoJWT.model.Role;
import demo.demoJWT.repository.RoleRepository;
import demo.demoJWT.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllByID(Long id) {
        return roleRepository.findAllById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public <S extends Role> S saveAndFlush(S entity) {
        return roleRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Role> S save(S entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Optional<Role> findById(Long aLong) {
        return roleRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return roleRepository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        roleRepository.deleteById(aLong);
    }
}
