var World = {
	loaded: false,
	rotating: false,

	init: function initFn() {
		this.createModel();
	},

	createModel: function createModelAtLocation() {

        AR.context.onLocationChanged = function (latitude, longitude, altitude, accuracy) {
          var location = new AR.RelativeLocation(null, latitude, longitude, accuracy);
          obj = new AR.GeoObject
            (location,
                {
                     drawables: {
                        cam: [modelEarth],
                        indicator: [indicatorDrawable]
                     }
                }
            );
        }
		/*
			First a location where the model should be displayed will be defined. This location will be relativ to the user.	
		*/
		var location = new AR.RelativeLocation(null, 15, 20, 2);

		/*
			Next the model object is loaded.
		*/
		var modelEarth = new AR.Model("assets/earth.wt3", {
			onLoaded: this.worldLoaded,
			scale: {
				x: 0.5,
				y: 0.5,
				z: 0.5
			}
		});

        var indicatorImage = new AR.ImageResource("assets/indi.png");

        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });

		/*
			Putting it all together the location and 3D model is added to an AR.GeoObject.
		*/
		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [modelEarth],
               indicator: [indicatorDrawable]
            }
        });
	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById('loadingMessage');
		e.parentElement.removeChild(e);
	}
};

World.init();
