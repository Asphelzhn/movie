<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	    <title>搜电影</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<style type="text/css">
		.w{width:760px;margin:0 auto;}
		.clearfix:after{content:'.';visibility: hidden;clear:both;overflow:hidden;zoom:1;}
		.banner{margin-top:120px;margin-bottom: 20px;text-align: center;}
		.input-group{width:100%;height: 32px;min-width: 760px;border:solid 1px #337ab7;border-radius:4px;overflow:hidden;}
		.input{display: block;width:88%;height:32px;margin:0 0 0 5px;padding: 0;float: left;line-height:32px;font-size:14px;border: none;outline: none;}
		.btn{display: block;float:right;width: 10%;height:32px;margin: 0;color:#fff;padding: 0;font-size:16px;text-align: center;line-height:32px;border:none;outline: none;background:#337ab7;}
		.btn:hover{background: #286090;}
		</style>
	</head>
	<body>
		<div class="w banner">
			<img id="bannerImg" width="120" height="160" src="images/1.jpeg" />
		</div>
		<div class="w">
			<form method="post" action="search" name="myForm">
				<div class="input-group clearfix">
					<input type="text" class="input" id="keyWords"  name="keywords" placeholder="铁道飞虎~" />
					<button class="btn" >搜索</button>
				</div>
			</form>
		<script type="text/javascript">
		window.onload = function(){
			var imgD = document.getElementById("bannerImg");
			setInterval(function(){
				var index = Math.ceil(Math.random()*4);
				var img = new Image;
				img.onload = function(){
					imgD.src = img.src;
				}
				img.src = 'images/' + index + '.jpeg';
			},3000);
		}
		</script>
	</body>
</html>
