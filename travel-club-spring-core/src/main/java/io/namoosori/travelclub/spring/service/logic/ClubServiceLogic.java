package io.namoosori.travelclub.spring.service.logic;

import java.util.List;
import org.springframework.stereotype.Service;
import io.namoosori.travelclub.spring.aggregate.club.TravelClub;
import io.namoosori.travelclub.spring.service.ClubService;
import io.namoosori.travelclub.spring.service.sdo.TravelClubCdo;
import io.namoosori.travelclub.spring.shared.NameValueList;
import io.namoosori.travelclub.spring.store.ClubStore;
import io.namoosori.travelclub.spring.util.exception.NoSuchClubException;

@Service("clubService")
public class ClubServiceLogic implements ClubService {

  private ClubStore clubStore;

  public ClubServiceLogic(ClubStore clubStore) {
    this.clubStore = clubStore;
  }

  @Override
  public TravelClub findClubById(String id) {
    return clubStore.retrieve(id);
  }

  @Override
  public List<TravelClub> findClubsByName(String name) {
    return clubStore.retrieveByName(name);
  }

  @Override
  public void modify(String clubId, NameValueList nameValues) {
    TravelClub foundClub = clubStore.retrieve(clubId);
    if (foundClub == null) {
      throw new NoSuchClubException("No such club with id: " + clubId);
    }
    foundClub.modifyValues(nameValues);
    clubStore.update(foundClub);
  }

  @Override
  public String registerClub(TravelClubCdo club) {
    TravelClub newClub = new TravelClub(club.getName(), club.getIntro());
    newClub.checkValidation();
    return clubStore.create(newClub);
  }

  @Override
  public void remove(String clubId) {
    if (!clubStore.exists(clubId)) {
      throw new NoSuchClubException("No such club with id: " + clubId);
    }
    clubStore.delete(clubId);
  }

}
