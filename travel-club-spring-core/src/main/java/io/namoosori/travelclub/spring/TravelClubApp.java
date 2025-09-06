package io.namoosori.travelclub.spring;

import java.sql.Date;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.aggregate.club.TravelClub;
import io.namoosori.travelclub.spring.service.ClubService;
import io.namoosori.travelclub.spring.service.MemberService;
import io.namoosori.travelclub.spring.service.sdo.MemberCdo;
import io.namoosori.travelclub.spring.service.sdo.TravelClubCdo;
import lombok.var;

public class TravelClubApp {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    // String[] beanNames = context.getBeanDefinitionNames();
    // System.out.println(Arrays.toString(beanNames));

    // TravelClubCdo clubCdo = new TravelClubCdo("TravelClub", "Test TravelClub");
    // ClubService clubService = context.getBean("clubService", ClubService.class);

    // String clubId = clubService.registerClub(clubCdo);
    // TravelClub foundClub = clubService.findClubById(clubId);
    // System.out.println("Club name : " + foundClub.getName());
    // System.out.println("Club intro : " + foundClub.getIntro());
    // System.out.println("Club FoundationTime : " + new Date(foundClub.getFoundationTime()));

    MemberService memberService = context.getBean("memberService", MemberService.class);
    String memberId = memberService.registerMember(
        new MemberCdo("test@nextree.io", "Kim", "Test Member", "010-0000-0000", "2010.10.10"));

    CommunityMember foundMember = memberService.findMemberById(memberId);
    System.out.println(foundMember.toString());
  }

}
