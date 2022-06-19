<template>
  <div class = "qna-container">
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
  
    <div class="article">
      <div v-if="qnas.length">
        <table class="table table-hover">
          <thead class="the">
            <tr>
              <th scope="col" style="width:70px;border-right: 3px solid #fff">번호</th>
              <th scope="col" style="width:230px;border-right: 3px solid #fff">제목</th>
              <th scope="col" style="width:80px;border-right: 3px solid #fff">작성자</th>
              <th scope="col" style="width:120px;border-right: 3px solid #fff">작성일</th>
            </tr>
          </thead>
          <tbody>
            <qnaListItem
            v-for = "qna in qnas"
            :key="qna.qnano"
            :qna="qna"
            />
          </tbody>
        </table>
      </div>
      <div v-else class="text-center"> 게시글이 없습니다 </div> 
    </div>
    <div class= "write">
      <button v-if="userInfo" type="button" class="btn btn-primary" @click="moveWrite()">글쓰기</button>
    </div>
    <div class="search">
      <Search />
    </div>
  </div>
</template>

<script>
import http from "~/api/http";
import qnaListItem from "~/components/board/qna/item/qnaListItem";
import Search from '~/components/board/qna/qnaSearch'


export default {
  name: "qnaList",
  components: {
    qnaListItem,
    Search
  },
  computed: {
    userInfo() {
      return this.$store.state.member.userInfo;
    },
  },
  data() {
    return {
      qnas: [],
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
    http.get(`/board/qna/list`).then(({ data }) => {
      this.qnas = data;
      console.log(data)
    });
  },
  methods: {
    isMatch(path){
            if(!path) return false
            return path.test(this.$route.fullPath)
    },
    moveWrite() {

      this.$router.push({ name: "qnawrite" });
    },
  },
};
</script>

<style lang="scss" scoped>
.qna-container{
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
      padding-right:30px;
    }
    .btn{
      background-color:  rgb(60, 127, 252);
      font-weight:700;
      color:white;

    }

    .article{
      width: 900px;
      font-size: 18px;
      text-align: left;
       padding-top: 50px;
    padding-bottom: 5px;
    padding-left: 10px;  
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
     .the{
    background-color:rgb(117, 164, 252);
    color:white;
    font-weight:700;
    text-align: center;
    border: 3px solid white;
}

    
}
</style>
