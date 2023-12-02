package sa.edu.yamama.riyadhgo.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Location;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;

public interface LocationRepository extends JpaRepository<Location,Long> {
    
    public List<Location> findAllByLocationType(TransportMethodTypes type);
}
