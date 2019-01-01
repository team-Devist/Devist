# Devist
> 일정 주기마다 자동 반복 생성되는 TodoList

## Stack
- Spring Boot
- MySQL


## URL Convention
| 역할 | Method | URL |
|:--------|:--------:|:--------|
| 메인 | GET | / |
| 로그인 페이지 | GET | /login |
| 로그인 | POST | /login |
| 로그아웃 | POST | /logout |
| 회원가입 페이지 | GET | /signup |
| 회원가입 | POST | /signup |
| 프로필 | GET | /mypage |
| 할일 추가 페이지 | GET | /todo/add |
| 할일 추가 | POST | /todo/add |
| 할일 상세 보기 | GET | /todo/{id} |
| 할일 수정 페이지 | GET | /todo/{id}/edit |
| 할일 수정 | POST | /todo/{id}/edit |
| 할일 제거 | POST | /todo/{id}/delete |
| 할일 진행률 통계 | GET | /statistics |

