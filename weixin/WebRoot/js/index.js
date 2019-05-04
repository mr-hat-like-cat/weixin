$(function(){
	//jQuery
	$('.sub').mouseenter(function() {
		$(this).css('color','rgb(228,74,128)').mouseleave(function() {
			$(this).css('color','white');
		});
		connect();
	});
	$('.sub').eq(0).click(function() {
		$('.attention').css('display','none');
		$('.content_s').css('display','block');
		$('#qrious').css('display','none');
	});
	$('.sub').eq(1).mouseenter(function() {
		$(this).css('display','none');
		$('.hide').css('display','block');	
	});
	$('.sub').eq(2).click(function() {
		$('.attention').css('display','none');
		$('.content_s').css('display','none');
		$('#qrious').css('display','block');
		send("we need userInfo");	
	});
	$('.sub').eq(3).click(function() {
		$('#qrious').css('display','none');	
		$('.sub').eq(1).css('display','block');
		$('.sub').eq(2).css('display','none');
		$('#username').text("");
		$('#headimg').text("");
		ws.close();
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

//webSocket
var ws = null;
var mess = null;
var target = "ws://2466s247i0.wicp.vip/weixin/echo";
function connect() 
{
	if( 'WebSocket' in window ) 
	{
		ws = new WebSocket(target);
	}else if( 'MozWebSocket' in window )
	{
		ws = new MozWebSocket(target);	
	}else 
	{
		alert('WebSocket is not supported by this browser.');
		return;
	}
	ws.onmessage = function(event)
	{
		var username = document.getElementById("username");
		var headimg = document.getElementById("headimg");
		mess = event.data;
		var str = mess.split("|");
		username.innerHTML = str[0];			
		headimg.innerHTML ="<img src="+str[1]+" width=50 height=50 />"
	};
}
function send(msg) {
	ws.send(msg);
}



