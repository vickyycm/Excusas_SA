package ar.edu.davinci;

public class CorteLuz extends Moderada {

    public static final String EMAIL_EDESUR = "EDESUR@mailfake.com.ar";

    @Override
    public void procesar(EncargadoI encargado, ExcusaI excusa) {
        encargado.enviarEmail(EMAIL_EDESUR, "consulta corte de suministro",
                "¿Hubo un corte de luz en el barrio?");
    }
}
