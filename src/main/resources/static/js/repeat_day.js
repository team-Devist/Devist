$(document).ready(function () {
    var checkedId = $("#id-fixed-or-flexible").val();
    toggleRepeatDay(checkedId);

    if (checkedId === "fixed")
        $("#btn-fixed").addClass('active');
    else if (checkedId === "flexible")
        $("#btn-flexible").addClass('active');
});

$("input[name=repeat-day]").change(function () {
    var checkedId = $(this).val();
    $("#id-fixed-or-flexible").val(checkedId);

    toggleRepeatDay(checkedId);
});

function toggleRepeatDay(checkedId) {
    if (checkedId === "fixed") {
        $("#div-fixedrepeatday").show();
        $("#div-flexiblerepeatday").hide();
    }
    else if (checkedId === "flexible") {
        $("#div-fixedrepeatday").hide();
        $("#div-flexiblerepeatday").show();
    }
}