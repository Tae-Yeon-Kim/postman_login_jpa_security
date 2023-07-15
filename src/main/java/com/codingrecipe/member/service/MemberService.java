package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor// 이게 있어야 MemberRepository 성공!
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    //이메일을 체크하는거다!

    /*public boolean checkuserIdExist(String userId) {
          boolean userIdDuplicate = memberRepository.existsByuserID(userId);
          return userIdDuplicate;

    }*/


    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO, passwordEncoder);
        memberRepository.save(memberEntity);

    }

    //////////////////////
    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단!
         */

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            System.out.println("~~~~~~~~~~");
            System.out.println("현재 db상에 아이디는 있어요! 비밀번호 확인과정들어갈께요!");
            System.out.println("현재 입력받은 id"+memberDTO.getMemberEmail());
            System.out.println(memberDTO.getMemberPassword());
            System.out.println(passwordEncoder.encode(memberDTO.getMemberPassword()));
            System.out.println("바로위 비밀번호 확인값!");
            System.out.println("~~~~~~~~~~");
            //조회 결과가 있다!(해당 이메일을 가진 회원 정보가 있다!!
            //.get() 을 이용해 optional 객체를 하나의 포장지를 벗겨낸것이다!

            MemberEntity memberEntity = byMemberEmail.get();
            if(encoder.matches(memberDTO.getMemberPassword(),memberEntity.getMemberPassword())){
                // 비밀번호 일치!
                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                System.out.println("현재 MemberService의 login 의 if 첫문장 즉 로그인 성공");
                return  dto;
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                System.out.println(memberEntity);
                System.out.println("현재 MemberService의 login 의 else  즉 로그인 실패");
                return null;
            }
        }
        else{
            //조회 결과가 없다 (해당 이메일을 가진 회원이 없다.)
            System.out.println("현재 db상에 아이디가 없습니다!");
            return null;
        }
    }

    public Optional<MemberEntity> findOne(String myEmail){
        return memberRepository.findByMemberEmail(myEmail);
    }



    ////////////////////
    // 과거 그 복후화 안사용하고 쓸때!
//    public MemberDTO login(MemberDTO memberDTO) {
//
//            1. 회원이 입력한 이메일로 DB에서 조회를 함
//            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단!
//
//
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
//        if(byMemberEmail.isPresent()){
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            System.out.println("~~~~~~~~~~");
//            System.out.println("현재 db상에 아이디는 있어요! 비밀번호 확인과정들어갈께요!");
//            System.out.println("현재 입력받은 id"+memberDTO.getMemberEmail());
//            System.out.println(memberDTO.getMemberPassword());
//            System.out.println(passwordEncoder.encode(memberDTO.getMemberPassword()));
//            System.out.println("바로위 비밀번호 확인값!");
//            System.out.println("~~~~~~~~~~");
//            //조회 결과가 있다!(해당 이메일을 가진 회원 정보가 있다!!
//            //.get() 을 이용해 optional 객체를 하나의 포장지를 벗겨낸것이다!
//
//            MemberEntity memberEntity = byMemberEmail.get();
//            if(memberEntity.getMemberPassword().equals(passwordEncoder.encode(memberDTO.getMemberPassword()))){
//                // 비밀번호 일치!
//                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//                System.out.println("현재 MemberService의 login 의 if 첫문장 즉 로그인 성공");
//                return  dto;
//            }
//            else{
//                // 비밀번호 불일치!!(로그인 실패!)
//                System.out.println(memberEntity);
//                System.out.println("현재 MemberService의 login 의 else  즉 로그인 실패");
//                return null;
//            }
//        }
//        else{
//            //조회 결과가 없다 (해당 이메일을 가진 회원이 없다.)
//            System.out.println("현재 db상에 아이디가 없습니다!");
//            return null;
//        }
//    }
//
//    public Optional<MemberEntity> findOne(String myEmail){
//        return memberRepository.findByMemberEmail(myEmail);
//    }

    /////////////////




}
