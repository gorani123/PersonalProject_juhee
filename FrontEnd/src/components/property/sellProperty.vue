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
            <h1 class="heading" data-aos="fade-up">Sell Property</h1>

            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  Upload property
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>
    <!-- 상단바양식 끝 -->


    
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
      <div class="col-lg-8" data-aos="fade-up" data-aos-delay="200">
        <form action="#">
          <div class="row">
            <div class="col-6 mb-3">
              <label for="propertyTitle" class="form-label"
              >Property Title</label
            >
            <input
              type="text"
              class="form-control"
              id="propertyTitle"
              required
              name="propertyTitle"
              placeholder="ex) 해운대 아이파크"
              v-model="propertyTitle"
            />
            </div>
            <div class="col-6 mb-3">
              <label for="propertyPrice" class="form-label">Price</label>
              <input
                type="text"
                class="form-control"
                id="propertyPrice"
                required
                name="propertyPrice"
                placeholder="ex) 13억 5000"
                v-model="propertyPrice"
              />
            </div>
            <div class="col-12 mb-3">
              <label for="propertyAddress" class="form-label">Address</label>
              <input
                type="text"
                class="form-control"
                id="propertyAddress"
                required
                name="propertyAddress"
                placeholder="ex) 해운대구 마린시티2로 38"
                v-model="propertyAddress"
              />
            </div>
            <div class="col-12 mb-3">
              <label for="propertyCity" class="form-label">City</label>
              <input
                type="text"
                class="form-control"
                id="propertyCity"
                required
                name="propertyCity"
                placeholder="ex) 부산광역시"
                v-model="propertyCity"
              />
            </div>
            <div class="col-12 mb-3">
              <label for="propertyBed" class="form-label">Bed</label>
              <input
                type="number"
                class="form-control"
                id="propertyBed"
                required
                name="propertyBed"
                placeholder="ex) 3 "
                v-model="propertyBed"
              />
            </div>
            <div class="col-12 mb-3">
              <div class="mb-4 col-md-3">
                <label for="propertyBath" class="form-label">Bath</label>
                <input
                  type="number"
                  class="form-control"
                  id="propertyBath"
                  required
                  name="propertyBath"
                  placeholder="ex) 1"
                  v-model="propertyBath"
                />
            </div>

       <!-- 이미지 선택상자 시작 -->
       <div class="mb-3 col-md-5">
        <label class="btn btn-default p-0">
          <!-- <!— 파일 선택상자 —> -->
          <input
            type="file"
            accept="image/*"
            ref="file"
            @change="selectProperty"
          />
        </label>
      </div>
      <!-- 이미지 선택상자 끝 -->

      <button
      class="btn btn-success btn-sm float-left"
      :disabled="!currentProperty"
      @click="upload"
    >
      Upload
    </button>
    <router-link to="/buy" class="btn btn-success btn-sm float-left"
    >등록확인</router-link
  >

         <!-- 미리보기 이미지 시작 -->
         <div v-if="previewProperty" class="preview">
          <div>
            <img
              width="200"
              class="preview my-3"
              :src="previewProperty"
              alt=""
            />
          </div>
        </div>
        <!-- 미리보기 이미지 끝 -->

                  <!-- 서버 에러 메세지가 있을 경우 아래 출력 시작 -->
                  <div v-if="message" class="alert alert-secondary" role="alert">
                    {{ message }}
                  </div>
                  <!-- 서버 에러 메세지가 있을 경우 아래 출력 끝 -->
                </div>

                
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<!-- /.untree_co-section -->


  </div>
</template>

<script>
// axios 공통 함수 import
import PropertyDataService from "../../service/PropertyDataService";

import navbarCom from "../../assets/js/navbar";
import counterCom from "../../assets/js/counter";
import customCom from "../../assets/js/custom";

