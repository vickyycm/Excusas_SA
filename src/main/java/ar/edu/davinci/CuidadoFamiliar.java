package ar.edu.davinci;

public class CuidadoFamiliar extends Moderada {

    @Override
    public void procesar(EncargadoI encargado, ExcusaI excusa) {
        encargado.enviarEmail(excusa.getEmpleado().getEmail(), "motivo demora", "¿Está todo bien?");
    }
}
