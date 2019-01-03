var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(function() {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

$(document).ready(function(){
    $(document).on("click", ".btn-complete-todo", function () {
        var table_id = $(this).closest("table").attr('id');
        var tr = $(this).closest('tr').remove();
        var is_done;

        if (table_id === "todo-table") {
            $("#completed-todo-table tbody").append(tr);
            is_done = "true";
        }
        else {
            $("#todo-table tbody").append(tr);
            is_done = "false";
        }

        $.ajax({
            type: "POST",
            url: "/todo/" + $(this).data("id") + "/do",
            data: {
                'isDone': is_done
            },
            async: false
        });
    });
});
