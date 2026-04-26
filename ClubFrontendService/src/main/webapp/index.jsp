<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head><title>Login</title></head>
<body>
    <table width="100%" height="80%" border="0">
        <tr>
            <td align="center" valign="middle">
                <h2>Club Registration System</h2>
                <form action="FrontController" method="POST">
                    <table border="0">
                        <tr><td><input type="text" name="username" placeholder="User"></td></tr>
                        <tr><td><input type="password" name="password" placeholder="Pass"></td></tr>
                        <tr><td align="center"><button type="submit">Login</button></td></tr>
                    </table>
                </form>
                <hr width="200">
                <div id="publicView"></div>
            </td>
        </tr>
    </table>
</body>
</html>