package sa.edu.yamama.riyadhgo.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Location;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;

//The interface adds the location to the location repository and connects each location to the transport method type
public interface LocationRepository extends JpaRepository<Location,Long> {
    
    public List<Location> findAllByLocationType(TransportMethodTypes type);
}
