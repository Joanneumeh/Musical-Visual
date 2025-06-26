package Assignment;

import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

public class Squares {
    Visualiser pa;

    int numHorizontal = 6; // Number of cubes in a horizontal row
    int numVertical = 6; // Number of cubes in a vertical column
    int numCubes; // Total number of cubes
    Cube[] cubes; // Array to hold cubes
    float rotationSpeed = 0.02f; // Speed of rotation
    float amplitude; // Audio amplitude
    float pulseStrength = 8.0f; // Strength of pulse
    float shimmerSpeed = 0.05f; // Speed of shimmer effect
    float shimmerOffset = 0.0f; // Offset for shimmer effect
    int[] colors; // Array to hold colours

    public Squares(Visualiser pa) {
        this.pa = pa;
        numCubes = numHorizontal * numVertical; 
        cubes = new Cube[numCubes]; // Initialize cubes array
        colors = new int[10]; // Initialize colors array with 10 slots

        // Generate a variety of colors
        for (int i = 0; i < colors.length; i++) {
            colors[i] = pa.color((i * 36) % 360, 80, 100); 
        }

        float cubeSize = 40; // Size of each cube
        float spacingX = pa.width / (numHorizontal + 1); // Spacing between cubes horizontally
        float spacingY = pa.height / (numVertical + 1); // Spacing between cubes vertically

        // Initialize cubes
        for (int i = 0; i < numVertical; i++) {
            for (int j = 0; j < numHorizontal; j++) {
                float x = spacingX * (j + 1); // Spread cubes uniformly across the screen horizontally
                float y = spacingY * (i + 1); // Spread cubes uniformly across the screen vertically
                float z = 0; // Place cubes on the screen 
                cubes[i * numHorizontal + j] = new Cube(x, y, z, cubeSize); // Create a cube
            }
        }
    }

    public void render() {
        pa.colorMode(PApplet.HSB, 255, 105, 180);
        pa.background(0); // Black background

        amplitude = pa.getAudioPlayer().mix.level(); // Get audio amplitude

        // Calculate colour index based on the audio amplitude
        int colorIndex = (int) PApplet.map(amplitude, 0, 1, 0, colors.length);

        // Update and display cubes
        for (int i = 0; i < numCubes; i++) {
            cubes[i].update(colors[colorIndex]);
            cubes[i].display();
        }

        // Update shimmer effect
        shimmerOffset += shimmerSpeed;
    }

    // Cube class
    class Cube {
        float x, y, z; // Position
        float size; // Size of the cube
        int color; // Cube colour
        float rotation; // Rotation angle
        float pulse; // Pulse value
        ArrayList<Particle> particles; // Arraylist to hold particles

        Cube(float x, float y, float z, float size) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = size;
            this.rotation = 0;
            this.pulse = 0;
            particles = new ArrayList<Particle>(); // Initialize particle arraylist
        }

        // Update cube colour and rotation based on audio input
        void update(int col) {
            color = col; // Set cube colour
            rotation += rotationSpeed; // Update rotation angle
            pulse = PApplet.sin(pa.frameCount * 0.2f) * pulseStrength; // Update pulse value

            // Add particles based on audio input
            if (amplitude > 0.5 && particles.size() < 100) {
                for (int i = 0; i < 5; i++) {
                    particles.add(new Particle(x, y, z, pa.random(-1, 1), pa.random(-1, 1), pa.random(-1, 1)));
                }
            }

            // Update particles
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update();
                if (p.isDead()) {
                    particles.remove(i);
                }
            }
        }

        // Display cube
        void display() {
            pa.pushMatrix();
            pa.translate(x, y, z); // Translate to the cube's position in 3D space
            pa.rotateX(rotation); // Apply rotation around the X axis
            pa.rotateY(rotation); // Apply rotation around the Y axis
            pa.fill(color);
            pa.stroke(255);
            pa.strokeWeight(1);
            float newSize = size + pulse; // Adjust size based on pulse
            pa.box(newSize); // Draw a cube shape

            // Display particles
            for (Particle p : particles) {
                p.display();
            }

            pa.popMatrix();
        }
    }

    // Particle class
    class Particle {
        PVector pos; // Position
        PVector vel; // Velocity
        int lifespan; // Lifespan

        Particle(float x, float y, float z, float vx, float vy, float vz) {
            pos = new PVector(x, y, z);
            vel = new PVector(vx, vy, vz);
            lifespan = 255;
        }

        void update() {
            pos.add(vel);
            lifespan -= 2;
        }

        void display() {
            pa.pushMatrix();
            pa.translate(pos.x, pos.y, pos.z);
            pa.noStroke();
            int alpha = PApplet.constrain(lifespan, 0, 255);
            pa.fill(255, alpha);
            pa.box(4);
            pa.popMatrix();
        }

        boolean isDead() {
            return lifespan < 0;
        }
    }
}
