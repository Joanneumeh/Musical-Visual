package Assignment;

import processing.core.PApplet;
import ddf.minim.analysis.BeatDetect;
import ie.tudublin.*;

public class Visualiser extends Visual {
    // Class Objects
    swirl swirl_obj;
    Amp Amp_obj;
    Squares Squares_obj;
    Space Space_obj;
    tunnel_vision tunnel_vision_obj;
    wave_length wave_length_obj;
    

    public void settings() {
        fullScreen(P3D, SPAN);
        smooth();
    }
    

    int mode = 1;

    public void keyPressed() {
        if (key >= '0' && key <= '9') {
            mode = key - '0';
        }
        if (keyCode == ' ') {
            if (getAudioPlayer().isPlaying()) {
                getAudioPlayer().pause();
            } else {
                getAudioPlayer().rewind();
                getAudioPlayer().play();
            }
        }
    }

    public void setup() {
        startMinim();
        loadAudio("Sere.mp3");
        getAudioPlayer().play();

        // Object Intialisation
        swirl_obj = new swirl(this);
        Amp_obj = new Amp(this);
        Squares_obj = new Squares(this);
        Space_obj = new Space(this);
        tunnel_vision_obj = new tunnel_vision(this);
        wave_length_obj = new wave_length(this);
        

    }

    public void draw() {

        switch(mode)
        {
            
            case 1:
            {
                Amp_obj.render();
                break;
            }

            case 2:
            {
                Squares_obj.render();
                break;
            }

            case 3:
            {
                Space_obj.render();
                break;
            }

            case 4:
            {
                tunnel_vision_obj.render();
                break;
            }

            case 5:
            {
                wave_length_obj.render();
                break;
            }

            case 6:
            {
                swirl_obj.render();
                break;
            }
            
            case 7:
            {
                //partyStars_obj.render();
                break;
            }
        }
        
    }

    public BeatDetect getBeatDetect() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBeatDetect'");
    }

}
