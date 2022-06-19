<template>
  <article>
    <div class="head">
    {{article.subject}}
    </div>
    <div class="body">
      <p>{{article.content}}</p>


    </div>
    <div class="foot">
      
      
      <div class="info">닉네임 : {{article.userid}}</div>
      <div class="info">{{article.regtime}}</div>

    </div>
  </article>

</template>

<script>
import moment from "moment";
import{
  EyeIcon
} from "vue-feather-icons";

export default {  
  name: "ReviewListItem",
  components:{EyeIcon},
  props: {
        article:{
            type:Object,
            default:() => ({})
        }
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>



<style lang="scss">
  article{
    padding:30px;
    
    border: 2px solid rgb(183, 202, 255);
    border-radius: 10px;
    margin-left:10px;
    margin-bottom: 10px;
    .head{
        font-weight:bold;
        font-size: 18px;
        line-height: 1.4em;
        margin-top:1px;
        height:52px;
        color:rgb(2, 33, 90);
      
    }
    .body{
      margin-top: 4px;
      color:#222;
      font-size: 14px;
      line-height: 1.25em;
    }
    .foot{
      margin-top:8px;
      display:flex;
      font-weight: 500;
      font-size:13px;
      justify-content: space-between;
      opacity: 0.7;
    }
  }  
</style>
