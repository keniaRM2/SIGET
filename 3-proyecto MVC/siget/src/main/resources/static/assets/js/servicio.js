function replaceItems(html) {
    $('#items').replaceWith($(html));
}

$('button[name="addItem"]').click(function (event) {
    event.preventDefault();
    var data = $('form').serialize();
    let url = window.location.origin + "/siget/servicio/registrar";
    $.get(url, data, replaceItems);
});


let removerDocumento = function (indice){
    let data = 'indice=' + indice +"&"+$('form').serialize();
    let url = window.location.origin + "/siget/servicio/removerDocumento";
    $.post(url, data, replaceItems);
};

