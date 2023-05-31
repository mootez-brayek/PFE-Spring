package Pi.Spring.Repositury;

import Pi.Spring.Entity.Role;
import Pi.Spring.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByLibelle(String libelle);
}
