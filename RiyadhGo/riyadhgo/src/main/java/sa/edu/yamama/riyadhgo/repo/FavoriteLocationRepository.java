package sa.edu.yamama.riyadhgo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.FavoriteLocation;

//The interface allows the user to add a favourite place to the repository, and connects each user ID to the favourite locations that they added.
public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {

    List<FavoriteLocation> findAllByUserId(Long userId);

}
