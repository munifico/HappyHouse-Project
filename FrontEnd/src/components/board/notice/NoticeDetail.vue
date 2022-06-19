<template>
  <div class="detail-container">
    <div class="container bc">📝 <span>글</span> 보기</div>
    <div class="bc">
      <div class="card">
        <h5 class="card-header">{{ article.userid }}님의 게시글</h5>

        <div class="head">{{ article.subject }}</div>
        <div class="body">{{ article.content }}</div>
        <div class="time">{{ article.regtime }} 작성</div>
      </div>
    </div>

    <div class="bc">
      <button v-if="userInfo && userInfo.userid === 'admin'" type="button" class="btn btn-primary" id="write-btn" @click="deleteArticle">삭제</button>
      <button v-if="userInfo && userInfo.userid === 'admin'" type="button" class="btn btn-primary" id="write-btn" @click="moveModifyArticle">수정</button>
      <button type="button" class="btn btn-primary" id="write-btn" @click="listArticle">목록</button>
    </div>
  </div>
</template>

<script>
// import moment from "moment";
import http from "~/api/http";
export default {
  name: "NoticeDetail",

  data() {
    return {
      article: {},
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.member.userInfo;
    },
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/notice/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "Noticelist" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "Noticemodify",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "Noticedelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style lang="scss" scoped>
.detail-container {
  .bc {
    padding: 0px 90px;
    padding-top: 20px;
    width: 900px;
    margin: auto;
    max-width: inherit;
    font-size: 30px;
    font-weight: 600;
    #write-btn {
      margin-left: 5px;
      font-weight: 700;
      float: right;
    }
    .card {
      width: 720px;
      min-height: 400px;
      .head {
        border-bottom: 1px solid #222;
        height: 50px;
        margin-left: 30px;
        margin-top: 20px;
        font-size: 30px;
        color: rgb(8, 42, 104);
      }
      .body {
        height: 150px;
        margin-top: 30px;
        margin-left: 40px;
        font-weight: 200;
        font-size: 22px;
      }
      .time {
        margin-top: 60px;
        margin-left: 400px;
        float: right;
        font-size: 20px;
        font-weight: 200;
      }
    }
  }
  padding: 38px 5px;
  width: 1000px;
  margin: auto;
  max-width: inherit;
}
</style>
