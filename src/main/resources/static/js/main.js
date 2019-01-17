var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function(){
    $(document).on("click", ".btn-complete-todo", function () {
        var div_id = $(this).closest("div").attr('id');
        var is_done;
        var a = $(this).closest("a").remove();

        if (div_id === "todo-list") {
            $("#completed-todo-list").append(a);
            is_done = "true";
        }
        else {
            $("#todo-list").append(a);
            is_done = "false";
        }

        $.ajax({
            type: "POST",
            url: "/todo/" + $(this).data("id") + "/do",
            async: false,
            data: {
                'isDone': is_done
           }
        });
        renewUserHomeData();
    });
});

$("#btn-showing-completed-todo-list").click(function () {
    var completed_todo_list = $("#completed-todo-list");
    if (completed_todo_list.is(":visible"))
        completed_todo_list.hide();
    else
        completed_todo_list.show();
});

function renewUserHomeData(){
    $.ajax({
        type: "GET",
        url: "/api/user-home-data",
        async: false,
        success: function(data) {
            var todoSize = data.todoSize;
            var completedTodoSize = data.completedTodoSize;

            var todoListStatus = $("#todo-list-status");
            todoListStatus.children().empty();

            if (todoSize === 0) {
                if (completedTodoSize === 0)
                    todoListStatus.append("<h3>새로운 할 일을 추가하세요!</h3>");
                else
                    todoListStatus.append("<h3>오늘의 할 일 완료!</h3>");
            }

            var userDoneRate = data.userDoneRate;
            var userDoneRateDiv = $("#user-done-rate");
            userDoneRateDiv.css("width", userDoneRate + "%");
            userDoneRateDiv.text(userDoneRate + "%");
            userDoneRateDiv.attr(aria-valuenow, userDoneRate);
        },
        error: function (request, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            alert("API 요청 실패");
        }
    });
}
