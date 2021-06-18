// SCROLL TO TOP ===============================================================================
$(function() {
	$(window).scroll(function() {
		if($(this).scrollTop() != 0) {
			$('#toTop').fadeIn();	
		} else {
			$('#toTop').fadeOut();
		}
	});
 
	$('#toTop').click(function() {
		$('body,html').animate({scrollTop:0},500);
	});	
	
});

if( window.innerWidth < 770 ) {
	$("button.forward, button.backword").click(function() {
  $("html, body").animate({ scrollTop: 115 }, "slow");
  return false;
});
}

if( window.innerWidth < 500 ) {
	$("button.forward, button.backword").click(function() {
  $("html, body").animate({ scrollTop: 245 }, "slow");
  return false;
});
}
if( window.innerWidth < 340 ) {
	$("button.forward, button.backword").click(function() {
  $("html, body").animate({ scrollTop: 280 }, "slow");
  return false;
});
}
// WIZARD  ===============================================================================
jQuery(function($) {
				// Basic wizard with validation
				$("#survey_container").wizard({
					stepsWrapper: "#wrapped",
					submit: ".submit",
					beforeSelect: function( event, state ) {
						if (!state.isMovingForward)
  						 return true;
						var inputs = $(this).wizard('state').step.find(':input');
						return !inputs.length || !!inputs.valid();
					}
			

				}).validate({
					errorPlacement: function(error, element) { 
						if ( element.is(':radio') || element.is(':checkbox') ) {
							error.insertBefore( element.next() );

						} else { 
							error.insertAfter( element );
						}
					}
				});
				
				
				//  progress bar
				$("#progressbar").progressbar();

				$("#survey_container").wizard({
					afterSelect: function( event, state ) {
						$("#progressbar").progressbar("value", state.percentComplete);
						$("#location").text("(" + state.stepsComplete + "/" + state.stepsPossible + ")");
					}
				});

			});

// OHTER ===============================================================================
 $(document).ready(function(){   
    
		//Menu mobile
		$(".btn-responsive-menu").click(function() {
			$("#top-nav").slideToggle(400);
		});
		
		//Check and radio input styles
		$('input.check_radio').iCheck({
    	checkboxClass: 'icheckbox_square-aero',
   	    radioClass: 'iradio_square-aero'
  		});
		
		//Pace holder
		$('input, textarea').placeholder();
			
	
	
    });
	
	/*===================================================================================*/
	/*  TWITTER FEED                                                                     */
	/*===================================================================================*/
	
			$('.latest-tweets').each(function(){
				$(this).tweet({
				username: $(this).data('username'),
				join_text: "auto",
				avatar_size: 0,
				count: $(this).data('number'),
				auto_join_text_default: " we said,",
				auto_join_text_ed: " we",
				auto_join_text_ing: " we were",
				auto_join_text_reply: " we replied to",
				auto_join_text_url: "",
				loading_text: " loading tweets...",
				modpath: "./twitter/"
			});
		});
		
$('.latest-tweets').find('ul').addClass('slider');
		  	if ( $().bxSlider ) {
				var $this = $('.latest-tweets');
				$('.latest-tweets .slider').bxSlider({
					mode 			: 	$this.data('mode') != 'undefined' ? $this.data('mode') : "horizontal",
					speed			:	$this.data('speed') != 'undefined' ? $this.data('speed') : 2000,
					controls		:	$this.data('controls') != 'undefined' != 'undefined' ? $this.data('controls') : true,
					nextSelector 	: 	$this.data('nextselector') != 'undefined' ? $this.data('nextselector') : '',
					prevSelector	: 	$this.data('prevselector') != 'undefined' ? $this.data('prevselector') : '',
					pager			:	$this.data('pager') != 'undefined' ? $this.data('pager') : true,
					pagerSelector	: 	$this.data('pagerselector') != 'undefined' ? $this.data('pagerselector') : '',
					pagerCustom		: 	$this.data('pagercustom') != 'undefined' ? $this.data('pagercustom') : '',
					auto			:	$this.data('auto') != 'undefined' ? $this.data('auto') : true,
					autoHover		: 	$this.data('autoHover') != 'undefined' ? $this.data('autoHover') : true,
					adaptiveHeight	: 	$this.data('adaptiveheight') != 'undefined' ? $this.data('adaptiveheight') : true,
					useCSS			: 	$this.data('useCSS') != 'undefined' ? $this.data('useCSS') : false,
					nextText		: 	'<i class="icon-angle-right">',
					prevText		: 	'<i class="icon-angle-left">',
					preloadImages 	: 	'all',
					responsive 		: 	true
				});
			}





    

