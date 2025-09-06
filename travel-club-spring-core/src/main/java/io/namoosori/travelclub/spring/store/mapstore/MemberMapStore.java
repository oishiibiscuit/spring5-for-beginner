package io.namoosori.travelclub.spring.store.mapstore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.store.MemberStore;

@Repository("memberStore")
public class MemberMapStore implements MemberStore {

  private Map<String, CommunityMember> memberMap;

  public MemberMapStore() {
    this.memberMap = new LinkedHashMap<>();
  }

  @Override
  public String create(CommunityMember member) {
    memberMap.put(member.getId(), member);
    return member.getId();
  }

  @Override
  public void delete(String memberId) {
    memberMap.remove(memberId);
  }

  @Override
  public boolean exists(String memberId) {
    // return memberMap.containsKey(memberId);
    return Optional.ofNullable(memberMap.get(memberId)).isPresent();
  }

  @Override
  public CommunityMember retrieve(String memberId) {
    return memberMap.get(memberId);
  }

  @Override
  public CommunityMember retrieveByEmail(String email) {
    Optional<CommunityMember> foundMember =
        memberMap.values().stream().filter(member -> member.getEmail().equals(email)).findFirst();
    return foundMember.orElse(null);
  }

  @Override
  public List<CommunityMember> retrieveByName(String name) {
    return memberMap.values().stream().filter(member -> member.getName().equals(name))
        .collect(Collectors.toList());
  }

  @Override
  public void update(CommunityMember member) {
    memberMap.replace(member.getId(), member);
  }

}
