var drpMoviesApp = angular.module('drpMoviesApp', []);

drpMoviesApp.factory('DRPMovieService', function() {
	var shinyNewServiceInstance;
	  // factory function body that constructs shinyNewServiceInstance
	  return shinyNewServiceInstance;
});

/*
drpMoviesApp.factory("Movie", function($resource) {
	  return $resource("/ws/drpmovie/:idmdbid");
	});
*/
/**/
drpMoviesApp.controller('MovieListController', 
	function ($scope, $http) {
		$http.get('/ws/drpmovie/').success(function(data) {
			$scope.currentMovies = data;
	  	});
		$scope.orderProp = 'age';
	}
);
/**/

/*
drpMoviesApp.controller('MovieListController', function ($scope) {
  $scope.movies = [
    {'title': 'The A-Team',
     'released': 'Jun 11, 2010 04:00:00 AM'},
    {'title': 'Sahara',
     'released': 'Apr 08, 2005 04:00:00 AM'},
    {'title': 'Journey to the Center of the Earth',
     'released': 'Jul 11, 2008 04:00:00 AM'}
  ];
});
*/