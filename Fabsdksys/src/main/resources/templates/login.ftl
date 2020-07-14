<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>优亚集团人事管理 - 登录</title>
    <link href="${basePath}/static/pc/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${basePath}/static/pc/css/login.css">
</head>

<body>
    <div class="main">
        <div class="top">
            <div style="min-width: 170px">
                <div style="text-align: center;">
                    <p style="font-size: 18px;font-weight: 700;color: #fff;">[ 优亚集团人事管理 ]</p>
                    <img src="${basePath}/static/pc/img/logo.png" alt="">
                </div>
            </div>
            <form method="post" action="${basePath}/loginAction">
                <h4 class="no-margins text-center" style="font-size: 2.5em;color: #fff;">登录</h4>
                <p style="color:#fff;font-size: 14px;">登录到店小二后台管理系统</p>
                <input  name="username" type="text" class="uname" placeholder="用户名">
                <input  id="pwd" name="password" type="password" class="pword" placeholder="密码">
                <!--  -->
                <button type="submit" id="signinSubmit" class="btn btn-success btn-block">登录</button>
                <div align="center">
                    <br>
                     <font  color="red">
			          ${loginerr!} 
			         </font>                    
                </div>
            </form>
        </div>
        <div class="footer">
            <p style="color: #676a6c;text-align: center;">
                <span>© 西安威沃网络科技有限公司 </span>
                <br>
                <span>备案号:陕ICP备15000560号-1 </span>
                <br>
                <span>3.6.2.323_hw</span>
            </p>
        </div>
    </div>
    </div>
    <script src="${basePath}/static/pc/js/jquery.min.js"></script>
    <script src="${basePath}/static/pc/js/bootstrap.min.js"></script>
</body>

</html>