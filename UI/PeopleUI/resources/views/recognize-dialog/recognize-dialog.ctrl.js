app.controller("recognizeCtrl", function($mdDialog, $mdToast, $scope, peopleService, picturesService, serverUrl) {
    $scope.step = '1';
    
    $scope.init = () => {
        // Wait for the dropzone div to load, them create the drop zone object
        angular.element(document.getElementById('#dropzone')).ready(() => {
            $scope.dropzone = new Dropzone("div#dropzone", {
                url: serverUrl + '/api/pictures/recognize',
                maxFiles: 1,
                dictDefaultMessage: "Drop your image here to recognize facaes in it"
            })

            $scope.dropzone.on("success", (file, res) => {
                console.log(res);
            })
        })
    }
});