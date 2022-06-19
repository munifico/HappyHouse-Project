<template>
<div class="container">
    <div>
        <h3>글보기</h3></div>
    <div>
        <button type="button" class="btn btn-primary"  @click="listArticle">목록</button>
        <button type="button" class="btn btn-primary"  @click="moveModifyArticle">수정</button>
        <button type="button" class="btn btn-primary"  @click="deleteArticle">삭제</button>
    </div>

    <div>
        <div class="card"
            :header-html="`<h3>${article.articleno}.
            ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`"
            border-variant="dark"
            no-body
        >

        </div>
        <div class="card">
            <h5 class="card-header">{{article.userid}}님의  게시글 </h5>
            <div class="card-body">
                <h5 class="card-title">{{article.subject}}</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                <a href="/#/board/review/list" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
    </div>


</div>
 
</template>

<script>
// import moment from "moment";
import http from "~/api/http";

export default {
    name: "ReviewDetail",
    data() {
        return {
            article: {},
        };
    },
    computed: {
        message() {
        if (this.article.content)
            return this.article.content.split("\n").join("<br>");
        return "";
        },
    },
    created() {
        http.get(`/board/review/${this.$route.params.articleno}`).then(({ data }) => {
            this.article = data;
        });
    },
    methods: {
        listArticle() {
            this.$router.push({ name: "Reviewlist" });
        },
        moveModifyArticle() {
            this.$router.replace({
            name: "Reviewmodify",
            params: { articleno: this.article.articleno },
        });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
        },
        deleteArticle() {
            if (confirm("정말로 삭제?")) {
                this.$router.replace({
                name: "Reviewdelete",
                params: { articleno: this.article.articleno },
                });
            }
        }
    }
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style></style>
