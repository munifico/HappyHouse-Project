<template>
  <div class = "review-container">
    <div class="category nav">
      <div v-for="nav in navigations" :key="nav.name" class="nav-item">
      <RouterLink 
        :to="nav.href"
        active-class="active"
        :class="{active:isMatch(nav.path)}"
        class="nav-link">
        {{nav.name}}
      </RouterLink>
      </div>
    </div>


    <section class="review-list">
        <ReviewListItem v-for = "article in articles"
            :key="article.articleno"
            :article="article"/>

    </section>

    <div class= "write">
      <button type="button" class="btn btn-primary" @click="moveWrite()">글쓰기</button>
    </div>
 
  </div>
</template>

<script>
import http from "~/api/http";
import ReviewListItem from "~/components/board/review/item/ReviewListItem";


export default {
  name: "ReviewList",
  components: {
    ReviewListItem,
  },
  data() {
    return {
      articles: [],
      navigations:[
        {
          name :'공지사항',
          href:'/board/notice/list'
        },
          {
          name :'수다 수다',
          href:'/board/review/list'
        },
        {
          name :'QnA',
          href:'/board/qna/list'
        },
      ]
    };
  },
  created() {
    http.get(`/board/review`).then(({ data }) => {
      this.articles = data;
    });
  },
  methods: {
    isMatch(path){
            if(!path) return false
            return path.test(this.$route.fullPath)
    },
    moveWrite() {
      this.$router.push({ name: "Reviewwrite" });
    },
  },
};
</script>

<style lang="scss" scoped>
.review-container{
    padding: 38px 5px;
    width:1000px;
    margin:auto;
    max-width: inherit;
    .nav-link{
      font-size:18px;
      font-weight: 700;
    }
    .write{
      font-size: 18px;
      float:right;
      padding-right:90px;
      margin-top:30px;
    }
    .btn{
      background-color:  rgb(60, 127, 252);
      font-weight:700;
      color:white;

    }

    .article{
      font-size: 18px;
      text-align: left;
      padding-top:30px;
      padding-bottom: 20px;
      padding-left:10px;  
    }
    .search{    
      padding-left:80px; 
    }

    .category{
      font-size: 16px;
      height:35px;
      width:100%;
      padding-top:9px;
      margin-bottom:20px;
      
      a{
        margin :0 12px 2px;
        color: rgb(148,150,155);
        padding: 0 20px 12px;
        border-bottom: 2px solid #dbdee7;
        &.active{
          color:rgb(0, 0, 0);
          font-weight: 700;
            border-bottom: 2px solid rgb(0, 0, 0);
        }
      }
    }

    .review-list{
      display:grid;
      width:900px;
      padding-top:40px;
      grid-template-columns:1fr 1fr;


    }
}
</style>
