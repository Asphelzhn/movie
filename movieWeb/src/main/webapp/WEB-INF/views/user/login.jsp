<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户登录</title>

    <!-- Bootstrap -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../resources/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../resources/js/bootstrap.min.js"></script>
    
  </head>
  <body>
  
  <div class="container" style="margin-top:100px;margin-left:400px">  
   <form class="form-horizontal" role="form">
  	  <h2 class="form-sigin-heading">欢迎用户登录</h2>
	  <div class="form-group">
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="userName" placeholder="邮箱/用户名" style="width:25%">
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-10">
	      <input type="password" class="form-control" id="password" placeholder="密码" style="width:25%">
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <div class="col-sm-10">
	      <button type="submit" class="btn btn-primary" style="width:25%">登录</button>
	    </div>
	  </div>
	  
	  <p><a href="#">新用户注册</a><a href="#" style="margin-left:95px">忘记密码?</a></p>
	  
	</form>
    </div>
  </body>
</html>