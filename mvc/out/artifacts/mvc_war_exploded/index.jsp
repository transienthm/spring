
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <a href="/helloworld">hello world!</a>

    <br/><br/>

    <form action="/testRest" method="post">
      <input type="hidden" name="_method" value="DELETE">
      <input type="submit" value="TestRest DELETE">
    </form>
  </body>
</html>
