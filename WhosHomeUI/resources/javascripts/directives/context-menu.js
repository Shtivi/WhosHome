// .controller('contextMenuCtrl', contextMenuCtrl)
directives.directive('contextMenu', function() {
  return {
    require: 'mdMenu',
    link: function($scope, $element, $attrs, mdMenuCtrl){
      let prev = { x: 0, y: 0 }
      $scope.$openContextMenu = function($event, arg){
				$scope.$userArg = arg;

        mdMenuCtrl.offsets = () => {
          var mouse = {
            x: $event.clientX,
            y: $event.clientY
          }
          var offsets = {
            left: mouse.x - prev.x,
            top: mouse.y - prev.y
          }
          prev = mouse;
          return offsets;
        }
        mdMenuCtrl.open($event);
			};
    }

  };
})
.directive('contextMenuTrigger', function ($parse) {
	return {
		link: (scope, element, attrs) => {
			// Listen to contextArg changes
			attrs.$observe('contextArg', (value) => {
				scope.contextArg = value;
			})

			const fn = $parse(attrs.contextMenuTrigger);
			element.bind('contextmenu', $event => {
				scope.$apply(() => {
					event.preventDefault();

					// Append the arg
					$event.userArg = scope.contextArg;

					fn(scope, { $event });
				});
			});
		}
	}
})
.directive('contextMenuOption', ($parse) => {
	return (scope, element, attrs) => {
		const fn = $parse(attrs.contextMenuOption);
		element.bind('click', $event => {
			scope.$apply(() => {
				$event.preventDefault();
				fn(scope, { $event });
			})
		})
	}
});