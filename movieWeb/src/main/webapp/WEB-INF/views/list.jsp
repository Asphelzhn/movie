<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	    <title>搜电影-列表</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<style type="text/css">
		html,body{margin:0;padding:0;}
		.w{width:760px;margin:0 auto;}
		.clearfix:after{content:'.';visibility: hidden;clear:both;overflow:hidden;zoom:1;}
		.input-group{width:100%;height: 32px;min-width: 760px;margin:40px 0 20px;border:solid 1px #337ab7;border-radius:4px;overflow:hidden;}
		.input{display: block;width:88%;height:32px;margin:0 0 0 5px;padding: 0;float: left;line-height:32px;font-size:14px;border: none;outline: none;}
		.btn{display: block;float:right;width: 10%;height:32px;margin: 0;color:#fff;padding: 0;font-size:16px;text-align: center;line-height:32px;border:none;outline: none;background:#337ab7;}
		.btn:hover{background: #286090;}
		.search-list{list-style:none;margin:0;padding:0;}
		.search-list li{margin:0;padding:0;}
		.search-list .title{margin-bottom:1px;font-size:medium;font-weight:400;line-height:1.54;}
		.search-list .desc a{font-size:13px;text-decoration:none;color:#666;}
		.no-res{margin-bottom:1px;font-size:16px;font-weight:400;line-height:1.54;color:#666;font-family:'微软雅黑';text-align: center;}
		</style>
	</head>
	<body>
		<div class="search-box w">
			<form action="search" name="myForm" method="post">
				<div class="input-group clearfix">
					<input type="text" class="input" id="keyWords" name="keywords" value="${keywords}" />
					<button class="btn">搜索</button>
				</div>
			</form>
		</div>
		<div class="w">
		    <c:if test="${list != null && ((fn:length(list)) >= 0) }">
			<ul class="search-list">
			    <c:forEach var="movie" items="${list}">
				<li>
					<h3 class="title">
						<a href="${movie.url}" target="_blank">${movie.title}</a>
					</h3>
					<div class="desc">
						<a href="${movie.url}" target="_blank">${movie.content}</a>
					</div>
				</li>
				</c:forEach>
			</ul>
			</c:if>
            <c:if test="${(empty list) || ((fn:length(list)) == 0) }">
            <h3 class="no-res">哈哈哈~~木有结果啦</h3>
            </c:if>
		</div>
	</body>
</html>
