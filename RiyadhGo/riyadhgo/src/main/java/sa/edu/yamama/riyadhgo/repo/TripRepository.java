package sa.edu.yamama.riyadhgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Trip;

//The interface allows the trip to be stored in the trip repository
public interface TripRepository extends JpaRepository<Trip,Long> {
    
}
