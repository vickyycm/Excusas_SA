package ar.edu.davinci;

import java.util.ArrayList;
import java.util.List;

//SINGLETON Y OBSERVER
public class AdministradorProntuario implements Observable {

    private static AdministradorProntuario instancia;

    private final List<ProntuarioI> prontuarios;
    private final List<Observador> observadores;

    private AdministradorProntuario() {
        this.prontuarios = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    public static AdministradorProntuario getInstancia() {
        instancia = java.util.Objects.requireNonNullElseGet(instancia, AdministradorProntuario::new);
        return instancia;
    }

    public void persistirProntuario(ProntuarioI prontuario) {
        prontuarios.add(prontuario);
        notificar(prontuario);
    }

    public int cantidadProntuarios() {
        return prontuarios.size();
    }

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificar(ProntuarioI prontuario) {
        observadores.forEach(observador -> observador.actualizar(prontuario));
    }
}
