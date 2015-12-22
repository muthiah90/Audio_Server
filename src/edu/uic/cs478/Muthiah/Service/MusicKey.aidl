package edu.uic.cs478.Muthiah.Service;

interface MusicKey {
    void startMusic(int clipID);
    
    void stopMusic();
    
    void pauseMusic();
    
    void resumePlay();
    
    List<String> readTransactionData();
}