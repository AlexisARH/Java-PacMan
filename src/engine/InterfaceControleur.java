package engine;

public interface InterfaceControleur{
    void start();
    void step();
    void run();
    void pause();
    void setTime(long _time);
}