package sa.edu.yamama.riyadhgo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
    public Optional<User> findOneByEmail(String email);
}
