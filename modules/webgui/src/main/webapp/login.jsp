<html>
<body>
<h1>Login</h1>
<form method="POST" action="login">
  Username: <input type="text" name="j_username" size="15" /><br />
  Password: <input type="password" name="j_password" size="15" /><br />
  <div align="center">
    <p><input type="submit" value="Login" /></p>
  </div>
  <input type="hidden" name="j_authokurl" value="loginok.jsp">
  <input type="hidden" name="j_authkourl" value="loginko.jsp">
</form>
</body>
</html>