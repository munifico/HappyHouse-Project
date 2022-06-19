<template>
  <transition name="modal" appear>
    <section class="modal">
      <div class="modal__window">
        <div class="apt-details">
          <!-- <div :style="{ backgroundImage: `url(${theApt.Poster})` }" class="poster"></div> -->
          <div class="specs">
            <div class="title">
              {{ aptInfo.aptName }}
            </div>
            <div class="labels">
              <span
                ><b>{{ aptInfo.count }}</b
                >건의 거래정보가 있습니다.</span
              >
            </div>
            <div class="plot">
              <!-- {{ theApt.Plot }} -->
            </div>
            <div class="ratings">
              <!-- <h3>Ratings</h3> -->
            </div>
            <div>
              <h3>Build Year</h3>
              <h2>{{ aptInfo.buildYear }}</h2>
            </div>
            <div>
              <h3>Price</h3>
              <h2>{{ Math.min(...aptInfo.price) }} 만원 ~ {{ Math.max(...aptInfo.price) }} 만원</h2>
            </div>
            <div>
              <h3>Size</h3>
              <h2>{{ Math.min(...aptInfo.size) }} m<sup>2</sup> ~ {{ Math.max(...aptInfo.size) }} m<sup>2</sup></h2>
            </div>
            <div>
              <h3>Address</h3>
              <h2>{{ aptInfo.address }}</h2>
            </div>
            <div>
              <h3>Map &amp; Directions</h3>
            </div>
            <div class="thismapboxDiv" style="padding-right: 20px">
              <div id="thismapbox" style="height: 400px; position: relative; overflow: hidden"></div>
            </div>
          </div>
          <div class="detail-list">
            <h3>Trade History</h3>
            <div class="trade-history-list">
              <div v-for="(trash, index) in aptInfo.price" :key="trash" class="card">
                <div class="img-gradient"></div>
                <img src="../../../static/icon/tradeIcon.png" style="width: 70%; max-height: 100%" srcset="" />
                <div class="text-wrap">
                  <div class="text-box">
                    <h1>거래가</h1>
                    <p>{{ aptInfo.price[index] }} 만원</p>
                  </div>
                  <div class="text-box">
                    <h1>전용면적</h1>
                    <p>{{ aptInfo.size[index] }} m<sup>2</sup></p>
                  </div>
                  <div class="text-box">
                    <h1>거래일자</h1>
                    <p>{{ aptInfo.dealDate[index] }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal__overlay" @click.self="$emit('close')"></div>
    </section>
  </transition>
</template>

<script>
import axios from "axios";
export default {
  components: {
    axios,
  },
  data() {
    return {
      thismap: null,
    };
  },
  mounted() {
    const scriptKaKao = document.createElement("script");
    scriptKaKao.type = "text/javascript";
    scriptKaKao.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=c5570e58f52bf1e33a745c40d47f7d85&libraries=services&autoload=false";
    scriptKaKao.onload = () => kakao.maps.load(this.loadGeocoder);
    document.head.appendChild(scriptKaKao);
  },
  created() {
    setTimeout(() => {
      this.initKakaothisMap();
    }, 300);
  },
  computed: {
    aptInfo() {
      return this.$store.state.apt.modalData;
    },
  },
  methods: {
    async initKakaothisMap() {
      var container = document.getElementById("thismapbox");
      var options = {
        center: this.aptInfo.latlng,
        level: 13,
      };
      this.thismap = new kakao.maps.Map(container, options);
      // var mapTypeControl = new kakao.maps.MapTypeControl();
      // this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
      var zoomControl = new kakao.maps.ZoomControl();
      this.thismap.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      var marker = new kakao.maps.Marker({
        position: this.aptInfo.latlng,
        map: this.thismap,
      });
      var bounds = new kakao.maps.LatLngBounds();
      bounds.extend(this.aptInfo.latlng);
      this.thismap.setBounds(bounds);
      // this.clearOverlays();
    },
  },
};
</script>

<style lang="scss">
@import "~/scss/main";
.modal {
  position: absolute;
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;

  &__overlay {
    position: absolute;
    width: 100%;
    height: 100%;

    background-color: black;
    opacity: 0.8;
  }

  &__window {
    width: 80rem;
    height: 60rem;
    border-radius: 0.4rem;
    overflow: hidden;
    padding: 1rem;
    z-index: 1;

    background-color: white;
  }

  &__title {
  }

  &__content {
  }

  &__footer {
  }

  // 상황에 따라 transition 변경가능 enter,leave class는 상단 문서 참고
  &-enter,
  &-leave-to {
    opacity: 0;
    transition: opacity 0.4s ease;
  }

  &-enter-to,
  &-leave {
    transition: opacity 0.4s ease;
  }
}

.apt-details {
  display: flex;
  color: $gray-600;
  height: 100%;
  width: 100%;
  padding: 50px;
  .detail-list {
    width: 30%;
    height: 100%;
    h3 {
      width: 100%;
      color: $black;
      font-family: "Oswald", sans-serif;
      font-size: 20px;
    }
    .trade-history-list::-webkit-scrollbar {
      width: 10px;
    }
    .trade-history-list::-webkit-scrollbar-thumb {
      background-color: #2f3542;
      border-radius: 10px;
      background-clip: padding-box;
      border: 2px solid transparent;
    }
    .trade-history-list::-webkit-scrollbar-track {
      background-color: grey;
      border-radius: 10px;
      box-shadow: inset 0px 0px 5px white;
    }
    .trade-history-list {
      flex-shrink: 0;
      width: 100%;
      padding: 10px;
      height: inherit;
      // margin-right: 30px;
      border-radius: 10px;
      background-color: $gray-200;
      background-size: cover;
      background-position: center;
      overflow: auto;

      .card {
        transition: 0.3s;
        position: relative;
        border-radius: 8px;
        width: 100%;
        height: 31%;
        overflow: hidden;
        display: inline-block;
        box-shadow: 0 20px 35px rgba(0, 0, 0, 0.3);
        transform: scale(1);
      }
      .card:hover {
        transform: scale(0.95);
      }
      .img-gradient {
        position: absolute;
        width: 100%;
        height: 100%;
        bottom: 0;
        background: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.8));
        z-index: 1;
      }
      .text-wrap {
        position: absolute;
        bottom: 0;
        right: 0%;
        z-index: 10;
        padding: 5%;
        height: 100%;
        text-align: right;
      }
      .text-wrap h1 {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 8px;
        color: #fff;
      }
      .text-wrap p {
        color: #fff;
        line-height: 1.5;
      }
      .play-button {
        position: absolute;
        right: 24px;
        top: 24px;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        // background: #004fff url(../img/icon-play.png) center no-repeat;
        background-size: 20px;
        z-index: 2;
      }
    }
  }
  .specs {
    flex-grow: 1;
    .title {
      color: $black;
      font-family: "Oswald", sans-serif;
      font-size: 50px;
      line-height: 1;
      margin-bottom: 30px;
    }
    .labels {
      color: $primary;
      span {
        &::after {
          content: "\00b7";
          margin: 0 6px;
        }
        &:last-child::after {
          display: none;
        }
      }
    }
    .plot {
      margin-top: 20px;
    }
    .ratings {
    }
    h3 {
      margin: 24px 0 6px;
      color: $black;
      font-family: "Oswald", sans-serif;
      font-size: 20px;
    }
    h2 {
      font-family: "Oswald", sans-serif;
      font-size: 15px;
    }
  }
}
</style>
