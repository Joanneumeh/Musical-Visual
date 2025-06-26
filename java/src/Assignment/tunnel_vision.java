package Assignment;

import processing.core.PApplet;

public class tunnel_vision {
    Visualiser pa;
    float tunnelRadius;
    float tunnelLength;
    int numShapes = 50; // Number of shapes in the tunnel
    float[] shapeSizes; // Sizes of the shapes
    float[] shapeSpeeds; // Speeds of the shapes
    float[] shapePositions; // Positions of the shapes
    float[] shapeColors; // Colors of the shapes
    float[] spectrum;

    public tunnel_vision(Visualiser pa) {
        this.pa = pa;
        tunnelRadius = pa.width * 0.05f; 
        tunnelLength = pa.width * 2;
        shapeSizes = new float[numShapes];
        shapeSpeeds = new float[numShapes];
        shapePositions = new float[numShapes];
        shapeColors = new float[numShapes];
        spectrum = new float[pa.getAudioPlayer().bufferSize()];

        // Initialize shapes
        for (int i = 0; i < numShapes; i++) {
            shapeSizes[i] = pa.random(10, 30);
            shapeSpeeds[i] = pa.random(0.5f, 2.0f);
            shapePositions[i] = pa.random(0, PApplet.TWO_PI);
            shapeColors[i] = pa.random(0, 255);
        }
    }

    public void render() {
        pa.colorMode(PApplet.HSB, 360, 100, 100);
        pa.background(0); // Black background

        spectrum = pa.getAudioPlayer().left.toArray();

        // Update shapes
        for (int i = 0; i < numShapes; i++) {
            shapePositions[i] += shapeSpeeds[i] * 0.01; // Update shape position
            if (shapePositions[i] > PApplet.TWO_PI) {
                shapePositions[i] -= PApplet.TWO_PI;
            }
        }

        pa.translate(pa.width / 2, pa.height / 2);
        pa.noFill();
        pa.strokeWeight(2);
        pa.stroke(255);

        for (int i = 0; i < numShapes; i++) {
            float angle = PApplet.map(i, 0, numShapes, 0, PApplet.TWO_PI);
            float xOffset = PApplet.cos(angle) * tunnelRadius;
            float yOffset = PApplet.sin(angle) * tunnelRadius;

            for (float z = 0; z < tunnelLength; z += 10) {
                float hue = (shapeColors[i] + z) % 360;
                float shapeSize = PApplet.map(spectrum[i % spectrum.length], 0, 1, 5, 30);
                pa.stroke(hue, 100, 100);
                pa.pushMatrix();
                pa.translate(xOffset, yOffset, z - tunnelLength / 2); // Adjust z position
                pa.box(shapeSize);
                pa.popMatrix();
            }
        }
    }
}