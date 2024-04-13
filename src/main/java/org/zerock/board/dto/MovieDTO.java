package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String title;

    @Builder.Default //화면에 이미지들도 같이 수집을 해야 함 리스트 이용해서 수집
    //빌더로 인스턴스 생성시 초기화할 값을 정할수 있다.
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    //영화의 평균 평점
    private double avg ;

    //리뷰 수 jpa의 count()
    private int reviewCnt;
    
    private LocalDateTime regDate ;

    private LocalDateTime modDate ;

}