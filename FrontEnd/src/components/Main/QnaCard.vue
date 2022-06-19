<template>
    <div class="board-card">
        <div class="head">
            <div class="title-side">

                <div class="board-icon"></div>
                <h2>Q&A</h2>
            </div>
            <a @click.prevent="clickQnaButton" id="qna-btn">더보기</a>
        </div>
        <div class="body">
            <ul class ="article-list">
                <li v-for="qna in qnas"
                    :key="qna.qnano" 
                    :qna="qna">
                    {{ qna.title }}
                    
                    <RouterLink 
                    class= "bt"
                    :to="`/board/notice/detail/${qna.qnano}`" 
                    >Click
                    </RouterLink>
                    
                <!-- <div class="count-display">{{article.hit}}</div> -->
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
            qnas: []
        }
    },
    props: {
        qna:{
            type:Object,
            default:() => ({})
        }
    },

    created() {
        http.get(`/board/qna/list`).then(({ data }) => {
            for(var i = 0; i < 5 && i < data.length; i++){
              this.qnas.push(data[i]);
            }
        });
    },
    methods:{
        clickQnaButton(){
            this.$router.push({ name: "qnalist" });
        },
    }
    
}
</script>
<style lang="scss">
.bt{
    text-decoration:none;
    cursor:pointer;
    color:black;
}

#qna-btn{
    color:#222;
    text-decoration: none;

}
</style>