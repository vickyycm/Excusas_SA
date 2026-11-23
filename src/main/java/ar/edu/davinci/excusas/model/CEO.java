package ar.edu.davinci.excusas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//observador
public class CEO extends Encargado implements Observador {

    private final List<ProntuarioI> prontuariosInformados = new ArrayList<>();

    //inverosímiles
    public CEO(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery, Set.of(Motivo.INVEROSIMIL));
    }

    @Override
    protected void notificar(ExcusaI excusa) {
        enviarEmail(excusa.getEmpleado().getEmail(), "motivo demora", "Aprobado por creatividad");
    }

    //sobrescribe template
    @Override
    protected void registrar(ExcusaI excusa) {
        AdministradorProntuario.getInstancia().persistirProntuario(new Prontuario(excusa.getEmpleado(), excusa));
    }

    @Override
    public void actualizar(ProntuarioI prontuario) {
        prontuariosInformados.add(prontuario);
    }

    public int cantidadProntuariosInformados() {
        return prontuariosInformados.size();
    }
}
