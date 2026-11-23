package ar.edu.davinci.excusas.model;

import java.util.List;

//OBSERVER
public class EquipoDireccion implements Observador {

    private final List<ProntuarioI> recibidos = new java.util.ArrayList<>();

    @Override
    public void actualizar(ProntuarioI prontuario) {
        recibidos.add(prontuario);
    }

    public int cantidadAvisosRecibidos() {
        return recibidos.size();
    }

    public List<ProntuarioI> getRecibidos() {
        return recibidos;
    }
}
