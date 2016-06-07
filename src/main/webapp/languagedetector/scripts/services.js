'use strict';

angular.module('langDetectorApp')
          .constant("baseURL","http://localhost:3001/")
          
		.factory('langFactory',['$resource','baseURL',function($resource,baseURL) {
		        var langfac = {};
		
		        langfac.detectLang = function(){
		          return $resource(baseURL+"langdetect",null,{'update':{method:'PUT'}})
		        };
		        
		        
		        langfac.getLanguages = function(){
			          return $resource(baseURL+"languages",null,{'update':{method:'PUT'}})
			        };
			        
		        return langfac;
		      }])
		      ;
