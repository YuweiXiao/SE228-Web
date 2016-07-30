var myApp = angular.module("myModule", [])
					.controller('bookDetailControl',function($scope, $http) {  
						$scope.getDetail = function(bookID){
							console.log("in angular");
                    		$http.get( base_url+'/item/action_getBookInfo?bookID='+bookID)
                    					.success(function(response){
	                    					console.log(response);
	                    					$scope.book = response.book;
	                    					$scope.amount = 1;
	                    					$('#item-detail').modal('show');
	                           			}, function(reason){
											$log.info(reason);
		                           		});
                  		};

                  		$scope.addNum = function() {
                  			if( $scope.amount < parseInt($scope.book.inventory) )
                  				$scope.amount ++;
                  		}

                  		$scope.minusNum = function() {
                  			if( $scope.amount > 1 )
                  				$scope.amount --;
                  		}

                  		$scope.addToCart = function() {
              				console.log(base_url+'/item/action_cart_add');
              			    $.post(
              			        base_url+'/item/action_cart_add',
              			        {
              			            'bookID':$scope.book.bookID,
              			            'amount':$scope.amount
              			        },
              			        function(msg){
              			            console.log(msg.success);
              			            if(msg.success)
              			            {
              			            	$('#order').removeClass('disabled');
              			                showTip('成功加入购物车', 'success');
              			                $('#item-detail').modal('hide');
              			            }
              			            else if(msg == 1)
              			            {
              			                showTip('you have to login','danger');
              			                setTimeout(function(){
              			                    window.location.href=base_url + '/auth/login'; 
              			                },1000);
              			            }
              			        });
                  		}
                  	});

