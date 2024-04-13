package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")   //@ToString에서 exclude는 습관적으로 사용하는게 좋음. member객체 출력시 db연결이 필요. 지연로딩 시 필수.
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    //fetch는 lazy loading을 권장, Eager(즉시로딩) - 특정한 엔티티를 조회할 때 연관관계를 가진 모든 엔티티를 같이 로딩
    @ManyToOne(fetch = FetchType.LAZY) //명시적으로 Lazy(지연로딩) 로딩 지정
    private Member writer; //연관관계 지정

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}