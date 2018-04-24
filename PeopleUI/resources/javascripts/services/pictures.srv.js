services.service("picturesService", function(serverUrl, $http) {
    this.attachFaceToPerson = (pictureID, faceID, personID) => {
        return $http.post(serverUrl + '/api/pictures/faces', { pictureID: pictureID, faceID: faceID, personID: personID });
    }

    this.removePicture = (pictureID) => {
        throw "not implemented";;
    }

    this.detachFaceFromPerson = (pictureID, faceID, personID) => {
        throw "notImplemeted";
    }
})