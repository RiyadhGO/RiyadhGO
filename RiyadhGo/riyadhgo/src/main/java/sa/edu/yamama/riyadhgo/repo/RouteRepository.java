package sa.edu.yamama.riyadhgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Route;

//The interface allows the route to be stored in the route repository

public interface RouteRepository extends JpaRepository<Route,Long> {
    
}
