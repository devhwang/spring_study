<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%   String path = request.getContextPath(); %>
<style>
	.navbar-brand { position: relative; z-index: 2; }

	.navbar-nav.navbar-right .btn { position: relative; z-index: 2; padding: 4px 20px; margin: 10px auto; }
	
	.navbar .navbar-collapse { position: relative; }
	.navbar .navbar-collapse .navbar-right > li:last-child { padding-left: 22px; }
	
	.navbar .nav-collapse { position: absolute; z-index: 1; top: 0; left: 0; right: 0; bottom: 0; margin: 0; padding-right: 120px; padding-left: 80px; width: 100%; }
	.navbar.navbar-default .nav-collapse { background-color: #f8f8f8; }
	.navbar.navbar-inverse .nav-collapse { background-color: #222; }
	.navbar .nav-collapse .navbar-form { border-width: 0; box-shadow: none; }
	.nav-collapse>li { float: right; }
	
	.btn.btn-circle { border-radius: 50px; }
	.btn.btn-outline { background-color: transparent; }
	
	@media screen and (max-width: 767px) {
	    .navbar .navbar-collapse .navbar-right > li:last-child { padding-left: 15px; padding-right: 15px; } 
	    
	    .navbar .nav-collapse { margin: 7.5px auto; padding: 0; }
	    .navbar .nav-collapse .navbar-form { margin: 0; }
	    .nav-collapse>li { float: none; }
	}
</style>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <!-- Second navbar for sign in -->
    <nav class="navbar navbar-default">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-2">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">UCS Study</a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-collapse-2">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Home</a></li>
            <li><a href="#">Board</a></li>
            <li><a href="#">Contact</a></li>
            <li>
              <a class="btn btn-default btn-outline btn-circle" href="<%= path%>/sign/logout.do">Logout</a>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
    
