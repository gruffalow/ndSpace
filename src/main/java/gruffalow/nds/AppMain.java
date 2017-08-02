package gruffalow.nds;

public class AppMain {
    public NDSEngine engine;

    public static void main(String[] args) {
        AppMain appMain = new AppMain();
        appMain.runner(args);
    }

    public void runner(String[] args) {
        System.out.println("N-Dimensional Space Mapping provides a technique to compress and encrypt data within a single algorithm.");
        engine = new NDSEngine();
    }
}
