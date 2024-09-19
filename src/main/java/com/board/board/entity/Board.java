package com.board.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

 // DB에 있는 테이블을 의미.
@Entity
@Data // 데이터를 받아오기 위함. lombok
public class Board { // DB 테이블 이름과 일치시키는게 좋음.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이덴티티는 maria db거.
    private Integer id;
    private String title;
    private String content;
    
}
