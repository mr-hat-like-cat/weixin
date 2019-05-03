$(function(){
	$('.sub').mouseenter(function() {
		$(this).css('color','rgb(228,74,128)').mouseleave(function() {
			$(this).css('color','white');
		});
	});
	$('.sub').eq(0).click(function() {
		$('.attention').css('display','none');
		$('.content_s').css('display','block');
		$('#qrious').css('display','none');
	});
	$('.sub').eq(1).click(function() {
		$('.attention').css('display','none');
		$('.content_s').css('display','none');
		$('#qrious').css('display','block');
	});
	//微信二维码
	var qr = new QRious({
		element:document.getElementById("qrious"),
		size:200,
		value:'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe1e3696881c14c1d&redirect_uri=http://2466s247i0.wicp.vip/weixin/AServlet&response_type=code&scope=snsapi_userinfo#wechat_redirect',
		level:'H'
	});
	var qrious = document.getElementById("qrious");
	$('#wx').click(function() {	
		if( qrious.style.visibility == "visible" ) {
			$('#qrious').css('visibility','hidden');
		}else {
			$('#qrious').css('visibility','visible');
		}
	});

});