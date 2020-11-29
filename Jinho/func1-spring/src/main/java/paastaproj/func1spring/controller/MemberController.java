package paastaproj.func1spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import paastaproj.func1spring.domain.Member;
import paastaproj.func1spring.service.MemberService;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;
    //컴포넌트 스캔방식의 DI , 그 중에서도 생성자 주입입
    //@Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //홈화면에서 회원가입 누를시
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //회원등록시
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    //회원목록 누를시
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
