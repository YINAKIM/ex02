<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../includes/header.jsp"%>
<%--     <div id="page-wrapper">태그 시작 까지 header.jsp  --%>

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Tables</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Board List Page
                            <button id='regBtn' type="button" class="btn btn-xs pull-right">Register New Board</button>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                    <%-- 목록화면처리 / Part3 - 11.2 / p236  --%>
                            <thead>
                            <tr>
                                <th>#번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                            </thead>

                        <c:forEach items="${list}" var="board">
                            <tbody>
                            <tr>
                                <td><c:out value="${board.bno}"/></td>
                                <td><a href="/board/get?bno=<c:out value='${board.bno}'/>"><c:out value="${board.title}"/></a></td>
                                <td><c:out value="${board.writer}"/></td>
                                <td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd"/></td>
                            </tr>

                            </tbody>
                        </c:forEach>
                        </table>





                    <%-- MODAL  --%>

                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">

                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                    </div>
                                    <div class="modal-body">처리가 완료되었습니다.</div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                                <!-- /.modal-content -->
                            </div>
                            <!-- /.modal-dialog -->
                        </div>
                    <%-- MODAL  --%>

                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-6 -->
        </div>
        <!-- /.row -->
<%-- checkModal                        --%>
<script type="text/javascript">
    $(document).ready(function(){
        // call checkModal()
        var result = '<c:out value="${result}"/>';
        checkModal(result);

        // *** 등록완료 후 이동된 상태에서 뒤로가기 했을 경우 모달창이 동작하는 것 방지
        //  ==> js 의 모든 처리가 끝나게 되면 history에 쌓이는 상태는 전부 모달창이 필요없다는 표시하는 것
        history.replaceState({},null,null);

        /*
            window.history객체 :
            stack구조로 동작 --> 페이지 이동에 대해 스택에 URL이동경로가 쌓이는 구조,

            [상황1] 등록 완료 후 > /board/list 호출(앞으로가기나 뒤로가기 아니고 redirect로 이동한 것) : 스택상단에 URL쌓임
            [상황2] 등록 직후 > /board/list 로 이동한 경우 : 모달창 동작

            =====> 등록 완료 후 list로 왔는데 거기서 뒤로가기 했을 때 모달창이 다시 뜨는 이유

            SOL. 스택 상단에 모달창이 필요하지 않다는 표시를 해주면 ===> 다시 돌아갈 때 모달창이 동작하지 않음
        */


        // checkModal()
        function checkModal(result) {

            if(result === '' || history.state ){
                                // history.state 체크 : *** 등록완료 후 이동된 상태에서 뒤로가기 했을 경우 모달창이 동작하는 것 방지
                return;
            }

            if (parseInt(result) > 0 ){
                $(".modal-body").html("게시글 "+parseInt(result)+"번이 등록되었습니다.");
            }
            $("#myModal").modal("show");//if안에 들어가있었음 => 등록완료일 때만 model 보임



        }

        // regBtn : move to register page
        $("#regBtn").on("click",function(){
           self.location = "/board/register";
        });
    });
</script>
<%@include file="../includes/footer.jsp"%>
