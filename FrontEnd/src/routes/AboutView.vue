<template>
<div class="container bc">Developer</div>
<div class="container">
    <div class="about">
      <div class="photo">
        <img src="/static/icon/ohm.png"
        :alt="name" />
      </div>
      <div class="name">
        이재환
      </div>
      <div class="items">eejh22@gmail.com</div>
      <div class="items">https://jaehwany.github.io</div>
      <div class="items">+82-10-6677-2068</div>
    </div>
    <div class="about">
      <div class="photo">
        <img src="/static/icon/pet.png" alt="name"/>
      </div>
      <div class="name">
        한제규
      </div>
      <div class="items">anfidthtn@naver.com</div>
      <div class="items">https://github.com/anfidthtn</div>
      <div class="items">+82-10-2074-0750</div>
    </div>
</div>
</template>

<script>
export default {
}
</script>

<style lang="scss" scoped>
.bc{
  margin-top:30px;
  font-size: 35px;
  font-weight:600;
}
.container{
  display:flex;
  width:900px;

  .about{
    padding-top: 60px;
  text-align:  center;
  margin:auto;
  .photo{
    width: 300px;
    height:300px;
    margin:40px auto 20px;
    margin-top:0;
    padding:30px;
    border:4px solid rgb(168, 188, 224);
    border-radius: 30%;
    box-sizing: border-box;
    img{
      width:100%;
    }
  }

  .name{
    font-size: 40px;
    font-family: "Oswald",sans-serif;
    margin-bottom: 20px;
    font-weight: 500;
  }
  .items{
    font-size:18px;
    text-align: center;
  }
}
    }


</style>