package jpabook.jpashop.controller;

import java.util.List;
import javax.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { //실무에서는 요구 사항이 복잡하기 때문에.. 필요한 데이터만 채워서 entity가 아닌 form 또는 DTO로 받기 entity는 순수하게 유지, 화면 로직은 없어야

        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers(); //지금은 심플한 상태, 복잡해지면 DTO로 바꿔서 화면으로 보내야, API만들땐 절대 entity 반환하면 안됨
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
