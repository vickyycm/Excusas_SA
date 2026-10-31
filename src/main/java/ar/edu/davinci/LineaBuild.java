package ar.edu.davinci;

//BUILDER
public interface LineaBuild {
    LineaBuild agregar(EncargadoI encargado);
    LineaDeEncargadosI construir();
}
