package sa.edu.yamama.riyadhgo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.TransportMethod;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;

public interface TransportMethodRepository extends JpaRepository<TransportMethod,Long> {
    
    public List<TransportMethod> findAllByMethodType(TransportMethodTypes type);
}
