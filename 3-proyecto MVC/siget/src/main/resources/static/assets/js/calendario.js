

let idCalendario = '#calendario';


let diaClick = function (date, jsEvent, view) {
    alert('Has hecho click en: ' + date.format());
}

let eventoClick = function (evento, jsEvent, view) {
    let modal = new bootstrap.Modal($('#modalCita'), {});
    let cita = evento.cita;

    $('#titulo').text(cita.servicio.nombre);
    $('#fecha').text(moment(cita.fechaCita).format('MM/DD/YYYY'));
    $('#horario').text(moment(cita.horaInicio).format('HH:mm') + " a "+moment(cita.horaFin).format('HH:mm'));
    $('#estado').text(cita.estado.nombre);
    $('#alumno').text(cita.alumno.persona.nombreCompleto);
    $('#empleado').text(cita?.empleado?.persona?.nombreCompleto);
    $('#costo').text(cita.servicio.costo);
    $('#ventanilla').text(cita.ventanilla.nombre);
    $('#solicitud').text(moment(cita.fechaRegistro).format('MM/DD/YYYY'));
    
    modal.show();

}

let configuracion = {
    height: "100%",
    header: { left: 'prev,next', center: 'title', right: 'month,agendaWeek,basicWeek,agendaDay' },
    // header: { left: 'prev,next', center: 'title', right: 'month,agendaWeek,agendaDay' },
    defaultDate: new Date(),
    buttonIcons: true, // show the prev/next text
    weekNumbers: false,
    editable: false,
    eventLimit: true, // allow "more" link when too many events
    events: [],
    displayEventTime: true,
    defaultView: 'basicWeek',
    // dayClick: diaClick,
    eventClick: eventoClick,
};

let listarCitas = function () {
    let url = window.location.origin+ "/siget/cita/listarCitas";
    $.get(url, function (data, status) {
        agregarCitasCalendario(data);
    });
}


let agregarCitasCalendario = function name(citas) {

    citas = citas.map((cita) => {

        let fechaCitaInicio = moment(cita.fechaCita);
        let fechaCitaFin = moment(cita.fechaCita);

        let horaInicio = moment(cita.horaInicio);
        let horaFin = moment(cita.horaFin);

        fechaCitaInicio.set({
            hour: horaInicio.get('hour'),
            minute: horaInicio.get('minute'),
            second: horaInicio.get('second')
        });

        fechaCitaFin.set({
            hour: horaFin.get('hour'),
            minute: horaFin.get('minute'),
            second: horaFin.get('second')
        });

        return {
            title: cita.servicio.nombre,
            description: cita.servicio.nombre,
            start: fechaCitaInicio,
            end: fechaCitaFin,
            color: '#3A87AD',
            textColor: '#ffffff',
            cita: cita
        };
    });

    $(idCalendario).fullCalendar('removeEvents');
    $(idCalendario).fullCalendar('addEventSource', citas);
}







$(document).ready(function () {
    $(idCalendario).fullCalendar(configuracion);
    listarCitas();
});


