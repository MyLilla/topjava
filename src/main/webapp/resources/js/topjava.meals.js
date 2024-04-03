$(function(){
    $('filter').click(function(request){
        $.ajax({
            url: "/meals/filter",
            type: 'GET',
            dataType: 'HttpServletRequest'});
    });
});