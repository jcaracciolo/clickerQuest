'use strict';
define(['clickerQuest'], function(clickerQuest) {

	clickerQuest.directive('sample', function() {
		return {
			restrict: 'E',
			template: '<span>Sample</span>'
		};
	});
});
