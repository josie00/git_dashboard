var options = {
		 chart: {
		        type: 'line',
		        zoomType: 'xy'
		    },
		    title: {
		        text: 'Fork+Pull Request Number Trending'
		    },
		    subtitle: {
		        text: 'Source: Github'
		    },
		    xAxis: {
		        title: {
		            enabled: true,
		            text: 'Date'

		        },
		        type: "category",
		        startOnTick: true,
		        endOnTick: true,
		        showLastLabel: true
		    },
		    yAxis: {
		        title: {
		            text: 'Fork+Pull Request Number'
		        }
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'left',
		        verticalAlign: 'top',
		        x: 100,
		        y: 70,
		        floating: true,
		        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
		        borderWidth: 1
		    },
		    plotOptions: {
		        scatter: {
		            marker: {
		                radius: 5,
		                states: {
		                    hover: {
		                        enabled: true,
		                        lineColor: 'rgb(100,100,100)'
		                    }
		                }
		            },
		            states: {
		                hover: {
		                    marker: {
		                        enabled: false
		                    }
		                }
		            },
		            tooltip: {
		                headerFormat: '<b>{series.name}</b><br>',
		                pointFormat: '#: {point.x}, Count: {point.y} '
		            }
		        }
		    },
		    series: []
		
};

$(document).ready(function() {
	console.log("start");
	get_score();

});

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function get_score(word) {
    var name = getParameterByName('name');
    console.log("name:" + name);
	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/getTrendingData?name=" + name,
		dataType : 'json',
		cache : false,
		timeout : 9000000,
		success : function(data) {
			console.log("SUCCESS : ", data);
			//var obj = JSON.parse(data);
			var len = 0;

			for ( var x in data) {
				if (data.hasOwnProperty(x)) {
					console.log(data[x]);
					len++;
				}
			}

			var myarray=new Array(len);

			for (var i=0; i < len; i++) {
				myarray[i] = new Array(2);
			}

            k = 0;
            for ( var x in data) {
                if (data.hasOwnProperty(x)) {
                    myarray[k][0] = x;
                    myarray[k][1] = data[x];
                }
                k++;
            }

			console.log("arr in success: " + myarray[0]);
			draw_chart(myarray);

		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});
	

}

function draw_chart(arr) {
	var len = arr.length;

	var newarr = new Array(len);
	for (var i=0; i < len; i++) {
		newarr[i] = new Array(2);
	}

	for (var i = 0; i < len; i++) {
		newarr[i][0] = arr[i][0];
		newarr[i][1] = parseFloat(arr[i][1]);
		console.log(newarr[i][1]);
	}

	console.log("newarr call back: " + newarr);
	options.series.push({
		name : 'Fork and Pull Request Number',
		color : 'rgba(223, 83, 83, .5)',
		data : newarr
	})

	var chart = new Highcharts.Chart('container', options);
}






