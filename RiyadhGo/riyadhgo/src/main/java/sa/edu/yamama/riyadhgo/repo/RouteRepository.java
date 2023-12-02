package sa.edu.yamama.riyadhgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.Route;

public interface RouteRepository extends JpaRepository<Route,Long> {
    
}
