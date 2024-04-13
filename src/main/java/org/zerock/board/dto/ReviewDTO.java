package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long reviewnum; //review num

    private Long mno;   //Movie mno

    private Long mid;   //Mmembmer id

    private String nickname;    //Mmember nickname

    private String email;   //Mmember email

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;


}
