public class Matricula extends Registre {
    private String assignatura;

    public Matricula(){
        super();
        tipus = Tipus.MATRICULA;
    }

    public Matricula(String registre) {
        super();
        tipus = Tipus.MATRICULA;
        String camps[] = registre.split(";");
        this.assignatura = camps[1];
    }

    public String getAssignatura() {
        return assignatura;
    }

    public static boolean filtrar(String registre){
        String camps[] = registre.split(";");
        return !("obligatoria".equalsIgnoreCase(camps[4]) && Float.parseFloat(camps[3]) > 3.5);
    }

    @Override
    public Tipus getTipus() {
        return super.tipus;
    }

    @Override
    public String toString() {
        return assignatura;
    }
}
