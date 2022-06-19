<template>
    <div class="container">
        
        <!-- 분류 Drop-down -->
        <div class="selects">
            <select
            v-for="filter in filters"
            v-model="$data[filter.name]"
            :key="filter.name"
            class="form-select">
                <option
                v-for="item in filter.items"
                :key="item">
                {{item}}
                </option>
            </select>
        </div>

        <!-- 내용 input-->
        <input 
        v-model="searchName"
        class="form-control"
        type="text"
        placeholder="blank 1"
        @keyup.enter="apply"
        />

        <!-- 검색 button-->
        <button class="btn btn-primary"
        @click="apply">
        Search

        </button>

    </div>
</template>

<script>

    export default {
        data(){
            return {
                searchName:'',
                type: '제목',
                filters: [
                    {
                        name: 'type',
                        items: ['제목','내용','작성자']
                    }
                ]
            }
        },
        methods:{
            apply(){
                this.$store.dispatch('board/searchArticles',{
                    title: this.title,
                    type: this.type
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
.container{
    display:flex;
    > * {
        margin-right:10px;
        font-size: 15px;
    }

    .selects{
        display:flex;
        select{
            width : 100px;
            margin-right:10px;
            font-size:15px;
            font-weight: 700;
        }
         &:last-child{
        margin-right: 0;
        }
    }
    .form-control{
        width:500px;
        height:40px;
        flex-shrink: 0;
    }

    .btn{
        width:100px;
        height:40px;
        font-weight: 700;
        flex-shrink: 0;
        background-color: white;
    }
}
</style>