export default {
  data() {
    return {
      currentProperty: undefined, // 현재 이미지 변수
      previewProperty: undefined, // 미리보기 이미지 변수
      message: "", // 서버쪽에서 에러가 날 때 보내는 메시지를 저장할 변수
      property: [], // 이미지 객체 배열(여러 개)이 날아오면 저장되는 곳👀
      searchSelect: "", // 이름으로 검색하는 변수

      // springboot 요청할 변수 , 이미지명(propertyTitle), 내용(content)
      propertyTitle: "",
      propertyPrice: "",
      propertyAddress: "",
      propertyCity: "",
      propertyBed: "",
      propertyBath: "",

      // 페이징을 위한 변수 정의
      page: 1, // 현재 페이지
      count: 0, // 전체 데이터 건수
      pageSize: 3, // 한페이지당 몇개를 화면에 보여줄지 결정하는 변수

      pageSizes: [3, 6, 9], // select box 에 넣을 기본 데이터
    };
  },
  methods: {
    // 조회 함수
    retrieveProperty() {
      PropertyDataService.getPropertys(
        this.propertyTitle,
        this.page - 1,
        this.pageSize
      ) // 프엔 자동 페이징처리가 1부터 시작하므로 -1해서 0으로 만들어 줌(스프링부트는 0번부터 매기니까 맞춰줌)
        //  axios성공하면 스프링부트 결과가 .then() 안으로 전송됨
        .then((response) => {
          // const(let){속성명, 속성명2} = 데이터객체배열  : 모던자바스크립트 문법 구조분해할당
          const { property, totalItems } = response.data; //  springboot 에서 전송된 맵(map) 정보
          this.property = property; // 스프링부트에서 전송한 데이터
          this.count = totalItems; // 스프링부트에서 전송한 페이지정보(총 건수)
          // 디버깅 콘솔에 정보 출력
          console.log(response.data);
        })
        // 실패하면 .catch() 에 에러가 전송됨
        .catch((e) => {
          console.log(e);
        });
    },
    // 파일 선택상자에서 선택한 이미지를 저장하는 함수
    selectProperty() {
      // 첫번째 선택한 이미지를 변수에 저장
      //  this.$refs : html에서 $refs 속성이 있는 컨트롤이 선택됨(바로접근가능)_이미지 선택상자의 input박스에 ref 걸려있음(ref="property")
      //               refs.property 중에 이름이 Propertys인 애 선택
      this.currentProperty = this.$refs.file.files.item(0); // 배열 중 첫번째 선택한 이미지(0)를 현재 이미지 변수
      // .createObjectURL() : 이미지 주소만 참조해서 이미지 보여주기 함수
      this.previewProperty = URL.createObjectURL(this.currentProperty);
      this.message = "";
    },
    //  upload 함수 : 변수 7개의 데이터를 axios를 통해 저장😦
    upload() {
      PropertyDataService.upload(
        this.propertyTitle,
        this.currentProperty,
        this.propertyPrice,
        this.propertyAddress,
        this.propertyCity,
        this.propertyBed,
        this.propertyBath
      )
        // insert 성공 then()
        .then((response) => {
          // 서버쪽 성공 메세지를 저장
          this.message = response.data.message;

          // 검색 : 재조회 요청(axios 조회함수 .getPropertys()) : 데이터 잘 들어왔는지 화면에 출력 요청
          return PropertyDataService.getPropertys(
            (this.searchSelect = "Title"), // searchSelect 에 title 선택한 것. 조건 대응 🥶
            this.propertyTitle, // proprtyTitle 조건 대응
            this.page - 1,
            this.pageSize
          );
        })
        // 조회가 성공하면 실행되는 then()
        .then((response2) => {
          const { property, totalItems } = response2.data;
          this.property = property;
          this.count = totalItems;
          console.log(response2.data);
        })
        .catch((e) => {
          console.log(e);
          // 서버 쪽에서 실패 메시지를 받아서 화면에 출력
          this.message = "Could not upload the image!!   " + e;
          this.currentProperty = undefined;
        });
    },
    // // select box 값 변경시 실행되는 함수(재조회)
    // handlePageSizeChange(event) {
    //   this.pageSize = event.target.value; // 한페이지당 개수 저장(3, 6, 9)
    //   this.page = 1;
    //   // 재조회 함수 호출
    //   this.retrieveProperty();
    // },
    // // 페이지 번호 변경시 실행되는 함수(재조회)
    // handlePageChange(value) {
    //   this.page = value; // 매개변수값으로 현재페이지 변경
    //   // 재조회 함수 호출
    //   this.retrieveProperty();
    // },
    // // pno로 삭제하는 함수
    // deleteImage(pno) {
    //   // axios 공통함수 호출
    //   PropertyDataService.delete(pno)
    //     // 성공하면 then() 결과가 전송됨
    //     .then((response) => {
    //       console.log(response);
    //       // 첫페이지(전체목록_조회_페이지) 강제 이동 : /property
    //       this.message = "정상적으로 삭제되었습니다.";
    //       // 삭제 후 재조회
    //       this.retrieveProperty();
    //     })
    //     // 실패하면 .catch() 에러메세지가 전송됨
    //     .catch((e) => {
    //       console.log(e);
    //       this.message = "오류가 발생했습니다." + e.message;
    //     });
    // },
  },
  // 화면이 뜨자 마자 실행되는 이벤트 - 전체조회 함수 retrieveProperty() 실행
  mounted() {
    // this.retrieveProperty();
    navbarCom();
    counterCom();
    customCom();
  },
};
</script>

<style>
.preview{
  width: 200px;
  height: 150px;
}
</style>
