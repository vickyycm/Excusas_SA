package ar.edu.davinci;

import java.util.ArrayList;
import java.util.List;

//observador
public class CEO extends Encargado implements Observador {

    private final List<ProntuarioI> prontuariosInformados = new ArrayList<>();

    public CEO(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery);
    }

    //inverosímiles
    @Override
    public void manejarInverosimil(ExcusaI excusa) {
        procesar(excusa);
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
