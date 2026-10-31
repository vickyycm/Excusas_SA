package ar.edu.davinci;

public class Productivo implements Modo {

    public static final String EMAIL_CTO = "cto@excusas.com";

    @Override
    public void tratar(Encargado encargado, ExcusaI excusa) {
        encargado.evaluar(excusa);
    }

    @Override
    public void noPuedeProcesar(Encargado encargado, ExcusaI excusa) {
        encargado.enviarEmail(EMAIL_CTO, "excusa derivada",
                "No pude procesar la excusa de " + excusa.getEmpleado().getNombre()
                        + ", la paso al siguiente sector");
        encargado.pasarAlSiguiente(excusa);
    }

    @Override
    public void volverAlDiaSiguiente(DeliveryI delivery) {
    }
}
