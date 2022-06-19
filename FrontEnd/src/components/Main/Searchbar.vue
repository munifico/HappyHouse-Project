<template>
    <div class="wrap-search">
        <img class="search-icon" src="/icon/search.png" alt="검색">
        <input type= "text" v-model="query" placeholder="관심 있는 아파트를 검색해보세요!"/>
    </div>
</template>
<script>
export default {
    components:{

    },
    data(){
        return{
            query : null
        }
    }
    
}
</script>
<style lang="scss" scoped>
.wrap-search{
    width:100%;
    padding-top:10px;
    padding-bottom:25px;
    input{
        display:block;
        width:inherit;
        height:60px;
        padding:0 50px 5px;
        border: 2px solid #222;
        border-radius: 30px;
        font-size: 18px;
        box-sizing: border-box;
    }
    .search-icon{
        position:absolute;
        margin-top:18px;
        margin-left:18px;
        width:25px;
        height:25px;
    }
}
    
</style>