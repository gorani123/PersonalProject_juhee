<template>
  <!-- 최외곽 div 필요 -->
  <div>


    <!-- 테마 상단바 양식 -->
    <div
      class="hero page-inner overlay"
      style="background-image: url('images/hero_bg_3.jpg')"
    >
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">Add QnA</h1>

            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item">
                  <router-link to="/qna">QnA</router-link>
                </li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  Add QnA
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->


    <!-- 왼쪽 섹션 -->
    <div class="section">
      <div class="container">
        <div class="row">
          <div
            class="col-lg-4 mb-5 mb-lg-0"
            data-aos="fade-up"
            data-aos-delay="100"
          >
            <div class="contact-info">
              <div class="address mt-2">
                <i class="icon-room"></i>
                <h4 class="mb-2">Location:</h4>
                <p>
                  43 Raymouth Rd. Baltemoer,<br />
                  London 3910
                </p>
              </div>

              <div class="open-hours mt-4">
                <i class="icon-clock-o"></i>
                <h4 class="mb-2">Open Hours:</h4>
                <p>
                  Sunday-Friday:<br />
                  11:00 AM - 2300 PM
                </p>
              </div>

              <div class="email mt-4">
                <i class="icon-envelope"></i>
                <h4 class="mb-2">Email:</h4>
                <p>info@Untree.co</p>
              </div>

              <div class="phone mt-4">
                <i class="icon-phone"></i>
                <h4 class="mb-2">Call:</h4>
                <p>+1 1234 55488 55</p>
              </div>
            </div>
          </div>
          
          <!-- 폼 양식 -->

          <!-- 기존 폼 테마양식 안에 넣기-->
          <div class="col-lg-8" data-aos="fade-up" data-aos-delay="200">
            <form action="#">
              <!-- <div class="row"> -->
                
              <!-- 기존qna데이터 시작 -->
              <!-- 양식폼 시작 -->
                <!-- v-if 시작 -->
                <div v-if="!submitted" class="row">

                  <div class="col-6 mb-3">
                    <input
                      type="text"
                      class="form-control"
                      required
                      v-model="qna.subject"
                      placeholder="Your Subject"
                    />
                  </div>
                  <div class="col-6 mb-3">
                    <input
                      type="text"
                      class="form-control"
                      required
                      v-model="qna.name"
                      placeholder="Your Name"
                    />
                  </div>
                  <div class="col-12 mb-3">
                    <textarea
                      name=""
                      id=""
                      cols="30"
                      rows="7"
                      v-model="qna.message"
                      class="form-control"
                      placeholder="궁금하신 사항을 문의하세요"
                    ></textarea>
                  </div>
                  <div class="col-12 mb-3">
                    <input
                      type="email"
                      class="form-control"
                      v-model="qna.email"
                      placeholder="Your Email"
                    />
                  </div>
                  <!-- 제출버튼 -->
                  <div class="col-12">
                    <input
                      @click="saveQna"
                      type="submit"
                      value="Submit"
                      class="btn btn-primary"
                    />
                  </div>
                </div>
                <!-- v-if 끝 -->

                <!-- v-else -->
                <div v-else>
                  <div class="alert alert-success" role="alert">
                    Save qna successfully!
                  </div>
                  <!-- 추가 등록 버튼 -->
                  <!-- <button @click="newQna" class="btn btn-primary">Add New Qna</button> -->
                  <!-- QnA 첫화면 돌아가기 -->
                  <router-link to="/qna" class="btn btn-primary"
                    >Go back to QnA</router-link
                  >
                </div>
                <!-- v-else 끝 -->
                
              <!-- </div> -->
            </form>
            <!-- 양식폼 끝 -->
          </div>
          <!-- 기존qna데이터 끝 -->

        </div>
      </div>
    </div>
    <!-- /.untree_co-section -->
    



  </div>
</template>

<script>
// 👀axios 공통함수(springboot 연동을 위한 함수들의 모임) import 필수!!
import QnaDataService from "../service/QnaDataService";
// 자바스크립트 import --> 작성한 컴포넌트마다 각각 임포트 필요(공통 컴포 빼고)
import navbarCom from "../assets/js/navbar";
import counterCom from "../assets/js/counter";
import customCom from "../assets/js/custom";

/* eslint-disable */
export default {
  data() {
    return {
      qna: {
        subject: "", //string 은 초기값세팅 "" (int 는 null)
        name: "",
        message: "",
        email: "",
      },
      // submit 버튼을 클릭하면 true(백엔, insert)가 되고, You submitted successfully! 화면에 출력됨
      submitted: false,
    };
  },
  methods: {
    saveQna() {
      // 임시 객체 변수 -> springboot 전송
      // 번호는(cid) 자동생성되므로 빼고 전송함
      let data = {
        subject: this.qna.subject,
        name: this.qna.name,
        message: this.qna.message,
        email: this.qna.email,
      };

      // insert 요청 함수 호출(axios 공통함수 호출)
      QnaDataService.create(data)
        // 성공하면 then() 결과가 전송됨
        .then((response) => {
          this.qna.cid = response.data.cid;
          // 콘솔 로그 출력(response.data)
          console.log(response.data);
          // 변수 submitted
          this.submitted = true;
        })
        // 실패하면 .catch() 결과가 전송됨
        .catch((e) => {
          console.log(e);
        });
    },
    newQna() {
      // 새양식 다시 보여주기 함수, 변수 초기화 (고객등록 후 추가로 또 고객 등록할 때)
      this.submitted = false;
      this.qna = {};
    },
  },
  mounted() {
    navbarCom();
    counterCom();
    customCom();
  },
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
.margin {
  margin-bottom: 20px;
}
</style>
