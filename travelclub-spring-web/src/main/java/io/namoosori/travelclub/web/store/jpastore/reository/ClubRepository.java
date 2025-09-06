package io.namoosori.travelclub.web.store.jpastore.reository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;

public interface ClubRepository extends JpaRepository<TravelClubJpo, String> {

  public List<TravelClubJpo> findAllByName(String name);

}
