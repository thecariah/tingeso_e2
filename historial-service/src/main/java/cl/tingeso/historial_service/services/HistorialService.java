package cl.tingeso.historial_service.services;

import cl.tingeso.historial_service.entities.HistorialEntity;
import cl.tingeso.historial_service.models.ReparacionModel;
import cl.tingeso.historial_service.models.VehiculoModel;
import cl.tingeso.historial_service.repositories.HistorialRepository;
import com.ctc.wstx.shaded.msv_core.grammar.dtd.LocalNameClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistorialService {
    @Autowired
    HistorialRepository historialRepository;

    @Autowired
    RestTemplate restTemplate;

    public VehiculoModel obtenerVehiculoPorPatente(String patente){
        VehiculoModel vehiculo = restTemplate.getForObject("http://vehiculo-service/vehiculos/" + patente, VehiculoModel.class);
        return vehiculo;
    }

    public List<ReparacionModel> obtenerReparacionesDeVehiculo(String patente){
        try{
            List<ReparacionModel> reparaciones = restTemplate.getForObject("http://reparacion-service/reparaciones/" + patente, List.class);
            return reparaciones;
        }
        catch(HttpClientErrorException ex){
            List<ReparacionModel> reparaciones = null;
            return reparaciones;
        }
    }

    public LocalDate convertirStringAFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formato);
    }

    public List<ReparacionModel> filtrarReparacionesPorFecha(List<ReparacionModel> reparaciones, String fechaInicio, String fechaFinal){
        List<ReparacionModel> reparacionesFiltradas = new ArrayList<>();
        LocalDate fechaIn = convertirStringAFecha(fechaInicio);
        LocalDate fechaFin = convertirStringAFecha(fechaFinal);

        for (int i = 0; i < reparaciones.size(); i++){
            ReparacionModel reparacion = reparaciones.get(i);
            LocalDate fechaRep = convertirStringAFecha(reparacion.getFecha());

            if ((fechaRep.isAfter(fechaIn) || fechaRep.isEqual(fechaIn)) && (fechaRep.isBefore(fechaFin) || fechaRep.isEqual(fechaFin))){
                reparacionesFiltradas.add(reparacion);
            }
        }

        return reparacionesFiltradas;
    }

    // Objetivo: Calcular el porcentaje de descuento segun el numero de reparaciones de los ultimos 12 meses.
    public int descuentoNumeroReparaciones(String patente){
        List<ReparacionModel> reparacionesVehiculo = obtenerReparacionesDeVehiculo(patente);

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaHace12Meses = fechaActual.minus(12, ChronoUnit.MONTHS);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ReparacionModel> reparacionesVehiculo12Meses = filtrarReparacionesPorFecha(reparacionesVehiculo,
                fechaHace12Meses.format(formato), fechaActual.format(formato));

        int numRepar = reparacionesVehiculo12Meses.size();

        VehiculoModel vehiculo = obtenerVehiculoPorPatente(patente);
        String motor = vehiculo.getMotor();

        if (numRepar >= 1 && numRepar <= 2){
            return switch (motor) {
                case "gasolina" -> 5;
                case "diesel" -> 7;
                case "hibrido" -> 10;
                case "electrico" -> 8;
                default -> 0;
            };
        }
        else if (numRepar >= 3 && numRepar <= 5){
            return switch (motor) {
                case "gasolina" -> 10;
                case "diesel" -> 12;
                case "hibrido" -> 15;
                case "electrico" -> 13;
                default -> 0;
            };
        }
        else if (numRepar >= 6 && numRepar <= 9) {
            return switch (motor) {
                case "gasolina" -> 15;
                case "diesel" -> 17;
                case "hibrido" -> 20;
                case "electrico" -> 18;
                default -> 0;
            };
        }
        else if (numRepar >= 10) {
            return switch (motor) {
                case "gasolina" -> 20;
                case "diesel" -> 22;
                case "hibrido" -> 25;
                case "electrico" -> 23;
                default -> 0;
            };
        }
        else{
            return 0;
        }
    }

    // Objetivo: Calcular el procentaje de descuento por el dia de atencion del vehiculo.
    public int descuentoDiaAtencion(String fechaIn, String horaIn) {
        LocalDate fechaIngreso = convertirStringAFecha(fechaIn);
        DayOfWeek dia = fechaIngreso.getDayOfWeek();
        String diaStr = dia.toString();


        if (diaStr.equals("MONDAY") || diaStr.equals("THURSDAY")) {
            LocalTime horaInicio = LocalTime.parse("09:00");
            LocalTime horaFin = LocalTime.parse("12:00");

            LocalTime horaIngreso = LocalTime.parse(horaIn);

            if (horaIngreso.isAfter(horaInicio) && horaIngreso.isBefore(horaFin)){
                return 10;
            }
            else{
                return 0;
            }
        }
        else{
            return 0;
        }
    }

    // Objetivo: Calcular el porcentaje de recargo segun el kilometraje registrado del vehiculo.
    public int recargoPorKilometraje(String patente, int kilometraje){
        VehiculoModel vehiculo = obtenerVehiculoPorPatente(patente);
        String tipoVehiculo = vehiculo.getTipo();


        if(kilometraje >= 0 && kilometraje <= 5000){
            return 0;
        }
        else if(kilometraje >= 5001 && kilometraje <= 12000){
            return switch (tipoVehiculo) {
                case "sedan" -> 3;
                case "hatchback" -> 3;
                case "suv" -> 5;
                case "pickup" -> 5;
                case "furgoneta" -> 5;
                default -> 0;
            };
        }
        else if(kilometraje >= 12001 && kilometraje <= 25000){
            return switch (tipoVehiculo) {
                case "sedan" -> 7;
                case "hatchback" -> 7;
                case "suv" -> 9;
                case "pickup" -> 9;
                case "furgoneta" -> 9;
                default -> 0;
            };
        }
        else if(kilometraje >= 25001 && kilometraje <= 40000){
            return 12;
        }
        else if(kilometraje >= 40001){
            return 20;
        }
        else{
            return 0;
        }
    }

    // Objetivo: Calcular el porcentaje de recargo segun la antiguedad del vehiculo.
    public int recargoPorAntiguedad(String patente){
        VehiculoModel vehiculo = obtenerVehiculoPorPatente(patente);
        String tipoVehiculo = vehiculo.getTipo();

        int vehiculoYear = vehiculo.getFabricacion();
        int actualYear = Year.now().getValue();
        int antiguedad = actualYear - vehiculoYear;

        if (antiguedad >= 0 && antiguedad <= 5){
            return 0;
        }
        else if(antiguedad >= 6 && antiguedad <= 10){
            return switch (tipoVehiculo) {
                case "sedan" -> 5;
                case "hatchback" -> 5;
                case "suv" -> 7;
                case "pickup" -> 7;
                case "furgoneta" -> 7;
                default -> 0;
            };
        }
        else if(antiguedad >= 11 && antiguedad <= 15){
            return switch (tipoVehiculo) {
                case "sedan" -> 9;
                case "hatchback" -> 9;
                case "suv" -> 11;
                case "pickup" -> 11;
                case "furgoneta" -> 11;
                default -> 0;
            };
        }
        else if(antiguedad >= 16){
            return switch (tipoVehiculo) {
                case "sedan" -> 15;
                case "hatchback" -> 15;
                case "suv" -> 20;
                case "pickup" -> 20;
                case "furgoneta" -> 20;
                default -> 0;
            };
        }
        else{
            return 0;
        }
    }

    // Objetivo: Calcular el porcentaje de recargo por dias de retraso en la recogida del vehiculo.
    public int recargoPorRetrasoRecogida(String fechaSal, String fechaRet){
        LocalDate fechaSalida = convertirStringAFecha(fechaSal);
        LocalDate fechaRetiro = convertirStringAFecha(fechaRet);

        long diasDeRetraso = ChronoUnit.DAYS.between(fechaSalida, fechaRetiro);
        diasDeRetraso = Math.max(diasDeRetraso, 0);

        int porcentajeRecargo = 5 * (int) diasDeRetraso;

        return porcentajeRecargo;
    }

    public int calcularValor(int montoTotal, int porcentaje){
        // 100% = montoTotal
        // porcentaje% = X

        return ((montoTotal * porcentaje) / 100);
    }

    public void guardarEnHistorial(String fecha_ingreso, String hora_ingreso,
                                   String fecha_salida, String hora_salida,
                                   String fecha_retiro, String hora_retiro,
                                   String patente, int bono, int kilometraje){

        HistorialEntity historial_rep = new HistorialEntity();

        historial_rep.setFecha_ingreso(fecha_ingreso);
        historial_rep.setHora_ingreso(hora_ingreso);
        historial_rep.setFecha_salida(fecha_salida);
        historial_rep.setHora_salida(hora_salida);
        historial_rep.setFecha_retiro(fecha_retiro);
        historial_rep.setHora_retiro(hora_retiro);
        historial_rep.setPatente(patente);
        historial_rep.setBono(bono);
        historial_rep.setKilometraje(kilometraje);

        List<ReparacionModel> reparacionesVehiculo = obtenerReparacionesDeVehiculo(patente);
        List<ReparacionModel> reparacionesRealizadas = filtrarReparacionesPorFecha(reparacionesVehiculo, fecha_ingreso, fecha_salida);

        // MONTO REPARACIONES
        int monto_reparaciones = 0;

        for (int i = 0; i < reparacionesRealizadas.size(); i++){
            monto_reparaciones = monto_reparaciones + reparacionesRealizadas.get(i).getMonto();
        }

        historial_rep.setMonto_reparaciones(monto_reparaciones);

        // MONTO RECARGOS
        int monto_recargos = calcularValor(monto_reparaciones, recargoPorKilometraje(patente, kilometraje)) +
                calcularValor(monto_reparaciones, recargoPorAntiguedad(patente)) +
                calcularValor(monto_reparaciones, recargoPorRetrasoRecogida(fecha_salida, fecha_retiro));

        historial_rep.setMonto_recargos(monto_recargos);

        // MONTO DESCUENTOS
        int monto_descuentos = calcularValor(monto_reparaciones, descuentoNumeroReparaciones(patente)) +
                calcularValor(monto_reparaciones, descuentoDiaAtencion(fecha_ingreso, hora_ingreso)) + bono;

        historial_rep.setMonto_descuentos(monto_descuentos);

        // MONTO IVA
        int monto_iva = calcularValor(monto_reparaciones, 19);
        historial_rep.setMonto_iva(monto_iva);

        // COSTO TOTAL
        int costo_total = (monto_reparaciones + monto_recargos - monto_descuentos) + monto_iva;
        historial_rep.setCosto_total(costo_total);

        historialRepository.save(historial_rep);
    }

    public void deleteHistorial(){ historialRepository.deleteAll(); }

    public List<HistorialEntity> obtenerHistorial(){
        return historialRepository.findAll();
    }

    public List<HistorialEntity> historialByVehiculo(String patente){
        return this.historialRepository.historialByPatente(patente);
    }
}
