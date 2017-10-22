package com.gestionuap33.www.asistenciasaup33.control;

/**
 * Created by Isai on 15/06/2017.
 */

public class Lista {

    public int ListaId;
    public String ListaGrupo;
    public String ListaPeriodoLectivo;
    public String ListaSemestre;
    public String HorarioDetalleEntrada;
    public String HorarioDetalleSalida;
    public String UnidadDeAprendizajeId;
    public String UnidadDeAprendizajeNombre;
    public String ListaTurno;
    public String HorarioDetalleDia;

    public Lista(int listaId, String listaGrupo, String listaPeriodoLectivo, String listaSemestre, String horarioDetalleEntrada, String horarioDetalleSalida, String unidadDeAprendizajeId, String unidadDeAprendizajeNombre, String listaTurno, String horarioDetalleDia) {
        ListaId = listaId;
        ListaGrupo = listaGrupo;
        ListaPeriodoLectivo = listaPeriodoLectivo;
        ListaSemestre = listaSemestre;
        HorarioDetalleEntrada = horarioDetalleEntrada;
        HorarioDetalleSalida = horarioDetalleSalida;
        UnidadDeAprendizajeId = unidadDeAprendizajeId;
        UnidadDeAprendizajeNombre = unidadDeAprendizajeNombre;
        ListaTurno = listaTurno;
        HorarioDetalleDia = horarioDetalleDia;
    }

    public int getListaId() {
        return ListaId;
    }

    public void setListaId(int listaId) {
        ListaId = listaId;
    }

    public String getListaGrupo() {
        return ListaGrupo;
    }

    public void setListaGrupo(String listaGrupo) {
        ListaGrupo = listaGrupo;
    }

    public String getListaPeriodoLectivo() {
        return ListaPeriodoLectivo;
    }

    public void setListaPeriodoLectivo(String listaPeriodoLectivo) {
        ListaPeriodoLectivo = listaPeriodoLectivo;
    }

    public String getListaSemestre() {
        return ListaSemestre;
    }

    public void setListaSemestre(String listaSemestre) {
        ListaSemestre = listaSemestre;
    }

    public String getHorarioDetalleEntrada() {
        return HorarioDetalleEntrada;
    }

    public void setHorarioDetalleEntrada(String horarioDetalleEntrada) {
        HorarioDetalleEntrada = horarioDetalleEntrada;
    }

    public String getHorarioDetalleSalida() {
        return HorarioDetalleSalida;
    }

    public void setHorarioDetalleSalida(String horarioDetalleSalida) {
        HorarioDetalleSalida = horarioDetalleSalida;
    }

    public String getUnidadDeAprendizajeId() {
        return UnidadDeAprendizajeId;
    }

    public void setUnidadDeAprendizajeId(String unidadDeAprendizajeId) {
        UnidadDeAprendizajeId = unidadDeAprendizajeId;
    }

    public String getUnidadDeAprendizajeNombre() {
        return UnidadDeAprendizajeNombre;
    }

    public void setUnidadDeAprendizajeNombre(String unidadDeAprendizajeNombre) {
        UnidadDeAprendizajeNombre = unidadDeAprendizajeNombre;
    }

    public String getListaTurno() {
        return ListaTurno;
    }

    public void setListaTurno(String listaTurno) {
        ListaTurno = listaTurno;
    }

    public String getHorarioDetalleDia() {
        return HorarioDetalleDia;
    }

    public void setHorarioDetalleDia(String horarioDetalleDia) {
        HorarioDetalleDia = horarioDetalleDia;
    }
}
