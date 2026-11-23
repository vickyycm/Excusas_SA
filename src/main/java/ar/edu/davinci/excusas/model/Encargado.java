package ar.edu.davinci.excusas.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

//CHAIN OF RESPONSIBILITY Y TEMPLATE METHOD
public abstract class Encargado extends Empleado implements EncargadoI {

    private EncargadoI siguiente;
    private final DeliveryI delivery;
    private final Email email;
    private final Map<Motivo, Consumer<ExcusaI>> acciones;

    public Encargado(String nombre, String emailEmpleado, int numeroLegajo, Email email, DeliveryI delivery,
                     Set<Motivo> motivos) {
        super(nombre, emailEmpleado, numeroLegajo);
        this.email = email;
        this.delivery = delivery;
        this.siguiente = new Rechazador(); // si nadie más la toma, se rechaza
        this.acciones = new EnumMap<>(Motivo.class);
        motivos.forEach(motivo -> acciones.put(motivo, this::procesar));
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

    //si el motivo no está en la tabla, no puede
    public void evaluar(ExcusaI excusa) {
        acciones.getOrDefault(excusa.getMotivo(), this::noPuedo).accept(excusa);
    }

    public void pasarAlSiguiente(ExcusaI excusa) {
        siguiente.manejar(excusa);
    }

    private void noPuedo(ExcusaI excusa) {
        delivery.noPuedeProcesar(this, excusa);
    }

    public final void procesar(ExcusaI excusa) {
        excusa.aceptar(getNombre());
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

    @Override
    public Set<Motivo> getMotivos() {
        return Set.copyOf(acciones.keySet());
    }

    public void cambiarModo(Modo modo) {
        delivery.cambiarModo(modo);
    }

    public ModoTrabajo getModoTrabajo() {
        return delivery.getModo().getModoTrabajo();
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
