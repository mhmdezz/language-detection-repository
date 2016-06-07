'use strict';

angular.module('langDetectorApp')

.controller('LanguageController',
		[ '$scope', 'langFactory', function($scope, langFactory) {
			
			$scope.showLanguage = false;
			$scope.showAvailableLanguages = false;
			$scope.detectLanguage = function() {
				langFactory.detectLang().save($scope.inputText)
				.$promise.then(function(response){
					console.log(response);
					$scope.detectedLang=response.name;
					$scope.showLanguage = true;
					});
//				$scope.langdetectForm.$setPristine();
			};
			
			
		  langFactory.getLanguages().query(
	              function(response){
	                  $scope.languages = response;
	                  $scope.showAvailableLanguages = true;
	              },
	              function(response){
	                  $scope.message = "Error: "+response.status + " " + response.statusText;
	              }
	            );
			  
			  
			  
			  
		} ]);
