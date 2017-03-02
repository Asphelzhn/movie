<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
    <script src="../resources/js/jquery.min.js" charset="utf-8"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../resources/js/bootstrap.min.js"></script>
    <script src="../resources/js/jquery.dataTables.min.js" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
    $("#userInfo").dataTable({
    	"bAutoWidth": false, //自适应宽度
        "aaSorting": [[1, "asc"]],
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sProcessing": "正在加载中......",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "对不起，查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "sSearch": "搜索",
            "oPaginate": {
                "sFirst": "首页&nbsp;",
                "sPrevious": "上一页&nbsp;",
                "sNext": "&nbsp;下一页&nbsp;",
                "sLast": "末页"
            }
        },//多语言配置
        "sServerMethod": "POST",
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "../user/load"	 
	});
    });
    </script>
  </head>
  <body>
  
  <div class="panel panel-default">
  	<div class="panel-body">
	 	 <!-- Table -->
		 <table class="table table-bordered" id="userInfo">
		   <caption>用户管理</caption>
		   <thead>
		      <tr>
		         <th>用户名</th>
		         <th>状态</th>
		         <th>操作</th>
		      </tr>
		   </thead>
		   <tbody>
		   </tbody>
		</table>
	</div>
</div>
  </body>
 </html>