package sa.edu.yamama.riyadhgo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.TransportMethod;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;

//The interface adds the transport method to the transport method repository and connects each method to the type
public interface TransportMethodRepository extends JpaRepository<TransportMethod,Long> {
    
    public List<TransportMethod> findAllByMethodType(TransportMethodTypes type);
}
