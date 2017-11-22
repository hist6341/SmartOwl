var modelcase = 'assets/earth.wt3';
var scalexyz = 1;



var World = {
	loaded: false,
	rotating: false,

	init: function initFn() {
		this.createModelAtLocation();
	},

	worldLoaded: function worldLoadedFn() {
    		World.loaded = true;
    		var e = document.getElementById('loadingMessage');
    		e.parentElement.removeChild(e);
    },


	createModelAtLocation: function createModelAtLocationFn() {

		/*
			First a location where the model should be displayed will be defined. This location will be relativ to the user.
		*/
		var location = new AR.RelativeLocation(null, 5, 1, 1);

		/*
			Next the model object is loaded.
		*/
		var modelEarth = new AR.Model(modelcase, {
			onLoaded: this.worldLoaded,
			scale: {
				x: scalexyz,
				y: scalexyz,
				z: scalexyz
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
	}


};

//World.init();