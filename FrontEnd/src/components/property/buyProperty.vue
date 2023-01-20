<template>
  <div>
    <!-- 테마양식 -->
    <div
      class="hero page-inner overlay"
      style="background-image: url('images/hero_bg_1.jpg')"
    >
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">Buy Property</h1>

            <nav
              aria-label="breadcrumb"
              data-aos="fade-up"
              data-aos-delay="200"
            >
              <ol class="breadcrumb text-center justify-content-center">
                <li class="breadcrumb-item">
                  <router-link to="/">Home</router-link>
                </li>
                <li
                  class="breadcrumb-item active text-white-50"
                  aria-current="page"
                >
                  Properties
                </li>
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </div>

    <!-- 테마양식 추가 -->
    <div class="section">
      <div class="container">
        <div class="row">
          <!-- TODO: select 박스 넣어서 Title/Price/City 로 검색 ***-->
          <!-- 갤러리 검색 시작 -->
          <div class="d-flex justify-content-end">
            <div class="input-group mb-3">
              <!-- select box ***-->
              <div class="col-2">
                <!-- searchSelect : select박스 선택값 -->
                <select class="form-select" v-model="searchSelect">
                  <option>Title</option>
                  <option>Price</option>
                  <option>City</option>
                </select>
              </div>

              <!-- searchKeyword : 검색어 -->
              <input
                type="text"
                class="form-control"
                placeholder="Search by Title"
                v-model="searchKeyword"
              />
              <div class="input-group-append">
                <button
                  class="btn btn-outline-secondary"
                  type="button"
                  @click="
                    page = 1;
                    retrieveProperty();
                  "
                >
                  Search
                </button>
              </div>
            </div>
          </div>
          <!-- 검색 끝 -->
        </div>

        <!-- 페이지당 표시 매물 갯수 선택-->
        <div>
          <div class="col-lg-3">
            Items per Page:
            <select v-model="pageSize" @change="handlePageSizeChange($event)">
              <option v-for="size in pageSizes" :key="size" :value="size">
                {{ size }}
              </option>
            </select>
          </div>
        </div>

        <!-- 페이징 처리 -->
        <div class="d-flex justify-content-end">
          <div class="col-lg-6 text-center">
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
        </div>
      </div>
    </div>

  <!-- 테마 원래소스 -->
  <div class="section section-4 bg-light">
    <div class="container">
     <div class="row">


      <!-- 매물 -->
      <div
          class="col-xs-12 col-sm-6 col-md-6 col-lg-4"
          v-for="(data, index) in property"
          :key="index"
        >

            <!-- <div class="col"> -->
            <div class="property-item mb-30">
              <div class="property-content">

                      <!-- 업로드된 매물 사진 -->
                      <div class="container">
                      <router-link to="/propertysingle" class="box">
                        <img :src="data.propertyUrl" class="img-thumbnail" alt="Image" />
                      </router-link>
                      </div>
                      <br>

                      <!-- sell에서 업로드한 파일 출력하기 -->
                      <div class="upload-detail">
                          <div class="price mb-2">
                            <span>&#8361; {{ data.propertyPrice }}</span>
                          </div>

                          <div>
                            <span class="d-block mb-2 text-black-50">{{
                              data.propertyTitle
                            }}</span>
                          <span class="city d-block mb-3">{{ data.propertyCity }}</span>

                          <div class="specs d-flex mb-4">
                              <span class="d-block d-flex align-items-center me-3">
                                <span class="icon-bed me-2"></span>
                                <span class="caption">{{ data.propertyBed }} bed</span>
                              </span>
                              <span class="d-block d-flex align-items-center">
                                <span class="icon-bath me-2"></span>
                                <span class="caption">{{ data.propertyBath }} baths</span>
                              </span>
                            </div>
                          </div>
                      </div>

                      <router-link
                          to="/propertysingle"
                          class="btn btn-primary py-2 px-3"
                          >See details
                      </router-link>
              </div>
            </div>

      </div>



     </div>
    </div>
  </div>

  </div>
</template>

