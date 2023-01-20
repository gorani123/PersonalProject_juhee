<template>
  <div>
    
    <!-- 상단바 양식 -->
    <div
      class="hero page-inner overlay"
      style="background-image: url('images/hero_bg_3.jpg')"
    >
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">QnA</h1>

            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item"><router-link to="/addqna">Add QnA</router-link></li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  QnA
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->


    <!-- 본문양식 추가 -->
    <div class="section">
      <div class="container">
        <div class="row">


    <!-- 기존qna데이터 시작 -->
    <div class="container">
     <!-- search 관련 div 시작 -->
     <div class="col-md-12">
      <div class="input-group mb-2">
        <div class="col-3">
          <!-- 검색 select 박스 :글제목(subject)으로 검색 또는 작성자(name)로 검색 -->
          <select class="form-select" v-model="searchSelect">
            <!-- 셀렉트박스🤍대문자 Subject  -->
            <option>Subject</option>
            <option>Name</option>
          </select>
        </div>
        <!-- searchDname -> searchMessage -> searchKeyword 변경 -->
        <div class="col-7">
          <input
            type="text"
            class="form-control"
            placeholder="Search by Subject"
            v-model="searchKeyword"
          />
        </div>

        <div class="input-group-append col-2">
          <button
            class="btn btn-outline-secondary"
            type="button"
            @click="
              page = 1;
              retrieveQna();
            "
          >
            Search
          </button>
        </div>
      </div>
    </div>
    <!-- search 관련 div 끝 -->

    <!-- page -->
    <div class="col-md-12">
      <div class="mb-3">
        Items per Page:
        <select v-model="pageSize" @change="handlePageSizeChange($event)">
          <option v-for="size in pageSizes" :key="size" :value="size">
            <!-- <!—            size : 3, 6, 9 —> -->
            {{ size }}
          </option>
        </select>
      </div>

      <b-pagination
        v-model="page"
        :total-rows="count"
        :per-page="pageSize"
        pills
        size="sm"
        prev-text="<"
        next-text=">"
        @change="handlePageChange"
      ></b-pagination>
    </div>
    <!-- page 끝 -->

   <!-- 테이블 -->
   <div class="col-lg-12 text-center mt-5">
    <table class="table table-striped table-dark">
    <thead>
    <tr>
      <th scope="col">Qno</th>
          <th scope="col">Subject</th>
          <th scope="col">Name</th>
          <th scope="col">Message</th>
          <th scope="col">Email</th>
          <th scope="col">Edit</th>
      </tr>
    </thead>
      <tbody v-for="(data, index) in qna" :key="index">
        <tr>
          <td>{{ data.qno }}</td>
          <td>{{ data.subject }}</td>
          <td>{{ data.name }}</td>
          <td>{{ data.message }}</td>
          <td>{{ data.email }}</td>
          <td>
            <router-link :to="'/qna/' + data.qno"
              ><span class="badge bg-success">Edit</span></router-link
            >
          </td>
        </tr>
      </tbody>
    </table>
   </div>
    <!-- 테이블 끝 -->

  </div>
    <!-- 기존qna데이터 끝 -->


    </div>
    </div>
    </div>

  </div>
</template>

<script>
// 👀axios 공통함수(springboot 연동을 위한 함수들의 모임) import 필수!!
import QnaDataService from "../service/QnaDataService";
// 자바스크립트 import --> 작성한 컴포넌트마다 각각 임포트 필요(공통 컴포 빼고)
import navbarCom from "../assets/js/navbar";
import counterCom from "../assets/js/counter";
import customCom from "../assets/js/custom";

export default {
    data() {
      return {
      qna: [],
      // currentQna: null,
      // currentIndex: -1,
      // dname: "", 소스 변경-> searchDname: "" 을 searchKeyword, searchSelect 으로 변경
      searchKeyword: "",  // 검색어
      searchSelect: "Subject",  // 셀렉트박스 기본값 (선택안했을때_글제목subject으로 검색) 

      // 페이징을 위한 변수 정의(최초세팅) 4가지
      page: 1,      // 현재 페이지
      count: 0,     // 전체 데이터 건수
      pageSize: 5,  // 한 페이지당 화면에 보여줄 개수를 결정하는 변수

      pageSizes: [5, 10, 15]  // 셀렉트박스에 넣을 기본 데이터. 선택창
      };
    },
    methods: {
      // 함수명 : retrieveQna()
      // 매개변수 : this.searchKeyword (검색어), this.searchSelect(셀렉트박스 선택값)
      //           this.page-1 (현재 페이지 번호)
      //           this.pageSize (페이지당 출력할 데이터갯수)
      // 리턴값 : 없음
      retrieveQna() {
        QnaDataService.getAll(
          this.searchSelect,   // 셀렉트박스에서 선택된 값
          this.searchKeyword,  // 검색어
          this.page-1, 
          this.pageSize)  // 프엔 자동 페이징처리가 1부터 시작하므로 -1해서 0으로 만들어 줌(스프링부트는 0번부터 매기니까 맞춰줌)
        // axios성공하면 스프링부트 결과가 .then() 안으로 전송됨
        .then((response) => {
          // const(let){속성명, 속성명2} = 데이터객체배열  : 모던자바스크립트 문법 구조분해할당
          const {qna, totalItems} = response.data; // springboot 에서 전송된 맵(map) 정보
          this.qna = qna;         // 스프링 부트에서 전송한 데이터
          this.count = totalItems;  // 스프링부트에서 전송한 페이지정보(총 건수)
          // 디버깅 콘솔에 정보 출력
          console.log(response.data);
        })
        // 실패하면 .catch() 에 에러가 전송됨
        .catch((e) => {
          console.log(e);
        });
      },
      // select box 값 변경시 실행되는 함수(재조회)💫
      // event : 사용자가 클릭한 컨트롤이 이벤트객체를 통해 접근,확인가능
      handlePageSizeChange(event) {
        this.pageSize = event.target.value    // 셀렉트박스 값 가져오기 -> 한 페이지당 출력할 데이터 갯수 저장
        this.page = 1;    // 현재페이지를 첫 페이지로 세팅
        // 재조회함수 호출
        this.retrieveQna();
      },
      // 페이지번호 변경 시 실행되는 함수(재조회)🔱
      handlePageChange(value) {
        // 매개변수값으로 현재 페이지번호 변경
        this.page = value;
        // 재조회 함수 호출
        this.retrieveQna();
      }
    },
    // 👀화면이 뜨자마자 실행되는 이벤트(라이프 사이클 함수) : mounted(), created()
    mounted() {
        this.retrieveQna(); // 👀화면 로딩시 전체 조회함수 retrieveQna() 함수 실행
        navbarCom();
        counterCom();
        customCom();
    },
  
  }
</script>
