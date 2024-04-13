package org.zerock.board.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.board.entity.Mmember;

import java.util.stream.IntStream;

@SpringBootTest
public class MmemberRepositoryTests {

    @Autowired
    private MmemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMmembers() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Mmember mmember = Mmember.builder()
                    .email("r"+i +"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(mmember);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMmember() {

        Long mid = 1L; //Mmember의 mid

        Mmember mmember = Mmember.builder().mid(mid).build();

        //기존
        //memberRepository.deleteById(mid);
        //reviewRepository.deleteByMember(mmember);

        //순서 주의
        reviewRepository.deleteByMember(mmember);
        memberRepository.deleteById(mid);
    }


}