<script>
// axios 공통 함수 import
import PropertyDataService from "../../service/PropertyDataService";

// 자바스크립트 import --> 작성한 컴포넌트마다 각각 임포트 필요(공통 컴포 빼고)
import navbarCom from "../../assets/js/navbar";
import counterCom from "../../assets/js/counter";
import customCom from "../../assets/js/custom";

export default {
  data() {
    return {
      currentProperty: undefined, // 현재 이미지 변수
      previewProperty: undefined, // 미리보기 이미지 변수
      message: "", // 서버쪽에서 에러가 날 때 보내는 메시지를 저장할 변수
      property: [], // 이미지 객체 배열(여러개)이 날아오면 저장되는 곳👀
      // searchTitle: "", // 이름으로 검색하는 변수
      searchKeyword: "", // 셀렉트박스에서 선택한 값 ***
      searchSelect: "Title", // 기본 선택, Title로 검색 ***

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
      pageSize: 6, // 한페이지당 몇개를 화면에 보여줄지 결정하는 변수

      pageSizes: [3, 6, 9], // select box 에 넣을 기본 데이터
    };
  },
  methods: {
    // 조회 함수
    retrieveProperty() {
      // PropertyDataService.getPropertys(this.searchTitle, this.page - 1, this.pageSize) // 프엔 자동 페이징처리가 1부터 시작하므로 -1해서 0으로 만들어 줌(스프링부트는 0번부터 매기니까 맞춰줌)
      PropertyDataService.getPropertys(
        this.searchSelect,
        this.searchKeyword,
        this.page - 1,
        this.pageSize
      ) //***
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
    //  upload 함수 : 변수 3개의 데이터를 axios를 통해 저장😦
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

          // 재조회 요청(axios 조회함수 .getPropertys()) : 데이터 잘 들어왔는지 화면에 출력 요청
          return PropertyDataService.getPropertys(
            this.searchSelect,
            this.searchKeyword,
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
          this.message = "Could not upload the image!" + e;
          this.currentProperty = undefined;
        });
    },
    // select box 값 변경시 실행되는 함수(재조회)
    handlePageSizeChange(event) {
      this.pageSize = event.target.value; // 한페이지당 개수 저장(3, 6, 9)
      this.page = 1;
      // 재조회 함수 호출
      this.retrieveProperty();
    },
    // 페이지 번호 변경시 실행되는 함수(재조회)
    handlePageChange(value) {
      this.page = value; // 매개변수값으로 현재페이지 변경
      // 재조회 함수 호출
      this.retrieveProperty();
    },
    // pno로 삭제하는 함수
    deleteImage(pno) {
      // axios 공통함수 호출
      PropertyDataService.delete(pno)
        // 성공하면 then() 결과가 전송됨
        .then((response) => {
          console.log(response);
          // 첫페이지(전체목록_조회_페이지) 강제 이동 : /property
          this.message = "정상적으로 삭제되었습니다.";
          // 삭제 후 재조회
          this.retrieveProperty();
        })
        // 실패하면 .catch() 에러메세지가 전송됨
        .catch((e) => {
          console.log(e);
          this.message = "오류가 발생했습니다." + e.message;
        });
    },
  },
  // 화면이 뜨자 마자 실행되는 이벤트 - 전체조회 함수 retrieveProperty() 실행
  mounted() {
    this.retrieveProperty();
    navbarCom();
    counterCom();
    customCom();
  },
};
</script>

<style>
/* 업로드용 사진 미리보기 */
.preview {
  max-width: 200px;
}


.property-content{
  width: 400px;
  height: 500px;
}


/* 매물 하나씩 카드로 나오도록 */
.property-item {
  width: 400px;
  height: 600px;
}



/* 이미지 사진 크기 조정 중*** */
/* .box {  */
  /* width: 300px; 요소 가로길이 지정 */
  /* background: #cccccc; 요소 배경색 */
  
  /* aspect-ratio: 16 / 9;  */
/* } */


.box {
  width: 100px;
  height: 100px;
  aspect-ratio: 16 / 9; /* 적용안됨 */
  background: red;
}

</style>
