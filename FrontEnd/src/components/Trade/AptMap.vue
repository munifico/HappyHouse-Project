<template>
  <div class="container" id="mapbox" style="height: 700px">
    <div class="map_wrap">
      <Loader v-if="loading" />
      <ul v-if="mouseInfoMessage" id="category">
        <li v-html="mouseInfoMessage" id="BK9" data-order="0"></li>
      </ul>
    </div>
  </div>
</template>

<script>
import http from "../../api/http";
export default {
  computed: {
    aptlist() {
      return this.$store.state.apt.apts;
    },
    mouseInfoMessage() {
      return this.$store.state.apt.mouseInfoMessage;
    },
    loading() {
      return this.$store.state.apt.loading;
    },
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
      this.initKakaoMap();
    }, 1000);
  },
  data() {
    return {
      tradeInfoDtoList: [],
      markersArray: [],
      map: null,
      aptInfo: [],
      geocoder: null,

      //마커 클릭이벤트용
      selectedMarker: null,
    };
  },
  methods: {
    loadGeocoder() {
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    async initKakaoMap() {
      var container = document.getElementById("mapbox");
      var options = {
        center: new kakao.maps.LatLng(36.02, 127.58),
        level: 13,
      };
      this.map = new kakao.maps.Map(container, options);
      // var mapTypeControl = new kakao.maps.MapTypeControl();
      // this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
      var zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

      this.clearOverlays();
      // var markerPosition = new kakao.maps.LatLng(36.02, 127.58);
      // 마커를 생성합니다
      // var marker = new kakao.maps.Marker({
      //   position: markerPosition,
      // });
      // this.markerEventProcess(marker, 0);
      // this.markersArray.push(marker);
      // marker.setMap(this.map);
    },
    clearOverlays() {
      for (var i = 0; i < this.markersArray.length; i++) {
        this.markersArray[i].setMap(null);
      }
      this.markersArray.length = 0;
    },
    async mapSearch(searchInfo) {
      const url = `/tradeInfo/${searchInfo["gugunInfo"][1].substring(0, 5)}/${searchInfo["sidoInfo"][0]}/${searchInfo["gugunInfo"][0]}/${searchInfo["dongInfo"][0]}/${searchInfo["year"]}/${
        searchInfo["month"]
      }/${searchInfo["aptName"]}`;
      // console.log(url);
      await http.get(url).then(({ data }) => {
        this.tradeInfoDtoList = data;
        this.makeMapData();
      });
    },
    makeMapData() {
      // console.log("메이크맵데이터");
      this.aptInfo = new Map();
      // console.log(this.tradeInfoDtoList);
      for (let dto in this.tradeInfoDtoList) {
        // 묶이지 않은 주소는 새로 키를 만든다.
        dto = this.tradeInfoDtoList[dto];
        if (this.aptInfo.get(dto["aptName"]) == undefined) {
          let obj = {
            aptName: dto["aptName"],
            address: dto["address"],
            latlng: null,
            dong: dto["dong"],
            price: [],
            priceNum: [],
            size: [],
            dealDate: [],
            buildYear: dto["buildYear"],
            count: 0,
          };
          this.aptInfo.set(dto["aptName"], obj);
        }
        let obj = this.aptInfo.get(dto["aptName"]);
        var price = dto["price"];
        obj.price.push(price);
        obj.priceNum.push(dto["price"]);
        obj.size.push(dto["size"]);
        obj.dealDate.push(dto["dealDate"]);
        obj.count++;
      }
      // console.log(this.aptInfo);
      const promiseArray = [];
      for (let key of this.aptInfo.keys()) {
        promiseArray.push(this.getCoords(key));
      }
      Promise.allSettled(promiseArray)
        .then((value) => {
          // console.log(value);
          for (let obj of value) {
            this.aptInfo.get(obj.value[0]).latlng = obj.value[1];
          }
          return 0;
        })
        .then(() => {
          this.paintMap();
        });
    },
    async getCoords(key) {
      return new Promise((resolve) => {
        this.geocoder.addressSearch(this.aptInfo.get(key).address, function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            resolve([key, new kakao.maps.LatLng(result[0].y, result[0].x)]);
          }
          // else{
          //   reject([key, new kakao.maps.LatLng(90, 180)]);
          // }
        });
      });
    },
    paintMap() {
      this.clearOverlays();
      if (this.aptInfo.size == 0) {
        alert("검색 결과가 없습니다.");
        return;
      }
      var bounds = new kakao.maps.LatLngBounds();
      for (let key of this.aptInfo.keys()) {
        let lat = this.aptInfo.get(key).latlng.Ma;
        let lng = this.aptInfo.get(key).latlng.La;
        bounds.extend(new kakao.maps.LatLng(lat, lng));
      }

      this.map.setBounds(bounds);

      var map = this.map;
      var index = 0;
      for (let key of this.aptInfo.keys()) {
        let lat = this.aptInfo.get(key).latlng.Ma;
        let lng = this.aptInfo.get(key).latlng.La;
        var marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(lat, lng),
          map: map,
        });
        marker.setTitle(key);
        this.markerEventProcess(marker, key);
        this.markersArray.push(marker);
        index++;
      }
      // console.log(this.map);
    },
    markerEventProcess(marker, key) {
      var MARKER_WIDTH = 33, // 기본, 클릭 마커의 너비
        MARKER_HEIGHT = 36, // 기본, 클릭 마커의 높이
        OVER_MARKER_HEIGHT = 42, // 오버 마커의 높이
        SPRITE_GAP = 10; // 스프라이트 이미지에서 마커간 간격

      var gapX = MARKER_WIDTH + SPRITE_GAP, // 스프라이트 이미지에서 마커로 사용할 이미지 X좌표 간격 값
        originY = (MARKER_HEIGHT + SPRITE_GAP) * 0, // 스프라이트 이미지에서 기본, 클릭 마커로 사용할 Y좌표 값
        overOriginY = (OVER_MARKER_HEIGHT + SPRITE_GAP) * 0, // 스프라이트 이미지에서 오버 마커로 사용할 Y좌표 값
        normalOrigin = new kakao.maps.Point(0, originY), // 스프라이트 이미지에서 기본 마커로 사용할 영역의 좌상단 좌표
        clickOrigin = new kakao.maps.Point(gapX, originY), // 스프라이트 이미지에서 마우스오버 마커로 사용할 영역의 좌상단 좌표
        overOrigin = new kakao.maps.Point(gapX * 2, overOriginY); // 스프라이트 이미지에서 클릭 마커로 사용할 영역의 좌상단 좌표
      // 마커를 생성하고 지도위에 표시합니다
      this.addMarker(marker, normalOrigin, overOrigin, clickOrigin, key);
    },
    addMarker(marker, normalOrigin, overOrigin, clickOrigin, key) {
      var MARKER_WIDTH = 33, // 기본, 클릭 마커의 너비
        MARKER_HEIGHT = 36, // 기본, 클릭 마커의 높이
        OFFSET_X = 12, // 기본, 클릭 마커의 기준 X좌표
        OFFSET_Y = MARKER_HEIGHT, // 기본, 클릭 마커의 기준 Y좌표
        OVER_MARKER_WIDTH = 40, // 오버 마커의 너비
        OVER_MARKER_HEIGHT = 42, // 오버 마커의 높이
        OVER_OFFSET_X = 13, // 오버 마커의 기준 X좌표
        OVER_OFFSET_Y = OVER_MARKER_HEIGHT; // 오버 마커의 기준 Y좌표

      var markerSize = new kakao.maps.Size(MARKER_WIDTH, MARKER_HEIGHT), // 기본, 클릭 마커의 크기
        markerOffset = new kakao.maps.Point(OFFSET_X, OFFSET_Y), // 기본, 클릭 마커의 기준좌표
        overMarkerSize = new kakao.maps.Size(OVER_MARKER_WIDTH, OVER_MARKER_HEIGHT), // 오버 마커의 크기
        overMarkerOffset = new kakao.maps.Point(OVER_OFFSET_X, OVER_OFFSET_Y); // 오버 마커의 기준 좌표
      // 기본 마커이미지, 오버 마커이미지, 클릭 마커이미지를 생성합니다
      var normalImage = this.createMarkerImage(markerSize, markerOffset, normalOrigin),
        overImage = this.createMarkerImage(overMarkerSize, overMarkerOffset, overOrigin),
        clickImage = this.createMarkerImage(markerSize, markerOffset, clickOrigin);

      // 마커를 생성하고 이미지는 기본 마커 이미지를 사용합니다
      marker.setImage(normalImage);

      // 마커 객체에 마커아이디와 마커의 기본 이미지를 추가합니다
      marker.normalImage = normalImage;
      // 마커에 mouseover 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, "mouseover", () => {
        // 클릭된 마커가 없고, mouseover된 마커가 클릭된 마커가 아니면
        // 마커의 이미지를 오버 이미지로 변경합니다
        if (!this.selectedMarker || !this.markersEquals(this.selectedMarker, marker)) {
          marker.setImage(clickImage);
        }
        this.$store.commit("apt/setMouseInfoMessage", {
          infoMessage: `
	        <div class="card">
	            <div class="card-body">
	                <div class="small text-uppercase fw-bold text-muted">${this.aptInfo.get(key).dong}</div>
	                <div class="mb-3">
	                    <span class="display-6 fw-bold">${key}</span>
	                </div>
	                <div class="mb-3">
	                    <span class="display-7 fw-bold">${this.aptInfo.get(key).count}</span>
	                    <span class="text-muted">개의 거래정보가 있습니다.</span>
	                </div>
	            </div>
	        </div>
	        `,
        });
      });

      // 마커에 mouseout 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, "mouseout", () => {
        // 클릭된 마커가 없고, mouseout된 마커가 클릭된 마커가 아니면
        // 마커의 이미지를 기본 이미지로 변경합니다
        if (!this.selectedMarker || !this.markersEquals(this.selectedMarker, marker)) {
          marker.setImage(normalImage);
        }
        this.$store.commit("apt/setMouseInfoMessage", {
          infoMessage: ``,
        });
      });

      // 마커에 click 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, "click", () => {
        this.$parent.openModal(this.aptInfo.get(key));
        // // 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
        // // 마커의 이미지를 클릭 이미지로 변경합니다
        // if (!this.selectedMarker || !this.markersEquals(this.selectedMarker, marker)) {
        //   // 클릭된 마커 객체가 null이 아니면
        //   // 클릭된 마커의 이미지를 기본 이미지로 변경하고
        //   // !!this.selectedMarker && this.selectedMarker.setImage(this.selectedMarker.normalImage);

        //   // 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
        //   // marker.setImage(clickImage);
        //   // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
        //   this.selectedMarker = marker;
        // } else {
        //   // marker.setImage(normalImage);
        //   this.selectedMarker = null;
        // }
      });
    },
    // MakrerImage 객체를 생성하여 반환하는 함수입니다
    createMarkerImage(markerSize, offset, spriteOrigin) {
      var MARKER_WIDTH = 33, // 기본, 클릭 마커의 너비
        MARKER_HEIGHT = 36, // 기본, 클릭 마커의 높이
        SPRITE_MARKER_URL = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markers_sprites2.png", // 스프라이트 마커 이미지 URL
        SPRITE_WIDTH = 126, // 스프라이트 이미지 너비
        SPRITE_HEIGHT = 146; // 스프라이트 이미지 높이

      var markerSize = new kakao.maps.Size(MARKER_WIDTH, MARKER_HEIGHT), // 기본, 클릭 마커의 크기
        spriteImageSize = new kakao.maps.Size(SPRITE_WIDTH, SPRITE_HEIGHT); // 스프라이트 이미지의 크기

      var markerImage = new kakao.maps.MarkerImage(
        SPRITE_MARKER_URL, // 스프라이트 마커 이미지 URL
        markerSize, // 마커의 크기
        {
          offset: offset, // 마커 이미지에서의 기준 좌표
          spriteOrigin: spriteOrigin, // 스트라이프 이미지 중 사용할 영역의 좌상단 좌표
          spriteSize: spriteImageSize, // 스프라이트 이미지의 크기
        }
      );

      return markerImage;
    },
    markersEquals(marker1, marker2) {
      if (!marker1 || !marker2) {
        return false;
      }
      if (marker1.getPosition().getLat() != marker2.getPosition().getLat()) {
        return false;
      }
      if (marker1.getPosition().getLng() != marker2.getPosition().getLng()) {
        return false;
      }
      return true;
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~/scss/main";
.container {
  margin-top: 30px;
  width:1000px;
  height:500px;
  z-index:1;
  .inner {
    background-color: $gray-200;
    padding: 10px 0;
    border-radius: 4px;
    text-align: center;
    &.no-result {
      padding: 70px 0;
    }
  }
  .message {
    color: $gray-400;
    font-size: 20px;
  }
  .apts {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
}

.map_wrap,
.map_wrap * {
  position: relative;
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: relative;
  top: 10px;
  left: 10px;
  max-width: 302px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 300px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
