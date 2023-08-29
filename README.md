# TEAM Project [multiplexShop]

### 참여 인원

- 권동혁 : https://github.com/kwon93
- 서정국 : https://github.com/wjdrnrdl97

---

### 목표 사항

- 다목적상품을 판매하는 쇼핑몰 웹사이트 제작하기.
- SpringBoot FrameWork 구조 와 웹 사이트 구조 이해
- JPA 기초 원리 구조 이해
- 단위 테스트를 통한 테스트 코드 이해
- 협업을 위한 git skill up 및 커뮤니케이션 능력 학습 (Slack 등등..)
  

### 제작 기간

- ***17 August 2023 ~ 30 September 2023***

---

1. **패키지 구조 :**
    - Domain→ Spring MVC [controller, dto entity,repository , service ]
2. **네이밍 규칙 :** 
    - JAVA Method,Class : CamelCase , DB : snake_case (lowercase)
    - REST API
        - C:
            - Post 요청으로 생성되는 기능은 `postXXX()`
        - R:
            - Get요청으로 제공되는 기능은 `getXXX()`
        - U
            - Put 요청으로 갱신이 필요한 기능은 `updateXXX()`
        - D
            - Delete 요청으로 삭제가 필요한 기능은 `deleteXXX()`
            
    - DTO
        - Entity name + DTO
        - Request에 필요한 DTO일 경우 ~~ + RequestDTO
        - Response에 필요한 DTO일 경우 ~~ + ResponseDTO
        - ex) : Member DTO일 경우
            
            ```jsx
            public class MemberDTOs {
                
                public static class MemberRequestDTO {
            	        //.......
            
                }
            
                public static class MemberResponseDTO {
            					//.....
                    }
                }
            }
            ```
            

- Entity
    - Entity Diagram 참고해서 naming

- Domain Root URL :
    - [backendshop.com](http://backend.shop.com/)/
- Mapping 경로 :
    - [backendshop.com](http://backend.shop.com/)/  : 메인 페이지
    - [backendshop.com](http://backend.shop.com/)/support  : 고객센터 글 목록 페이지
        - [backendshop.com](http://backend.shop.com/)/support/1 : 1번 글 조회
            - Restful 하게 경로 작성
    - [backend.shop.com](http://backend.shop.com/)/products : 제품 전체 페이지
        - [backendshop.com](http://backend.shop.com/)/products/food :  음식 전체 페이지
        - [backendshop.com](http://backend.shop.com/)/products/stuff  :  음식 전체 페이지
        
    - [backendshop.com](http://backend.shop.com/)/prducts/order : 주문페이지
    - [backendshop.com](http://backend.shop.com/)/prducts/order/complete : 주문 완료 페이지
    - [backendshop.com](http://backend.shop.com/)/prducts/order/cancleorder : 주문 완료 페이지
    
    - [backendshop.com](http://backend.shop.com/)/cart  : 장바구니 페이지
    - [backendshop.com](http://backend.shop.com/)/login  : 로그인 페이지 → memberUtil
    - [backendshop.com](http://backend.shop.com/)/mypage  : 회원의 마이페이지
    - [backendshop.com](http://backend.shop.com/)/join  : 회원가입 페이지
    
1. **코드 주석 :**
    - // ← 이용 혹은
    - javadoc
        - 인터페이스
        - 코드 라인이 15줄이상 되는 함수
2. **테스트 코드 작성 :**
    - mocking 을 이용한 Unit Test 진행
    - PostMan으로 API TEST
3. **버전 관리 :**
    - Master Branch
    - Dev Branch
        - feat/[기능 이름]→ Dev merger  후 feat Branch 삭제
    - 예시 `feat/login`
    - 프로젝트 끝난 후 dev → master Merge

## Commit Convention

## Commit Convention

- `ex) feat: 로그인 API 기능 추가`
- `ex) refactor: posting service 내 인증 로직 필터로 분리`
    - feat : 기능 개발
    - fix : 버그 수정 - 수정 이유와 수정 내역
    - refactor : 기능 변경이 없는 코드 수정 (예: 변수명 수정) - 수정 이유와 수정 내역
    - test : 테스트 코드 작성 및 수정
    - docs : 문서 작성 및 수정
    - build : 빌드 파일 작성 및 수정
    - style : 스타일 변경(reformat, indent)
    - setting: 개발 환경 설정
    - chore : 단순한 작업 (예: 파일 경로)

## 사용 기술

- Java 17
- Spring Boot 3.1.
- Spring Data JPA
- lombok
- thymeleaf
- Spring Security
