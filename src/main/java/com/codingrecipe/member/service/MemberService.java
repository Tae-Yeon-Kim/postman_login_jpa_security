package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor// 이게 있어야 MemberRepository 성공!
public class MemberService {

    private final MemberRepository memberRepository;



    //블러그 보고 따라하기!!

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
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
            //조회 결과가 있다!(해당 이메일을 가진 회원 정보가 있다!!
            //.get() 을 이용해 optional 객체를 하나의 포장지를 벗겨낸것이다!

            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호 일치!
                // 처음에 시작을 MemberDTO 라 했으니! entity -> dto 로 변환 후 리턴하는 과정이 필요!!
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                System.out.println("현재 MemberService의 login 의 if 첫문장 즉 로그인 성공");
                return  dto;
            }
            else{
                // 비밀번호 불일치!!(로그인 실패!)
                System.out.println("현재 MemberService의 login 의 else  즉 로그인 실패");
                return null;
            }
        }
        else{
            //조회 결과가 없다 (해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }
    ////////////////////
}
