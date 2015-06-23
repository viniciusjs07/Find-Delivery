package finddelivery.es.projeto.finddelivery.models;

/**
 * Created by Rayssa on 23/06/2015.
 */
public enum SpecialityType {

    COMIDA_BRASILEIRA(1), COMIDA_MEXICANA(2), COMIDA_JAPONESA(3), COMIDA_CHINESA(4),
    COMIDA_ITALIANA(5), COMIDA_VARIADA(6), COMIDA_SAUDAVEL(7), LANCHES(8), PIZZA(9),
    DOCES(10), SALGADOS(11), FRUTOS_DO_MAR(12),CAFE(13), CARNES(14), BEBIDAS(15),
    SALADAS(16), MARMITAS(17), MASSAS(18);

    private final int value;

    private SpecialityType(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
