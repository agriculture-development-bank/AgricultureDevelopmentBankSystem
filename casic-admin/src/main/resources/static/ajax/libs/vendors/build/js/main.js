/**
 * Resize function without multiple trigger
 * 
 * Usage:
 * $(window).smartresize(function(){  
 *     // code here
 * });
 */
(function($,sr){
    // debouncing function from John Hann
    // http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
    var debounce = function (func, threshold, execAsap) {
      var timeout;
        return function debounced () {
            var obj = this, args = arguments;
            function delayed () {
                if (!execAsap)
                    func.apply(obj, args); 
                timeout = null; 
            }

            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);

            timeout = setTimeout(delayed, threshold || 100); 
        };
    };
    // smartresize 
    jQuery.fn[sr] = function(fn){  return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr); };

})(jQuery,'smartresize');


/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
    $BODY = $('body'),
    $MENU_TOGGLE = $('#menu_toggle'),
    $SIDEBAR_MENU = $('#sidebar-menu'),
    $SIDEBAR_FOOTER = $('.sidebar-footer'),
    $LEFT_COL = $('.left_col'),
    $RIGHT_COL = $('.right_col'),
    $NAV_MENU = $('.nav_menu'),
    $FOOTER = $('footer');
	
	var randNum = function() {
	  return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
	};

	// Panel toolbox
	$(document).ready(function() {
	    $('.collapse-link').on('click', function() {
	        var $BOX_PANEL = $(this).closest('.x_panel'),
	            $ICON = $(this).find('i'),
	            $BOX_CONTENT = $BOX_PANEL.find('.x_content');
	        
	        // fix for some div with hardcoded fix class
	        if ($BOX_PANEL.attr('style')) {
	            $BOX_CONTENT.slideToggle(200, function(){
	                $BOX_PANEL.removeAttr('style');
	            });
	        } else {
	            $BOX_CONTENT.slideToggle(200); 
	            $BOX_PANEL.css('height', 'auto');  
	        }
	
	        $ICON.toggleClass('fa-chevron-up fa-chevron-down');
	    });
	
	    $('.close-link').click(function () {
	        var $BOX_PANEL = $(this).closest('.x_panel');
	        $BOX_PANEL.remove();
	    });
	    
	});


	// /Panel toolbox
	// Tooltip
	$(document).ready(function() {
	    $('[data-toggle="tooltip"]').tooltip({
	        container: 'body'
	    });
	});
	// /Tooltip
	
	// Progressbar
	if ($(".progress .progress-bar")[0]) {
	    $('.progress .progress-bar').progressbar();
	}
	// /Progressbar

	// iCheck
	$(document).ready(function() {
	    if ($("input.flat")[0]) {
	        $(document).ready(function () {
	            $('input.flat').iCheck({
	                checkboxClass: 'icheckbox_flat-green',
	                radioClass: 'iradio_flat-green'
	            });
	        });
	    }
	});
	// /iCheck

	// Accordion
	$(document).ready(function() {
		    $(".expand").on("click", function () {
	        $(this).next().slideToggle(200);
	        $expand = $(this).find(">:first-child");
	
	        if ($expand.text() == "+") {
	            $expand.text("-");
	        } else {
	            $expand.text("+");
	        }
	    });
	});
	
	// NProgress
	if (typeof NProgress != 'undefined') {
	    $(document).ready(function () {
	        NProgress.start();
	    });
	    
	    $(window).load(function () {
	        NProgress.done();
	    });
	}

	
	//hover and retain popover when on popover content
    var originalLeave = $.fn.popover.Constructor.prototype.leave;
    $.fn.popover.Constructor.prototype.leave = function(obj) {
      var self = obj instanceof this.constructor ?  obj : $(obj.currentTarget)[this.type](this.getDelegateOptions()).data('bs.' + this.type);
      var container, timeout;
      originalLeave.call(this, obj);
      if (obj.currentTarget) {
        container = $(obj.currentTarget).siblings('.popover');
        timeout = self.timeout;
        container.one('mouseenter', function() {
          //We entered the actual popover – call off the dogs
          clearTimeout(timeout);
          //Let's monitor popover content instead
          container.one('mouseleave', function() {
            $.fn.popover.Constructor.prototype.leave.call(self, self);
          });
        });
      }
    };

    $('body').popover({
      selector: '[data-popover]',
      trigger: 'click hover',
      delay: {
        show: 50,
        hide: 400
      }
    });


	function gd(year, month, day) {
		return new Date(year, month - 1, day).getTime();
	}
	
	function init_flot_chart(){
		if( typeof ($.plot) === 'undefined'){ return; }
		console.log('init_flot_chart');
		var arr_data1 = [
			[gd(2012, 1, 1), 17],
			[gd(2012, 1, 2), 74],
			[gd(2012, 1, 3), 6],
			[gd(2012, 1, 4), 39],
			[gd(2012, 1, 5), 20],
			[gd(2012, 1, 6), 85],
			[gd(2012, 1, 7), 7]
		];

		var arr_data2 = [
		  [gd(2012, 1, 1), 82],
		  [gd(2012, 1, 2), 23],
		  [gd(2012, 1, 3), 66],
		  [gd(2012, 1, 4), 9],
		  [gd(2012, 1, 5), 119],
		  [gd(2012, 1, 6), 6],
		  [gd(2012, 1, 7), 9]
		];
		
		var arr_data3 = [
			[0, 1],
			[1, 9],
			[2, 6],
			[3, 10],
			[4, 5],
			[5, 17],
			[6, 6],
			[7, 10],
			[8, 7],
			[9, 11],
			[10, 35],
			[11, 9],
			[12, 12],
			[13, 5],
			[14, 3],
			[15, 4],
			[16, 9]
		];
		
		var chart_plot_02_data = [];
		var chart_plot_03_data = [
			[0, 1],
			[1, 9],
			[2, 6],
			[3, 10],
			[4, 5],
			[5, 17],
			[6, 6],
			[7, 10],
			[8, 7],
			[9, 11],
			[10, 35],
			[11, 9],
			[12, 12],
			[13, 5],
			[14, 3],
			[15, 4],
			[16, 9]
		];
		
		for (var i = 0; i < 30; i++) {
		  chart_plot_02_data.push([new Date(Date.today().add(i).days()).getTime(), randNum() + i + i + 10]);
		}
		
		var chart_plot_01_settings = {
          series: {
            lines: {
              show: false,
              fill: true
            },
            splines: {
              show: true,
              tension: 0.4,
              lineWidth: 1,
              fill: 0.4
            },
            points: {
              radius: 0,
              show: true
            },
            shadowSize: 2
          },
          grid: {
            verticalLines: true,
            hoverable: true,
            clickable: true,
            tickColor: "#d5d5d5",
            borderWidth: 1,
            color: '#fff'
          },
          colors: ["rgba(38, 185, 154, 0.38)", "rgba(3, 88, 106, 0.38)"],
          xaxis: {
            tickColor: "rgba(51, 51, 51, 0.06)",
            mode: "time",
            tickSize: [1, "day"],
            //tickLength: 10,
            axisLabel: "Date",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            axisLabelPadding: 10
          },
          yaxis: {
            ticks: 8,
            tickColor: "rgba(51, 51, 51, 0.06)",
          },
          tooltip: false
        }
		
		var chart_plot_02_settings = {
			grid: {
				show: true,
				aboveData: true,
				color: "#3f3f3f",
				labelMargin: 10,
				axisMargin: 0,
				borderWidth: 0,
				borderColor: null,
				minBorderMargin: 5,
				clickable: true,
				hoverable: true,
				autoHighlight: true,
				mouseActiveRadius: 100
			},
			series: {
				lines: {
					show: true,
					fill: true,
					lineWidth: 2,
					steps: false
				},
				points: {
					show: true,
					radius: 4.5,
					symbol: "circle",
					lineWidth: 3.0
				}
			},
			legend: {
				position: "ne",
				margin: [0, -25],
				noColumns: 0,
				labelBoxBorderColor: null,
				labelFormatter: function(label, series) {
					return label + '&nbsp;&nbsp;';
				},
				width: 40,
				height: 1
			},
			colors: ['#96CA59', '#3F97EB', '#72c380', '#6f7a8a', '#f7cb38', '#5a8022', '#2c7282'],
			shadowSize: 0,
			tooltip: true,
			tooltipOpts: {
				content: "%s: %y.0",
				xDateFormat: "%d/%m",
			shifts: {
				x: -30,
				y: -50
			},
			defaultTheme: false
			},
			yaxis: {
				min: 0
			},
			xaxis: {
				mode: "time",
				minTickSize: [1, "day"],
				timeformat: "%d/%m/%y",
				min: chart_plot_02_data[0][0],
				max: chart_plot_02_data[20][0]
			}
		};	
	
		var chart_plot_03_settings = {
			series: {
				curvedLines: {
					apply: true,
					active: true,
					monotonicFit: true
				}
			},
			colors: ["#26B99A"],
			grid: {
				borderWidth: {
					top: 0,
					right: 0,
					bottom: 1,
					left: 1
				},
				borderColor: {
					bottom: "#7F8790",
					left: "#7F8790"
				}
			}
		};
        
		
        if ($("#chart_plot_01").length){
			console.log('Plot1');
			$.plot( $("#chart_plot_01"), [ arr_data1, arr_data2 ],  chart_plot_01_settings );
		}
        
		if ($("#chart_plot_02").length){
			console.log('Plot2');
			
			$.plot( $("#chart_plot_02"), 
			[{ 
				label: "Email Sent", 
				data: chart_plot_02_data, 
				lines: { 
					fillColor: "rgba(150, 202, 89, 0.12)" 
				}, 
				points: { 
					fillColor: "#fff" } 
			}], chart_plot_02_settings);
		}
		
		if ($("#chart_plot_03").length){
			console.log('Plot3');
			$.plot($("#chart_plot_03"), [{
				label: "Registrations",
				data: chart_plot_03_data,
				lines: {
					fillColor: "rgba(150, 202, 89, 0.12)"
				}, 
				points: {
					fillColor: "#fff"
				}
			}], chart_plot_03_settings);
		};
	  
	} 
	
	
	
	function init_JQVmap(){
		//console.log('check init_JQVmap [' + typeof (VectorCanvas) + '][' + typeof (jQuery.fn.vectorMap) + ']' );	
		if(typeof (jQuery.fn.vectorMap) === 'undefined'){ return; }
		console.log('init_JQVmap');
		if ($('#world-map-gdp').length ){
			$('#world-map-gdp').vectorMap({
				map: 'world_en',
				backgroundColor: null,
				color: '#ffffff',
				hoverOpacity: 0.7,
				selectedColor: '#666666',
				enableZoom: true,
				showTooltip: true,
				values: sample_data,
				scaleColors: ['#E6F2F0', '#149B7E'],
				normalizeFunction: 'polynomial'
			});
		}
		
		if ($('#usa_map').length ){
			$('#usa_map').vectorMap({
				map: 'usa_en',
				backgroundColor: null,
				color: '#ffffff',
				hoverOpacity: 0.7,
				selectedColor: '#666666',
				enableZoom: true,
				showTooltip: true,
				values: sample_data,
				scaleColors: ['#E6F2F0', '#149B7E'],
				normalizeFunction: 'polynomial'
			});
		}
	};
			
	    
	function init_skycons(){
		if( typeof (Skycons) === 'undefined'){ return; }
		console.log('init_skycons');
		var icons = new Skycons({
			"color": "#73879C"
		  }),
		  list = [
			"clear-day", "clear-night", "partly-cloudy-day",
			"partly-cloudy-night", "cloudy", "rain", "sleet", "snow", "wind",
			"fog"
		  ],
		i;
		for (i = list.length; i--;) {
		  icons.set(list[i], list[i]);
		}
		icons.play();
	}  
	   
	   
	function init_chart_doughnut(){
		if( typeof (Chart) === 'undefined'){ return; }
		console.log('init_chart_doughnut');
		if ($('.canvasDoughnut').length){
		var chart_doughnut_settings = {
				type: 'doughnut',
				tooltipFillColor: "rgba(51, 51, 51, 0.55)",
				data: {
					labels: [
						"Symbian",
						"Blackberry",
						"Other",
						"Android",
						"IOS"
					],
					datasets: [{
						data: [15, 20, 30, 10, 30],
						backgroundColor: [
							"#BDC3C7",
							"#9B59B6",
							"#E74C3C",
							"#26B99A",
							"#3498DB"
						],
						hoverBackgroundColor: [
							"#CFD4D8",
							"#B370CF",
							"#E95E4F",
							"#36CAAB",
							"#49A9EA"
						]
					}]
				},
				options: { 
					legend: false, 
					responsive: false 
				}
			}
		
			$('.canvasDoughnut').each(function(){
				
				var chart_element = $(this);
				var chart_doughnut = new Chart( chart_element, chart_doughnut_settings);
				
			});			
		
		}  
	}
	   
	function init_gauge() {
		if( typeof (Gauge) === 'undefined'){ return; }
		console.log('init_gauge [' + $('.gauge-chart').length + ']');
		console.log('init_gauge');
		var chart_gauge_settings = {
			  lines: 12,
			  angle: 0,
			  lineWidth: 0.4,
			  pointer: {
				  length: 0.75,
				  strokeWidth: 0.042,
				  color: '#1D212A'
			  },
			  limitMax: 'false',
			  colorStart: '#1ABC9C',
			  colorStop: '#1ABC9C',
			  strokeColor: '#F0F3F3',
			  generateGradient: true
		};
		
		if ($('#chart_gauge_01').length){ 
			var chart_gauge_01_elem = document.getElementById('chart_gauge_01');
			var chart_gauge_01 = new Gauge(chart_gauge_01_elem).setOptions(chart_gauge_settings);
			
		}	
		
		
		if ($('#gauge-text').length){ 
		
			chart_gauge_01.maxValue = 6000;
			chart_gauge_01.animationSpeed = 32;
			chart_gauge_01.set(3200);
			chart_gauge_01.setTextField(document.getElementById("gauge-text"));
		
		}
		
		if ($('#chart_gauge_02').length){
		
			var chart_gauge_02_elem = document.getElementById('chart_gauge_02');
			var chart_gauge_02 = new Gauge(chart_gauge_02_elem).setOptions(chart_gauge_settings);
		}
		
		
		if ($('#gauge-text2').length){
			chart_gauge_02.maxValue = 9000;
			chart_gauge_02.animationSpeed = 32;
			chart_gauge_02.set(2400);
			chart_gauge_02.setTextField(document.getElementById("gauge-text2"));
		}
	}   
	
   
	/* SELECT2 */
	function init_select2() {
		if( typeof (select2) === 'undefined'){ return; }
		console.log('init_toolbox');
		 
		$(".select2_single").select2({
		  placeholder: "Select a state",
		  allowClear: true
		});
		$(".select2_group").select2({});
		$(".select2_multiple").select2({
		  maximumSelectionLength: 4,
		  placeholder: "With Max Selection limit 4",
		  allowClear: true
		});
		
	};
	   
	 
	   
	   
    /* DATERANGEPICKER */
	function init_daterangepicker() {
		if( typeof ($.fn.daterangepicker) === 'undefined'){ return; }
		console.log('init_daterangepicker');
	
		var cb = function(start, end, label) {
		  console.log(start.toISOString(), end.toISOString(), label);
		  $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		};

		var optionSet1 = {
		  startDate: moment().subtract(29, 'days'),
		  endDate: moment(),
		  minDate: '01/01/2012',
		  maxDate: '12/31/2015',
		  dateLimit: {
			days: 60
		  },
		  showDropdowns: true,
		  showWeekNumbers: true,
		  timePicker: false,
		  timePickerIncrement: 1,
		  timePicker12Hour: true,
		  ranges: {
			'Today': [moment(), moment()],
			'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'Last 7 Days': [moment().subtract(6, 'days'), moment()],
			'Last 30 Days': [moment().subtract(29, 'days'), moment()],
			'This Month': [moment().startOf('month'), moment().endOf('month')],
			'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		  },
		  opens: 'left',
		  buttonClasses: ['btn btn-default'],
		  applyClass: 'btn-small btn-primary',
		  cancelClass: 'btn-small',
		  format: 'MM/DD/YYYY',
		  separator: ' to ',
		  locale: {
			applyLabel: 'Submit',
			cancelLabel: 'Clear',
			fromLabel: 'From',
			toLabel: 'To',
			customRangeLabel: 'Custom',
			daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
			monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
			firstDay: 1
		  }
		};
		
		$('#reportrange span').html(moment().subtract(29, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
		$('#reportrange').daterangepicker(optionSet1, cb);
		$('#reportrange').on('show.daterangepicker', function() {
		  console.log("show event fired");
		});
		
		$('#reportrange').on('hide.daterangepicker', function() {
		  console.log("hide event fired");
		});
		
		$('#reportrange').on('apply.daterangepicker', function(ev, picker) {
		  console.log("apply event fired, start/end dates are " + picker.startDate.format('MMMM D, YYYY') + " to " + picker.endDate.format('MMMM D, YYYY'));
		});
		
		$('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
		  console.log("cancel event fired");
		});
		
		$('#options1').click(function() {
		  $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
		});
		
		$('#options2').click(function() {
		  $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
		});
		
		$('#destroy').click(function() {
		  $('#reportrange').data('daterangepicker').remove();
		});
	}
   	   
		
	/* DATERANGEPICKER */
	function init_daterangepicker_zh() {
		if( typeof ($.fn.daterangepicker) === 'undefined'){ return; }
		console.log('init_daterangepicker_zh');
		var cb = function(start, end, label) {
		  console.log(start.toISOString(), end.toISOString(), label);
		  $('#reportrange span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
		};
		
		var optionSet1 = {
		  startDate: moment().subtract(29, 'days'),
		  endDate: moment(),
		  minDate: '2015-01-01',
		  maxDate: '2020-12-31',
		  dateLimit: {
			days: 60
		  },
		  showDropdowns: true,
		  showWeekNumbers: true,
		  timePicker: false,
		  timePickerIncrement: 1,
		  timePicker12Hour: true,
		  ranges: {
			'今天': [moment(), moment()],
			'昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'过去7天': [moment().subtract(6, 'days'), moment()],
			'过去30天': [moment().subtract(29, 'days'), moment()],
			'本月': [moment().startOf('month'), moment().endOf('month')],
			'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		  },
		  opens: 'left',
		  buttonClasses: ['btn btn-default'],
		  applyClass: 'btn-small btn-primary',
		  cancelClass: 'btn-small',
		  format: 'YYYY-MM-DD',
		  separator: ' to ',
		  locale: {
			applyLabel: '确定',
			cancelLabel: '清除',
			fromLabel: 'From',
			toLabel: 'To',
			customRangeLabel: '自定义选择',
			daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			firstDay: 1
		  }
		};
		
		$('#reportrange span').html(moment().subtract(29, 'days').format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD'));
		$('#reportrange').daterangepicker(optionSet1, cb);
		$('#reportrange').on('show.daterangepicker', function() {
		  console.log("show event fired");
		});
		$('#reportrange').on('hide.daterangepicker', function() {
		  console.log("hide event fired");
		});
		$('#reportrange').on('apply.daterangepicker', function(ev, picker) {
		  console.log("apply event fired, start/end dates are " + picker.startDate.format('YYYY-MM-DD') + " to " + picker.endDate.format('YYYY-MM-DD'));
		});
		$('#reportrange').on('cancel.daterangepicker', function(ev, picker) {
		  console.log("cancel event fired");
		});
		$('#options1').click(function() {
		  $('#reportrange').data('daterangepicker').setOptions(optionSet1, cb);
		});
		$('#options2').click(function() {
		  $('#reportrange').data('daterangepicker').setOptions(optionSet2, cb);
		});
		$('#destroy').click(function() {
		  $('#reportrange').data('daterangepicker').remove();
		});
	}
		
	$(document).ready(function() {
		init_flot_chart();
		init_JQVmap();
		init_daterangepicker_zh();
		init_skycons();
		init_select2();
		init_chart_doughnut();
		init_gauge();
				
	});	
	

