package com.codingrecipe.member.controller;
// 회원가입 로그인 요청처리 관련해서 작성!!

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    @Autowired
    private final MemberService memberService;



   /* // 회원가입 페이지 출력 요청!
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save~!~!~!~!");
        System.out.println("제발.. : "+memberDTO);
        memberService.save(memberDTO);
        return "index";
    }*/
   @GetMapping("/member/save")
   public String saveForm() {
       return "save";
   }

    @PostMapping("/member/save")
    public MemberDTO save(@RequestBody MemberDTO memberDTO) {
        System.out.println("MemberController.save~!~!~!~!");
        System.out.println("제발.. : " + memberDTO);
        //memberService.save(memberDTO);

        return memberDTO;
    }



    //아래 만든게 회원가입할때 id 복수 체크 부분
    @GetMapping("/member/save/{user_id}")
    public boolean checkIDExist(@PathVariable String user_id) {

        Optional<MemberEntity> memberEntity_cehckIDExist;
        memberEntity_cehckIDExist = memberService.findOne(user_id);
        //MemberEntity memberEntity_cehckIDExist = memberService.findOne(user_id).orElse(null);
        System.out.println(memberEntity_cehckIDExist);
        boolean check;
        if(memberEntity_cehckIDExist.isPresent()) {
            check = true;
        }
        else {
            check = false;
        }
        return check;


    }

    /*//아래 만든게 회원가입할때 id 복수 체크 부분
    @GetMapping("/member/save/{user_id}")
    public boolean checkIDExist(@PathVariable String user_id) {

       Optional<MemberEntity> memberEntity_cehckIDExist;
       memberEntity_cehckIDExist = memberService.findOne(user_id);
       //MemberEntity memberEntity_cehckIDExist = memberService.findOne(user_id).orElse(null);
        System.out.println(memberEntity_cehckIDExist);
        boolean check;
        if(memberEntity_cehckIDExist.isPresent())
            check = true;

        else
            check = false;

        return check;


    }*/



    @GetMapping("/member/login")
    public String login() {
        return "login22";
    }

    @PostMapping("/member/login")
    public boolean login(@RequestBody MemberDTO memberDTO) {
        System.out.println(memberDTO);


        MemberDTO loginResult = memberService.login(memberDTO);
        boolean check_login_correct;
        if (loginResult != null) {
            //login 성공!!

            check_login_correct = true;
        } else {
            // 실패!
            check_login_correct = false;

        }
        return check_login_correct;
    }




    //원본
    /*@GetMapping("/member/logindashboard")
    public String login22() {
        return "temp3";
    }*/

    @GetMapping("/member/logindashboard")
    public String login22(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("authorities", user.getAuthorities());
        model.addAttribute("loginId", user.getUsername());
        model.addAttribute("loginpassword", user.getPassword());

        System.out.println("authorities : "+user.getAuthorities());
        System.out.println("loginId : "+user.getUsername());
        System.out.println("loginpassword : "+user.getPassword());
        return "temp3";
    }


}






//@Controller
//@RequiredArgsConstructor
//public class MemberController {
//
//    // 생성자 주입
//    @Autowired
//    private final MemberService memberService;
//
//
//    // 회원가입 페이지 출력 요청!
//    @GetMapping("/member/save")
//    public String saveForm() {
//        return "save";
//    }
//
//    @PostMapping("/member/save")
//    public String save(@ModelAttribute MemberDTO memberDTO) {
//        System.out.println("MemberController.save~!~!~!~!");
//        System.out.println("제발.. : "+memberDTO);
//        memberService.save(memberDTO);
//        return "index";
//    }
//@GetMapping("/member/save")
//public String saveForm() {
//    return "save";
//}
//
//    @PostMapping("/member/save")
//    public MemberDTO save(@ModelAttribute MemberDTO memberDTO) {
//        System.out.println("MemberController.save~!~!~!~!");
//        System.out.println("제발.. : " + memberDTO);
//        memberService.save(memberDTO);
//
//        return memberDTO;
//    }
//
//    @GetMapping("/member/login")
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/member/login")
//    public String login(@ModelAttribute MemberDTO memberDTO) {
//        MemberDTO loginResult = memberService.login(memberDTO);
//
//        if (loginResult != null) {
//            //login 성공!!
//
//            return "temp2";
//        } else {
//            // 실패!
//            return "login";
//
//        }
//    }
//
//    //원본
//    @GetMapping("/member/logindashboard")
//    public String login22() {
//        return "temp3";
//    }
//
//    @GetMapping("/member/logindashboard")
//    public String login22(@AuthenticationPrincipal User user, Model model) {
//        model.addAttribute("authorities", user.getAuthorities());
//        model.addAttribute("loginId", user.getUsername());
//        model.addAttribute("loginpassword", user.getPassword());
//
//        System.out.println("authorities : "+user.getAuthorities());
//        System.out.println("loginId : "+user.getUsername());
//        System.out.println("loginpassword : "+user.getPassword());
//        return "temp3";
//    }
//
//
//}


