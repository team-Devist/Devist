$(document).ready(function(){
    $(document).on("click", ".btn-complete-todo", function () {
        var table_id = $(this).closest("table").attr('id');
        var tr = $(this).closest('tr').remove();

        if (table_id === "todo-table")
            $("#completed-todo-table tbody").append(tr);
        else
            $("#todo-table tbody").append(tr);
    });
});
