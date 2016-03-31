/*************************************
//
// connect-4 app
//
**************************************/

// connect to our socket server
var socket = io.connect('http://127.0.0.1:1337/');

var app = angular.module('gameStation', []);


// shortcut for document.ready
app.controller( 'ChatboxController', ['$scope', function( $scope ){
	//setup some common vars
	var $blastField = $('#blast'),
		$allPostsTextArea = $('#allPosts'),
		$clearAllPosts = $('#clearAllPosts'),
		$sendBlastButton = $('#send');


	//SOCKET STUFF
	socket.on("blast", function(data){
		var copy = $allPostsTextArea.html();
		$allPostsTextArea.html('<p>' + copy + data.msg + "</p>");
		$allPostsTextArea.scrollTop($allPostsTextArea[0].scrollHeight - $allPostsTextArea.height());
		//.css('scrollTop', $allPostsTextArea.css('scrollHeight'));

	});

	$clearAllPosts.click(function(e){
		$allPostsTextArea.text('');
	});

	$sendBlastButton.click(function(e){

		var blast = $blastField.val();
		if(blast.length){
			socket.emit("blast", {msg:blast},
				function(data){
					$blastField.val('');
				});
		}


	});

	$blastField.keydown(function (e){
			if(e.keyCode == 13){
					$sendBlastButton.trigger('click');//lazy, but works
			}
	})
}]);
