public class Estudiant extends Registre {
    private String nom;
    private String cognom1;
    private String cognom2;

    public Estudiant(){
        super();
        tipus = Tipus.ESTUDIANT;
    }

    public Estudiant(String registre) {
        super();
        tipus = Tipus.ESTUDIANT;
        String[] camps = registre.split(";");
        this.nom = camps[1];
        this.cognom1 = camps[2];
        this.cognom2 = camps[3];
    }

    public String getNom() {
        return nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public static boolean filtrar(String registre){
        String [] camps = registre.split(";");
        return !"zaragoza".equalsIgnoreCase(camps[5]);
    }

    @Override
    public Tipus getTipus() {
        return tipus;
    }

    @Override
    public String toString() {
        return nom+", "+cognom1+", "+cognom2;
    }
}
