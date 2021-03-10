# Rest API 부동산/신용 투자 서비스
- 사용자는 원하는 부동산/신용 투자 상품을 투자할 수 있습니다. 
- 투자상품이 오픈될 때, 다수의 고객이 동시에 투자를 합니다. 
- 투자 후 투자상품의 누적 투자모집 금액, 투자자 수가 증가됩니다. 
- 총 투자모집금액 달성 시 투자는 마감되고 상품은 Sold out 됩니다.

## 테스트 방법
- 1. junit test를 통해 전체상품조회/투자/투자내역조회를 전체 테스트할 수 있도록 소스 구성
- 2. 테스트 전에 투자할 상품을 미리 만들어 두어야 함(POST:/products호출 필요)

## 프레임워크
- 사용기술 : JAVA, Spring boot, JPA, H2 Database, JUnit Test

## 테이블 설계
**Product테이블**
- product_id : Unique ID로 테이블의 PK
- title : 투자명 
- total_investing_amount : 총 투자 모집금액
- started_at : 투자시작일시
- finished_at : 투자종료일시

**Invest테이블**
- invest_id : Unique ID로 Invest테이블의 PK
- product_id : Product테이블의 아이디 FK
- user_id : 사용자id
- invested_at : 투자 일시
- amount : 투자금액

## API
### 전체 투자 상품 조회 API
> GET:/products
> **Example:**

> - **request**

> - **response**
> [
    {
        "productId": 1,
        "title": "개인신용 포트폴리오",
        "totalInvestingAmount": 1000,
        "startedAt": "2021-03-10T02:35:20.539+00:00",
        "finishedAt": "2021-03-10T02:40:20.539+00:00",
        "investCount": 0,
        "investSubTotal": 0,
        "investState": "모집중"
    },
    {
        "productId": 2,
        "title": "부동산 포트폴리오",
        "totalInvestingAmount": 1000000,
        "startedAt": "2021-03-10T02:35:20.539+00:00",
        "finishedAt": "2021-03-10T02:40:20.539+00:00",
        "investCount": 0,
        "investSubTotal": 0,
        "investState": "모집중"
    }
]

### 투자하기 API
> POST:/invest
> **Example:**

> - **request**
> {
	"amount": 500,
	"productId" : 1
}


> - **response**
> {
    "code": "E000",
    "message": "SUCCESS."
}

### 나의 투자 상품 조회 API
> GET:/invest
> **Example:**

> - **request**

> - **response**
> [
    {
        "investedAt": "2021-03-10T02:41:24.577+00:00",
        "amount": 500,
        "productId": 1,
        "totalInvestingAmount": 1000,
        "title": "개인신용 포트폴리오"
    }
]

### 투자 상품 생성 API
> POST:/products
> **Example:**

> - **request**

> - **response**
> [
    {
        "productId": 1,
        "title": "개인신용 포트폴리오",
        "totalInvestingAmount": 1000,
        "startedAt": "2021-03-10T02:41:18.528+00:00",
        "finishedAt": "2021-03-10T02:46:18.528+00:00",
        "investCount": 0,
        "investSubTotal": 0,
        "investState": "모집중"
    },
    {
        "productId": 2,
        "title": "부동산 포트폴리오",
        "totalInvestingAmount": 1000000,
        "startedAt": "2021-03-10T02:41:18.528+00:00",
        "finishedAt": "2021-03-10T02:46:18.528+00:00",
        "investCount": 0,
        "investSubTotal": 0,
        "investState": "모집중"
    }
]

## RETURN CODE
- E000("SUCCESS.", "E000")
- E001("product is not available.", "E001")
- E002("Total investment amount is insufficient.", "E002")
- E003("SOLD OUT.", "E003")