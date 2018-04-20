app.controller("uploadPictureCtrl", function($scope, $mdToast, picturesService, peopleService, serverUrl) {
    // Init
    $scope.init = () => {
        // Wait for the dropzone div to load, them create the drop zone object
        angular.element(document.getElementById('#dropzone')).ready(() => {
            $scope.dropzone = new Dropzone("div#dropzone", {
                url: serverUrl + '/api/pictures/upload',
                init: () => {
                    // INIT DROPZONE
                },
                maxFiles: 1,
                dictDefaultMessage: "Drop your image here to start uploading and proccessing it"
            })
        })
    };
})