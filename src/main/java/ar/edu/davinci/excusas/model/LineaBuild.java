package ar.edu.davinci.excusas.model;

//BUILDER
public interface LineaBuild {
    LineaBuild agregar(EncargadoI encargado);
    LineaDeEncargadosI construir();
}
