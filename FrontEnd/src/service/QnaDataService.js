// 목적 : Dept 정보를 axios 로 CRUD 를 하는 공통 함수들 정의
import http from "../http-common";

class QnaDataService {
    // 모든 고객정보 조회 요청 함수 + 글제목 + 글내용 검색 요청 함수 
    // searchSelect : 글제목과 질문자 검색(체크박스로선택)
    // page : 현재 페이지 번호 
    // size : 한 페이지당 화면에 표시할 데이터 갯수
    getAll(searchSelect, searchKeyword, page, size) {
    //   alert("확인용2")

        // get 방식 통신 요청 -> @GetMapping("/api/qna")
        // 1) 전체 조회 와
        // 2) 질문 조회를 같이 하는 함수 getAll
        return http.get(`/qna?searchSelect=${searchSelect}&searchKeyword=${searchKeyword}&page=${page}&size=${size}`); 
    }

    // CID로 조회 요청 함수
    // get 방식 통신 요청 -> @GetMapping("/api/qna/{qno}"), @PathVariable
    get(qno) {
        return http.get(`/qna/${qno}`)
    }

    // 고객정보 생성(insert) 요청 함수
    // post 방식 통신 요청 -> @PostMapping("/api/qna"), @RequestBody
    create(data) {
        console.log(data);
        return http.post("/addqna", data);
    }

    // 고객정보 수정(update) 요청 함수
    // put 방식 통신 요청 -> @PutMapping("/api/qna/{qno}"), @RequestBody
    update(qno, data) {
        return http.put(`/qna/${qno}`, data);
    }

    // 고객정보 삭제(delete) 요청 함수
    // delete 방식 통신 요청 -> @DeleteMapping("/api/qna/deletion/{qno}")
    //                        , @PathVariable  
    delete(qno) {
        return http.delete(`/qna/deletion/${qno}`);
    }

    // // 고객정보 전체 삭제 요청 함수
    // // delete 방식 통신 요청 -> @DeleteMapping("/api/qna/all")
    // deleteAll() {
    //     return http.delete("/qna/all")
    // }

}

export default new QnaDataService;