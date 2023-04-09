

let idCalendario = '#calendario';


let diaClick = function (date, jsEvent, view) {
    alert('Has hecho click en: ' + date.format());
}


let configuracion = {
    height: "100%",
    header: { left: 'prev,next', center: 'title', right: 'month,basicWeek,basicDay' },
    // header: { left: 'prev,next', center: 'title', right: 'month,agendaWeek,agendaDay' },
    defaultDate: new Date(),
    buttonIcons: true, // show the prev/next text
    weekNumbers: false,
    editable: false,
    eventLimit: true, // allow "more" link when too many events
    events: [],
    displayEventTime: true,
    defaultView: 'basicDay',
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
            color: cita.estado.color || '#3A87AD',
            textColor: '#ffffff',
            cita: cita,
            url:  window.location.origin+ "/siget/cita/informacionCita/"+cita.id
        };
    });

    $(idCalendario).fullCalendar('removeEvents');
    $(idCalendario).fullCalendar('addEventSource', citas);
}







$(document).ready(function () {
    $(idCalendario).fullCalendar(configuracion);
    listarCitas();
});


