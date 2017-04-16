angular.module('CurrencyConveter', [])
.controller('ConvertCtrl', ['$scope','$http', function ($scope, $http) {	
    //offline - http://localhost:4502/content/dam/thdc/common/currencyresponse.json
	//online - http://api.fixer.io/latest?base=ZAR
	$http.get('http://api.fixer.io/latest?base=ZAR')
	.then(function(res){              
        $scope.currencies = [];

        // Get all options from the currency converter component dialog and construct a list for from and to currency select boxes
        var defaultFromCurrency = angular.element(document.getElementById('fromcurrencylist')).find('option');
		 // Loop
        angular.forEach(defaultFromCurrency, function(type){            
            var currencyOption =  angular.element(type);			
            if(res.data.rates[currencyOption.text()]){
                $scope.currencies.push({ 
                	"type" : currencyOption.text(),
                	"rate"  : res.data.rates[currencyOption.text()]                
            	});
            }                        
        });        
        $scope.fromCurrency = $scope.currencies[0];
        $scope.toCurrency = $scope.currencies[1];        

		$scope.fromValue = 1;
		$scope.forExConvert();
	});
	$scope.forExConvert = function(){        
		$scope.fromCurrencyRate = $scope.fromCurrency.rate;
        $scope.toCurrencyRate = $scope.toCurrency.rate;        
		$scope.toValue = $scope.fromValue * ($scope.toCurrencyRate * (1 / $scope.fromCurrencyRate));		
	};
}]);