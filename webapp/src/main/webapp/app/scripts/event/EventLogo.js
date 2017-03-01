'use strict';
eventModule.controller('EventLogoController', function($scope, Event, FileUploader, ErrorPropagationService) {

    $scope.uploader = buildUploader();
    $scope.event = Event.data;
    $scope.link = $scope.event.eventLogo.linkLarge;

    $scope.removeEventLogo = function() {
        Event.removeEventLogo($scope.event.identifier).then(function() {
            $scope.link = undefined;
        }, function() {
            ErrorPropagationService.manualErrorAlert('Löschen des Logos fehlgeschlagen.');
        });
    };

    function buildUploader() {
        return Event.logoUploader($scope.event.identifier, function() {
            ErrorPropagationService.resetErrors();
            $scope.uploading = true;
        }, function(eventLogo) {
            $scope.link = eventLogo.linkLarge + "?" + new Date().getTime();
            $scope.uploadEventLogoSuccess = true;
            $scope.uploading = false;
        }, function() {
            $scope.uploading = false;
            ErrorPropagationService.manualErrorAlert('Upload fehlgeschlagen! Datei muss eins der folgenden Bildformate sein (PNG, JPG) und darf nicht größer als 5MB sein.');
        });
    }
});


eventModule.controller('PdfUploadController', function($scope, Event, FileUploader, ErrorPropagationService) {

    $scope.event = Event.data;
    $scope.uploader = buildUploader();

    function buildUploader() {
        return Event.pdfUploader($scope.event.identifier, function() {
            ErrorPropagationService.resetErrors();
            $scope.uploading = true;
        }, function() {
            $scope.uploadPdfSuccess = true;
            $scope.uploading = false;
        }, function() {
            $scope.uploading = false;
            ErrorPropagationService.manualErrorAlert('Upload fehlgeschlagen! Datei muss im PDF Format und darf nicht größer als 5MB sein.');
        });
    }
});