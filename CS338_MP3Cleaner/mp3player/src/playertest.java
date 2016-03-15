import javax.swing.JFrame;
/**
 * Joshua Gihorski
 * MP3 Cleaner Project
 * CS338
 * 03/09/2016
 */
    public class playertest { 
       public static void main(String[] arguments){ 
    	   player myplayer = new player(); // create a new mp3 cleaner window
    	   myplayer.setVisible(true); // turn on the visibility
    	   myplayer.setSize(800, 650); // set the mp3 cleaner window size width=800 height=650
    	   myplayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close mp3 cleaner window on exit
       }
   }