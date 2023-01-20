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
            <h1 class="heading" data-aos="fade-up">QnA Edit</h1>

            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item"><router-link to="/qna">QnA</router-link></li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  QnA Edit
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
    <!-- 수정폼 -->
    <div v-if="currentQna" class="edit-form">
      <form>
        <div class="mb-3">
          <label for="subject" class="form-label">Subject</label>
          <input
            type="text"
            class="form-control"
            id="subject"
            required
            name="subject"
            v-model="currentQna.subject"
          />
        </div>
        <div class="mb-3">
          <label for="name" class="form-label">Name</label>
          <input
            type="name"
            class="form-control"
            id="name"
            required
            name="name"
            v-model="currentQna.name"
          />
        </div>
        <div class="mb-3">
          <label for="message" class="form-label">Message</label>
          <input
            type="message"
            class="form-control"
            id="message"
            required
            name="message"
            v-model="currentQna.message"
          />
        </div>
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input
            type="email"
            class="form-control"
            id="email"
            required
            name="email"
            v-model="currentQna.email"
          />
        </div>
      </form>

      <!-- 정보 업데이트/삭제 버튼 -->
      <div class="mb-3">
        <button @click="updateQna" class="btn btn-primary me-3">Update</button>
        <button @click="deleteQna" class="btn btn-danger">Delete</button>
      </div>

      <div class="alert alert-success" role="alert" v-if="message">
        {{ message }}
      </div>
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
      currentQna: null,
      message: "",
    };
  },
  methods: {
    // 고객번호(qno)로 조회 요청하는 함수
    getQna(qno) {
      // axios 공통함수 호출
      QnaDataService.get(qno)
        // 성공하면 .then() 결과가 리턴됨
        .then((response) => {
          // springboot 결과를 리턴함(고객 객체)
          this.currentQna = response.data;
          // 콘솔 로그 출력
          console.log(response.data);
        })
        // 실패하면 .catch() 에러메세지가 리턴됨
        .catch((e) => {
          console.log(e);
        });
    },
    // qno로 고객정보를 수정 요청하는 함수
    updateQna() {
      // axios 공통함수 호출
      QnaDataService.update(this.currentQna.qno, this.currentQna)
        // 성공하면 then() 결과가 전송됨
        .then((response) => {
          console.log(response.data);
          this.message = "The Qna was updated successfully!";
        })
        // 실패하면 .catch() 에러메세지가 전송됨
        .catch((e) => {
          console.log(e);
        });
    },
    // qno 로 고객정보를 삭제 요청하는 함수
    deleteQna() {
      // axios 공통함수 호출
      QnaDataService.delete(this.currentQna.qno)
        // 성공하면 then() 결과가 전송됨
        .then((response) => {
          console.log(response.data);
          // 첫페이지(전체목록_조회_페이지) 강제 이동 : /
          this.$router.push("/qna");
        })
        // 실패하면 .catch() 에러메세지가 전송됨
        .catch((e) => {
          console.log(e);
        });
    },
  },
  // 화면이 뜨자 마자 실행되는 이벤트
  mounted() {
    // 클라이언트쪽 디버깅
    // alert(this.$route.params.qno);
    // console.log(this.$route.params.qno);
    navbarCom();
    counterCom();
    customCom();

    this.message = "";
    //  this.$route.params.qno : 이전페이지에서 전송한 매개변수는 $route.params 안에 있음
    // $route 객체 : 주로 url 매개변수 정보들이 있음
    // router/index.js 상세페이지 url의 매개변수명 : qno
    this.getQna(this.$route.params.qno);
  },
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>
