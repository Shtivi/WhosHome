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
            scope.backgroundImage = "'url('+{{" + scope.imgSrc + "}}+')'";
            scope.elem = elem;
            scope.imgElem = elem.find('img')[0];

            elem.find('img').on('load', (ev) => {
                // Trigger the watch function
                scope.faces = scope.faces;
                scope.$digest();

                scope.$watch('faces', (newValue, oldValue) => {
                    if (newValue != null) {
                        var afterFitting = scope.fitFaceRects(scope.imgSrc, scope.imgElem, newValue);
                        angular.copy(afterFitting, scope.faces);
                    }
                })
            })

            scope.faceClicked = (face) => {
                $peoplePicture.emit("faceClicked", face);
            }

            scope.fitFaceRects = (imgSrc, imgElem, faces) => {
                var originalSize = originalImageSize(imgSrc);
                var xRatio = imgElem.width / originalSize.width;
                var yRatio = imgElem.height / originalSize.height;

                return faces.map((face) => {
                    var adapted = {};
                    var props = Object.keys(face);

                    for (var propIndex in props) {
                        var prop = props[propIndex];
                        adapted[prop] = face[prop];
                    }

                    adapted.x = face.x * xRatio;
                    adapted.y = face.y * yRatio;
                    adapted.width = face.width * xRatio;
                    adapted.height = face.width * yRatio;
                    
                    return adapted;
                })
            }
        },
        template: 
            '<div class="people-picture">' +
            '   <img ng-src="{{imgSrc}}" class="picture-element">' +
            '   <div ng-repeat="face in faces" class="face" ng-class="{recognized: face.person}" ng-style="{top: face.y, left: face.x, height: face.height, width: face.width}" ng-click="faceClicked(face)">' +
            '       <md-tooltip md-direction="bottom">' +
            '           <span ng-show="!face.personID">Not recognized yet</span>' +
            '           <span ng-show="face.person">{{face.person.firstname}} {{face.person.lastname}}</span>' +
            '       </md-tooltip>' +
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