<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>Hello ${username}, your id is ${userId}!</h2>
<img src="${pageContext.request.contextPath}${userIcon}"/>
</body>
</html>