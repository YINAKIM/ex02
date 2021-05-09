<%--
  Created by IntelliJ IDEA.
  User: kim-yina
  Date: 2021/05/08
  Time: 10:46 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../includes/header.jsp"%>




<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Forms</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-heading">Board Read Page</div>

            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-6">

            <%-- hidden값 보내기 --%>
            <form id="operForm" action="/board/modify" method="get">
                <input type="hidden" id="bno" name="bno" value="<c:out value='${board.bno}'/>">
            </form>

                <script type="text/javascript">
                    $(document).ready(function(){
                        var operForm = $("#operForm");
                        console.log("operForm / hidden값 : ...."+operForm);

                        // modify 버튼 누르면 hidden값으로 bno 가지고 ----> submit()
                        $("button[data-oper='modify']").on("click",function(e){
                            operForm.attr("action","/board/modify").submit();
                        });

                        //list버튼 누르면 id="bno" 값 없애고 ----> submit() (list로 이동)
                        $("button[data-oper='list']").on("click",function(e){
                            operForm.find("#bno").remove();
                            operForm.attr("action","/board/list");
                            operForm.submit();
                        });

                    });
                </script>

                        <%-- 번호로 조회 : 반드시 모든 input을 readonly="readonly"로 속성지정 --%>

                        <%-- BNO --%>
                            <div class="form-group">
                                <label>Bno</label>
                                <input class="form-control" name="bno" value="<c:out value='${board.bno}'/>"
                                       readonly="readonly"/>
                            </div>
                        <%-- 제목 --%>
                            <div class="form-group">
                                <label>Title</label>
                                <input class="form-control" name="title" value="<c:out value='${board.title}'/>"
                                       readonly="readonly"/>
                            </div>

                        <%-- 내용 --%>
                            <div class="form-group">
                                <label>Content</label>
                                <textarea class="form-control" rows="3" name="content"
                                readonly="readonly"><c:out value='${board.content}'/>
                                </textarea>
                            </div>

                        <%-- 작성자 --%>
                            <div class="form-group">
                                <label>Writer</label>
                                <input class="form-control" name="writer" value="<c:out value='${board.writer}'/>"
                                       readonly="readonly"/>
                            </div>
<hr>
                        <%-- 버튼 modify : 수정화면으로 이동 --%>
                            <button data-oper='modify'
                                    class="btn btn-default"
                                    onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'"
                            >Modify</button>

                        <%-- 버튼 modify : 수정화면으로 이동 --%>
                            <button data-oper='list'
                                    class="btn btn-info"
                                    onclick="location.href='/board/list'"
                            >List</button>

                    </div>


<%@include file="../includes/footer.jsp"%>
