# 수강신청시스템
데이터베이스시스템을 이용해서 다음 기능을 갖춘 수강신청시스템을 구현하도록 한다.

- 시연 영상(https://www.youtube.com/watch?v=wX6m8qUzI6A)
![img3](/img/img3.png)

# UML : Use case diagram
![img1](/img/img1.png)
- 지속적인 transacation이 가능한 인터페이스
- 수강편람기능(강의조회)
- 사용자 로그인
- 수강신청 기능
- 설강 및 폐강

"학생이 강의시간이 겹치는 수업을 수강신청 하거나 동일한 교사가 같은시간에 2개이상의 강의를 가진다. 또는 강의실 한개에 같은시간대에 2개 이상의 수업이 이루어 진다." 이러한 불가능한 상황에 대해서 데이터베이스 디자인을 통해(Primary Key, 제약조건설정)을 통해 신뢰성인은 DB를 구축한다.

## Conceptual database design
![img2](/img/img2.png)

