<template>
  <aside>
    <div class="head">π₯ μ€μκ° μΈκΈ° μννΈ</div>
    <ol>
      <li><em>1</em> {{ranking[0]}}</li>
      <li><em>2</em> {{ranking[1]}}</li>
      <li><em>3</em> {{ranking[2]}}</li>
      <li><em>4</em> {{ranking[3]}}</li>
      <li><em>5</em> {{ranking[4]}}</li>
    </ol>
    <p>ν΄νΌνμ°μ€ μ€μκ° μννΈ κ²μ μμ</p>
  </aside>
</template>
<script>
export default {
  data(){
    return{
      ranking:[
        "μμ΄",
        "λ‘―λ° μΊμ¬",
        "λλ―Έμ",
        "e νΈνμΈμ",
        "μμ΄νν¬",
        "ν μ€νμ΄νΈ",
        "μΌνΈλ λΉ",
        "μμ°μ ",
        "μΌνΈλ΄ μμ΄",
        "νμ μλμ€",
        "KCC μ€μμ²Έ",
        "νΈλ° λ² λ₯΄λμ",
        "μ°μ±",
        "μν μ΄λ€μ"
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
