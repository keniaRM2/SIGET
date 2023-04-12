function agregarFila() {
    var tabla = document.getElementById("tabla").getElementsByTagName('tbody')[0];
    var nuevaFila = tabla.insertRow(tabla.rows.length);
    var conteo = tabla.rows.length;
    var celdaConteo = nuevaFila.insertCell(0);
    var celdaDia = nuevaFila.insertCell(0);
    var celdaHoraInicio = nuevaFila.insertCell(1);
    var celdaHoraFin = nuevaFila.insertCell(2);
    var celdaBoton = nuevaFila.insertCell(3);


    var selectDia = document.getElementsByName("dia")[0];
    if(selectDia){

    }
    var selectHoraInicio = document.getElementsByName("horaInicio")[0];
    var selectHoraFin = document.getElementsByName("horaFin")[0];

    celdaConteo.innerHTML = conteo;
    celdaDia.innerHTML = selectDia.options[selectDia.selectedIndex].value;
    celdaHoraInicio.innerHTML = selectHoraInicio.options[selectHoraInicio.selectedIndex].value;
    celdaHoraFin.innerHTML = selectHoraFin.options[selectHoraFin.selectedIndex].value;
    celdaBoton.innerHTML = '<a class="btn btn-link boton-estatus" type="button"\n' +
        '                                           th:href="@{/horario/eliminar/{id} (id=${horario.id})}">\n' +
        '                                            <i class="bi bi-trash text-danger"></i>\n' +
        '                                        </a>';
    console.log("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
}

function eliminarFila(boton) {
    var fila = boton.parentNode.parentNode;
    fila.parentNode.removeChild(fila);
}
