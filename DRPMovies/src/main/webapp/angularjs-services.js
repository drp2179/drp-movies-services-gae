var drpMovieServices = angular.module('drpMovieServices', ['ngResource']);

app.factory("Post", function($resource) {
	  return $resource("/api/posts/:id");
	});

phonecatServices.factory('Phone', ['$resource',
  function($resource){
    return $resource('phones/:phoneId.json', {}, {
      query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
    });
  }]);