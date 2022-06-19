<template>
  <div class="container" id="mapbox" style="height: 700px"></div>
</template>

<script>
import http from "../../api/http";
export default {
  mounted() {
    const scriptGoogle = document.createElement("script");
    scriptGoogle.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyCGkkT42fQhr9D0RRKvdee-MdkpAIiMav4&callback=this.initmap&v=weekly&libraries=visualization";
    scriptGoogle.async = true;
    document.head.appendChild(scriptGoogle);
    const scriptKaKao = document.createElement("script");
    scriptKaKao.type = "text/javascript";
    scriptKaKao.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=c5570e58f52bf1e33a745c40d47f7d85&libraries=services&autoload=false";
    scriptKaKao.onload = () => kakao.maps.load(this.loadGeocoder);
    document.head.appendChild(scriptKaKao);
  },
  created() {
    setTimeout(() => {
      this.initMap();
    }, 1000);
  },
  data() {
    return {
      tradeInfoDtoList: [],
      markersArray: [],
      map: null,
      aptInfo: [],
      geocoder: null,
    };
  },
  methods: {
    loadGeocoder() {
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    async initMap() {
      const myLatLng = { lat: 36.02, lng: 127.58 };
      var map = await new google.maps.Map(document.getElementById("mapbox"), {
        zoom: 6.95,
        center: myLatLng,
      });
      this.map = map;
      this.clearOverlays();
      this.markersArray.push(
        new google.maps.Marker({
          position: myLatLng,
          map,
          label: {
            text: `0`,
            fontFamily: "Material Icons",
            color: "#ffffff",
            fontSize: "18px",
          },
          title: "Hello World!",
        })
      );
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
      Promise.all(promiseArray)
        .then((value) => {
          // console.log(value);
          for (let obj of value) {
            this.aptInfo.get(obj[0]).latlng = obj[1];
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
        });
      });
    },
    paintMap() {
      this.clearOverlays();
      // console.log(this.aptInfo);
      // alert("dasfasd");
      if (this.aptInfo.size == 0) {
        this.initMap();
        alert("검색 결과가 없습니다.");
        return;
      }
      this.$store.commit("apt/enrollResults", {
        aptInfo: this.aptInfo,
      });
      /**
       * Ma : 위도
       * La : 경도
       * min max를 구해서 해당 범위에 맞게 중심좌표와 zoom의 정도를 결정
       * 물론 경도의 경우에는 날짜변경선 지나가면 문제가 되지만, 지금의 검색 조건(대한민국 내부)정도에서는 문제없다.
       */
      let minMa = 90;
      let maxMa = -90;
      let minLa = 180;
      let maxLa = -180;
      for (let key of this.aptInfo.keys()) {
        let lat = this.aptInfo.get(key).latlng.Ma;
        let lng = this.aptInfo.get(key).latlng.La;
        if (minMa > lat) {
          minMa = lat;
        }
        if (maxMa < lat) {
          maxMa = lat;
        }
        if (minLa > lng) {
          minLa = lng;
        }
        if (maxLa < lng) {
          maxLa = lng;
        }
      }
      console.log(minMa);
      console.log(maxMa);
      console.log(minLa);
      console.log(maxLa);
      /**
       * center : 각 좌표값들의 최저 최고점의 평균값
       */
      let center = { lat: (maxMa + minMa) / 2, lng: (maxLa + minLa) / 2 };
      /**
       * zoom : 1에 가까울수록 축소, 20에 가까울수록 확대
       * 위도와 경도값 중 차이가 큰쪽으로 log를 씌우는데, 해당 값을 분모에 두어서
       * 차이의 크기가 작아질수록 log값이 커지도록 만든다.
       * (log(n)의 값은 n이 작을수록 작아진다. 그러니까 분모에 두어서 log(1/n)에서 n이 작을수록 log는 커지게 그런식으로 한 것이다.)
       * 만약 지점이 1개밖에 없으면 log값이 무한으로 발산하는데 이 때는 그냥 해당 지점을 어느정도의 zoom으로 보게 한다.
       */
      let zoom = Math.min(9 + 1.2 * Math.log(3.2 / Math.max(maxMa - minMa, maxLa - minLa)), 17.5);
      console.log(zoom);
      var map = new google.maps.Map(document.getElementById("mapbox"), {
        zoom: zoom,
        center: center,
      });
      this.map = map;
      let index = 0;
      for (let key of this.aptInfo.keys()) {
        let lat = this.aptInfo.get(key).latlng.Ma;
        let lng = this.aptInfo.get(key).latlng.La;
        let marker = new google.maps.Marker({
          position: { lat: lat, lng: lng },
          label: {
            text: `${this.aptInfo.get(key).count}`,
            fontFamily: "Material Icons",
            color: "#ffffff",
            fontSize: "18px",
          },
          // icon: {
          //   path: google.maps.SymbolPath.CIRCLE,
          //   strokeWeight: 5,
          //   strokeColor: "#ffffff",
          //   fillOpacity: 0.1,
          //   scale: 15,
          // },
          map: map,
          title: key,
          opacity: 0.8,
          animation: Animation.BOUNCE,
        });

        const contentHtml = `
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
	        `;

        let infoWindow = new google.maps.InfoWindow({
          content: contentHtml,
        });
        google.maps.event.addListener(marker, "click", function () {
          if (marker.getAnimation() != null) {
            // marker.setAnimation(null);
          } else {
            // marker.setAnimation(google.maps.Animation.BOUNCE);
          }
        });
        marker.addListener("mouseover", () => {
          infoWindow.open(this.map, marker);
        });
        marker.addListener("mouseout", () => {
          infoWindow.close();
        });
        this.markersArray.push(marker);
        index++;
      }
      console.log(this.map);
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~/scss/main";
.container {
  margin-top: 30px;
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
</style>
