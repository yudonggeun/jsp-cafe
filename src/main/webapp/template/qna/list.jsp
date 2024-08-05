<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>SLiPP Java Web Programming</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/static/css/styles.css" rel="stylesheet">
</head>

<body>
<%@ include file="/template/common/header.jsp" %>
<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default qna-list">
            <%@ page import="java.util.List" %>
            <%@ page import="woowa.cafe.dto.QuestionInfo" %>
            <%@ page import="woowa.frame.web.collection.Page" %>
            <%
                Page<QuestionInfo> pages = (Page<QuestionInfo>) request.getAttribute("questions");
                List<QuestionInfo> questions = pages.getContent();
            %>
            <ul class="list">

                <% for (QuestionInfo questionInfo : questions) { %>
                <li>
                    <div class="wrap">
                        <div class="main">
                            <strong class="subject">
                                <a href="/question/<%=questionInfo.id()%>"><%=questionInfo.title()%></a>
                            </strong>
                            <div class="auth-info">
                                <i class="icon-add-comment"></i>
                                <span class="time"><%=questionInfo.createdAt().toLocalDate().toString()%></span>
                                <a href="./user/profile.html" class="author"><%=questionInfo.authorName()%></a>
                            </div>
                        </div>
                    </div>
                </li>
                <% } %>
            </ul>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 text-center">
                    <ul class="pagination center-block" style="display:inline-block;">
                        <% for (int i = 1; i <= pages.getTotalPages(); i++) {
                            int pageNumber = i;
                            int size = 15;
                        %>
                        <li><a href="/?page=<%=pageNumber%>&size=<%=size%>"><%=pageNumber%></a></li>
                        <% } %>
                    </ul>
                </div>
                <div class="col-md-3 qna-write">
                    <a href="/question" class="btn btn-primary pull-right" role="button">질문하기</a>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/static/js/jquery-2.2.0.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/scripts.js"></script>
</body>
</html>
