package sa.edu.yamama.riyadhgo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.User;

//The interface allows the user to be stored in the user repository and connects it to the email signed by the user
public interface UserRepository extends JpaRepository<User,Long> {
    
    public Optional<User> findOneByEmail(String email);
}
