<template>
    <div class="best board-card">
        <div class="head">
             <div class="title-side">

                <div class="board-icon"></div>
                <h2>수다 수다</h2>
            </div>
           
        
            <a @click.prevent="clickReviewButton" id="review-btn">더보기</a>
        </div>
        <div class="body">
            <ul class="article-list">
                <li v-for="article in articles"
                        :key="article.articleno" 
                        :article="article">
                        <div class="review-tag">
                            익명
                        </div>
                    {{article.subject}}
                    <!-- <div class="count-display">120</div> -->
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
        http.get(`/board/review`).then(({ data }) => {
            for(var i = 0; i < 5 && i < data.length; i++){
              this.articles.push(data[i]);
            }
        });
    },
    methods:{
        clickReviewButton(){
            this.$router.push({ name: "Reviewlist" });
        },
    }
    
}
</script>
<style lang="scss">
.review-tag{
    font-size:12px;
    padding: 2px;
    
    border-radius: 4px;
    color:#1e89ec;
    font-weight: 500;
    margin-right: 15px;
    position:relative;
    top:-2px;
}

    
</style>