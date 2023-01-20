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
            <h1 class="heading" data-aos="fade-up">관리자 페이지</h1>
            <h3 class="heading" data-aos="fade-up">User Information</h3>
            

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

    <div class="untree_co-section">
      <div class="container" data-aos="fade-left" data-aos-delay="200">
        <div class="row">
          <div class="col-lg-12 mx-auto text-center">
            <!-- search 관련 div 시작 -->
            <div class="col-md-8" style="margin: 0 auto">
              <div class="input-group mb-3">
                <!--            Todo : 수정 시작 -->
                <input
                  type="text"
                  class="form-control"
                  placeholder="Search by Username"
                  v-model="searchUsername"
                />
                <div class="input-group-append">
                  <button
                    class="btn btn-outline-secondary"
                    type="button"
                    @click="
                      page = 1;
                      retrieveUser();
                    "
                  >
                    Search
                  </button>
                </div>
                <!--            Todo : 수정 끝 -->
              </div>
            </div>
            <!-- search 관련 div 끝 -->

            <!--    Todo : page 바 시작 -->
            <div class="col-lg-2">
              <div class="mb-3">
                Items per Page:
                <select
                  v-model="pageSize"
                  @change="handlePageSizeChange($event)"
                >
                  <option v-for="size in pageSizes" :key="size" :value="size">
                    <!--            size : 3, 6, 9 -->
                    {{ size }}
                  </option>
                </select>
              </div>

              <b-pagination
                v-model="page"
                :total-rows="count"
                :per-page="pageSize"
                pills
                size="size-sm"
                prev-text="<"
                next-text=">"
                @change="handlePageChange"
              ></b-pagination>
            </div>
            <!--    Todo : page 바 끝 -->

            <table class="table">
              <thead>
                <tr>
                  <th scope="col">Username</th>
                  <th scope="col">Email</th>
                  <th scope="col">roles</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody v-for="(data, index) in user" :key="index">
                <tr>
                  <td>{{ data.username }}</td>
                  <!-- <td>{{ data.lastName }}</td> -->
                  <td>{{ data.email }}</td>
                  <td>{{ data.name }}</td>
                  <td>
                    <router-link :to="'/user/' + data.id"
                      ><span class="badge bg-success">Edit</span></router-link
                    >
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import navbarCom from "../../assets/js/navbar";
import counterCom from "../../assets/js/counter";
import customCom from "../../assets/js/custom";
import UserDataService from "../../service/UserDataService";

export default {
  // 변수 정의하는 곳 : data(), 초기화
  data() {
    return {
      user: [],
      searchUsername: "", // 유저명

      // 페이징을 위한 변수 정의
      page: 1, // 현재 페이지
      count: 0, // 전체 데이터 건수
      pageSize: 3, // 한페이지당 몇개를 화면에 보여줄지 결정하는 변수

      pageSizes: [3, 6, 9], // select box 에 넣을 기본 데이터
    };
  },
  methods: {
    retrieveUser() {
      UserDataService.getAll(this.searchUsername, this.page - 1, this.pageSize)
        .then((response) => {
          const { user, totalItems } = response.data; // springboot 의 전송된 맵 정보
          this.user = user; // 스프링부트에서 전송한 데이터
          this.count = totalItems; // 스프링부트에서 전송한 페이지정보(총 건수)

          // 디버깅 콘솔에 정보 출력
          console.log(response.data);
        })
        // 실패하면 .catch() 에 에러가 전송됨
        .catch((e) => {
          console.log(e);
        });
    },
    // select box 값 변경시 실행되는 함수(재조회)
    handlePageSizeChange(event) {
      this.pageSize = event.target.value; // 한페이지당 개수 저장(3, 6, 9)
      this.page = 1;
      // 재조회 함수 호출
      this.retrieveUser();
    },
    // 페이지 번호 변경시 실행되는 함수(재조회)
    handlePageChange(value) {
      this.page = value; // 매개변수값으로 현재페이지 변경
      // 재조회 함수 호출
      this.retrieveUser();
    },
  },
  // 화면이 뜨자마자 실행되는 이벤트(라이프 사이클 함수) : mounted(), created()
  mounted() {
    navbarCom();
    counterCom();
    customCom();
    this.retrieveUser(); // 화면 로딩시 전체 조회함수 실행
  },
};
</script>

<style></style>
