package ar.edu.davinci;

public class GerenteRRHH extends Encargado {

    public GerenteRRHH(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo, email, delivery);
    }

    //complejas
    @Override
    public void manejarCompleja(ExcusaI excusa) {
        procesar(excusa);
    }

    @Override
    protected void notificar(ExcusaI excusa) {
    }
}
