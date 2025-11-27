package preproject.spring_boot_security.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import preproject.spring_boot_security.model.Role;
import preproject.spring_boot_security.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + id + " not found"));
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role " + name + " not found"));
    }

    @Override
    public Set<Role> getRolesByIds(List<Long> ids) {
        Set<Role> roles = new HashSet<>();
        if (ids == null || ids.isEmpty()) {
            return roles;
        }
        for (Long id : ids) {
            roleRepository.findById(id).ifPresent(roles::add);
        }
        return roles;
    }
}