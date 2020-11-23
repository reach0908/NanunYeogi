package paastaproj.func1spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paastaproj.func1spring.domain.Member;
import paastaproj.func1spring.repositories.MeberRepository;
import paastaproj.func1spring.repositories.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;


//@Service
public class MemberService {

    private final MeberRepository meberRepository;

    //@Autowired
   public MemberService(MeberRepository meberRepository) {
        this.meberRepository = meberRepository;
    }

    /*
     *회원가입
     */
    public Long join(Member member){
        //비즈니스 로직에 따라 구현
        //1. 중복회원이 있으면 안된다.
        //null일 가능성이 있으면 optional 로 감싸는걸 추천한다.
        validateDuplicateMember(member);
        meberRepository.save(member);
        return member.getId();
    }

    //중복검사
    private void validateDuplicateMember(Member member) {
        meberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    /*
     *전체회원 조회
     */
    public List<Member> findMembers(){
        return meberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return meberRepository.findById(memberId);
    }
}
