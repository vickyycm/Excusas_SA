package ar.edu.davinci;

//CHAIN OF RESPONSIBILITY Y TEMPLATE METHOD
public abstract class Encargado extends Empleado implements EncargadoI {

    private EncargadoI siguiente;
    private final DeliveryI delivery;
    private final Email email;

    public Encargado(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery) {
        super(nombre, emailEmpleado, numeroLegajo);
        this.email = email;
        this.delivery = delivery;
        this.siguiente = new Rechazador(); // si nadie más la toma, se rechaza
    }

    @Override
    public void setSiguiente(EncargadoI siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void manejar(ExcusaI excusa) {
        revisarExcusa(excusa);
    }

    @Override
    public void revisarExcusa(ExcusaI excusa) {
        delivery.entregar(this, excusa);
    }

    public void evaluar(ExcusaI excusa) {
        excusa.serManejadaPor(this);
    }

    public void pasarAlSiguiente(ExcusaI excusa) {
        siguiente.manejar(excusa);
    }

    @Override public void manejarTrivial(ExcusaI excusa) { noPuedo(excusa); }
    @Override public void manejarModerada(ExcusaI excusa) { noPuedo(excusa); }
    @Override public void manejarCompleja(ExcusaI excusa) { noPuedo(excusa); }
    @Override public void manejarInverosimil(ExcusaI excusa) { noPuedo(excusa); }

    private void noPuedo(ExcusaI excusa) {
        delivery.noPuedeProcesar(this, excusa);
    }

    public final void procesar(ExcusaI excusa) {
        excusa.aceptar();
        notificar(excusa);
        registrar(excusa);
        delivery.excusaProcesada();
    }

    protected abstract void notificar(ExcusaI excusa);

    protected void registrar(ExcusaI excusa) {
    }

    @Override
    public void enviarEmail(String destino, String asunto, String cuerpo) {
        email.enviarEmail(destino, getEmail(), asunto, cuerpo);
    }

    public void tomarCafe() {
        delivery.tomarCafe();
    }

    public void tomarBreak(int minutos) {
        delivery.tomarBreak(minutos);
    }

    public void volverAlDiaSiguiente() {
        delivery.volverAlDiaSiguiente();
    }

    public DeliveryI getDelivery() {
        return delivery;
    }
}
