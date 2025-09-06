package io.namoosori.travelclub.spring.service.logic;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.service.MemberService;
import io.namoosori.travelclub.spring.service.sdo.MemberCdo;
import io.namoosori.travelclub.spring.shared.NameValueList;
import io.namoosori.travelclub.spring.store.MemberStore;
import io.namoosori.travelclub.spring.util.exception.MemberDuplicationException;
import io.namoosori.travelclub.spring.util.exception.NoSuchMemberException;

@Service("memberService")
public class MemberServiceLogic implements MemberService {

  private MemberStore memberStore;

  public MemberServiceLogic(MemberStore memberStore) {
    this.memberStore = memberStore;
  }

  @Override
  public CommunityMember findMemberByEmail(String memberEmail) {
    return memberStore.retrieveByEmail(memberEmail);
  }

  @Override
  public CommunityMember findMemberById(String memberId) {
    return memberStore.retrieve(memberId);
  }

  @Override
  public List<CommunityMember> findMembersByName(String name) {
    return memberStore.retrieveByName(name);
  }


  @Override
  public void modifyMember(String memberId, NameValueList member) {
    Optional<CommunityMember> foundMember = Optional.ofNullable(memberStore.retrieve(memberId));
    if (foundMember.isEmpty()) {
      throw new NoSuchMemberException("No such member with memberId : " + memberId);
    }
    foundMember.get().modifyValues(member);
    memberStore.update(foundMember.get());
  }

  @Override
  public String registerMember(MemberCdo member) {
    String email = member.getEmail();
    Optional<CommunityMember> foundMember = Optional.ofNullable(memberStore.retrieveByEmail(email));

    if (foundMember.isPresent()) {
      throw new MemberDuplicationException("Member already exist with email : " + email);
    }

    CommunityMember newMember =
        new CommunityMember(member.getEmail(), member.getName(), member.getPhoneNumber());
    newMember.setNickName(member.getNickName());
    newMember.setBirthDay(member.getBirthDay());

    newMember.checkValidation();
    return memberStore.create(newMember);
  }

  @Override
  public void removeMember(String memberId) {
    Optional<CommunityMember> foundMember = Optional.ofNullable(memberStore.retrieve(memberId));
    if (foundMember.isEmpty()) {
      throw new NoSuchMemberException("No such member with memberId : " + memberId);
    }
    memberStore.delete(memberId);
  }

}
