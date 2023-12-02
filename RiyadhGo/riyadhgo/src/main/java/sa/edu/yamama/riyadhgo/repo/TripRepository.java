package sa.edu.yamama.riyadhgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Trip;

public interface TripRepository extends JpaRepository<Trip,Long> {
    
}
