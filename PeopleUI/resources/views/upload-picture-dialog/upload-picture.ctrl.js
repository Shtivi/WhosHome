app.controller("uploadPictureCtrl", function($scope, $mdToast, picturesService, peopleService, serverUrl, $peoplePicture) {
    $scope.step = 1;

    function adaptFacesToImageSize(faces) {
        var img = document.getElementById('#picture');
        
        faces.map((face) => {
            var xRatio = face.width / img.width;
            var yRatio = face.height / img.height;
            
            return  {
                x: face.x * xRatio,
                y: face.y * yRatio,
                width: face.width * xRatio,
                height: face.height * xRatio
            }
        })
    }

    $scope.init = () => {
        // Wait for the dropzone div to load, them create the drop zone object
        angular.element(document.getElementById('#dropzone')).ready(() => {
            $scope.dropzone = new Dropzone("div#dropzone", {
                url: serverUrl + '/api/pictures/upload',
                maxFiles: 1,
                dictDefaultMessage: "Drop your image here to start uploading and proccessing it"
            })

            $scope.dropzone.on("success", (file, res) => {
                console.log(res);

                $scope.fileURL = file.dataURL;
                $scope.picture = res;
                $scope.step = 2;

                $scope.$digest();
            })
        })

        $peoplePicture.on("faceClicked", (face) => console.log(face));
    };
})