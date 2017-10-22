package com.gestionuap33.www.asistenciasaup33.control;

import java.util.ArrayList;

/**
 * Created by Isai on 04/06/2017.
 */

public class Grupos {

    public int estado;
    public ArrayList<Grupo> autos = new ArrayList<>();

    public Grupos(int estado, ArrayList<Grupo> autos) {
        this.estado = estado;
        this.autos = autos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ArrayList<Grupo> getAutos() {
        return autos;
    }

    public void setAutos(ArrayList<Grupo> autos) {
        this.autos = autos;
    }

    class Grupo {

        public int listaId;
        public int listaGrupo;
        public String horarioDetalleDia;
        public String horarioDetalleEntrada;
        public String horarioDetalleSalida;

        public Grupo(int listaId, int listaGrupo, String horarioDetalleDia, String horarioDetalleEntrada, String horarioDetalleSalida) {
            this.listaId = listaId;
            this.listaGrupo = listaGrupo;
            this.horarioDetalleDia = horarioDetalleDia;
            this.horarioDetalleEntrada = horarioDetalleEntrada;
            this.horarioDetalleSalida = horarioDetalleSalida;
        }

        public int getListaId() {
            return listaId;
        }

        public void setListaId(int listaId) {
            this.listaId = listaId;
        }

        public int getListaGrupo() {
            return listaGrupo;
        }

        public void setListaGrupo(int listaGrupo) {
            this.listaGrupo = listaGrupo;
        }

        public String getHorarioDetalleDia() {
            return horarioDetalleDia;
        }

        public void setHorarioDetalleDia(String horarioDetalleDia) {
            this.horarioDetalleDia = horarioDetalleDia;
        }

        public String getHorarioDetalleEntrada() {
            return horarioDetalleEntrada;
        }

        public void setHorarioDetalleEntrada(String horarioDetalleEntrada) {
            this.horarioDetalleEntrada = horarioDetalleEntrada;
        }

        public String getHorarioDetalleSalida() {
            return horarioDetalleSalida;
        }

        public void setHorarioDetalleSalida(String horarioDetalleSalida) {
            this.horarioDetalleSalida = horarioDetalleSalida;
        }
    }

}
