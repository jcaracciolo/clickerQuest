<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><c:out> Tag Example</title>
</head>
<body>
<c:out value="${'<tag> , &'}"/>
</body>