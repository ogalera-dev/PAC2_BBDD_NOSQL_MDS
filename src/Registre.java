public class Registre {
    public static enum Tipus{
        ESTUDIANT,
        MATRICULA,
        INDEFINIT
    }

    protected Tipus tipus;

    public Registre(){
        tipus = Tipus.INDEFINIT;
    }

    public Tipus getTipus(){
        return tipus;
    }
}
