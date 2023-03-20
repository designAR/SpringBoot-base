package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.repository.MemoryMemberRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    // 회원가입 테스트
    @Test
    void join() {
        // 문법 given, when, then
        // given : Member 객체를 생성하고 이름을 "hello"로 정의한다.
        Member member = new Member();
        member.setName("hello");

        //when
        // MemberService클래스의 join메서드를 호출하고 반환된 값을 savaId 변수에 저장한다.
        Long savaId = memberService.join(member);

        //then
        // 저장된 ID값을 사용하여 memberService의 findOne메서드를 호출하여 Member객체를 검색하고
        // 검색된 Member객체의 이름과 기존 Member객체의 이름이 같은지를 비교한다.
        Member findMember = memberService.findOne(savaId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void duplicatedException() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}