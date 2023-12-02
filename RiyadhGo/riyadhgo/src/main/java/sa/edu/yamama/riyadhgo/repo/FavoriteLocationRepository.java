package sa.edu.yamama.riyadhgo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.edu.yamama.riyadhgo.domain.FavoriteLocation;

public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {

    List<FavoriteLocation> findAllByUserId(Long userId);

}
