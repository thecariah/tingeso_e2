package cl.tingeso.autofix_service.services;

import cl.tingeso.autofix_service.models.ReparacionModel;
import cl.tingeso.autofix_service.models.VehiculoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutofixService {

    @Autowired
    RestTemplate restTemplate;

    public VehiculoModel obtenerVehiculoPorPatente(String patente){
        VehiculoModel vehiculo = restTemplate.getForObject("http://vehiculo-service/vehiculos/" + patente, VehiculoModel.class);
        return vehiculo;
    }

    public List<ReparacionModel> obtenerReparacionesPorTipo(String tipo){
        List<ReparacionModel> reparaciones = restTemplate.getForObject("http://reparacion-service/reparaciones/" + tipo, List.class);
        return reparaciones;
    }

    public ArrayList<ArrayList<Integer>> crearLista2D(int filas, int columnas){
        ArrayList<ArrayList<Integer>> lista2D = new ArrayList<>();

        for (int i = 0; i < filas; i++) {
            ArrayList<Integer> fila = new ArrayList<>();

            for (int j = 0; j < columnas; j++) {
                fila.add(0);
            }
            lista2D.add(fila);
        }

        return lista2D;
    }

    public LocalDate convertirStringAFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formato);
    }

    public int calcularPorcentajeVariacion(int valorActual, int valorAnterior){
        double variacion = valorActual - valorAnterior;
        double porcentajeVar = (variacion / valorAnterior) * 100;
        int porcentajeVarInt = Double.valueOf(porcentajeVar).intValue();
        return porcentajeVarInt;
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

    // Objetivo: Reporte de todos los tipos de reparaciones vs el número de tipos de vehículos que se repararon
    public ArrayList<ArrayList<Integer>> reporte1(String month, String year){

        // DEFINIMOS LISTA 2D CON 11 FILAS Y 10 COLUMNAS:
        // rep 1  = {cantSedan, montoSedan, cantHatch, montoHatch, cantSuv, montoSuv, cantPickup, montoPickup, cantFurg, montoFurg}
        // rep 2  = {cantSedan, montoSedan, cantHatch, montoHatch, cantSuv, montoSuv, cantPickup, montoPickup, cantFurg, montoFurg}
        // etc...
        ArrayList<ArrayList<Integer>> reporte1 = crearLista2D(11, 10);

        // DEFINIMOS FECHAS:
        String inicioMes = year + "-" + month + "-01";
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        String finMes = endOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // LLENAMOS LISTA REPORTE1:
        for (int i = 1; i <= 11; i++){
            List<ReparacionModel> reparaciones = obtenerReparacionesPorTipo(Integer.toString(i));
            List<ReparacionModel> reparacionesFiltrada = filtrarReparacionesPorFecha(reparaciones, inicioMes, finMes);

            // si no esta vacia:
            if (!reparacionesFiltrada.isEmpty()){
                for (int j = 0; j < reparaciones.size(); j++){
                    ReparacionModel rep = reparacionesFiltrada.get(j);
                    String patente = rep.getPatente();

                    VehiculoModel vehiculo = obtenerVehiculoPorPatente(patente);
                    String tipoVehiculo = vehiculo.getTipo();

                    int cant, monto;

                    switch (tipoVehiculo) {
                        case "sedan":
                            cant = reporte1.get(i-1).get(0);
                            monto = reporte1.get(i-1).get(1);
                            reporte1.get(i-1).set(0, cant + 1);
                            reporte1.get(i-1).set(1, monto + rep.getMonto());
                            break;
                        case "hatchback":
                            cant = reporte1.get(i-1).get(2);
                            monto = reporte1.get(i-1).get(3);
                            reporte1.get(i-1).set(2, cant + 1);
                            reporte1.get(i-1).set(3, monto + rep.getMonto());
                            break;
                        case "suv":
                            cant = reporte1.get(i-1).get(4);
                            monto = reporte1.get(i-1).get(5);
                            reporte1.get(i-1).set(4, cant + 1);
                            reporte1.get(i-1).set(5, monto + rep.getMonto());
                            break;
                        case "pickup":
                            cant = reporte1.get(i-1).get(6);
                            monto = reporte1.get(i-1).get(7);
                            reporte1.get(i-1).set(6, cant + 1);
                            reporte1.get(i-1).set(7, monto + rep.getMonto());
                            break;
                        case "furgoneta":
                            cant = reporte1.get(i-1).get(8);
                            monto = reporte1.get(i-1).get(9);
                            reporte1.get(i-1).set(8, cant + 1);
                            reporte1.get(i-1).set(9, monto + rep.getMonto());
                            break;
                        default:
                            break;
                    }
                }

            }
        }

        return reporte1;
    }

    // Objetivo: Reporte comparativo de reparaciones (cantidad y monto) de un mes en particular respecto de los dos meses previos
    public ArrayList<ArrayList<Integer>> reporte2(String month, String year){

        // DEFINIMOS LISTA 2D CON 11 FILAS Y 10 COLUMNAS:
        // rep 1  = {cantMesActual, montoMesActual, cantMesAnt1, montoMesAnt1, cantMesAnt2, montoMesAnt2, cantVar1, montoVar1, cantVar2, montoVar2}
        // rep 2  = {cantMesActual, montoMesActual, cantMesAnt1, montoMesAnt1, cantMesAnt2, montoMesAnt2, cantVar1, montoVar1, cantVar2, montoVar2}
        // etc...
        ArrayList<ArrayList<Integer>> reporte2 = crearLista2D(11, 10);

        // MES ACTUAL:
        String inicioMesActual = year + "-" + month + "-01";
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        String finMesActual = endOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // MES ANTERIOR 1:
        YearMonth previousMonth = yearMonth.minusMonths(1);
        String inicioMesAnterior1 = previousMonth.atDay(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endOfPreviousMonth1 = previousMonth.atEndOfMonth();
        String finMesAnterior1 = endOfPreviousMonth1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // MES ANTERIOR 2:
        YearMonth previousMonth2 = previousMonth.minusMonths(1);
        String inicioMesAnterior2 = previousMonth2.atDay(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endOfPreviousMonth2 = previousMonth2.atEndOfMonth();
        String finMesAnterior2 = endOfPreviousMonth2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // LLENAMOS LISTA REPORTE2:
        for (int i = 1; i <= 11; i++){
            List<ReparacionModel> reparaciones = obtenerReparacionesPorTipo(Integer.toString(i));
            List<ReparacionModel> repMesActual = filtrarReparacionesPorFecha(reparaciones, inicioMesActual, finMesActual);
            List<ReparacionModel> repMesAnterior1 = filtrarReparacionesPorFecha(reparaciones, inicioMesAnterior1, finMesAnterior1);
            List<ReparacionModel> repMesAnterior2 = filtrarReparacionesPorFecha(reparaciones, inicioMesAnterior2, finMesAnterior2);

            // si las listas no estan vacia:
            if (!repMesActual.isEmpty()){
                int montoMesActual = 0;

                for (int j = 0; j < repMesActual.size(); j++){
                    ReparacionModel rep = repMesActual.get(j);
                    montoMesActual = montoMesActual + rep.getMonto();
                }

                reporte2.get(i-1).set(0, repMesActual.size());
                reporte2.get(i-1).set(1, montoMesActual);
            }
            if (!repMesAnterior1.isEmpty()){
                int montoMesAnt1 = 0;

                for (int j = 0; j < repMesAnterior1.size(); j++){
                    ReparacionModel rep = repMesAnterior1.get(j);
                    montoMesAnt1 = montoMesAnt1 + rep.getMonto();
                }

                reporte2.get(i-1).set(2, repMesAnterior1.size());
                reporte2.get(i-1).set(3, montoMesAnt1);
            }
            if (!repMesAnterior2.isEmpty()){
                int montoMesAnt2 = 0;

                for (int j = 0; j < repMesAnterior2.size(); j++){
                    ReparacionModel rep = repMesAnterior2.get(j);
                    montoMesAnt2 = montoMesAnt2 + rep.getMonto();
                }
                reporte2.get(i-1).set(4, repMesAnterior2.size());
                reporte2.get(i-1).set(5, montoMesAnt2);
            }
        }

        // ahora se calcula los porcentajes de variaciones:
        for (int i = 0; i <= 10; i++){
            // variacion entre mesActual y mesAnterior1
            reporte2.get(i).set(6, calcularPorcentajeVariacion(reporte2.get(i).get(0), reporte2.get(i).get(2)));
            reporte2.get(i).set(7, calcularPorcentajeVariacion(reporte2.get(i).get(1), reporte2.get(i).get(3)));
            // variacion entre mesAnterior1 y mesAnterior2
            reporte2.get(i).set(8, calcularPorcentajeVariacion(reporte2.get(i).get(2), reporte2.get(i).get(4)));
            reporte2.get(i).set(9, calcularPorcentajeVariacion(reporte2.get(i).get(3), reporte2.get(i).get(5)));
        }

        return reporte2;
    }

}
