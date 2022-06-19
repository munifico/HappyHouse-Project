<template>
  <aside>
    <div class="head">🔥 실시간 인기 아파트</div>
    <ol>
      <li><em>1</em> {{ranking[0]}}</li>
      <li><em>2</em> {{ranking[1]}}</li>
      <li><em>3</em> {{ranking[2]}}</li>
      <li><em>4</em> {{ranking[3]}}</li>
      <li><em>5</em> {{ranking[4]}}</li>
    </ol>
    <p>해피하우스 실시간 아파트 검색 순위</p>
  </aside>
</template>
<script>
export default {
  data(){
    return{
      ranking:[
        "자이",
        "롯데 캐슬",
        "래미안",
        "e 편한세상",
        "아이파크",
        "힐 스테이트",
        "센트레빌",
        "자연애",
        "센트럴 자이",
        "태왕 아너스",
        "KCC 스위첸",
        "호반 베르디움",
        "우성",
        "서한 이다음"
        ],
    }
  },
  mounted() {
    this.shuffle(this.ranking);
  },
  methods:{
    shuffle(array){
      array.sort(() => Math.random() - 0.5);
    }
  }
};
</script>
<style lang="scss">
aside {
  margin-top:70px;
  width: 280px;
  height:280px;
  margin-left: 64px;
  padding: 30px 25px 14px;
  background: #ebf3fc;
  border-radius: 8%;
  .head {
    font-weight: 700;
    font-size: 18px;
    padding: 0 0 8px 6px;
  }

  ol {
    padding-left: 0;
    margin: 0;
    li {
      padding: 5px 5px;
      line-height: 17px;
      vertical-align: middle;
      em {
        display: inline-block;
        width: 20px;
        margin-right: 10px;
        font-weight: 700;
        color: #618bf3;
        text-align: center;
        line-height: 20px;
        vertical-align: middle;
      }
    }
  }

  p {
    margin-top: 4px;
    padding: 9px 5px 8px;
    color: #94969b;
    font-size: 11px;
    letter-spacing: -0.5px;
  }
}
</style>
