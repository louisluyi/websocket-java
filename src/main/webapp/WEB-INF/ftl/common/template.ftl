<#macro template title="webscoket" styles="" scripts="">
<!DOCTYPE HTML>
<html>
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="keywords" content="websocket, contact room">
    <meta name="description" content="websocket in tomcat">
    <meta name="renderer" content="webkit">
    <link href="/css/common/reset.css" rel="stylesheet" type="text/css">
    ${styles}
</head>
<body>
<#nested><#--insert here-->
<script src="/js/libraries/jquery/jquery-2.1.4.min.js"></script>
<script src="/js/libraries/seajs/sea.js"></script>
${scripts}
</body>
</html>
</#macro>