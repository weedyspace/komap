<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>


<style>
	@media (max-width: 600px) {
	  .desktop {
	    display: none;
	  }
	 
	}
	
	@media (min-width: 1000px) {
	  .mobile {
	    display: none;
	  }
	}
</style>

<body>


	<div class="mobile" style="width:200px;height:100px;background:red;">
		mobile board
	</div>
	
	<div class="desktop" style="width:200px;height:100px;background:blue;">
		desktop board
	</div>

</body>
</html>
