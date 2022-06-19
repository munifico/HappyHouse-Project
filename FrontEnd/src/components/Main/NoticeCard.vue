<template>
    <div class="board-card">
        <div class="head">
            <div class="title-side">

                <div class="board-icon"></div>
                <h2>공지사항</h2>
            </div>
            <a @click.prevent="clickNoticeButton" id="notice-btn">더보기</a>
        </div>
        <div class="body">
            <ul class ="article-list">
                <li v-for="article in articles"
                    :key="article.articleno" 
                    :article="article">
                    <div class="board-tag">공지</div>
                    {{ article.subject }}
                    <div>
                    <RouterLink 
                    class="nb"
                    :to="`/board/notice/detail/${article.articleno}`" 
                    >    Click</RouterLink>
                    </div>
                </li>

                
            </ul>
            
        </div>
    </div>
</template>
<script>

import http from "~/api/http";
export default {
    data(){
        return{
            articles: []
        }
    },
    props: {
        article:{
            type:Object,
            default:() => ({})
        }
    },

    created() {
        http.get(`/board/notice`).then(({ data }) => {
            for(var i = 0; i < 5 && i < data.length; i++){
              this.articles.push(data[i]);
            }
        });
    },
    methods:{
        clickNoticeButton(){
            this.$router.push({ name: "Noticelist" });
        },
    }
    
}
</script>
<style lang="scss" scoped>
    .board-tag{
    font-size:12px;
    padding: 2px;
    
    border: solid 1px #ff0000;
    border-radius: 4px;
    color:#d64026;
    font-weight: 500;
    margin-right: 15px;
    position:relative;
    top:-2px;
}
.nb{
    text-decoration: none;
    color:black;
}

</style>