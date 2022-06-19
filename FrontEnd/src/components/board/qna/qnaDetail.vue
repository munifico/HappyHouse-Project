<template>
 <div class="detail-container">
    <div class="container bc">📝 <span>글</span> 보기</div>
        <div class="bc">
            <div class="card">
                <h5 class="card-header">{{qna.userid}}님의 게시글</h5>
                <div class="head">{{qna.title}}</div>
                <div class="body">{{qna.content}}</div>
                <div class="time">{{ qna.regtime }} 작성</div>
            </div>
            <br>
            <div class="card c-a">
                <h5 class="card-header">관리자 답변</h5>
                <p class="card-text">{{qna.answer}}</p>
                <!-- <a href="/#/board/qna/list" class="btn btn-primary">Go somewhere</a> -->
            </div>
        </div>
        <div class="bc">
            <button type="button" class="btn btn-primary" id="write-btn" @click="listQna">목록</button>
            <button v-if="userInfo && userInfo.userid === qna.userid && userInfo.userid !== 'admin'" type="button" class="btn btn-primary" id="write-btn" @click="moveModifyQna">수정</button>
            <button v-if="userInfo && userInfo.userid === 'admin'" type="button" class="btn btn-primary" id="write-btn" @click="moveModifyQna">답변하기</button>
            <button v-if="userInfo && (userInfo.userid === qna.userid || userInfo.userid === 'admin')" type="button" class="btn btn-primary" id="write-btn" @click="deleteQna">삭제</button>
        </div>
</div>

 
</template>

<script>
// import moment from "moment";
import http from "~/api/http";

export default {
    name: "qnaDetail",
    data() {
        return {
            qna: {},
        };
    },
    computed: {
        userInfo() {
          return this.$store.state.member.userInfo;
        },
        message() {
        if (this.qna.content)
            return this.qna.content.split("\n").join("<br>");
        return "";
        },
    },
    created() {
        console.log(this.$route);
        http.get(`/board/qna/get/${this.$route.params.articleno}`).then(({ data }) => {
            this.qna = data;
        });
    },
    methods: {
        listQna() {
            this.$router.push({ name: "qnalist" });
        },
        moveModifyQna() {
            this.$router.replace({
            name: "qnamodify",
            params: { qnano: this.qna.qnano },
        });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
        },
        deleteQna() {
            if (confirm("정말로 삭제하시겠습니까?")) {
                this.$router.replace({
                name: "qnadelete",
                params: { qnano: this.qna.qnano },
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


<style lang="scss" scoped>
.detail-container{
    .bc{
        padding: 0px 90px;
        padding-top:20px;
        width:900px;
        margin:auto;
        max-width: inherit;
        font-size: 30px;
        font-weight:600;
        #write-btn{
            margin-left:5px;
            font-weight: 700;
            float:right;
            
        }
    .card{
        width:720px;
        min-height:350px;
        .head{
            border-bottom: 1px solid #222;
            height:50px;
            margin-left:30px;
            margin-top:20px;
            font-size:30px;
            color:rgb(8, 42, 104);
        }
        .body{
            height:120px;
            margin-top:30px;
            margin-left:40px;
            font-weight: 200;
            font-size:22px;
        }
        .time{
            margin-top:20px;
            margin-left:400px;
            margin-bottom:20px;
            float:right;
            font-size:20px;
            font-weight: 200;

        }
    }
    .c-a{
        min-height:200px;
    }
    }
      padding: 38px 5px;
        width:1000px;
        margin:auto;
        max-width: inherit;
}

</style>
