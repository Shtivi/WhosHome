directives.directive("peoplePicture", function($peoplePicture) {
    function originalImageSize(base64, callback) {
        var img = new Image();
        img.src = base64;
        return {width: img.width, height: img.height};
    }

    return {
        restrict: 'E',
        scope: {
            imgSrc: '=',
            faces: '=',
            eventListeners: '='
        },
        link: (scope, elem, attrs) => {
            elem.find('img').on('load', (ev) => {
                var originalSize = originalImageSize(scope.imgSrc);
                var imgElem = ev.srcElement;
                var xRatio = imgElem.width / originalSize.width;
                var yRatio = imgElem.height / originalSize.height;

                scope.faces = scope.faces.map((face) => {
                    return {
                        x: face.x * xRatio,
                        y: face.y * yRatio,
                        width: face.width * xRatio,
                        height: face.height * yRatio,
                        _id: face._id
                    }
                })

                scope.$digest();
            })

            scope.faceClicked = (face) => {
                $peoplePicture.emit("faceClicked", face);
            }
        },
        template: 
            '<div class="people-picture">' +
            '   <img ng-src="{{imgSrc}}" class="picture-element">' +
            '   <div ng-repeat="face in faces" class="face" ng-style="{top: face.y, left: face.x, height: face.height, width: face.width}" ng-click="faceClicked(face)">' +
            '       <md-tooltip md-direction="bottom"><span ng-show="!face.personID">Not recognized yet</span></md-tooltip>' +
            '   </div>' +
            '</div>'
    }
})

directives.service('$peoplePicture', function() {
    var listeners = {
        faceClicked: []
    };

    this.on = (eventName, eventListener) => {
        if (listeners[eventName]) {
            listeners[eventName].push(eventListener);
        } else {
            throw "Event not exist: " + eventName;
        }
    }

    this.emit = (eventName, arg) => {
        if (!listeners[eventName]) {
            throw "Event not exist: " + eventName;
        } else {
            listeners[eventName].forEach((listener) => {
                try {
                    listener(arg);
                } catch(err) {
                    console.log("Error invoking listener, continued to next one");
                }
            })
        }
    }
})