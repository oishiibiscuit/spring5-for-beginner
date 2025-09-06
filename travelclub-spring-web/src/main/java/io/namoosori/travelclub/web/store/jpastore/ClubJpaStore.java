package io.namoosori.travelclub.web.store.jpastore;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.store.ClubStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import io.namoosori.travelclub.web.store.jpastore.reository.ClubRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchClubException;

@Repository
public class ClubJpaStore implements ClubStore {

  private ClubRepository clubRepository;

  public ClubJpaStore(ClubRepository clubRepository) {
    this.clubRepository = clubRepository;
  }

  @Override
  public String create(TravelClub club) {
    clubRepository.save(new TravelClubJpo(club));
    return club.getId();
  }

  @Override
  public void delete(String clubId) {
    clubRepository.deleteById(clubId);
  }

  @Override
  public boolean exists(String clubId) {
    return clubRepository.existsById(clubId);
  }

  @Override
  public TravelClub retrieve(String clubId) {
    Optional<TravelClubJpo> clubJpo = clubRepository.findById(clubId);
    if (clubJpo.isEmpty()) {
      throw new NoSuchClubException(String.format("TravelClub(%s) is not found.", clubId));
    }
    return clubJpo.get().toDomain();
  }

  @Override
  public List<TravelClub> retrieveAll() {
    return clubRepository.findAll().stream().map(TravelClubJpo::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<TravelClub> retrieveByName(String name) {
    // return clubRepository.findAll().stream().filter(clubJpo -> clubJpo.getName().equals(name))
    // .map(TravelClubJpo::toDomain).collect(Collectors.toList());
    return clubRepository.findAllByName(name).stream().map(TravelClubJpo::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void update(TravelClub club) {
    clubRepository.save(new TravelClubJpo(club));
  }

}
