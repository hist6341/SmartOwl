var nowLat;

var nowLong;

var nowAlt;

var World = {

    init: function initFn() {

        AR.context.onScreenClick = function(){

              var location = new AR.GeoLocation(

                     parseFloat(nowLat),
                     parseFloat(nowLong),
                     1// altitude

              );

              var modelEarth = new AR.Model("assets/earth.wt3", {

                    scale: {x: 1,y: 1,z: 1}

              });
              var indicatorImage = new AR.ImageResource("assets/indi.png");
              var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                    verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
              });


            var obj = new AR.GeoObject(location, {

                       drawables: {

                            cam: [modelEarth],
                            indicator: [indicatorDrawable]
                       }

                    });


        };

        AR.context.onLocationChanged = function(latitude, longitude, altitude, accuracy){

            nowLat = latitude;
            nowLong = longitude;
            nowAlt = altitude;
        };

    },



}
World.init();
