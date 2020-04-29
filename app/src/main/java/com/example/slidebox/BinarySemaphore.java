package com.example.slidebox;

public class BinarySemaphore extends Semaphore
{
    public BinarySemaphore(){
        super();
    }
    public BinarySemaphore(int initial){
        super(validation(initial));
    }
    private static int validation(int initial){
        return (initial>=0) ? 1 : 0;
    }
    @Override
    public synchronized void V()
    {
        if (value==0){
            super.V();
        }
    }
}
