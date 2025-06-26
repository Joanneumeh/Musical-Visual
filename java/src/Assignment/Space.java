package Assignment;

import processing.core.PApplet;

public class Space {
    Visualiser pa;

    float angle = 0;
    float radius = 100;
    float rotationSpeed = 0.05f;

    public Space(Visualiser pa) {
        this.pa = pa;
    }

    public void render() {
        pa.background(0);
        pa.colorMode(PApplet.RGB);
        pa.noStroke();

        // Centering the rotations
        pa.translate(pa.width / 2, pa.height / 2);

        for (int i = 0; i < pa.getAudioPlayer().bufferSize() - 1; i++) {
            float rotationX = PApplet.cos(angle) * PApplet.PI / 4;
            float rotationZ = PApplet.sin(angle) * PApplet.PI / 4;

            pa.rotateX(rotationX);
            pa.fill(255, 20, 147); // Dark pink
            float leftLevel = pa.getAudioPlayer().left.level() * 100;
            drawStar(i * 20, 0, leftLevel);

            pa.rotateZ(rotationZ);
            pa.fill(128, 0, 128); // Purple
            float rightLevel = pa.getAudioPlayer().right.level() * 100;
            drawStar(i * 20, 0, rightLevel);

            angle += rotationSpeed;
        }
    }

    // Star
    private void drawStar(float x, float y, float size) {
        float angleStep = PApplet.TWO_PI / 5;
        float outerRadius = size / 2;
        float innerRadius = size / 4;

        pa.beginShape();
        for (float angle = -PApplet.HALF_PI; angle < PApplet.TWO_PI - PApplet.HALF_PI; angle += angleStep) {
            float x1 = x + PApplet.cos(angle) * outerRadius;
            float y1 = y + PApplet.sin(angle) * outerRadius;
            pa.vertex(x1, y1);

            float x2 = x + PApplet.cos(angle + angleStep / 2) * innerRadius;
            float y2 = y + PApplet.sin(angle + angleStep / 2) * innerRadius;
            pa.vertex(x2, y2);
        }
        pa.endShape(PApplet.CLOSE);
    }
}
