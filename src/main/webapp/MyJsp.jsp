<!DOCTYPE HTML>
<html>
	<head>
		<title>webscoket test</title>
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
		欢迎您：${uname}！<br>
		<textarea id="chatlog" readonly rows="10" cols="50"></textarea>
		<br />
		<input id="msg" type="text" />
		<button type="submit" id="sendButton" onClick="postToServer()">
			Send!
		</button>
		<button type="submit" id="sendButton" onClick="closeConnect()">
			End
		</button>
		<script type="text/javascript">
			var ws = new WebSocket("ws://localhost:8080/wsServlet?uname=${uname}");
			ws.onopen = function() {
			};
			ws.onmessage = function(message) {
				document.getElementById("chatlog").textContent += message.data + "\n";
			};
			function postToServer() {
				ws.send(document.getElementById("msg").value);
				document.getElementById("msg").value = "";
			};
			function closeConnect(){
				ws.close();
			};
		</script>
	</body>
</html>
